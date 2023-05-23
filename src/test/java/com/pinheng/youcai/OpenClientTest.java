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

        val token2 = client.refreshAccessToken("9u5fwqu6", token.getRefreshToken());
        assertThat(token2)
                .as("通过测试环境肯定可以成功刷新token")
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

        val listAllSpuResult
                = client.executeApi("9u5fwqu6", "", token2.getAccessToken()
                , "yanxuan.product.listAllSpu", "1.0.0", null);

        assertThat(listAllSpuResult)
                .as("切不管这个测试用户是否拥有商品，但是必须得返回")
                .isNotNull()
                .satisfies(it -> log.info(it.toString()));

    }

}