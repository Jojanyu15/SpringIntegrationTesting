package co.epam.springtesting.domain.services;

import co.epam.springtesting.domain.pojos.BookingTicket;
import co.epam.springtesting.infraestructure.repository.DTO.BookingTicketDTO;

public interface BookingService {
    void queueBooking(BookingTicket ticket);
    BookingTicketDTO saveBooking(BookingTicket ticket);
}
