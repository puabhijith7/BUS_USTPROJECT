package org.example.Repo;

import org.example.Model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepo extends JpaRepository<Seat,Integer> {
    List<Seat> findByScheduleId(int id);

    List<Seat> findByFhault(String s);

    List<Seat> findByThault(String s);

    List<Seat> findByScheduleIdAndSeatNo(int scheduleId, int seatNo);


    @Modifying
    @Query("UPDATE Seat SET status = 1 WHERE seatId >= :startSeatId AND seatId <= :endSeatId")
    void setstatus(@Param("startSeatId") int startSeatId, @Param("endSeatId") int endSeatId);

}
