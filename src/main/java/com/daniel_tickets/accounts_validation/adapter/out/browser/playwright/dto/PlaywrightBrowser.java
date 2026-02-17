package com.daniel_tickets.accounts_validation.adapter.out.browser.playwright.dto;

import com.daniel_tickets.accounts_validation.adapter.out.browser.LocalBrowser;
import com.daniel_tickets.accounts_validation.adapter.out.browser.playwright.exception.ElementNotFoundException;
import com.daniel_tickets.accounts_validation.adapter.out.browser.playwright.util.PlaywrightUtil;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.LoadState;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class PlaywrightBrowser implements LocalBrowser {

    private Page page;

    private List<AutoCloseable> autoCloseables;

    @Override
    public void navigate(String url) {
        page.navigate(url);
    }

    @Override
    public void waitForLoadState() {
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
    }

    @Override
    public void waitForSelector(String selector, double timeout, Runnable action) {
        Locator locator = page.locator(selector);
        boolean appeared;

        try {
            locator.waitFor(new Locator.WaitForOptions().setTimeout(timeout));
            appeared = locator.isVisible(new Locator.IsVisibleOptions().setTimeout(timeout));

            if (appeared) {
                action.run();;
            }
        } catch (PlaywrightException e) {
            throw new ElementNotFoundException("Element %s not found.".formatted(selector));
        }
    }

    @Override
    public void fill(String selector, String value) {
        page.fill(selector, value);
    }

    @Override
    public void click(String selector) {
        page.click(selector);
    }

    @Override
    public void clickRandomly(String selector) {
        Locator button = page.locator(selector);
        PlaywrightUtil.executeClick(button, page);
    }

    @Override
    public void close() {
        if (CollectionUtils.isEmpty(autoCloseables)) {
            return;
        }
        for (AutoCloseable autoCloseable : autoCloseables) {
            try {
                autoCloseable.close();;
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }
}
