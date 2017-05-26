package hau.graphtheory.exercises.racingandrefueling;
/******************************************************************************
 *  Compilation:  javac DijkstraUndirectedSP.java
 *  Execution:    java DijkstraUndirectedSP input.txt s
 *  Dependencies: EdgeWeightedGraph.java IndexMinPQ.java Stack.java Edge.java
 *  Data files:   http://algs4.cs.princeton.edu/43mst/tinyEWG.txt
 *                http://algs4.cs.princeton.edu/43mst/mediumEWG.txt
 *                http://algs4.cs.princeton.edu/43mst/largeEWG.txt
 *
 *  Dijkstra's algorithm. Computes the shortest path tree.
 *  Assumes all weights are nonnegative.
 *
 *  % java DijkstraUndirectedSP tinyEWG.txt 6
 *  6 to 0 (0.58)  6-0 0.58000
 *  6 to 1 (0.76)  6-2 0.40000   1-2 0.36000
 *  6 to 2 (0.40)  6-2 0.40000
 *  6 to 3 (0.52)  3-6 0.52000
 *  6 to 4 (0.93)  6-4 0.93000
 *  6 to 5 (1.02)  6-2 0.40000   2-7 0.34000   5-7 0.28000
 *  6 to 6 (0.00)
 *  6 to 7 (0.74)  6-2 0.40000   2-7 0.34000
 *
 *  % java DijkstraUndirectedSP mediumEWG.txt 0
 *  0 to 0 (0.00)
 *  0 to 1 (0.71)  0-44 0.06471   44-93  0.06793  ...   1-107 0.07484
 *  0 to 2 (0.65)  0-44 0.06471   44-231 0.10384  ...   2-42  0.11456
 *  0 to 3 (0.46)  0-97 0.07705   97-248 0.08598  ...   3-45  0.11902
 *  ...
 *
 *  % java DijkstraUndirectedSP largeEWG.txt 0
 *  0 to 0 (0.00)  
 *  0 to 1 (0.78)  0-460790 0.00190  460790-696678 0.00173   ...   1-826350 0.00191
 *  0 to 2 (0.61)  0-15786  0.00130  15786-53370   0.00113   ...   2-793420 0.00040
 *  0 to 3 (0.31)  0-460790 0.00190  460790-752483 0.00194   ...   3-698373 0.00172
 *
 ******************************************************************************/


import java.util.Iterator;
import java.util.Stack;

import princeton.graphtheory.graphlib.Edge;
import princeton.graphtheory.graphlib.EdgeWeightedGraph;
import princeton.graphtheory.graphlib.IndexMinPQ;
import princeton.graphtheory.stdlib.In;
import princeton.graphtheory.stdlib.StdOut;


/**
 *  The {@code DijkstraUndirectedSP} class represents a data type for solving
 *  the single-source shortest paths problem in edge-weighted graphs
 *  where the edge weights are nonnegative.
 *  <p>
 *  This implementation uses Dijkstra's algorithm with a binary heap.
 *  The constructor takes time proportional to <em>E</em> log <em>V</em>,
 *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 *  Afterwards, the {@code distTo()} and {@code hasPathTo()} methods take
 *  constant time and the {@code pathTo()} method takes time proportional to the
 *  number of edges in the shortest path returned.
 *  <p>
 *  For additional documentation,    
 *  see <a href="http://algs4.cs.princeton.edu/44sp">Section 4.4</a> of    
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne. 
 *  See {@link DijkstraSP} for a version on edge-weighted digraphs.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @author Nate Liu
 */
