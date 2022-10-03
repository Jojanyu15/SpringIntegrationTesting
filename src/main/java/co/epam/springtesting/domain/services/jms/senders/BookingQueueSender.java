package co.epam.springtesting.domain.services.jms.senders;

import co.epam.springtesting.domain.pojos.BookingTicket;

public interface BookingQueueSender {
    void queueBooking(BookingTicket ticket);
}
