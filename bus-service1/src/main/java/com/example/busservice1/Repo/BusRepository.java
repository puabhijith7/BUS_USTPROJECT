package com.example.busservice1.Repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.busservice1.model.Bus;
@Repository
public interface BusRepository extends JpaRepository<Bus,Integer> {
}
