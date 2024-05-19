package com.neurogine.revenumonster.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("revenumonster")
public class RevenumonsterProperties {
    private String rsaPrivateKeyFilePath;
    private String clientId;
    private String clientSecret;
    private String test;
    
    public String getRsaPrivateKeyFilePath() {
        return rsaPrivateKeyFilePath;
    }
    public void setRsaPrivateKeyFilePath(String rsaPrivateKeyFilePath) {
        this.rsaPrivateKeyFilePath = rsaPrivateKeyFilePath;
    }
    public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    public String getClientSecret() {
        return clientSecret;
    }
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
    public String getTest() {
        return test;
    }
    public void setTest(String test) {
        this.test = test;
    }
    
}
