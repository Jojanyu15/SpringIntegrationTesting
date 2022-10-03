package co.epam.springtesting.domain.services;

import co.epam.springtesting.domain.pojos.BookingTicket;
import co.epam.springtesting.domain.services.jms.senders.BookingQueueSender;
import co.epam.springtesting.infraestructure.repository.BookingRepository;
import co.epam.springtesting.infraestructure.repository.DTO.BookingTicketDTO;
import org.springframework.stereotype.Service;

@Service
public class JMSBookingService implements BookingService{

    private final BookingQueueSender bookingQueueSender;
    private final BookingRepository bookingRepository;

    public JMSBookingService(BookingQueueSender bookingQueueSender, BookingRepository bookingRepository) {
        this.bookingQueueSender = bookingQueueSender;
        this.bookingRepository = bookingRepository;
    }



    @Override
    public void queueBooking(BookingTicket ticket) {
        bookingQueueSender.queueBooking(ticket);
    }

    @Override
    public BookingTicketDTO saveBooking(BookingTicket ticket) {
       return bookingRepository.save(new BookingTicketDTO(ticket.getDateOfOrder(),ticket.getBookingDate(), ticket.getCustomerName()));
    }
}
