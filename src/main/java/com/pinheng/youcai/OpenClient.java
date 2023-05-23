package com.pinheng.youcai;

import com.pinheng.youcai.model.GrantAccessToken;

import java.io.IOException;

/**
 * <a href="https://b2b.you.163.com/distribution/openapi#/home">API说明</a>
 */
public class OpenClient {

    /**
     * 目标环境要么是 https://b2b.test.you.163.com/ 要么 https://b2b.you.163.com/
     */
    @SuppressWarnings("JavadocLinkAsPlainText")
    private String targetEndpoint;

    public void setTargetEndpoint(String targetEndpoint) {
        this.targetEndpoint = targetEndpoint;
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
        throw new NoSuchMethodError("");
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
     * @param key 应用key
     * @param token {@link GrantAccessToken#getAccessToken()}
     * @param method 业务方法，详见：<a href="https://b2b.you.163.com/distribution/openapi#/home">API说明</a>
     * @param v api版本，详见：<a href="https://b2b.you.163.com/distribution/openapi#/home">API说明</a>
     * @param parameters 业务参数，详见：<a href="https://b2b.you.163.com/distribution/openapi#/home">API说明</a>
     * @return 业务结果
     * @throws OpenException 协议异常
     * @throws IOException 读写异常
     */
    public Object executeApi(String key, String token, String method, String v, Object parameters) throws OpenException, IOException {
        throw new NoSuchMethodError("");
    }
}
