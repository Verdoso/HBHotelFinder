package org.greeneyed.hotelf.model.api;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class APIAvailableRate {
    @EqualsAndHashCode.Include
    private String rateKey;
    private String rateClass;
    private BigDecimal net;
    private String boardCode;
    private String boardName;
}