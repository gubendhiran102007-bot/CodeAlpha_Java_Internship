package com.codealpha.hotelreservation.service;

import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.codealpha.hotelreservation.entity.Booking;
import com.codealpha.hotelreservation.entity.Room;
import com.codealpha.hotelreservation.repository.BookingRepository;
import com.codealpha.hotelreservation.repository.RoomRepository;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    public BookingService(BookingRepository bookingRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
    }

    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBooking(Long id) {
        return bookingRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));
    }

    @Transactional
    public Booking createBooking(String name, String email, Long roomId,
                                 LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null || checkOut == null || !checkOut.isAfter(checkIn)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Check-out date must be after check-in date");
        }
        if (checkIn.isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Check-in date cannot be in the past");
        }

        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));

        if (bookingRepository.countOverlappingBookings(roomId, checkIn, checkOut) > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                "This room is already booked for the selected dates");
        }

        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        Booking booking = new Booking();
        booking.setCustomerName(name);
        booking.setCustomerEmail(email);
        booking.setRoom(room);
        booking.setCheckIn(checkIn);
        booking.setCheckOut(checkOut);
        booking.setTotalAmount(nights * room.getPricePerNight());
        booking.setStatus(Booking.BookingStatus.CONFIRMED);
        booking.setPaymentStatus(Booking.PaymentStatus.PENDING);
        return bookingRepository.save(booking);
    }

    @Transactional
    public Booking cancelBooking(Long id) {
        Booking booking = getBooking(id);
        if (booking.getStatus() == Booking.BookingStatus.CANCELLED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Booking is already cancelled");
        }
        booking.setStatus(Booking.BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }

    // DEMO ONLY: this marks a booking paid; it does not charge a real card/account.
    @Transactional
    public Booking simulatePayment(Long id) {
        Booking booking = getBooking(id);
        if (booking.getStatus() == Booking.BookingStatus.CANCELLED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Cannot pay for a cancelled booking");
        }
        booking.setPaymentStatus(Booking.PaymentStatus.PAID);
        return bookingRepository.save(booking);
    }
}
