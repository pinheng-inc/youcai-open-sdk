package com.pinheng.youcai.model;


import java.util.Objects;

public class GrantAccessToken {
    private String accessToken;
    private String refreshToken;
    /**
     * 单位秒
     */
    private int expiresIn;
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
