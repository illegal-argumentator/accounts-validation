package com.daniel_tickets.accounts_validation.adapter.out.browser;

public interface BrowserInitializer {

    LocalBrowser initBrowser();

    LocalBrowser initBrowserOverCdp(String profileId);

}
