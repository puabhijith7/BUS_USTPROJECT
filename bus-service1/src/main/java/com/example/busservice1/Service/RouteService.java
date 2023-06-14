package com.example.busservice1.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.busservice1.Repo.RouteRepository;
import com.example.busservice1.model.Route;

import java.util.List;
import java.util.Optional;

@Service
public class RouteService{
    @Autowired
    private RouteRepository routeRepository;

	public Route save(Route route) {
		return routeRepository.save(route);
		
	}

	public List<Route> getall() {
		// TODO Auto-generated method stub
		return routeRepository.findAll();
	}

	public Optional<Route> getbyrouteId(int routeId) {
		// TODO Auto-generated method stub
		return routeRepository.findById(routeId);
	}

	public void delete(int routeId) {
		// TODO Auto-generated method stub
		routeRepository.deleteById(routeId);
		
	}

    public Integer getFare(int routeId) {
		Optional<Route> r=routeRepository.findById(routeId);
		return r.get().getFarePerKm();

    }

//	public List<Integer> findRouteIdsBySource(String source) {
//		// TODO Auto-generated method stub
//		return routeRepository.findRouteIdsBySource(source);
//	}
//
//	public List<Integer> findRouteIdsByDest(String destination) {
//		// TODO Auto-generated method stub
//		return routeRepository.findRouteIdsByDest(destination);
//	}
   
}
