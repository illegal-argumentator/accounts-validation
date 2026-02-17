package com.daniel_tickets.accounts_validation.adapter.out.browser;

public interface LocalBrowser extends AutoCloseable {

    void navigate(String url);

    void waitForLoadState();

    void waitForSelector(String selector, double timeout, Runnable action);

    void fill(String selector, String value);

    void click(String selector);

    void clickRandomly(String selector);

}
