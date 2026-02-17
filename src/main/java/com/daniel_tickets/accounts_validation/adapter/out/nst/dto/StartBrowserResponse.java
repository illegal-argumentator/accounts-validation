package com.daniel_tickets.accounts_validation.adapter.out.nst.dto;

import lombok.Data;

@Data
public class StartBrowserResponse {

    private int code;

    private ResponseData data;

    private boolean err;

    private String msg;

    @Data
    public static class ResponseData {

        private int port;

        private String profileId;

        private String proxy;

        private String webSocketDebuggerUrl;

    }

}
