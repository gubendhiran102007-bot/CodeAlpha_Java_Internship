package com.codealpha.hotelreservation.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.codealpha.hotelreservation.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomerEmailIgnoreCase(String customerEmail);

    @Query("""
        SELECT COUNT(b) FROM Booking b
        WHERE b.room.id = :roomId
          AND b.status = com.codealpha.hotelreservation.entity.Booking.BookingStatus.CONFIRMED
          AND b.checkIn < :checkOut
          AND b.checkOut > :checkIn
        """)
    long countOverlappingBookings(@Param("roomId") Long roomId,
                                  @Param("checkIn") LocalDate checkIn,
                                  @Param("checkOut") LocalDate checkOut);
}
