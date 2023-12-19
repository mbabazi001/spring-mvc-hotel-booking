package edu.apartmentbooking.service;

import edu.apartmentbooking.model.HotelManager;
import edu.apartmentbooking.model.User;

public interface HotelManagerService {

    HotelManager findByUser(User user);

}
