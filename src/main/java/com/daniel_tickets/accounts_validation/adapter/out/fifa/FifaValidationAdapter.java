package com.daniel_tickets.accounts_validation.adapter.out.fifa;

import com.daniel_tickets.accounts_validation.adapter.out.browser.BrowserInitializer;
import com.daniel_tickets.accounts_validation.adapter.out.browser.LocalBrowser;
import com.daniel_tickets.accounts_validation.adapter.out.nst.NstBrowserPort;
import com.daniel_tickets.accounts_validation.adapter.out.nst.dto.CreateProfileResponse;
import com.daniel_tickets.accounts_validation.domain.proxy.Proxy;
import com.daniel_tickets.accounts_validation.domain.user.model.User;
import com.daniel_tickets.accounts_validation.port.fifa.FifaValidationPort;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.daniel_tickets.accounts_validation.adapter.out.fifa.constants.FifaUrls.SIGN_IN_URL;

@Slf4j
@Component
@RequiredArgsConstructor
public class FifaValidationAdapter implements FifaValidationPort {

    private final BrowserInitializer browserInitializer;

    private final FifaBrowserAuthService fifaBrowserAuthService;

    private final FifaTicketVerificationService fifaTicketVerificationService;

    private final NstBrowserPort nstBrowserPort;

    @Override
    public boolean isValid(User user) {
        return processValidation(user);
    }

    @SneakyThrows
    private boolean processValidation(User user) {
        try (LocalBrowser localBrowser = browserInitializer.initBrowserOverCdp(initializeNstProfile())) {

            log.info("Successfully initialized browser.");
            localBrowser.navigate(SIGN_IN_URL);

            log.info("Processing account log in.");
            fifaBrowserAuthService.login(user, localBrowser);

            log.info("Processing ticket validation.");
            return fifaTicketVerificationService.verify(localBrowser);
        }
    }

    private String initializeNstProfile() {
        Proxy proxy = Proxy.builder()
                .username("package-331393-country-us-sessionid-j3ocPlGcXRiVYHlg-sessionlength-300")
                .password("GgmsW2oeY7AWvz6I")
                .host("proxy.soax.com")
                .port(5000)
                .build();
        CreateProfileResponse profile = nstBrowserPort.createProfile("Clara Watkins", proxy.toUri());
        return profile.getData().getProfileId();
    }
}
