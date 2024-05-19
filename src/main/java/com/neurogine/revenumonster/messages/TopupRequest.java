package com.neurogine.revenumonster.messages;

public class TopupRequest {
    private String redirect = "https://revenuemonster.my";
    private Integer amount;
    
    public TopupRequest(Integer amount) {
        this.amount = amount;
    }
    public TopupRequest(String redirect, Integer amount) {
        this.redirect = redirect;
        this.amount = amount;
    }
    public String getRedirect() {
        return redirect;
    }
    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }
    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    
    // IMPORTANT
    // This content should be sorted by JSON key and compacted, according to the Documentation.
    // I did this manually for testing purposes,
    // But for real-life applications, it should be dynamically processed.
    @Override
    public String toString() {
        return "{\"amount\":" + getAmount() + ",\"redirect\":\"" + getRedirect() + "\"}";
    }
}