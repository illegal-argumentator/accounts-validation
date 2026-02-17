package com.daniel_tickets.accounts_validation.domain.proxy;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Proxy {

    private static String SECURED_PROXY_URL_TEMPLATE = "http://%s:%s@%s:%d";

    private String username;
    private String password;
    private String host;
    private int port;

    public String toUri() {
        return SECURED_PROXY_URL_TEMPLATE.formatted(username, password, host, port);
    }

}
