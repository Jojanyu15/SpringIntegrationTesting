package co.epam.springtesting.controller;

import co.epam.springtesting.domain.pojos.BookingTicket;
import co.epam.springtesting.domain.services.BookingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/CreateBookingTicket")
    void createBookingTicket(@RequestBody BookingTicket ticket){
        bookingService.queueBooking(ticket);
    }
}
