package com.netease.youcai.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@SuppressWarnings("unused")
public class GrantAccessToken {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    /**
     * 单位秒
     */
    @JsonProperty("expires_in")
    private int expiresIn;
    @JsonProperty("refresh_token_expires")
    private long refreshTokenExpires;

    @Override
    public String toString() {
        return "GrantAccessToken{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expiresIn=" + expiresIn +
                ", refreshTokenExpires=" + refreshTokenExpires +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GrantAccessToken that = (GrantAccessToken) o;
        return expiresIn == that.expiresIn && refreshTokenExpires == that.refreshTokenExpires && Objects.equals(accessToken, that.accessToken) && Objects.equals(refreshToken, that.refreshToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessToken, refreshToken, expiresIn, refreshTokenExpires);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public long getRefreshTokenExpires() {
        return refreshTokenExpires;
    }

    public void setRefreshTokenExpires(long refreshTokenExpires) {
        this.refreshTokenExpires = refreshTokenExpires;
    }
}
