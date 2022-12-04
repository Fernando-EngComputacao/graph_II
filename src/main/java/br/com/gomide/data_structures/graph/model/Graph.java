package br.com.gomide.data_structures.graph.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
	
	public void addEdge(String firstLabel, String secondLabel, int weight) {
		Vertice verticeS = findVertice(firstLabel);
		Vertice verticeE = findVertice(secondLabel);
		Edge edge = new Edge(verticeS, verticeE, weight);
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
	
	public String getShortestPathFrom(String origin, String destination) {
		Vertice verticeOrigin = findVertice(origin);
		getPath(verticeOrigin);
		Vertice verticeDestination = findVertice(destination);
		return "Start -> " + getPathString(verticeDestination, "") + " -> End";
	}
	
	private String getPathString(Vertice current, String path) {
		if(current.getFather() != null) {
			path = " (" + current.getDistance() + ") " + current.getLabel() + path;
			return getPathString(current.getFather(), path);
		}
		return current.getLabel() + path;
	}
	
	private void getPath(Vertice current) {
		List<Vertice> nextV = new ArrayList<Vertice>();
		List<Edge> currentEdge = this.edges
				.stream()
				.filter(e -> e.getStartpoint().equals(current) || e.getEndpoint().equals(current))
				.collect(Collectors.toList());
		
		currentEdge = currentEdge.stream().sorted(Comparator.comparingInt(Edge::getWeight)).collect(Collectors.toList());
		
		currentEdge.forEach(e-> {
			Vertice endPoint =  e.getEndpoint().equals(current) ? this.findVertice(e.getStartpoint().getLabel()) : this.findVertice(e.getEndpoint().getLabel());
			this.edges.remove(e);
			
			if(e.getWeight() < endPoint.getDistance()) {
				endPoint.setDistance(e.getWeight());
				endPoint.setFather(current);
				updateElementVertice(endPoint);
			}
			nextV.add(endPoint);
		});
		
		nextV.forEach(v -> getPath(v));
	}
	
	private void updateElementVertice(Vertice vertice) {
		int i = this.vertices.indexOf(vertice);
		this.vertices.set(i, vertice);
	}
}
