package br.com.gomide.data_structures.graph.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.gomide.data_structures.graph.model.DirectedGraph;
import br.com.gomide.data_structures.graph.model.Graph;
import br.com.gomide.data_structures.graph.model.Edge;
import br.com.gomide.data_structures.graph.model.Vertice;
import br.com.gomide.data_structures.graph.model.NonDirectedGraph;

public class GraphService implements IGraphService {

	@Override
	public void addNodes(List<String> nodes, Graph graph) {
		nodes.forEach(label -> addNode(label, graph));
	}

	@Override
	public void addNode(String node, Graph graph) {
		graph.addVertice(node);
	}

	@Override
	public void connectNode(String firstNode, String secondNode, Graph graph) {
		graph.addEdge(firstNode, secondNode);
	}

	@Override
	public void connectNode(String firstNode, String secondNode, Integer weight, Graph graph) {
		graph.addEdge(firstNode, secondNode, weight);

	}

	@Override
	public int countLoops(Graph graph) {
		return (int) graph.getEdges()
				.stream()
				.filter(edge -> edge.getStartpoint().equals(edge.getEndpoint()))
				.count();
	}

	@Override
	public int countMultipleLink(Graph graph) {
		return (int) graph.getEdges()
				.stream()
				.distinct()
				.filter(edge -> Collections.frequency(graph.getEdges(), edge) > 1)
				.count();
	}

	@Override
	public int nodeDegree(String node, Graph graph) {
		return (int) graph.getEdges()
				.stream()
				.filter(edge -> edge.getStartpoint().getLabel().equals(node) || edge.getEndpoint().getLabel().equals(node))
				.count();
	}

	@Override
	public boolean isConnected(Graph graph) {
		List<Vertice> v = walkInGraphBy(graph.getVertice(), graph.getEdges(), graph.getVertice().get(0));
		return v.isEmpty();
	}

	@Override
	public boolean isComplete(Graph graph) {
		boolean state = true;
		for (Vertice element : graph.getVertice()) {
			int soma = (int) graph.getEdges()
					.stream()
					.filter(edge -> edge.getEndpoint().getLabel().equals(element.getLabel()) || edge.getStartpoint().getLabel().equals(element.getLabel()))
					.count();
			state = (state == false ? false : (soma != (graph.getVertice().size() - 1) ? false : true));
		}
		return state;
	}

	@Override
	public String showPath(String origin, String destination, DirectedGraph graph) {
		return this.setUpPath(origin, destination, graph, true);
	}

	@Override
	public String showPath(String origin, String destination, NonDirectedGraph graph) {
		return this.setUpPath(origin, destination, graph, false);
	}

	@Override
	public String toString(Graph graph) {
		return graph.toString();
	}

	@Override
	public String showShortestPath(String origin, String destination, DirectedGraph graph) {
		return graph.getShortestPathFrom(origin, destination);
	}

	@Override
	public String showShortestPath(String origin, String destination, NonDirectedGraph graph) {
		return graph.getShortestPathFrom(origin, destination);
	}
	
	private List<Vertice> walkInGraphBy(List<Vertice> vertices, List<Edge> edges, Vertice origin) {
		List<Edge> edgesByOrigin = edges
				.stream()
				.filter(edge -> !edge.isResolved() && edge.getStartpoint().equals(origin))
				.collect(Collectors.toList());
		int s = edgesByOrigin.size();
		if (s > 0) {
			edgesByOrigin.forEach(edge -> {
				edges.stream().filter(e -> e.equals(edge)).forEach(e-> e.switchResolved());
				vertices.remove(origin);
				walkInGraphBy(vertices, edges, edge.getEndpoint());
			});
		} 
		return vertices;
	}

	private String setUpPath(String origin, String destination, Graph graph, boolean isDirected) {
		List<String> list = new ArrayList<>();
		
		if (isDirected && !isValidPath(origin, destination, graph.getEdges())) {
			return "INVALID PATH";
		} 
		
		for (Vertice vertice : graph.getVertice()) {
			 List<String> p = graph.getEdges()
			.stream()
			.filter(edge -> edge.getStartpoint().equals(vertice))
			.map(point -> point.getEndpoint().toString())
			.sorted()
			.map(point -> vertice + " " + point)
			.collect(Collectors.toList());
			 
			 list = Stream.concat(list.stream(), p.stream()).collect(Collectors.toList());
		}
		
		String way = list.stream().reduce("Start ->", (previous, current) -> (previous + " " + current)) + " -> End";
		
		if (!(way.contains(destination) && way.contains(origin))) {
			way = "INVALID PATH";
		}
		
		return way;
	}
	
	private boolean isValidPath(String origin, String destination, List<Edge> edges) {
		int originStart = (int) edges.stream().filter(e -> e.getStartpoint().getLabel().equals(origin)).count();
		int destinationEnd = (int) edges.stream().filter(e -> e.getEndpoint().getLabel().equals(destination)).count();
		
		return (originStart >= 1) && (destinationEnd >= 1);
	}
}
