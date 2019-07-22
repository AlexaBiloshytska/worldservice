package com.alexa.worldservice.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchCity {
    private String name;
    private String countryName;
    private String district;
    private Integer population;
    private Integer countryPopulation;
}
