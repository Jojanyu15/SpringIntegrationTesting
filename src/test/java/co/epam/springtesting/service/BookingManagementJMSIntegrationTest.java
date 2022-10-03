package co.epam.springtesting.service;

import co.epam.springtesting.configuration.sqs.SqsTestConfiguration;
import co.epam.springtesting.domain.pojos.BookingTicket;
import co.epam.springtesting.domain.services.jms.senders.BookingQueueSender;
import co.epam.springtesting.infraestructure.queues.SQSBookingQueueSender;
import co.epam.springtesting.infraestructure.repository.BookingRepository;
import co.epam.springtesting.infraestructure.repository.DTO.BookingTicketDTO;
import co.epam.springtesting.jms.JmsTestConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.Date;

import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@Import(JmsTestConfiguration.class)
public class BookingManagementJMSIntegrationTest {
    @Autowired
    private JmsTemplate bookingQueueSender;
    @MockBean
    private BookingRepository bookingRepository;
    @Container
    static LocalStackContainer localStack = new LocalStackContainer(DockerImageName.parse("localstack/localstack:0.14.3"))
            .withClasspathResourceMapping("/localstack", "/docker-entrypoint-initaws.d", BindMode.READ_ONLY)
            .withServices(LocalStackContainer.Service.SQS)
            .withExposedPorts(49165)
            .waitingFor(Wait.forLogMessage(".*Initialized\\.\n", 1));

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("cloud.aws.sqs.endpoint", () -> localStack.getEndpointOverride(LocalStackContainer.Service.SQS).toString());
        registry.add("cloud.aws.credentials.access-key", () -> "foo");
        registry.add("cloud.aws.credentials.secret-key", () -> "bar");
        registry.add("cloud.aws.region.static", () -> localStack.getRegion());
        registry.add("booking-queue", () -> "booking-queue");
    }

    @Test
    void shouldStoreABookingAfterQueueATicket() {
        BookingTicket bookingTicket =  new BookingTicket(null,new Date(), new Date(), "JMSTestCustomer");
        this.bookingQueueSender.convertAndSend("booking-queue",bookingTicket);
        await().atMost(Duration.ofSeconds(6))
               .untilAsserted(()->verify(bookingRepository).save(any(BookingTicketDTO.class)));
    }
}
