package hau.graphtheory.exercises.racingandrefueling;

import princeton.graphtheory.stdlib.StdOut;

public class OrderedSortedMinArray<Key extends Comparable<Key>> {
    private Key[] pq;          // elements
    private int n;             // number of elements

    // set initial size of heap to hold size elements
    public OrderedSortedMinArray(int capacity) {
        pq = (Key[]) (new Comparable[capacity]);
        n = 0;
    }

    public boolean isFull() {
    	return n == pq.length;  	
    }
    
    public boolean isEmpty() {
    	return n == 0;  			
    }
    
    public int size() {
    	return n;
    }
    
    public Key delMin() {
    	return pq[--n]; 			
    }
    
    public Key returnMax() {
    	return pq[0]; 		
    }
    
    public Key returnMin() {
    	return pq[n-1]; 		
    }
    
    public void insert(Key key) {    
    	if ( isEmpty() ) {
        	pq[0] = key;
        	n++;
        } else if (!isFull()) {
        	int i = n-1;
            while (i >= 0 && greaterOrEqual(key, pq[i])) {
            	pq[i+1] = pq[i];
            	i--;
            }
            pq[i+1] = key;
            n++;
        } else if (lessOrEqual(key, pq[0])) {
        	int i=1; 
        	while (i <= n-1 && lessOrEqual(key, pq[i])) {
        		pq[i-1] = pq[i];
        		i++;
        	}
        	pq[i-1] = key;
        }
    	
    }
    
    public void printAll() {    
    	for (int i = 0; i < pq.length; i++) {
			System.out.println(pq[i]);
		}
    		
    }
    
    private boolean greaterOrEqual(Key v, Key w) {
        return v.compareTo(w) >= 0;
    }
    
    private boolean lessOrEqual(Key v, Key w) {
        return v.compareTo(w) <= 0;
    }
    

   /***************************************************************************
    * Test routine.
    ***************************************************************************/
    public static void main(String[] args) {
        OrderedSortedMinArray<Integer> pq = new OrderedSortedMinArray<Integer>(6);
        
        
        pq.insert(300);
        pq.insert(80);
        pq.insert(350);
        
        pq.insert(70);
        pq.insert(100);
        pq.insert(10);
        pq.insert(1);
        pq.insert(6);
        pq.insert(60);
        pq.insert(50);
        pq.insert(70);
        pq.insert(6000);
        pq.insert(69);
        pq.insert(1);
        pq.insert(5);
        
//        pq.insert(1);
        
        System.out.println("Min="+ pq.returnMin());
        System.out.println("Max="+ pq.returnMax());
        
        
        while (!pq.isEmpty())
            StdOut.println(pq.delMin());
        //StdOut.println(pq.size());
    }

}

