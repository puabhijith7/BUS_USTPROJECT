package com.example.busservice1.dto;



public class RouteDto {
	private int routeId;
	private String Source;
    private String Destination;
    private int totalDistance;
    private int farePerKm;
	
    public RouteDto(int routeId, String source, String destination, int totalDistance, int farePerKm) {
		
		this.routeId = routeId;
		Source = source;
		Destination = destination;
		this.totalDistance = totalDistance;
		this.farePerKm = farePerKm;
	}
    public RouteDto() {}
	
    public int getRouteId() {
		return routeId;
	}
	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}
	public String getSource() {
		return Source;
	}
	public void setSource(String source) {
		Source = source;
	}
	public String getDestination() {
		return Destination;
	}
	public void setDestination(String destination) {
		Destination = destination;
	}
	public int getTotalDistance() {
		return totalDistance;
	}
	public void setTotalDistance(int totalDistance) {
		this.totalDistance = totalDistance;
	}
	public int getFarePerKm() {
		return farePerKm;
	}
	public void setFarePerKm(int farePerKm) {
		this.farePerKm = farePerKm;
	}
	

   
}

