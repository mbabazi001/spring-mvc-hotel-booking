package edu.viviane.apartmentbookingapp.service;

import edu.viviane.apartmentbookingapp.model.HotelManager;
import edu.viviane.apartmentbookingapp.model.User;

public interface HotelManagerService {

    HotelManager findByUser(User user);

}
