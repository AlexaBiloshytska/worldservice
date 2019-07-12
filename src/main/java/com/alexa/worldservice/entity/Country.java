package com.alexa.worldservice.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Country {
    private String code;
    private String name;
    private String continent;
    private String region;
    private double surfaceArea;
    private int indepYear;
    private int population;
    private List<CountryLanguage> countryLanguages;
    List<Country> countries;
}
