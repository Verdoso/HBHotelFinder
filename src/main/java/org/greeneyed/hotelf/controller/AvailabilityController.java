package org.greeneyed.hotelf.controller;

import java.time.LocalDate;
import java.util.List;

import org.greeneyed.hotelf.model.api.APIAvailableHotel;
import org.greeneyed.hotelf.service.HotelbedsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@RestController
public class AvailabilityController {

    private final HotelbedsService hotelbedsService;

    @GetMapping(value = "/availability")
    public ResponseEntity<List<APIAvailableHotel>> availability(
            @RequestParam(name = "checkOut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut,
            @RequestParam(name = "checkIn") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam(name = "destinationId") String destinationId,
            @RequestParam(name = "numAdults") Integer numAdults,
            @RequestParam(name = "numChildren") Integer numChildren) {
        return ResponseEntity
                .ok(hotelbedsService.getAvailability(checkIn, checkOut, destinationId, numAdults, numChildren));
    }
}
