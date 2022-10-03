package co.epam.springtesting.service;

import co.epam.springtesting.infraestructure.repository.BookingRepository;
import co.epam.springtesting.infraestructure.repository.DTO.BookingTicketDTO;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.text.ParseException;
import java.util.Date;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookingManagementDatabaseIntegrationTest {

    @Autowired
    private BookingRepository bookingRepository;

    @DynamicPropertySource
    static void mySqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
    }

    @Container
    public static MySQLContainer mySQLContainer = new MySQLContainer<>("mysql:latest")
            .withDatabaseName("BookingDatabase")
            .withPassword("sql")
            .withUsername("my")
            .withInitScript("Booking.sql");

    @Test
    void shouldSaveReservation() {
        BookingTicketDTO bookingTicketDTO = buildTestCustomer();
        BookingTicketDTO ticket = bookingRepository.save(bookingTicketDTO);
        Assertions.assertEquals(ticket.getCustomerName(), bookingTicketDTO.getCustomerName());
    }

    @NotNull
    private static BookingTicketDTO buildTestCustomer() {
        return new BookingTicketDTO(null, new Date(), new Date(), "test customer");
    }

}
