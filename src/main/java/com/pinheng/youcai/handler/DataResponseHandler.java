package com.pinheng.youcai.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinheng.youcai.OpenException;
import lombok.val;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Objects;

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
        val ct = response.getFirstHeader("Content-Type").getValue();
        if (!Objects.equals(ContentType.APPLICATION_JSON.getMimeType(), ContentType.parse(ct).getMimeType())) {
            throw new OpenException(0, "server responded Content-Type:" + ct);
        }

        byte[] data = EntityUtils.toByteArray(response.getEntity());
        try {
            try {
                val root = objectMapper.readTree(data);
                val code = root.get("code");
                if (code == null || !code.isNumber())
                    throw new OpenException(1, "response bad data(without code)");
                val message = root.get("message");
                if (message == null || !message.isTextual())
                    throw new OpenException(1, "response bad data(without message)");

                return root.get("data");

            } catch (IOException ex) {
                throw new OpenException(1, "parse json error", ex);
            }
        } finally {
            EntityUtils.consumeQuietly(response.getEntity());
        }
    }
}
