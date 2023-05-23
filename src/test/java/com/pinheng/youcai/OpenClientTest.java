package com.pinheng.youcai;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


@SuppressWarnings("HttpUrlsUsage")
@Slf4j
class OpenClientTest {

    @Test
    void dummy() throws OpenException, IOException {
        OpenClient client = new OpenClient();
        client.setTargetEndpoint("http://b2b.test.you.163.com/");
        val token = client.grantAccessToken("9u5fwqu6", "a28a53e7f2deb3cb9a038d95d1600827");
        assertThat(token)
                .as("通过测试环境肯定可以成功获取token")
                .satisfies(it -> {
                    assertThat(it.getAccessToken())
                            .isNotNull();
                    assertThat(it.getRefreshToken())
                            .isNotNull();
                    assertThat(it.getExpiresIn())
                            .isGreaterThan(0);
                    assertThat(it.getRefreshTokenExpires())
                            .isGreaterThan(0);
                });
    }

}