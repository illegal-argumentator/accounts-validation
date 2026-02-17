package com.daniel_tickets.accounts_validation.infrastructure.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WaitUtil {

    public static void waitRandomlyInRange(long from, long to) {
        waitSafely((long) (from + Math.random() * (to - from)));
    }

    public static void waitSafely(long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            log.error("Error waiting ", e);
            Thread.currentThread().interrupt();
        }
    }

}
