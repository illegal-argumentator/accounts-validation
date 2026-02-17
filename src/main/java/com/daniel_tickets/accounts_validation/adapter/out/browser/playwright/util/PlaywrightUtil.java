package com.daniel_tickets.accounts_validation.adapter.out.browser.playwright.util;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

import static java.lang.Thread.sleep;

@Slf4j
public class PlaywrightUtil {

    private static final Random RANDOM = new Random();

    public static void executeClick(Locator btn, Page page)  {

        scrollIntoView(btn);
        String target = (String) btn.evaluate("el => el.target");
        if ("_blank".equals(target)) {
            btn.evaluate("el => el.removeAttribute('target')");
        }

        setHover(btn, page);

        try {
            var boundingBox = btn.boundingBox();
            if (boundingBox == null) {
                btn.click();
                sleep(500, 500);
                return;
            }

            double offsetX = (RANDOM.nextDouble() - 0.5) * boundingBox.width * 0.3; // ±30% от центра
            double offsetY = (RANDOM.nextDouble() - 0.5) * boundingBox.height * 0.3;
            double targetX = boundingBox.x + boundingBox.width / 2 + offsetX;
            double targetY = boundingBox.y + boundingBox.height / 2 + offsetY;

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

                double currentX = uuu * startX +
                        3 * uu * t * ctrlX1 +
                        3 * u * tt * ctrlX2 +
                        ttt * targetX;
                double currentY = uuu * startY +
                        3 * uu * t * ctrlY1 +
                        3 * u * tt * ctrlY2 +
                        ttt * targetY;

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
                btn.evaluate("el => el.dispatchEvent(new MouseEvent('click', {bubbles: true, cancelable: true}))");
            }
        }
    }

    private static void setHover(Locator element, Page page) {
        try {
            element.evaluate("el => el.scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'})");
            sleep(150, 100);

            var boundingBox = element.boundingBox();
            if (boundingBox == null) {
                return;
            }
            double offsetX = (RANDOM.nextDouble() - 0.5) * boundingBox.width * 0.2;
            double offsetY = (RANDOM.nextDouble() - 0.5) * boundingBox.height * 0.2;
            double targetX = boundingBox.x + boundingBox.width / 2 + offsetX;
            double targetY = boundingBox.y + boundingBox.height / 2 + offsetY;

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

                double currentX = uu * u * startX +
                        3 * uu * t * ctrlX1 +
                        3 * u * tt * ctrlX2 +
                        tt * t * targetX;
                double currentY = uu * u * startY +
                        3 * uu * t * ctrlY1 +
                        3 * u * tt * ctrlY2 +
                        tt * t * targetY;

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

    private static void scrollIntoView(Locator btn) {
        try {
            btn.evaluate(
                    "el => { if (el && typeof el.scrollIntoViewIfNeeded === 'function') el.scrollIntoViewIfNeeded(); else if (el) el.scrollIntoView({behavior: 'smooth', block: 'center'}); }"
            );
            sleep(500);
        } catch (Exception e) {
            log.debug("Failed to scroll into view", e);
        }
    }

}
