package com.netease.youcai;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Slf4j
public class SignUtils {
    @SneakyThrows(java.security.NoSuchAlgorithmException.class)
    public static String sign(Map<String, String> map, String secret) {
        val afterJoin = new TreeMap<>(map)
                .entrySet()
                .stream().map(it -> it.getKey() + it.getValue())
                .collect(Collectors.joining(""));
        log.debug("步骤12 拼接后:" + afterJoin);

        val aj2 = secret + afterJoin + secret;

        val md5 = MessageDigest.getInstance("md5").digest(aj2.getBytes(StandardCharsets.UTF_8));

        val sign = Hex.encodeHexString(md5).toUpperCase(Locale.ROOT);

        log.debug("步骤34后:" + sign);

        return sign;
    }
}
