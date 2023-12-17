package edu.viviane.apartmentbookingapp.controller;

import edu.viviane.apartmentbookingapp.service.BookingService;
import edu.viviane.apartmentbookingapp.service.HotelService;
import edu.viviane.apartmentbookingapp.service.UserService;
import edu.viviane.apartmentbookingapp.model.dto.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URI;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/booking")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private final HotelService hotelService;
    private final UserService userService;
    private final BookingService bookingService;

    @PostMapping("/initiate")
    public String initiateBooking(@ModelAttribute BookingInitiationDTO bookingInitiationDTO, HttpSession session) {
        session.setAttribute("bookingInitiationDTO", bookingInitiationDTO);
        log.debug("BookingInitiationDTO set in session: {}", bookingInitiationDTO);
        return "redirect:/booking/payment";
    }

    @GetMapping("/payment")
    public String showPaymentPage(Model model, RedirectAttributes redirectAttributes, HttpSession session) {
        BookingInitiationDTO bookingInitiationDTO = (BookingInitiationDTO) session.getAttribute("bookingInitiationDTO");
        log.debug("BookingInitiationDTO retrieved from session: {}", bookingInitiationDTO);

        if (bookingInitiationDTO == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Your session has expired. Please start a new search.");
            return "redirect:/search";
        }

        HotelDTO hotelDTO = hotelService.findHotelDtoById(bookingInitiationDTO.getHotelId());

        model.addAttribute("bookingInitiationDTO", bookingInitiationDTO);
        model.addAttribute("hotelDTO", hotelDTO);
        model.addAttribute("paymentCardDTO", new PaymentCardDTO());

        return "booking/payment";
    }

    @PostMapping("/payment")
    public String confirmBooking(@Valid @ModelAttribute("paymentCardDTO") PaymentCardDTO paymentDTO, BindingResult result, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        BookingInitiationDTO bookingInitiationDTO = (BookingInitiationDTO) session.getAttribute("bookingInitiationDTO");
        log.debug("BookingInitiationDTO retrieved from session at the beginning of completeBooking: {}", bookingInitiationDTO);
        if (bookingInitiationDTO == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Your session has expired. Please start a new search.");
            return "redirect:/search";
        }

        if (result.hasErrors()) {
            log.warn("Validation errors occurred while completing booking");
            HotelDTO hotelDTO = hotelService.findHotelDtoById(bookingInitiationDTO.getHotelId());
            model.addAttribute("bookingInitiationDTO", bookingInitiationDTO);
            model.addAttribute("hotelDTO", hotelDTO);
            model.addAttribute("paymentCardDTO", paymentDTO);
            return "booking/payment";
        }

        try {

            String url = "https://opay-api.oltranz.com/opay/paymentrequest";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> paymentRequest = new HashMap<>();
            paymentRequest.put("telephoneNumber", paymentDTO.getCardNumber());
            paymentRequest.put("amount", bookingInitiationDTO.getTotalPrice());
            paymentRequest.put("organizationId", "a6dcf64d-26fa-4de7-a42a-04d30000bb67");
            paymentRequest.put("description", "Apartment Booking");
            paymentRequest.put("transactionId", UUID.randomUUID().toString());

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(paymentRequest, headers);

            ResponseEntity<String> responseEntity = new RestTemplate().postForEntity(new URI(url), requestEntity, String.class);
            System.out.println("Response: " + responseEntity.getBody());

            Long userId = getLoggedInUserId();
            BookingDTO bookingDTO = bookingService.confirmBooking(bookingInitiationDTO, userId);
            redirectAttributes.addFlashAttribute("bookingDTO", bookingDTO);

            return "redirect:/booking/confirmation";
        } catch (Exception e) {
            log.error("An error occurred while completing the booking", e);
            redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred. Please try again later.");
            return "redirect:/booking/payment";
        }
    }

    @GetMapping("/confirmation")
    public String showConfirmationPage(Model model, RedirectAttributes redirectAttributes) {
        // Attempt to retrieve the bookingDTO from flash attributes
        BookingDTO bookingDTO = (BookingDTO) model.asMap().get("bookingDTO");

        if (bookingDTO == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Your session has expired or the booking process was not completed properly. Please start a new search.");
            return "redirect:/search";
        }

        LocalDate checkinDate = bookingDTO.getCheckinDate();
        LocalDate checkoutDate = bookingDTO.getCheckoutDate();
        long durationDays = ChronoUnit.DAYS.between(checkinDate, checkoutDate);

        model.addAttribute("days", durationDays);
        model.addAttribute("bookingDTO", bookingDTO);

        return "booking/confirmation";
    }

    private Long getLoggedInUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserDTO userDTO = userService.findUserDTOByUsername(username);
        log.info("Fetched logged in user ID: {}", userDTO.getId());
        return userDTO.getId();
    }

}