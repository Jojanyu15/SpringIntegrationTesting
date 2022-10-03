package co.epam.springtesting.infraestructure.repository.DTO;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Booking", schema = "BookingDatabase")
public class BookingTicketDTO {
    private Long id;
    private Date dateOfOrder;
    private Date bookingDate;
    private String customerName;

    public BookingTicketDTO(Date dateOfOrder, Date bookingDate, String customerName) {
        this.dateOfOrder = new Date(dateOfOrder.getTime());
        this.bookingDate = new Date(bookingDate.getTime());
        this.customerName = customerName;
    }

    public BookingTicketDTO(Long id, Date dateOfOrder, Date bookingDate, String customerName) {
        this.id = id;
        this.dateOfOrder = dateOfOrder;
        this.bookingDate = bookingDate;
        this.customerName = customerName;
    }

    public BookingTicketDTO() {
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "dateOfOrder", nullable = false)
    public Date getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(Date dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    @Basic
    @Column(name = "bookingDate", nullable = false)
    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    @Basic
    @Column(name = "customerName", nullable = false, length = 30)
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingTicketDTO that = (BookingTicketDTO) o;
        return id == that.id && Objects.equals(dateOfOrder, that.dateOfOrder) && Objects.equals(bookingDate, that.bookingDate) && Objects.equals(customerName, that.customerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateOfOrder, bookingDate, customerName);
    }
}
