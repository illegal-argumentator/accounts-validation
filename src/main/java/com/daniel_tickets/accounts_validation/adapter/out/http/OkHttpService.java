package com.daniel_tickets.accounts_validation.adapter.out.http;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class OkHttpService {

    private final ObjectMapper objectMapper;

    private final OkHttpClient okHttpClient;

    public <T> T handleApiRequest(Request request, Class<T> responseTarget) {
        return objectMapper.readValue(handleApiRequest(request), responseTarget);
    }

    public String handleApiRequest(Request request) {
        try (Response response = okHttpClient.newCall(request).execute()) {

            ResponseBody body = response.body();
            String responseBody = body != null ? body.string() : "";

            if (!response.isSuccessful()) {
                throw new RuntimeException(
                        "API error. Code: " + response.code() + ", Body: " + responseBody
                );
            }

            return responseBody;

        } catch (IOException e) {
            throw new RuntimeException("Failed to execute API request", e);
        }
    }

}
