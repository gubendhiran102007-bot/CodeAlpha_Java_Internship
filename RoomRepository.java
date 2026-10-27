package com.codealpha.hotelreservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.codealpha.hotelreservation.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
