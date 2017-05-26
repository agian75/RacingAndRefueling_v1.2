package hau.graphtheory.exercises.racingandrefueling;

import java.util.Iterator;
import java.util.List;

import princeton.graphtheory.graphlib.Edge;
import princeton.graphtheory.graphlib.EdgeWeightedGraph;

public class RouteDistanceCost {
	private double routeDistance; 
	
	public double getRouteDistance() {
		return routeDistance;
	}

	public RouteDistanceCost(EdgeWeightedGraph G, List<Integer> routeCities) {
		int n = routeCities.size()-1;
		while ( n>=1 ) {
			
			Iterable<Edge> edges = G.adj(routeCities.get(n));
			boolean found=false;
			for (Edge edge : edges) {
				int graph_city = routeCities.get(n);
				int adj_graph_city = routeCities.get(n-1);	
				found = edge.other(routeCities.get(n)) == (routeCities.get(n-1));
				if (found) {
					routeDistance += edge.weight();
					System.out.print("Distance from City: " + routeCities.get(n-1) ); 
					System.out.print(" to City: " + routeCities.get(n) + " is :" +  edge.weight());
					System.out.println();
					break;					
				}
			}
			if (!found) {
				System.out.println("Incorrect route distance calculation.");
				System.out.println("An edge is missing from the Adjacency List");
			}
			n--;
		}
		System.out.println("Total route(Path) Distance:" + routeDistance);
	}
}