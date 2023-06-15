package com.netease.youcai;

import com.netease.youcai.handler.DataResponseHandler;
import com.netease.youcai.handler.OpenLogic;
import com.netease.youcai.model.GrantAccessToken;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * <a href="https://b2b.you.163.com/distribution/openapi#/home">API说明</a>
 */
@Slf4j
public class OpenClient {

    /**
     * 目标环境要么是 https://b2b.test.you.163.com/ 要么 https://b2b.you.163.com/
     */
    @SuppressWarnings("JavadocLinkAsPlainText")
    private String targetEndpoint;
    /**
     * 应用key
     */
    @Setter
    private String key;
    /**
     * 应用secret
     */
    @Setter
    private String secret;

    private final DateTimeFormatter timestampFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static CloseableHttpClient createHttpClient() {
        return HttpClientBuilder
                .create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectionRequestTimeout(500)
                        .setConnectTimeout(5000)
                        .setSocketTimeout(5000)
                        .build())
                .setDnsResolver(s -> {
                    if (Objects.equals(s, "b2b.test.you.163.com")) {
                        return InetAddress.getAllByName("121.40.143.36");
                    }
                    return SystemDefaultDnsResolver.INSTANCE.resolve(s);
                })
//                .setSSLHostnameVerifier(new HostnameVerifier() {
//                    @Override
//                    public boolean verify(String s, SSLSession sslSession) {
//                        return false;
//                    }
//                })
                .build();
    }

    public void setTargetEndpoint(String targetEndpoint) {
        this.targetEndpoint = targetEndpoint;
        if (this.targetEndpoint != null && !this.targetEndpoint.endsWith("/")) {
            this.targetEndpoint = this.targetEndpoint + "/";
        }
    }

    private static ZonedDateTime timestampForChina() {
        return ZonedDateTime.now(ZoneId.of(ZoneId.SHORT_IDS.get("CTT")));
    }

    /**
     * 获取token
     *
     * @return 执行结果
     * @throws OpenException 协议异常
     * @throws IOException   读写异常
     */
    public GrantAccessToken grantAccessToken() throws OpenException, IOException {
        val map = new HashMap<String, String>();
        map.put("grant_type", "access_token");
        map.put("app_key", key);
        map.put("app_secret", secret);
        map.put("timestamp", generateTimestamp());
        return submitUrlEncoded("distribution/auth/open/oauth2/accessToken", map, data -> {
            val reader = DataResponseHandler.objectMapper.readerFor(GrantAccessToken.class);
            return reader.readValue(data);
        }, null);
    }

    /**
     * 刷新token
     *
     * @param refreshToken {@link GrantAccessToken#getRefreshToken()}
     * @return 执行结果
     * @throws OpenException 协议异常
     * @throws IOException   读写异常
     */
    public GrantAccessToken refreshAccessToken(String refreshToken) throws OpenException, IOException {
        val map = new HashMap<String, String>();
        map.put("grant_type", "refresh_token");
        map.put("app_key", key);
        map.put("refresh_token", refreshToken);
        map.put("timestamp", generateTimestamp());
        return submitUrlEncoded("distribution/auth/open/oauth2/accessToken", map, data -> {
            val reader = DataResponseHandler.objectMapper.readerFor(GrantAccessToken.class);
            return reader.readValue(data);
        }, null);
    }

    /**
     * 执行对应api并且返回业务结果
     *
     * @param token      {@link GrantAccessToken#getAccessToken()}
     * @param method     业务方法，详见：<a href="https://b2b.you.163.com/distribution/openapi#/home">API说明</a>
     * @param v          api版本，详见：<a href="https://b2b.you.163.com/distribution/openapi#/home">API说明</a>
     * @param parameters 业务参数，详见：<a href="https://b2b.you.163.com/distribution/openapi#/home">API说明</a>
     * @return 业务结果
     * @throws OpenException 协议异常
     * @throws IOException   读写异常
     */
    public Object executeApi(String token, String method, String v, Object parameters) throws OpenException, IOException {
        log.debug("method:" + method + ",parameters:" + parameters);
        val map = new HashMap<String, String>();
        map.put("method", method);
        map.put("app_key", key);
        map.put("v", v);
        map.put("timestamp", generateTimestamp());
        map.put("param_json", DataResponseHandler.objectMapper.writeValueAsString(parameters));
        val sign = SignUtils.sign(map, secret);
        map.put("sign", sign);

        return submitUrlEncoded("distribution/open/api/entry", map, it -> it, it -> it.addHeader("Authorization", "Bearer " + token));
    }

    private String generateTimestamp() {
        return timestampFormatter.format(timestampForChina());
    }

    private <T> T submitUrlEncoded(String uri, Map<String, String> data, OpenLogic<T> logic
            , Consumer<HttpPost> beforeEntity) throws IOException {
        try (val client = createHttpClient()) {
            val method = new HttpPost(targetEndpoint + uri);
            if (beforeEntity != null) {
                beforeEntity.accept(method);
            }
            val entity = EntityBuilder.create()
                    .setContentType(ContentType.APPLICATION_FORM_URLENCODED.withCharset(StandardCharsets.UTF_8))
                    .setParameters(
                            data.entrySet().stream()
                                    .map(it -> new BasicNameValuePair(it.getKey(), it.getValue()))
                                    .collect(Collectors.toList())
                    )
                    .build();

            method.setEntity(entity);
            val response = client.execute(method, new DataResponseHandler());
            return logic.executeLogic(response);
        }

    }
}
