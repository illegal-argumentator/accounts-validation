package com.daniel_tickets.accounts_validation.adapter.out.nst;

import com.daniel_tickets.accounts_validation.adapter.out.nst.dto.CreateProfileResponse;

public interface NstBrowserPort {

    CreateProfileResponse createProfile(String profileName, String proxyUrl);

}
