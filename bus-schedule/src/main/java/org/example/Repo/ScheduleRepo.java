package org.example.Repo;

import org.example.Model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepo extends JpaRepository<Schedule,Integer> {
    List<Schedule> findByDate(LocalDate date);
}
