package org.greeneyed.hotelf.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.greeneyed.hotelf.mappers.MapperService;
import org.greeneyed.hotelf.model.api.APIAvailableHotel;
import org.greeneyed.hotelf.model.api.APIDestination;
import org.springframework.stereotype.Service;

import com.hotelbeds.distribution.hotel_api_sdk.HotelApiClient;
import com.hotelbeds.distribution.hotel_api_sdk.helpers.AvailRoom;
import com.hotelbeds.distribution.hotel_api_sdk.helpers.AvailRoom.AvailRoomBuilder;
import com.hotelbeds.distribution.hotel_api_sdk.helpers.Availability;
import com.hotelbeds.distribution.hotel_api_sdk.types.HotelApiSDKException;
import com.hotelbeds.hotelapimodel.auto.messages.AvailabilityRS;
import com.hotelbeds.hotelcontentapi.auto.messages.Destination;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Service
public class HotelbedsService {

    private final HotelApiClient hotelApiClient;

    private final MapperService mapperService;

    private List<Destination> destinations = null;

    @PostConstruct
    public void postConstruct() {
        try {
            log.info("Retrieving destinations from Hotelbeds");
            destinations = hotelApiClient.getAllDestinations("ENG", false);
            log.info("... destinations retrieved!");
        } catch (HotelApiSDKException e) {
            log.error("Error retrieving destinations", e);
        }
    }

    public Stream<APIDestination> retrieveHotelZones(String criteria) {
        if (StringUtils.isNotBlank(criteria)) {
            String normalizedCriteria = criteria.toLowerCase();
            return destinations.stream()
                    .filter(destination -> destination.getName()
                            .getContent()
                            .toLowerCase()
                            .contains(normalizedCriteria))
                    .map(mapperService::from)
                    .sorted(Comparator.comparing(APIDestination::getDestination));
        } else {
            return Stream.of();
        }
    }

    public List<APIAvailableHotel> getAvailability(LocalDate checkIn, LocalDate checkOut, String destinationID,
            Integer numAdults, Integer numChildren) {
        List<APIAvailableHotel> availableHotels = new ArrayList<>();
        AvailRoomBuilder availRoom = AvailRoom.builder().adults(numAdults).children(numChildren);
        try {
            AvailabilityRS availabilityRS = hotelApiClient.availability(Availability.builder()
                    .language("ENG")
                    .checkIn(checkIn)
                    .checkOut(checkOut)
                    .addRoom(availRoom)
                    .destination(destinationID)
                    .limitHotelsTo(50)
                    .build());
            if (availabilityRS != null && availabilityRS.getHotels() != null
                    && availabilityRS.getHotels().getHotels() != null) {
                log.info("Availability answered with {} hotels!", availabilityRS.getHotels().getHotels().size());
                availabilityRS.getHotels()
                        .getHotels()
                        .stream()
                        //.peek(this::print)
                        .map(mapperService::from)
                        //.peek(this::print)
                        .forEach(availableHotels::add);
            } else {
                log.info("No availability!");
            }
        } catch (HotelApiSDKException e) {
            log.error("Error calling availability", e);
        }
        return availableHotels;
    }

    @SuppressWarnings("unused")
    private void print(Object o) {
        log.info("{}", o);
    }
}
