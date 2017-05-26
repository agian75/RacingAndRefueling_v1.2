package hau.graphtheory.exercises.racingandrefueling;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class RacingAndRefuelingModifiedVariables extends ReadRacingAndRefuelingVariables {
	
	private Integer virtualCity; 
	private List<Integer[]> virtualRoads = new ArrayList<>();
	private int virtualWeight;
	
	public RacingAndRefuelingModifiedVariables(String fileName) {
		super(fileName);
		
		this.virtualWeight = 0;
		this.setVirtualCity();
		this.setVirtualRoads();
	}
	
	private void setVirtualCity() {
		//because of the EdgeWeightedGraph implementation of Princeton 
		//City 1 is cities[0]=city[1-1], so city n is cities[n-1]
		this.virtualCity = getNumberOfCities(); 
		this.setNumberOfCities(getNumberOfCities() + 1);
	}
	
	private  void setVirtualRoads() {
		Integer[] road;   
		for (Integer gasStation : getGasStationsCities()) {
			road = new Integer[3];
			road[0] = gasStation;
			road[1] = virtualCity;  
			road[2] = virtualWeight;
			
			this.virtualRoads.add(road);
		}
		getRoads().addAll(virtualRoads);
	}
	
	public Integer getVirtualCity() {
		return virtualCity;
	}

	public List<Integer[]> getVirtualRoads() {
		return virtualRoads;
	}

	public int getVirtualWeight() {
		return virtualWeight;
	}

	public void setVirtualWeight(int virtualWeight) {
		this.virtualWeight = virtualWeight;
	}
}
