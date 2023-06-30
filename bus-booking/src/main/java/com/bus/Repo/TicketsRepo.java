package com.bus.Repo;

import com.bus.model.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketsRepo extends JpaRepository<Tickets,Integer> {
}
