package com.tomaszkyc.app.model;

public class APIClaims {

    public String getApiEndpoint() {
        return apiEndpoint;
    }

    public void setApiEndpoint(String apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
    }

    private String apiEndpoint;

    public APIClaims(String apiKey, String apiEndpoint) {
        this.apiKey = apiKey;
        this.apiEndpoint = apiEndpoint;
    }

    public APIClaims() {
        this.apiEndpoint = "";
        this.apiKey = "";
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    private String apiKey;



}
