package com.thales.id.jakarta.entities.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenizeRequest {
    private String tokengroup;
    private String tokentemplate;
    private String data;

}
