package com.alexa.worldservice.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchCity {
    String name;
    String countryName;
    String district;
    Integer population;
    Integer countryPopulation;
}
