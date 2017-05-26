package hau.graphtheory.exercises.racingandrefueling;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import princeton.graphtheory.graphlib.Edge;
import princeton.graphtheory.graphlib.EdgeWeightedGraph;

public class RacingAndRefueling {
	
	private double totalDistance; //route + n-refuel distance  
	private double routeDistance; //route distance
	private double refuelDistance; //n-refuel distance
	
	private EdgeWeightedGraph raceMap;
	
	public RacingAndRefueling(RacingAndRefuelingModifiedVariables input) {
		
		//create map of race (Edge Weighted Graph)
		this.raceMap = new EdgeWeightedGraph(input.getNumberOfCities());
		
		//Update the Race and Refueling map properties
		//Cities Already created (EdgeWeightedGraph constructor)
		//Roads and distances
		for (Iterator iterator = input.getRoads().iterator(); iterator.hasNext();) {
			Integer[] x =  (Integer[]) iterator.next();
			Edge e = new Edge(x[0], x[1], x[2]); 
			this.raceMap.addEdge(e);
		}
		
		//Find Route Distance
		routeDistance = new RouteDistanceCost(raceMap,input.getRouteCities()).getRouteDistance();
		
		//Find n-Refueling Distance
		//refuelDistance = new RefuelingDistanceCost(raceMap, input.getRouteCities(), input.getGasStationsCities(), input.getNumberOfRefueling()).getRefuelingDistance();
		refuelDistance = new RefuelingDistanceCost(raceMap, input.getRouteCities(), input.getGasStationsCities(), input.getNumberOfRefueling(), input.getVirtualCity()).getRefuelingDistance();
		
		totalDistance = routeDistance + refuelDistance;
		
		System.out.printf("Total Distance: %f\n" , totalDistance);
	}

	public static void main(String[] args) {
		
		//read from console name of input file 
		//Validate name
		double startTime   = System.currentTimeMillis();
		
		new RacingAndRefueling(new RacingAndRefuelingModifiedVariables("input14.txt"));
		
		double endTime   = System.currentTimeMillis();
		
		double totalTime = endTime - startTime;
		
		System.out.println("Calculation Time: " + totalTime/60000 +" mins");
	}

}
	

