package co.epam.springtesting.infraestructure.repository;

import co.epam.springtesting.infraestructure.repository.DTO.BookingTicketDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends CrudRepository<BookingTicketDTO, Long> {
}
