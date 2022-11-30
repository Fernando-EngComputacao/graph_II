package br.com.gomide.data_structures.graph.model;


public class Vertice implements Comparable<Vertice>{
	private String label;
	
	public Vertice(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

    @Override
    public int compareTo(Vertice vertice) {
        return (this.label.equalsIgnoreCase(vertice.label) ? 0: -1);
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertice other = (Vertice) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return this.label;
	}
}
