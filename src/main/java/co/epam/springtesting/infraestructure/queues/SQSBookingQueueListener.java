package co.epam.springtesting.infraestructure.queues;

import co.epam.springtesting.domain.pojos.BookingTicket;
import co.epam.springtesting.domain.services.BookingService;
import co.epam.springtesting.domain.services.jms.listeners.BookingQueueListener;
import com.amazon.sqs.javamessaging.message.SQSTextMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;

@Component
public class SQSBookingQueueListener implements BookingQueueListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(SQSBookingQueueListener.class);

    private final BookingService bookingService;

    @Autowired
    public SQSBookingQueueListener(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @JmsListener(destination = "${queues.booking-queue}")
    public void receiveBooking(SQSTextMessage message) throws JMSException, JsonProcessingException {
        BookingTicket ticket = new ObjectMapper().readValue(message.getText(), BookingTicket.class);
        LOGGER.info(ticket.toString());
        bookingService.saveBooking(ticket);
    }
}
