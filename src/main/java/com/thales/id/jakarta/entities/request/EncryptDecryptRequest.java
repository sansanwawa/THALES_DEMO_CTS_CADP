package com.thales.id.jakarta.entities.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EncryptDecryptRequest {
    private String plainValue;
    private String encryptionValue;
    private String convertedValue;
}
