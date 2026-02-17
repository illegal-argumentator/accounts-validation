package com.daniel_tickets.accounts_validation.adapter.out.nst.dto.builder;

import com.daniel_tickets.accounts_validation.adapter.out.nst.dto.CreateProfileRequest;

public class CreateProfileRequestBuilder {

    public static CreateProfileRequest buildCreateProfileRequest(String profileName, String proxyUrl, String groupId) {
        CreateProfileRequest request = new CreateProfileRequest();
        request.setName(profileName);
        request.setPlatform("Windows");
        request.setProxy(proxyUrl);
        request.setGroupId(groupId);

        return request;
    }
}
