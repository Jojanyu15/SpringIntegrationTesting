package co.epam.springtesting.domain.services.jms.listeners;

import com.amazon.sqs.javamessaging.message.SQSTextMessage;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.jms.JMSException;

public interface BookingQueueListener{
    void receiveBooking(SQSTextMessage sqsTextMessage) throws JMSException, JsonProcessingException;
}
