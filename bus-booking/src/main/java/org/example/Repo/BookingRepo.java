package org.example.Repo;

import org.example.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepo extends JpaRepository<Booking,Integer> {
}
