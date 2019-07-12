package com.alexa.worldservice.constant;

public enum MimeType {
    APPLICATION_XML("application/xml"),
    APPLICATION_JSON("application/json");

    String value;

    MimeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
