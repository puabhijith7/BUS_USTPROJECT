package com.example.busservice1.Repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.busservice1.model.RouteDetails;
@Repository
public interface RouteDetailsRepository extends JpaRepository<RouteDetails,Integer> {

	List<RouteDetails> findByrouteId(int routeId);


	@Query("SELECT DISTINCT s1.routeId " +
			"FROM RouteDetails s1 " +
			"JOIN RouteDetails s2 ON s1.routeId = s2.routeId " +
			"WHERE s1.hault = :source " +
			"AND s2.hault = :destination " +
			"AND s1.stopNumber < s2.stopNumber"
	)

	List<Integer> findDistinctRouteIdsByHault(@Param("source") String source,@Param("destination") String destination);

	@Query("SELECT DISTINCT r.hault FROM RouteDetails r")
	List<String> findDistinctHaults();
}
