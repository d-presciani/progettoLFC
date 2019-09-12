package graph;

import org.jgrapht.graph.DefaultEdge;

//Classe per cambiare nome sulla freccia
class RelationshipEdge extends DefaultEdge {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8132020941179850957L;
	private String label;

	/**
	 * Constructs a relationship edge
	 *
	 * @param label the label of the new edge.
	 * 
	 */
	public RelationshipEdge(String label) {
		this.label = label;
	}

	/**
	 * Gets the label associated with this edge.
	 *
	 * @return edge label
	 */
	public String getLabel() {
		return label;
	}

	@Override
	public String toString() {
		return label;
	}
}