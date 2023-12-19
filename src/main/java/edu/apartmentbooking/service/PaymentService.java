package edu.viviane.apartmentbookingapp.service;

import edu.viviane.apartmentbookingapp.model.Booking;
import edu.viviane.apartmentbookingapp.model.Payment;
import edu.viviane.apartmentbookingapp.model.dto.BookingInitiationDTO;

public interface PaymentService {

    Payment savePayment(BookingInitiationDTO bookingInitiationDTO, Booking booking);
}
