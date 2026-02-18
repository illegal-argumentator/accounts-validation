package com.daniel_tickets.accounts_validation.adapter.out.fifa;

import com.daniel_tickets.accounts_validation.adapter.out.browser.LocalBrowser;
import com.daniel_tickets.accounts_validation.infrastructure.util.WaitUtil;
import org.springframework.stereotype.Service;

import static com.daniel_tickets.accounts_validation.adapter.out.fifa.constants.FifaSelectors.*;

@Service
public class FifaTicketVerificationService {

    boolean verify(LocalBrowser localBrowser) {
        WaitUtil.waitRandomlyInRange(2500, 3000);
        localBrowser.click(TICKETS_AND_HOSPITALITY_DIV);

        WaitUtil.waitRandomlyInRange(1200, 2000);
        localBrowser.waitForSelector(EXPLORE_DETAILS_DIV, 10_000, () -> localBrowser.click(EXPLORE_DETAILS_DIV));

        System.out.println("learn more");
        WaitUtil.waitRandomlyInRange(1200, 2000);
        localBrowser.waitForSelector(LEARN_MORE_DIV, 10_000, () -> localBrowser.click(LEARN_MORE_DIV));

        System.out.println("after learn more");
        WaitUtil.waitRandomlyInRange(1200, 2000);
        localBrowser.waitForSelector(OTP_INPUT, 10_000, () -> localBrowser.fill(OTP_INPUT, "Hello"));
        return false;
    }

}