public class DijkstraUndirectedPtPSP {
    private double[] distTo;          // distTo[v] = distance  of shortest s->v path
    private Edge[] edgeTo;            // edgeTo[v] = last edge on shortest s->v path 
    private IndexMinPQ<Double> pq;    // priority queue of vertices
    
    
    /**
     * Computes the shortest-distance from the source vertex {@code s} 
     * to a target vertex {@code t} in the edge-weighted graph {@code G}.
     * 
     * @param  G the edge-weighted digraph
     * @param  s the source vertex
     * @param  t the target vertex
     * @throws IllegalArgumentException if an edge weight is negative
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public DijkstraUndirectedPtPSP(EdgeWeightedGraph G, int s, int t) {
    	boolean[] processed;  

    	for (Edge e : G.edges()) {
    		if (e.weight() < 0) {
    			throw new IllegalArgumentException("edge " + e + " has negative weight");
    		}
        }
    	
        distTo = new double[G.V()]; //
        edgeTo = new Edge[G.V()];
        processed = new boolean[G.V()];
        
        validateVertex(s);
        validateVertex(t);
        
        // set initialize variables
        // set distance from s to all other vertices to infinity
        // mark all nodes(cities) as unvisited
        // set parent for all nodes to null
        for (int v = 0; v < G.V(); v++) {
        	distTo[v] = Double.POSITIVE_INFINITY;
        	processed[v] = false;
        	edgeTo[v] = null;
        }
        //distance to source equal to 0    
        distTo[s] = 0.0;

        // relax vertices in order of distance from s
        pq = new IndexMinPQ<Double>(G.V());
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
        	
        	// if distance to all unprocessed nodes equals to POSITIVE_INFINITY then
        	// no path exists from s to t and break loop 
        	boolean doesNotExistSourceToTargetPath = true;
        	int i = 0;
        	while (( doesNotExistSourceToTargetPath ) && ( i<G.V() ) ) {
        		doesNotExistSourceToTargetPath =  !processed[i] && ( distTo[i] == Double.POSITIVE_INFINITY );
        		i++;
			}
        	if (doesNotExistSourceToTargetPath) {
        		System.out.println("Does not exist Path from "+ s +" to " + t );
        		break;
        	}
        	
        	// get the closest vertex (City) from indexPriorityQueue 
            int v = pq.delMin();
            
            // if vertex v = t then exit shortest path form s to t found
            // exit while
            if (v == t) {
            	break;
            }
            
            // 
            processed[v] = true;
            
            
            for (Edge e : G.adj(v)) {
            	relax(e, v);
            }
        }
        // output result to console
		//System.out.println("Shortest Path Cost from " + s + " to " + t + ": " +  distTo[t]);
		
        // check optimality conditions
        assert check(G, s);
    }

    // relax edge e and update pq if changed
    private void relax(Edge e, int v) {
        int w = e.other(v);
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            if (pq.contains(w)) {
            	pq.decreaseKey(w, distTo[w]);
            } else {
            	pq.insert(w, distTo[w]);
            }
        }
    }

    /**
     * Returns the length of a shortest path between the source vertex {@code s} and
     * vertex {@code v}.
     *
     * @param  v the destination vertex
     * @return the length of a shortest path between the source vertex {@code s} and
     *         the vertex {@code v}; {@code Double.POSITIVE_INFINITY} if no such path
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public double distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    /**
     * Returns true if there is a path between the source vertex {@code s} and
     * vertex {@code v}.
     *
     * @param  v the destination vertex
     * @return {@code true} if there is a path between the source vertex
     *         {@code s} to vertex {@code v}; {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    /**
     * Returns a shortest path between the source vertex {@code s} and vertex {@code v}.
     *
     * @param  v the destination vertex
     * @return a shortest path between the source vertex {@code s} and vertex {@code v};
     *         {@code null} if no such path
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<Edge> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<Edge> path = new Stack<Edge>();
        int x = v;
        for (Edge e = edgeTo[v]; e != null; e = edgeTo[x]) {
            path.push(e);
            x = e.other(x);
        }
        return path;
    }

    
    // check optimality conditions:
    // (i) for all edges e = v-w:            distTo[w] <= distTo[v] + e.weight()
    // (ii) for all edge e = v-w on the SPT: distTo[w] == distTo[v] + e.weight()
    private boolean check(EdgeWeightedGraph G, int s) {

        // check that edge weights are nonnegative
        for (Edge e : G.edges()) {
            if (e.weight() < 0) {
                System.err.println("negative edge weight detected");
                return false;
            }
        }

        // check that distTo[v] and edgeTo[v] are consistent
        if (distTo[s] != 0.0 || edgeTo[s] != null) {
            System.err.println("distTo[s] and edgeTo[s] inconsistent");
            return false;
        }
        for (int v = 0; v < G.V(); v++) {
            if (v == s) continue;
            if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
                System.err.println("distTo[] and edgeTo[] inconsistent");
                return false;
            }
        }

        // check that all edges e = v-w satisfy distTo[w] <= distTo[v] + e.weight()
        for (int v = 0; v < G.V(); v++) {
            for (Edge e : G.adj(v)) {
                int w = e.other(v);
                if (distTo[v] + e.weight() < distTo[w]) {
                    System.err.println("edge " + e + " not relaxed");
                    return false;
                }
            }
        }

        // check that all edges e = v-w on SPT satisfy distTo[w] == distTo[v] + e.weight()
        for (int w = 0; w < G.V(); w++) {
            if (edgeTo[w] == null) continue;
            Edge e = edgeTo[w];
            if (w != e.either() && w != e.other(e.either())) return false;
            int v = e.other(w);
            if (distTo[v] + e.weight() != distTo[w]) {
                System.err.println("edge " + e + " on shortest path not tight");
                return false;
            }
        }
        return true;
       
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    /**
     * Unit tests the {@code DijkstraUndirectedSP} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

    	ReadRacingAndRefuelingVariables x  = new ReadRacingAndRefuelingVariables("input0.txt");
    	
    	EdgeWeightedGraph raceMap;
		raceMap = new EdgeWeightedGraph(x.getNumberOfCities());

    	

		for (Iterator iterator = x.getRoads().iterator(); iterator.hasNext();) {
			Integer[] y =  (Integer[]) iterator.next();
			Edge e = new Edge(y[0], y[1], y[2]); 
			raceMap.addEdge(e);
		}
		
    	DijkstraUndirectedPtPSP sp = new DijkstraUndirectedPtPSP(raceMap,0,5);
    			
//        In in = new In(args[0]);
//        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
//        int s = 1;
//        int t = 5;
//        
//        // compute shortest paths
//        DijkstraUndirectedPtPSP sp = new DijkstraUndirectedPtPSP(G, s, t);
//
//
//        // print shortest path
//        for (int j = 0; t < G.V(); j++) {
//            if (sp.hasPathTo(t)) {
//                StdOut.printf("%d to %d (%.2f)  ", j, t, sp.distTo(j));
//                for (Edge e : sp.pathTo(j)) {
//                    StdOut.print(e + "   ");
//                }
//                StdOut.println();
//            }
//            else {
//                StdOut.printf("%d to %d         no path\n", s, t);
//            }
//        }
    }
    
}

/******************************************************************************
 *  Copyright 2002-2016, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/
