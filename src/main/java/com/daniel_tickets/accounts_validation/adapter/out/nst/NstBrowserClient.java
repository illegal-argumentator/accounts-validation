package com.daniel_tickets.accounts_validation.adapter.out.nst;

import com.daniel_tickets.accounts_validation.adapter.out.http.OkHttpService;
import com.daniel_tickets.accounts_validation.adapter.out.nst.dto.CreateProfileRequest;
import com.daniel_tickets.accounts_validation.adapter.out.nst.dto.CreateProfileResponse;
import com.daniel_tickets.accounts_validation.adapter.out.nst.dto.builder.CreateProfileRequestBuilder;
import com.daniel_tickets.accounts_validation.adapter.out.nst.props.NstProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Service
@RequiredArgsConstructor
public class NstBrowserClient implements NstBrowserPort {

    private final ObjectMapper objectMapper;

    private final OkHttpService okHttpService;

    private final NstProperties nstProperties;

    @Override
    public CreateProfileResponse createProfile(String profileName, String proxyUrl) {
        CreateProfileRequest createProfileRequest = CreateProfileRequestBuilder.buildCreateProfileRequest(
                profileName,
                proxyUrl,
                nstProperties.getGroupId()
        );

        String json = objectMapper.writeValueAsString(createProfileRequest);

        MediaType mediaType = MediaType.parse(APPLICATION_JSON_VALUE);
        RequestBody body = RequestBody.create(mediaType, json);

        Request request = new Request.Builder()
                .url(nstProperties.getUrl() + "/profiles")
                .method("POST", body)
                .addHeader("Content-Type", APPLICATION_JSON_VALUE)
                .addHeader("x-api-key", nstProperties.getApiKey())
                .build();

        return okHttpService.handleApiRequest(request, CreateProfileResponse.class);

    }
}
