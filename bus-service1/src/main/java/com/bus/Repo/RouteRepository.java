package com.bus.Repo;

import com.bus.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route,Integer> {

	
//	@Query("SELECT r.routeId FROM Route r WHERE r.Source = :source")
//    List<Integer> findRouteIdsBySource(@Param("source") String source);
//
//	@Query("SELECT r.routeId FROM Route r WHERE r.Destination = :destination")
//	List<Integer> findRouteIdsByDest(@Param("destination")String destination);
}
