package br.com.gomide.data_structures.graph.model;

import java.util.ArrayList;

public abstract class Graph {
	private ArrayList<Vertice> vertices = new ArrayList<Vertice>();
	private ArrayList<Edge> edges = new ArrayList<Edge>();

	public ArrayList<Vertice> getVertice() {
		return vertices;
	}
	
	public ArrayList<Edge> getEdges() {
		return edges;
	}
	
	
	public void addVertice(String label) {
		Vertice v = new Vertice(label);
		if(!vertices.contains(v)) {
			vertices.add(v);
		}
	}
	
	public void addEdge(String firstLabel, String secondLabel) {
		Vertice verticeS = findVertice(firstLabel);
		Vertice verticeE = findVertice(secondLabel);
		Edge edge = new Edge(verticeS, verticeE);
		edges.add(edge);
	}
	
	private Vertice findVertice(String vertice) {
		return this.vertices
				.stream()
				.filter(v -> vertice.equals(v.getLabel()))
				.findFirst()
				.orElse(null);
	}
	
	@Override
	public String toString() {
		return this.getVertice().stream()
	    	.map(vertice -> vertice.getLabel())
	    	.reduce("Nodes:", (previous, current) -> (previous + " " + current))
	    	.toString();	    			
	}
}
