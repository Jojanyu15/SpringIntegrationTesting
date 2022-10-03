package co.epam.springtesting.infraestructure.queues;

import co.epam.springtesting.domain.pojos.BookingTicket;
import co.epam.springtesting.domain.services.jms.senders.BookingQueueSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class SQSBookingQueueSender implements BookingQueueSender {

    private final Logger LOGGER = LoggerFactory.getLogger(SQSBookingQueueSender.class);
    @Value("${queues.booking-queue}")
    private String BOOKING_QUEUE_NAME;

    private final JmsTemplate jmsTemplate;

    public SQSBookingQueueSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void queueBooking(BookingTicket ticket) {
        LOGGER.info(ticket.toString());
        LOGGER.info(this.BOOKING_QUEUE_NAME);
        jmsTemplate.convertAndSend(BOOKING_QUEUE_NAME, ticket);
    }
}
