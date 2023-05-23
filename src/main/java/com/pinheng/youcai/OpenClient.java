package com.pinheng.youcai;

import com.pinheng.youcai.handler.DataResponseHandler;
import com.pinheng.youcai.handler.OpenLogic;
import com.pinheng.youcai.model.GrantAccessToken;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <a href="https://b2b.you.163.com/distribution/openapi#/home">API说明</a>
 */
public class OpenClient {

    /**
     * 目标环境要么是 https://b2b.test.you.163.com/ 要么 https://b2b.you.163.com/
     */
    @SuppressWarnings("JavadocLinkAsPlainText")
    private String targetEndpoint;

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

    /**
     * 获取token
     *
     * @param key    应用key
     * @param secret 应用secret
     * @return 执行结果
     * @throws OpenException 协议异常
     * @throws IOException   读写异常
     */
    public GrantAccessToken grantAccessToken(String key, String secret) throws OpenException, IOException {
        val map = new HashMap<String, String>();
        map.put("grant_type", "access_token");
        map.put("app_key", key);
        map.put("app_secret", secret);
        map.put("timestamp", timestampFormatter.format(LocalDateTime.now()));
        return submitUrlEncoded("distribution/auth/open/oauth2/accessToken", map, data -> {
            val reader = DataResponseHandler.objectMapper.readerFor(GrantAccessToken.class);
            return reader.readValue(data);
        });
    }

    private <T> T submitUrlEncoded(String uri, Map<String, String> data, OpenLogic<T> logic) throws IOException {
        try (val client = createHttpClient()) {
            val method = new HttpPost(targetEndpoint + uri);
            val entity = EntityBuilder.create()
                    .setContentType(ContentType.APPLICATION_FORM_URLENCODED)
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

    /**
     * 刷新token
     *
     * @param key          应用key
     * @param refreshToken {@link GrantAccessToken#getRefreshToken()}
     * @return 执行结果
     * @throws OpenException 协议异常
     * @throws IOException   读写异常
     */
    public GrantAccessToken refreshAccessToken(String key, String refreshToken) throws OpenException, IOException {
        throw new NoSuchMethodError("");
    }

    /**
     * 执行对应api并且返回业务结果
     *
     * @param key        应用key
     * @param token      {@link GrantAccessToken#getAccessToken()}
     * @param method     业务方法，详见：<a href="https://b2b.you.163.com/distribution/openapi#/home">API说明</a>
     * @param v          api版本，详见：<a href="https://b2b.you.163.com/distribution/openapi#/home">API说明</a>
     * @param parameters 业务参数，详见：<a href="https://b2b.you.163.com/distribution/openapi#/home">API说明</a>
     * @return 业务结果
     * @throws OpenException 协议异常
     * @throws IOException   读写异常
     */
    public Object executeApi(String key, String token, String method, String v, Object parameters) throws OpenException, IOException {
        throw new NoSuchMethodError("");
    }
}
