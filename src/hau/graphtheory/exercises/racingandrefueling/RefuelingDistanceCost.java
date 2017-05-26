package hau.graphtheory.exercises.racingandrefueling;

import java.text.DecimalFormat;
import java.util.List;

import princeton.graphtheory.graphlib.Edge;
import princeton.graphtheory.graphlib.EdgeWeightedGraph;
import princeton.graphtheory.stdlib.StdOut;

public class RefuelingDistanceCost {

	//Minimum distance costs for every refueling
	//Contains the n(# of refueling)-lower total distances from every
	//different city to a gas station
	//
	//We use double data type although we know that weights we have 
	//as input are integers b/c the implementation we use for weighted
	//undirected graphs uses double data type for weights. 
	//private double[] distanceCost;
	private OrderedSortedMinArray<Double> distanceCost; 
	private double refuelingDistance;  
	
	private static DecimalFormat df2 = new DecimalFormat(".##"); 

	public double getRefuelingDistance() {
		return refuelingDistance;
	}
	
	//overload constructor for implementations from version 1.0 and on
	public RefuelingDistanceCost(EdgeWeightedGraph G, List<Integer> routeCities, List<Integer> gasStationsCities, int numberOfRefuelings , int virtualCity) {
		double endTime, startTime, totalTime;		
		//Do not forget to check if #r<G(V)
		
		//n closest distances from all routeCities to All Gas Stations 
		distanceCost = new OrderedSortedMinArray<Double>(numberOfRefuelings);
		
		//counter cities of route examined (-2 b/c we do not examine first and last city)
		int i=1;
		
		//for every city of the route
		for (Integer routeCity : routeCities ) {
			
			if (routeCity==routeCities.get(0) || routeCity==routeCities.get(routeCities.size()-1)){
				continue;
			}
			//Distance from routeCity to closest GasStation
			Double minAllGasStationsDistance = Double.POSITIVE_INFINITY;
			
			//start time for city
			startTime   = System.currentTimeMillis();
			
			minAllGasStationsDistance = new DijkstraUndirectedPtPSP(G, routeCity, virtualCity).distTo(virtualCity);
//			for (Integer gasStation : gasStationsCities) {
//				
//				//calculate shortest path distance 
//				//routeCity -> gasStation			
//				Double minGasStationDistance = new DijkstraUndirectedPtPSP(G, routeCity, gasStation).distTo(gasStation);
//				
//				System.out.println("City:" + Integer.valueOf(routeCity+1) + " gasStation:" +  Integer.valueOf(gasStation+1) + " Distance:" + minGasStationDistance);
//				//if distance of shortest path is less than the 
//				//minimum distance of all the other shortest paths keep it
//				if ( minAllGasStationsDistance > minGasStationDistance ) {
//					minAllGasStationsDistance = minGasStationDistance;
//				}
//				
//			}
			
			endTime   = System.currentTimeMillis();
			totalTime = endTime - startTime;
			System.out.println("City refuel distance calculation time: " + totalTime/1000 +" sec");
			
			//print percentage done
			System.out.println("City " + i + " from " + (routeCities.size()-2));
			double t = routeCities.size()-2;
			double percent = i / t * 100;
			System.out.println( df2.format(percent) + "%...");
			i++;
			// and insert it to the n-minimum shortest path array  
			distanceCost.insert(minAllGasStationsDistance);
		}
		//distanceCost.printAll();
		while (!distanceCost.isEmpty()) {
			refuelingDistance += distanceCost.delMin();
		}
		System.out.println("Refueling Cost:" + refuelingDistance);
	}
	
	//constructor of implementations till version 0.8
	public RefuelingDistanceCost(EdgeWeightedGraph G, List<Integer> routeCities, List<Integer> gasStationsCities, int numberOfRefuelings) {
		double endTime, startTime, totalTime;		
		//Do not forget to check if #r<G(V)
		
		//n closest distances from all routeCities to All Gas Stations 
		distanceCost = new OrderedSortedMinArray<Double>(numberOfRefuelings);
		
		//counter cities of route examined (-2 b/c we do not examine first and last city)
		int i=1;
		
		//for every city of the route
		for (Integer routeCity : routeCities ) {
			
			if (routeCity==routeCities.get(0) || routeCity==routeCities.get(routeCities.size()-1)){
				continue;
			}
			//Distance from routeCity to closest GasStation
			Double minAllGasStationsDistance = Double.POSITIVE_INFINITY;
			
			//start time for city
			startTime   = System.currentTimeMillis();
			
			for (Integer gasStation : gasStationsCities) {
				
				//calculate shortest path distance 
				//routeCity -> gasStation			
				Double minGasStationDistance = new DijkstraUndirectedPtPSP(G, routeCity, gasStation).distTo(gasStation);
				
				System.out.println("City:" + Integer.valueOf(routeCity+1) + " gasStation:" +  Integer.valueOf(gasStation+1) + " Distance:" + minGasStationDistance);
				//if distance of shortest path is less than the 
				//minimum distance of all the other shortest paths keep it
				if ( minAllGasStationsDistance > minGasStationDistance ) {
					minAllGasStationsDistance = minGasStationDistance;
				}
				
			}
			endTime   = System.currentTimeMillis();
			totalTime = endTime - startTime;
			System.out.println("City refuel distance calculation time: " + totalTime/1000 +" sec");
			
			//print percentage done
			System.out.println("City " + i + " from " + (routeCities.size()-2));
			double t = routeCities.size()-2;
			double percent = i / t * 100;
			System.out.println( df2.format(percent) + "%...");
			i++;
			// and insert it to the n-minimum shortest path array  
			distanceCost.insert(minAllGasStationsDistance);
		}
		//distanceCost.printAll();
		while (!distanceCost.isEmpty()) {
			refuelingDistance += distanceCost.delMin();
		}
		System.out.println("Refueling Cost:" + refuelingDistance);
	} 
}
