package com.alexa.worldservice.entity;

import lombok.Data;

@Data
public class CityCriteria {
    private boolean countryRequired;
    private boolean populationRequired;
    private boolean countryPopulationRequired;
    private String country;
    private String name;
    private String continent;
}
