package com.example.busservice1.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.busservice1.model.Route;
@Repository
public interface RouteRepository extends JpaRepository<Route,Integer> {

	
//	@Query("SELECT r.routeId FROM Route r WHERE r.Source = :source")
//    List<Integer> findRouteIdsBySource(@Param("source") String source);
//
//	@Query("SELECT r.routeId FROM Route r WHERE r.Destination = :destination")
//	List<Integer> findRouteIdsByDest(@Param("destination")String destination);
}
