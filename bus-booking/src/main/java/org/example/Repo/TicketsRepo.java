package org.example.Repo;

import org.example.model.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketsRepo extends JpaRepository<Tickets,Integer> {
}
