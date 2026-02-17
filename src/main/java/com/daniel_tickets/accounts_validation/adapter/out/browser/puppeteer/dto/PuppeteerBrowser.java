package com.daniel_tickets.accounts_validation.adapter.out.browser.puppeteer.dto;

import com.daniel_tickets.accounts_validation.adapter.out.browser.LocalBrowser;
import com.daniel_tickets.accounts_validation.adapter.out.browser.playwright.exception.ElementNotFoundException;
import com.daniel_tickets.accounts_validation.adapter.out.browser.puppeteer.util.PuppeteerUtil;
import com.ruiyun.jvppeteer.api.core.ElementHandle;
import com.ruiyun.jvppeteer.api.core.Page;
import com.ruiyun.jvppeteer.cdp.entities.WaitForSelectorOptions;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@AllArgsConstructor
public class PuppeteerBrowser implements LocalBrowser {

    private Page page;

    private List<AutoCloseable> autoCloseables;

    @Override
    public void navigate(String url) {
        try {
            page.goTo(url);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void waitForLoadState() {
        try {
            page.waitForNavigation(); // чекає завантаження сторінки
        } catch (Exception e) {
            log.error("Page load failed: {}", e.getMessage());
        }
    }

    @Override
    public void waitForSelector(String selector, double timeout, Runnable action) {
        try {
            WaitForSelectorOptions options = new WaitForSelectorOptions();
            options.setTimeout((int) timeout);
            ElementHandle handle = page.waitForSelector(selector, options);
            if (handle != null) {
                action.run();
            } else {
                throw new ElementNotFoundException("Element %s not found.".formatted(selector));
            }
        } catch (Exception e) {
            throw new ElementNotFoundException("Element %s not found.".formatted(selector));
        }
    }

    @Override
    public void fill(String selector, String value) {
        try {
            ElementHandle handle = page.$(selector);
            if (handle != null) {
                handle.type(value);
            } else {
                throw new ElementNotFoundException("Element %s not found.".formatted(selector));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void click(String selector) {
        try {
            ElementHandle handle = page.$(selector);
            if (handle != null) {
                handle.click();
            } else {
                throw new ElementNotFoundException("Element %s not found.".formatted(selector));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clickRandomly(String selector) {
        try {
            ElementHandle handle = page.$(selector);
            if (handle != null) {
                PuppeteerUtil.executeClickJVppeteer(handle, page);
            } else {
                throw new ElementNotFoundException("Element %s not found.".formatted(selector));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        if (CollectionUtils.isEmpty(autoCloseables)) {
            return;
        }
        for (AutoCloseable autoCloseable : autoCloseables) {
            try {
                autoCloseable.close();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }
}
