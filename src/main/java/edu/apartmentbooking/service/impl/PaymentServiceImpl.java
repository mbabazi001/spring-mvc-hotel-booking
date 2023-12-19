package edu.viviane.apartmentbookingapp.service.impl;

import edu.viviane.apartmentbookingapp.model.Booking;
import edu.viviane.apartmentbookingapp.model.Payment;
import edu.viviane.apartmentbookingapp.model.dto.BookingInitiationDTO;
import edu.viviane.apartmentbookingapp.model.enums.Currency;
import edu.viviane.apartmentbookingapp.model.enums.PaymentMethod;
import edu.viviane.apartmentbookingapp.model.enums.PaymentStatus;
import edu.viviane.apartmentbookingapp.repository.PaymentRepository;
import edu.viviane.apartmentbookingapp.service.PaymentService;
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
