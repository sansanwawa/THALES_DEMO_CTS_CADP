package com.thales.id.jakarta.entities.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * Author : sandy.haryono@thalesgroup.com
 *
 */

@Getter
@Setter
@NoArgsConstructor
public class DeTokenizeRequest {
    private String tokengroup;
    private String tokentemplate;
    private String token;

}
