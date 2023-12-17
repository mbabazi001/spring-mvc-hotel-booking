package edu.viviane.apartmentbookingapp.service;

import edu.viviane.apartmentbookingapp.model.Hotel;
import edu.viviane.apartmentbookingapp.model.dto.HotelAvailabilityDTO;

import java.time.LocalDate;
import java.util.List;

public interface HotelSearchService {

    List<HotelAvailabilityDTO> findAvailableHotelsByCityAndDate(String city, LocalDate checkinDate, LocalDate checkoutDate);

    HotelAvailabilityDTO findAvailableHotelById(Long hotelId, LocalDate checkinDate, LocalDate checkoutDate);

    HotelAvailabilityDTO mapHotelToHotelAvailabilityDto(Hotel hotel, LocalDate checkinDate, LocalDate checkoutDate);
}
