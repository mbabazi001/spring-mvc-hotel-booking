package edu.viviane.apartmentbookingapp.service.impl;

import edu.viviane.apartmentbookingapp.model.HotelManager;
import edu.viviane.apartmentbookingapp.model.User;
import edu.viviane.apartmentbookingapp.repository.HotelManagerRepository;
import edu.viviane.apartmentbookingapp.service.HotelManagerService;
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
