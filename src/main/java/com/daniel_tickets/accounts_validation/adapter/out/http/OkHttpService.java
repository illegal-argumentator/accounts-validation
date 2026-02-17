package com.daniel_tickets.accounts_validation.adapter.out.http;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
            String responseBody = response.body().string();
            if (!response.isSuccessful()) {
                throw new RuntimeException("response: " + responseBody + ", code: " + response.code());
            }
            return responseBody;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
