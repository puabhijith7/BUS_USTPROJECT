package com.example.busservice1.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.busservice1.Repo.RouteDetailsRepository;
import com.example.busservice1.model.Route;
import com.example.busservice1.model.RouteDetails;

@Service
public class RouteDetailsService{
    @Autowired
    private RouteDetailsRepository routeDetailsRepo;

	public RouteDetails save(RouteDetails routeDetails) {
		return routeDetailsRepo.save(routeDetails);
		
	}

	public List<RouteDetails> search(int routeId) {
		// TODO Auto-generated method stub
		List<RouteDetails> list=routeDetailsRepo.findByrouteId(routeId);
		return list;
	}

	public List<Integer> findDistinctRouteIdsByHault(String source,String destination) {
		// TODO Auto-generated method stub
		System.out.println(routeDetailsRepo.findDistinctRouteIdsByHault(source,destination));
		return routeDetailsRepo.findDistinctRouteIdsByHault(source,destination);

	}

	public List<RouteDetails> getall() {
		// TODO Auto-generated method stub
		return routeDetailsRepo.findAll();
	}

	public Optional<RouteDetails> getbystopId(int stopId) {
		// TODO Auto-generated method stub
		return routeDetailsRepo.findById(stopId);
	}

	public void delete(int stopId) {
		// TODO Auto-generated method stub
		 routeDetailsRepo.deleteById(stopId);
	}

    public List<Integer> searchforStopsbyRouteId(int routeId) {
		List<RouteDetails> list=routeDetailsRepo.findByrouteId(routeId);
		List<Integer> stops=new ArrayList<>();

			stops.add(list.get(0).getStopNumber());
			stops.add(list.get(list.size()-1).getStopNumber());
			return stops;

    }

	public List<RouteDetails> getbyrouteId(int routeId) {
		return routeDetailsRepo.findByrouteId(routeId);
	}

    public List<String> findDistinctHaults() {
		return routeDetailsRepo.findDistinctHaults();
    }
}
