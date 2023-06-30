package com.bus.Service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bus.Repo.BusRepository;
import com.bus.model.Bus;

@Service
public class BusService{
    @Autowired
    private BusRepository busRepo;

	public Bus save(Bus bus) {
		return busRepo.save(bus);
		
		
	}

	public List<Bus> getall() {
		// TODO Auto-generated method stub
		return busRepo.findAll();
	}

	public Optional<Bus> getbybusId(int busId) {
		// TODO Auto-generated method stub
		return busRepo.findById(busId);
	}

	public void delete(int busId) {
		// TODO Auto-generated method stub
		busRepo.deleteById(busId);
		
		
	}
}
    
    
