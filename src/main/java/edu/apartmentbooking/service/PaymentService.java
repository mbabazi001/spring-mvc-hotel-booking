package edu.apartmentbooking.service;

import edu.apartmentbooking.model.Booking;
import edu.apartmentbooking.model.Payment;
import edu.apartmentbooking.model.dto.BookingInitiationDTO;

public interface PaymentService {

    Payment savePayment(BookingInitiationDTO bookingInitiationDTO, Booking booking);
}
