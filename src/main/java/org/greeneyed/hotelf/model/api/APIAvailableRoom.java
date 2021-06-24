package org.greeneyed.hotelf.model.api;

import java.util.Set;

import lombok.Data;

@Data
public class APIAvailableRoom {
    private String code;
    private String name;
    private Set<APIAvailableRate> rates;
}