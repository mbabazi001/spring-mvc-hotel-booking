package edu.apartmentbooking.service.impl;

import edu.apartmentbooking.model.Booking;
import edu.apartmentbooking.model.Payment;
import edu.apartmentbooking.model.dto.BookingInitiationDTO;
import edu.apartmentbooking.model.enums.Currency;
import edu.apartmentbooking.model.enums.PaymentMethod;
import edu.apartmentbooking.model.enums.PaymentStatus;
import edu.apartmentbooking.repository.PaymentRepository;
import edu.apartmentbooking.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public Payment savePayment(BookingInitiationDTO bookingInitiationDTO, Booking booking) {


        Payment payment = Payment.builder()
                .booking(booking)
                .totalPrice(bookingInitiationDTO.getTotalPrice())
                .paymentStatus(PaymentStatus.COMPLETED) // Assuming the payment is completed
                .paymentMethod(PaymentMethod.CREDIT_CARD) // Default to CREDIT_CARD
                .currency(Currency.USD) // Default to USD
                .build();

        Payment savedPayment = paymentRepository.save(payment);
        log.info("Payment saved with transaction ID: {}", savedPayment.getTransactionId());

        return savedPayment;
    }
}
