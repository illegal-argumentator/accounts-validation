package com.daniel_tickets.accounts_validation.adapter.out.nst.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "nst")
public class NstProperties {

    private String apiKey;

    private String url;

    private String groupId;

    private String host;

    private int port;

}
