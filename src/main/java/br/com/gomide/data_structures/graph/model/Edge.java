package br.com.gomide.data_structures.graph.model;

public class Edge implements Comparable<Edge>{
	
	private Vertice startpoint;
	private Vertice endpoint;
	private boolean isResolved = false;
	
	public Edge(Vertice startpoint, Vertice endpoint) {
		this.startpoint = startpoint;
		this.endpoint = endpoint;
	}
	
	public Vertice getStartpoint() {
		return startpoint;
	}
	public void setStartpoint(Vertice startpoint) {
		this.startpoint = startpoint;
	}
	public Vertice getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(Vertice endpoint) {
		this.endpoint = endpoint;
	}
	public boolean isResolved() {
		return isResolved;
	}
	public void switchResolved() {
		this.isResolved = !this.isResolved;
	}
	
	
    @Override
    public int compareTo(Edge edge) {
    	if ((this.startpoint.equals(edge.startpoint)) && (this.endpoint.equals(edge.endpoint)))
			return 0;
		else
			return -1;
    }
    

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endpoint == null) ? 0 : endpoint.hashCode());
		result = prime * result + ((startpoint == null) ? 0 : startpoint.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		if (endpoint == null) {
			if (other.endpoint != null)
				return false;
		} else if (!endpoint.equals(other.endpoint))
			return false;
		if (startpoint == null) {
			if (other.startpoint != null)
				return false;
		} else if (!startpoint.equals(other.startpoint))
			return false;
		return true;
	}

	public String toString(boolean isDirected) {
    	String arrow = isDirected ? " -> ": " ";
		return this.getStartpoint().getLabel() + arrow + this.getEndpoint().getLabel();	
    }
	
	public String toString() {
		return this.getStartpoint().getLabel() + "-" + this.getEndpoint().getLabel() + "\n";	
    }

}
