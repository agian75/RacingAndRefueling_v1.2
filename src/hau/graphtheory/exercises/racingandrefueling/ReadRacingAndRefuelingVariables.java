package hau.graphtheory.exercises.racingandrefueling;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthSplitPaneUI;

public class ReadRacingAndRefuelingVariables {
	
	private int numberOfCities=-1;
	private int numberOfRoads=-1; 
	private int numberOfRacingRouteCities=-1; 
	private int numberOfRefueling=-1; 
	private int numberOfGasStations=-1; 
	
	private List<Integer[]> roads = new ArrayList<>();
	private List<Integer> routeCities = new ArrayList<>();
	private List<Integer> gasStationsCities = new ArrayList<>();
	
	public ReadRacingAndRefuelingVariables(String fileName) {
		
		Scanner lines = null;
		try {
			lines = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Incorrect Filename!");
			System.exit(1);
		}
				
		int parameterType = 1;
		
		String[] numbers; 
		String s;
		try {
			while(lines.hasNext()){
				switch (parameterType) {	
					case 1:
						s=lines.nextLine();
						numbers = s.trim().split("\\s+");
						numberOfCities = Integer.valueOf(numbers[0]);
						numberOfRoads = Integer.valueOf(numbers[1]);
						numberOfRacingRouteCities = Integer.valueOf(numbers[2]);
						numberOfRefueling = Integer.valueOf(numbers[3]);
						numberOfGasStations = Integer.valueOf(numbers[4]);
						parameterType=2;
						break;
					case 2: 
						int i=0;
						
						while ((i < numberOfRoads) && (lines.hasNext()))  {
							Integer[] road;
							
							s=lines.nextLine();
							numbers = s.trim().split("\\s+");
							
							road = new Integer[3];
							road[0] = Integer.valueOf(numbers[0])-1; 
							road[1] = Integer.valueOf(numbers[1])-1;
							road[2] = Integer.valueOf(numbers[2]);
							roads.add(road);
							i++;
						}
						parameterType=3;
						break;
					case 3:
						//routeCities
						i=0;
						while ((i < numberOfRacingRouteCities) && (lines.hasNext())){
							s=lines.nextLine().trim();
							routeCities.add(Integer.valueOf(s)-1);
							i++;
						}
						parameterType=4;
						break;
					case 4:
						//gasStationsCities
						i=0;
						while ((i < numberOfGasStations) && (lines.hasNext())){
							s=lines.nextLine().trim();
							gasStationsCities.add(Integer.valueOf(s)-1);
							i++;
						}
						parameterType=0;
						break;
					default:
						break;
				}
			}
		} catch (Exception e) {
			System.out.println("Input File Structure Error");
			//System.out.println(e.getMessage());
			System.exit(1);
		}

		this.printAll();
	}
	
	public int getNumberOfCities() {
		return numberOfCities;
	}

	public void setNumberOfCities(int numberOfCities) {
		this.numberOfCities = numberOfCities;
	}

	public int getNumberOfRacingRouteCities() {
		return numberOfRacingRouteCities;
	}

	public void setNumberOfRacingRouteCities(int numberOfRacingRouteCities) {
		this.numberOfRacingRouteCities = numberOfRacingRouteCities;
	}

	public int getNumberOfRefueling() {
		return numberOfRefueling;
	}

	public void setNumberOfRefueling(int numberOfRefueling) {
		this.numberOfRefueling = numberOfRefueling;
	}

	public int getNumberOfGasStations() {
		return numberOfGasStations;
	}

	public void setNumberOfGasStations(int numberOfGasStations) {
		this.numberOfGasStations = numberOfGasStations;
	}

	public List<Integer[]> getRoads() {
		return roads;
	}

	public void setRoads(List<Integer[]> roads) {
		this.roads = roads;
	}

	public List<Integer> getRouteCities() {
		return routeCities;
	}

	public void setRouteCities(List<Integer> routeCities) {
		this.routeCities = routeCities;
	}

	public List<Integer> getGasStationsCities() {
		return gasStationsCities;
	}

	public void setGasStationsCities(List<Integer> gasStationsCities) {
		this.gasStationsCities = gasStationsCities;
	}

	public int getNumberOfRoads() {
		return numberOfRoads;
	}

	public void printRacingAndRefuelingVariables() {
		
		printBeginSection("Racing And Refueling");
		
		System.out.println("Number of Cities: " + numberOfCities );
		System.out.println("racing Route: " + numberOfRacingRouteCities );
		System.out.println("Number Of Refuelings: " + numberOfRefueling );
		System.out.println("Number Of Gas Stations: " + numberOfGasStations );
		
		printEndSection("Racing And Refueling");
	}
	
	public void printGasStations() {
		Integer gasStation;
		
		printBeginSection("Gas Stations");
		
		Iterator<Integer> gasStationCity = gasStationsCities.iterator();
		if ( gasStationsCities.isEmpty() ) {
			System.out.println("No Gas Stations exits");
			System.out.println("Please re-check your input file");
		} else {
			gasStation = (Integer) gasStationCity.next();
			System.out.print("Gas Stations at Cities: " + gasStation);
			while (gasStationCity.hasNext()) {
				gasStation = (Integer) gasStationCity.next();
				System.out.print(", " + gasStation );
			}
			System.out.println();
		}
		printEndSection("Gas Stations");
	}

	private void printBeginSection(String title) {
		
		System.out.println();
		System.out.println(title);
		System.out.println(new String(new char[title.length()]).replace("\0", "-"));
		
	}
	
	private void printEndSection(String title) {
		System.out.println();
		//System.out.println(new String(new char[title.length()]).replace("\0", "-"));
	
	}
	
	public void printRouteCities() {
		Integer routeCity;
		
		printBeginSection("Route Cities");
		
		Iterator<Integer> routeCities = this.routeCities.iterator();
		if ( this.routeCities.isEmpty() ) {
			System.out.println("No cities exist in route");
			System.out.println("Please re-check your input file");
		} else {
			routeCity = (Integer) routeCities.next();
			System.out.print("Racing route: " + routeCity);
			while (routeCities.hasNext()) {
				routeCity = (Integer) routeCities.next();
				System.out.print(" -> " + routeCity );
			}
			System.out.println();
		}
		printEndSection("Route Cities");
	}
	
	public void printRoads() {
		Integer[] road;
		printBeginSection("Roads");
		
		Iterator<Integer[]> roadIterator = roads.iterator();
		if ( roads.isEmpty() ) {
			System.out.println("No Roads exit between Cities");
			System.out.println("Please re-check your input file");
		} else {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			int i=1;
			while (roadIterator.hasNext()) {
				road = roadIterator.next();
				System.out.println("Road: " + i + ", Cities: " + road[0] + " - " + road[1] +  ", Distance: " +  road[2]);
				i++;
			}
		}
		
		printEndSection("Roads");	
	}
	
	public void printAll() {
		printRacingAndRefuelingVariables();
		printRoads();
		printRouteCities();
		printGasStations();
	}

	
}
