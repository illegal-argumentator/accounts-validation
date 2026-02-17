package com.daniel_tickets.accounts_validation.adapter.out.browser.playwright;

import com.daniel_tickets.accounts_validation.adapter.out.browser.BrowserInitializer;
import com.daniel_tickets.accounts_validation.adapter.out.browser.LocalBrowser;
import com.daniel_tickets.accounts_validation.adapter.out.browser.playwright.dto.PlaywrightBrowser;
import com.daniel_tickets.accounts_validation.adapter.out.nst.props.NstProperties;
import com.microsoft.playwright.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.lang.Boolean.TRUE;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlaywrightInitializer implements BrowserInitializer {

    @Value("${browser.headless}")
    private boolean BROWSER_HEADLESS;

    private final NstProperties nstProperties;

    private final static String NST_BROWSER_URL_TEMPLATE = "ws://%s:%s/devtool/launch/%s?x-api-key=%s&config=%%7B%%22headless%%22%%3A%s%%2C%%22autoClose%%22%%3A%s%%7D";

    @Override
    public LocalBrowser initBrowser() {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(BROWSER_HEADLESS));
        Page page = browser.newPage();

        return new PlaywrightBrowser(page, List.of(playwright, browser, page));
    }

    @Override
    public LocalBrowser initBrowserOverCdp(String profileId) {
        try {
            String url = String.format(
                    NST_BROWSER_URL_TEMPLATE,
                    nstProperties.getHost(),
                    nstProperties.getPort(),
                    profileId,
                    nstProperties.getApiKey(),
                    BROWSER_HEADLESS,
                    TRUE
            );

            log.info("Init playwright url: {}", url);

            return initPlaywrightWithCdp(url);
        } catch (PlaywrightException e) {
            log.error("Error connecting to Nst Browser: {}", e.getMessage());
            throw new RuntimeException("Error connecting to Nst Browser. Possibly daily quote reached");
        }
    }

    private LocalBrowser initPlaywrightWithCdp(String cdpUrl) throws PlaywrightException {
        Playwright playwright = Playwright.create();

        Browser browser = playwright.chromium().connectOverCDP(cdpUrl);
        BrowserContext context = browser.contexts().get(0);
        Page page = context.pages().get(0);
        page.bringToFront();

        return new PlaywrightBrowser(page, List.of(page, context, browser, playwright));
    }

}
