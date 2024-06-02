package com.thales.id.jakarta.entities.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CADPConfigRequest {
    
    private String cadpNAEIP;
    private String cadpNAEPort;
    private String cadpNAEProtokol;
    private String cadpVerifySSL;
    private String cadpLoadBalancingAlg;
    private String cadpKeyName;
    private String cadpAlgorithm;
    private String cadpUsername;
    private String cadpPassword;

}
