package co.epam.springtesting.domain.pojos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
public class BookingTicket {
    Long id;
    Date dateOfOrder;
    Date bookingDate;
    String customerName;

    @JsonCreator
    public BookingTicket(@JsonProperty("id") Long id,
                         @JsonProperty("dateOfOrder") Date dateOfOrder,
                         @JsonProperty("bookingDate")Date bookingDate,
                         @JsonProperty("customerName")String customerName) {
        this.id = id;
        this.dateOfOrder = dateOfOrder;
        this.bookingDate = bookingDate;
        this.customerName = customerName;
    }

    public Long getId() {
        return id;
    }

    public Date getDateOfOrder() {
        return dateOfOrder;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"dateOfOrder\":" + dateOfOrder +
                ", \"bookingDate\":" + bookingDate +
                ", \"customerName\":" + customerName +'}';
    }
}
