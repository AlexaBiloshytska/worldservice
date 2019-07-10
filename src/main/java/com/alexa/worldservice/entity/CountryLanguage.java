package com.alexa.worldservice.entity;

import lombok.Data;

@Data
public class CountryLanguage {
    private String countryCode;
    private String language;
    private boolean isOficial;
    private float percentage;
    
}
