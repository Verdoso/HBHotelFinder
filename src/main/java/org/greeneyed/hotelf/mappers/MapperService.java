package org.greeneyed.hotelf.mappers;

import org.greeneyed.hotelf.model.api.APIAvailableHotel;
import org.greeneyed.hotelf.model.api.APIDestination;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hotelbeds.hotelapimodel.auto.model.Hotel;
import com.hotelbeds.hotelcontentapi.auto.messages.Destination;

@Mapper(componentModel = "spring")
public abstract class MapperService {

    @Mapping(target="destination", source="destination.name.content")
    @Mapping(target="country", source="destination.isoCode")
    public abstract APIDestination from(Destination destination);

    public abstract APIAvailableHotel from(Hotel hotel);
}