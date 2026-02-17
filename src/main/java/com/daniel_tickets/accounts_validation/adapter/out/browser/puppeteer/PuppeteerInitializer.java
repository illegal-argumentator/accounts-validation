package com.daniel_tickets.accounts_validation.adapter.out.browser.puppeteer;

import com.daniel_tickets.accounts_validation.adapter.out.browser.BrowserInitializer;
import com.daniel_tickets.accounts_validation.adapter.out.browser.LocalBrowser;
import com.daniel_tickets.accounts_validation.adapter.out.browser.puppeteer.dto.PuppeteerBrowser;
import com.ruiyun.jvppeteer.api.core.Browser;
import com.ruiyun.jvppeteer.api.core.Page;
import com.ruiyun.jvppeteer.cdp.core.Puppeteer;
import com.ruiyun.jvppeteer.cdp.entities.LaunchOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

//@Component
public class PuppeteerInitializer implements BrowserInitializer {

    @Value("${browser.headless}")
    private boolean BROWSER_HEADLESS;

    @Override
    public LocalBrowser initBrowser() {
        try {
            Browser browser = Puppeteer.launch(LaunchOptions.builder().headless(BROWSER_HEADLESS).build());
            Page page = browser.newPage();
            return new PuppeteerBrowser(page, List.of(browser));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LocalBrowser initBrowserOverCdp(String profileId) {
        return null;
    }

}
