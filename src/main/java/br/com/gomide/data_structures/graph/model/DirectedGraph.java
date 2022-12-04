package br.com.gomide.data_structures.graph.model;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class DirectedGraph extends Graph {

	
    public String toString() {
    	String nodes = super.toString();
    	
    	String link = super.getEdges()
    	.stream()
    	.map(edge -> edge.toString(true))
    	.reduce("", (previous, current) -> (previous + "\n Link: " + current))
    	.toString();
    			
		return nodes + link;	
    }
    

}