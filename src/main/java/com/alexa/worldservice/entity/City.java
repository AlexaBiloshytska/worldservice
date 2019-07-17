package com.alexa.worldservice.entity;

import lombok.Data;

@Data
public class City {
    int id;
    String name;
    String countryCode;
    String district;
    int population;
}
