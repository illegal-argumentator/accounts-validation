package com.daniel_tickets.accounts_validation.adapter.out.browser.puppeteer.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ruiyun.jvppeteer.api.core.ElementHandle;
import com.ruiyun.jvppeteer.api.core.Page;
import com.ruiyun.jvppeteer.cdp.entities.BoundingBox;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class PuppeteerUtil {

    private static final Random RANDOM = new Random();

    public static void executeClickJVppeteer(ElementHandle btn, Page page) {
        scrollIntoView(btn);
        try {
            // Перевіряємо target="_blank"
            Object targetObj = btn.evaluate("el => el.target");
            if ("_blank".equals(targetObj)) {
                btn.evaluate("el => el.removeAttribute('target')");
            }

            setHover(btn, page);

            BoundingBox boundingBox = btn.boundingBox();
            if (boundingBox == null) {
                btn.click();
                sleep(500, 500);
                return;
            }

            double offsetX = (RANDOM.nextDouble() - 0.5) * boundingBox.getWidth() * 0.3;
            double offsetY = (RANDOM.nextDouble() - 0.5) * boundingBox.getHeight() * 0.3;
            double targetX = boundingBox.getX() + boundingBox.getWidth() / 2 + offsetX;
            double targetY = boundingBox.getY() + boundingBox.getHeight() / 2 + offsetY;

            double startX = RANDOM.nextDouble() * 200 + 50;
            double startY = RANDOM.nextDouble() * 200 + 50;
            int steps = 12 + RANDOM.nextInt(8);

            double ctrlX1 = startX + (targetX - startX) * (0.3 + RANDOM.nextDouble() * 0.2);
            double ctrlY1 = startY + (targetY - startY) * (0.2 + RANDOM.nextDouble() * 0.3);
            double ctrlX2 = startX + (targetX - startX) * (0.6 + RANDOM.nextDouble() * 0.2);
            double ctrlY2 = startY + (targetY - startY) * (0.7 + RANDOM.nextDouble() * 0.2);

            for (int i = 1; i <= steps; i++) {
                double t = (double) i / steps;
                double u = 1 - t;
                double tt = t * t;
                double uu = u * u;
                double uuu = uu * u;
                double ttt = tt * t;

                double currentX = uuu * startX + 3 * uu * t * ctrlX1 + 3 * u * tt * ctrlX2 + ttt * targetX;
                double currentY = uuu * startY + 3 * uu * t * ctrlY1 + 3 * u * tt * ctrlY2 + ttt * targetY;

                currentX += (RANDOM.nextDouble() - 0.5) * 2;
                currentY += (RANDOM.nextDouble() - 0.5) * 2;

                page.mouse().move(currentX, currentY);

                if (i < 3 || i > steps - 3) {
                    sleep(25, 15);
                } else {
                    sleep(12, 8);
                }
            }

            sleep(15, 25);
            page.mouse().down();
            sleep(80, 90);
            page.mouse().up();
            sleep(300, 400);

        } catch (Exception e) {
            log.debug("Advanced click failed, using fallback", e);
            try {
                btn.click();
                sleep(500, 500);
            } catch (Exception ex) {
                try {
                    btn.evaluate("el => el.dispatchEvent(new MouseEvent('click', {bubbles: true, cancelable: true}))");
                } catch (JsonProcessingException exc) {
                    throw new RuntimeException(exc);
                }
            }
        }
    }

    private static void setHover(ElementHandle element, Page page) {
        try {
            element.evaluate("el => el.scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'})");
            sleep(150, 100);

            BoundingBox box = element.boundingBox();
            if (box == null) return;

            double offsetX = (RANDOM.nextDouble() - 0.5) * box.getWidth() * 0.2;
            double offsetY = (RANDOM.nextDouble() - 0.5) * box.getHeight() * 0.2;
            double targetX = box.getX() + box.getWidth() / 2 + offsetX;
            double targetY = box.getY() + box.getHeight() / 2 + offsetY;

            double startX = RANDOM.nextDouble() * 150 + 50;
            double startY = RANDOM.nextDouble() * 150 + 50;

            int steps = 10 + RANDOM.nextInt(6);
            double ctrlX1 = startX + (targetX - startX) * (0.25 + RANDOM.nextDouble() * 0.2);
            double ctrlY1 = startY + (targetY - startY) * (0.2 + RANDOM.nextDouble() * 0.25);
            double ctrlX2 = startX + (targetX - startX) * (0.65 + RANDOM.nextDouble() * 0.15);
            double ctrlY2 = startY + (targetY - startY) * (0.75 + RANDOM.nextDouble() * 0.15);

            for (int i = 1; i <= steps; i++) {
                double t = (double) i / steps;
                double u = 1 - t;
                double tt = t * t;
                double uu = u * u;

                double currentX = uu * u * startX + 3 * uu * t * ctrlX1 + 3 * u * tt * ctrlX2 + tt * t * targetX;
                double currentY = uu * u * startY + 3 * uu * t * ctrlY1 + 3 * u * tt * ctrlY2 + tt * t * targetY;

                currentX += (RANDOM.nextDouble() - 0.5) * 1.5;
                currentY += (RANDOM.nextDouble() - 0.5) * 1.5;

                page.mouse().move(currentX, currentY);
                sleep(18, 12);
            }

            sleep(150, 150);

        } catch (Exception e) {
            log.debug("Failed to set hover", e);
        }
    }

    private static void scrollIntoView(ElementHandle btn) {
        try {
            btn.evaluate("el => { if (el && typeof el.scrollIntoViewIfNeeded === 'function') el.scrollIntoViewIfNeeded(); else if (el) el.scrollIntoView({behavior: 'smooth', block: 'center'}); }");
            Thread.sleep(500);
        } catch (Exception e) {
            log.debug("Failed to scroll into view", e);
        }
    }

    private static void sleep(long base, long random) {
        try {
            Thread.sleep(base + (long) (RANDOM.nextDouble() * random));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
