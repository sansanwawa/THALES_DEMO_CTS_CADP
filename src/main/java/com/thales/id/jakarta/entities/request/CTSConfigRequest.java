package com.thales.id.jakarta.entities.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CTSConfigRequest {
    private String ctsBaseUrl;
    private String ctsTokenizeUrl;
    private String ctsDeTokenizeUrl;
    private String ctsTokenTemplate;
    private String ctsTokenGroup;
    private String ctsUsername;
    private String ctsPassword;
}
