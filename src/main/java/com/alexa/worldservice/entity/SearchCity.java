package com.alexa.worldservice.entity;

import lombok.Data;

@Data
public class SearchCity {
    int id;
    String name;
    String countryName;
    String district;
    int population;
    int countryPopulation;
}
