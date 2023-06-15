package com.netease.youcai.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netease.youcai.OpenException;
import lombok.val;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * 结果值有可能是{@link JsonNode}也有可能是{@link String}
 */
public class DataResponseHandler implements ResponseHandler<JsonNode> {

    public static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public JsonNode handleResponse(HttpResponse response) throws IOException {
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new IOException("target server response " + response.getStatusLine());
        }
        if (!response.containsHeader("Content-Type")) {
            throw new OpenException(0, "server did not response Content-Type");
        }
//        val ct = response.getFirstHeader("Content-Type").getValue();
//        实际上我们没遇到了 text/plain;charset=ISO-8859-1
//        而且内容是 code.. data ok:true
        byte[] data = EntityUtils.toByteArray(response.getEntity());
        try {
            try {
                val root = objectMapper.readTree(data);
                val code = root.get("code");
                if (code == null || !code.isNumber())
                    throw new OpenException(1, "response bad data(without code)");

                if (code.intValue() != 2000) {
                    val message = root.get("message");
                    throw new OpenException(code.intValue()
                            , (message != null && message.isTextual()) ? message.textValue()
                            : "response without message.");
                }

                return root.get("data");

            } catch (IOException ex) {
                throw new OpenException(1, "parse json error", ex);
            }
        } finally {
            EntityUtils.consumeQuietly(response.getEntity());
        }
    }
}
