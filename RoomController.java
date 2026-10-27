package com.codealpha.hotelreservation.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.codealpha.hotelreservation.entity.Room;
import com.codealpha.hotelreservation.service.BookingService;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "*")
public class RoomController {
    private final BookingService bookingService;

    public RoomController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<Room> getRooms() {
        return bookingService.getRooms();
    }
}
