package org.greeneyed.hotelf.model.api;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class APIAvailableHotel {
    private Integer code;
    private String name;
    private String categoryName;
    private String zoneName;
    private String latitude;
    private String longitude;
    private BigDecimal minRate;
    private BigDecimal maxRate;
    private List<APIAvailableRoom> rooms;
}