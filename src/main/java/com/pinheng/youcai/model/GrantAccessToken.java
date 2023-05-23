package com.pinheng.youcai.model;

import lombok.Data;

@Data
public class GrantAccessToken {
    private String accessToken;
    private String refreshToken;
    /**
     * 单位秒
     */
    private int expiresIn;
    private long refreshTokenExpires;
}
