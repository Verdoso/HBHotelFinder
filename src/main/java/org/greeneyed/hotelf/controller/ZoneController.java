package org.greeneyed.hotelf.controller;

import java.util.stream.Stream;

import org.greeneyed.hotelf.model.api.APIDestination;
import org.greeneyed.hotelf.service.HotelbedsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@RestController
public class ZoneController {

    private final HotelbedsService hotelbedsService;

    @GetMapping(value = "/zone")
    public ResponseEntity<Stream<APIDestination>> locate(@RequestParam(name = "query") String query) {
        return ResponseEntity.ok(hotelbedsService.retrieveHotelZones(query));
    }
}
