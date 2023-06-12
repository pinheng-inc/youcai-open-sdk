package com.netease.youcai;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;


class SignUtilsTest {

    @Test
    void sign() {
        val data = new HashMap<String, String>();
        data.put("app_key", "9u5fwqu6");
        data.put("method", "yanxuan.product.stock");
        data.put("param_json", "\"100004163001,100004165001,200006518004\"");
        data.put("timestamp", "2023-06-02 10:35:05");
        data.put("v", "1.0.0");

        assertThat(SignUtils.sign(data, "a28a53e7f2deb3cb9a038d95d1600827"))
                .isEqualTo("8E2A3A6E19AA7CE952DCFF7A777ADBB2");
    }

}