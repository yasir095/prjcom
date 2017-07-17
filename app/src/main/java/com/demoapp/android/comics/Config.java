package com.demoapp.android.comics;

/**
 * Created by yasirmahmood on 12/06/2017.
 */

public class Config
{
    private String baseUrl;
    private String publicApiKey;
    private String privateApiKey;

    public enum Environment {DEV, BETA, STAGING, PRODUCTION}

    public Config(Environment environment) {
        switch (environment) {
            case DEV:
                this.baseUrl = "http://gateway.marvel.com";
                this.publicApiKey = "54306733de0f5cd1418aa05a85fa062a";
                this.privateApiKey = "5de1fabcda2ea08912bd8b09bca4321f50563655";
                break;

            case BETA:
                this.baseUrl = "http://gateway.marvel.com";
                this.publicApiKey = "54306733de0f5cd1418aa05a85fa062a";
                this.privateApiKey = "5de1fabcda2ea08912bd8b09bca4321f50563655";
                break;

            case STAGING:
                this.baseUrl = "http://gateway.marvel.com";
                this.publicApiKey = "54306733de0f5cd1418aa05a85fa062a";
                this.privateApiKey = "5de1fabcda2ea08912bd8b09bca4321f50563655";
                break;

            case PRODUCTION:
                this.baseUrl = "http://gateway.marvel.com";
                this.publicApiKey = "54306733de0f5cd1418aa05a85fa062a";
                this.privateApiKey = "5de1fabcda2ea08912bd8b09bca4321f50563655";
                break;

            default:
                break;
        }
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getPublicApiKey() {
        return publicApiKey;
    }

    public void setPublicApiKey(String publicApiKey) {
        this.publicApiKey = publicApiKey;
    }

    public String getPrivateApiKey() {
        return privateApiKey;
    }

    public void setPrivateApiKey(String privateApiKey) {
        this.privateApiKey = privateApiKey;
    }
}