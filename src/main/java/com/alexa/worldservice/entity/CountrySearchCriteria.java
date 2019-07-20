package com.alexa.worldservice.entity;

import lombok.Data;

@Data
public class CountrySearchCriteria {
    private String name;
    private String continent;
    private Integer population;
    private Integer page;
    private Integer limit;
}
