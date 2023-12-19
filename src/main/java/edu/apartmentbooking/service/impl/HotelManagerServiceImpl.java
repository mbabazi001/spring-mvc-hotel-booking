package edu.apartmentbooking.service.impl;

import edu.apartmentbooking.model.HotelManager;
import edu.apartmentbooking.model.User;
import edu.apartmentbooking.repository.HotelManagerRepository;
import edu.apartmentbooking.service.HotelManagerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelManagerServiceImpl implements HotelManagerService {

    private final HotelManagerRepository hotelManagerRepository;

    @Override
    public HotelManager findByUser(User user) {
        log.info("Attempting to find HotelManager for user ID: {}", user.getId());
        return hotelManagerRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("HotelManager not found for user " + user.getUsername()));
    }
}
