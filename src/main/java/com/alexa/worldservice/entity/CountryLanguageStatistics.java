package com.alexa.worldservice.entity;

import lombok.Data;

@Data
public class CountryLanguageStatistics {
    private String code;
    private String name;
    private String continent;
    private String region;
    private double surfaceArea;
    private int indepYear;
    private int population;
    private String language;
    private boolean isOficial;
    private int percentage;
}
