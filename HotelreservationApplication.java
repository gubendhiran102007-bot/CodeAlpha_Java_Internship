package com.codealpha.hotelreservation;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.codealpha.hotelreservation.entity.Room;
import com.codealpha.hotelreservation.repository.RoomRepository;

@SpringBootApplication
public class HotelreservationApplication {
    public static void main(String[] args) {
        SpringApplication.run(HotelreservationApplication.class, args);
    }

    // Adds sample rooms the first time the application starts.
    @Bean
    CommandLineRunner seedRooms(RoomRepository roomRepository) {
        return args -> {
            if (roomRepository.count() == 0) {
                roomRepository.save(new Room("Standard", "Standard room with one double bed", 1800.00, 2));
                roomRepository.save(new Room("Deluxe", "Deluxe room with city view", 2800.00, 3));
                roomRepository.save(new Room("Suite", "Large suite with separate sitting area", 5000.00, 4));
            }
        };
    }
}
