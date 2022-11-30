package br.com.gomide.data_structures.graph.model;

public class NonDirectedGraph extends Graph {
		
	   public String toString() {
	    	String nodes = super.toString();
	    	
	    	String link = this.getEdges()
	    	.stream()
	    	.map(edge -> edge.toString(false))
	    	.reduce("", (previous, current) -> (previous + "\n Link: " + current))
	    	.toString();
	    			
			return nodes + link;	
	    }
    
}
