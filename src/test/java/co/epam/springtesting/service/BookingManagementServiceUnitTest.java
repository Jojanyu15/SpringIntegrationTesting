package co.epam.springtesting.service;

import co.epam.springtesting.domain.pojos.BookingTicket;
import co.epam.springtesting.domain.services.JMSBookingService;
import co.epam.springtesting.infraestructure.repository.BookingRepository;
import co.epam.springtesting.infraestructure.repository.DTO.BookingTicketDTO;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Date;

import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class BookingManagementServiceUnitTest {
	@InjectMocks
	private JMSBookingService jmsBookingService;

	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void shouldStoreABookingTicketInDatabaseMock() {
		BookingTicket bookingTicket = new BookingTicket(null, new Date(), new Date(), "TestCustomer");
		when(jmsBookingService.saveBooking(bookingTicket)).thenReturn(new BookingTicketDTO());
	}


}
