package com.neurogine.revenumonster.messages;

public class AuthRequest {
	private String grantType;
    public AuthRequest(String grantType) {
        this.grantType = grantType;
    }
    public String getGrantType() {
        return grantType;
    }
    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }
}
