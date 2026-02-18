package com.daniel_tickets.accounts_validation.adapter.out.fifa;

import com.daniel_tickets.accounts_validation.adapter.out.browser.LocalBrowser;
import com.daniel_tickets.accounts_validation.domain.user.model.User;
import com.daniel_tickets.accounts_validation.infrastructure.util.WaitUtil;
import org.springframework.stereotype.Component;

import static com.daniel_tickets.accounts_validation.adapter.out.fifa.constants.FifaSelectors.*;

@Component
public class FifaBrowserAuthService {

    public void login(User user, LocalBrowser localBrowser) {
        localBrowser.waitForLoadState();

        localBrowser.waitForSelector(COOKIE_ACCEPT_BUTTON, 20_000, () -> localBrowser.click(COOKIE_ACCEPT_BUTTON));

        WaitUtil.waitRandomlyInRange(2500, 3000);
        localBrowser.fill(EMAIL_INPUT, user.email());
        WaitUtil.waitRandomlyInRange(1200, 1500);
        localBrowser.fill(PASSWORD_INPUT, user.password());
        WaitUtil.waitRandomlyInRange(1200, 1500);
        localBrowser.clickRandomly(SUBMIT_BUTTON);

        localBrowser.waitForSelector(POP_UP_CLOSE_BUTTON, 15_000, () -> localBrowser.click(POP_UP_CLOSE_BUTTON));
    }

}
