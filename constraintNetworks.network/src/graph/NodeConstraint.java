package graph;



import java.util.TreeSet;

import cspElements.Constraint;

public class NodeConstraint extends Vertex {

	/**
	 * Creates a new constraint node.  
	 * The id in a constraint node is formed by concatening variables id's 
	 * @param id String, an id 
	 */
	//private Constraint cons;
	private TreeSet <NodeVariable> neighbors;
	public NodeConstraint (String id, Constraint c){
		initialize(id);
		addConstraint(c);
		//cons= c;
	}
	
	public String toString(){
		return  getConstraints().get(0).toString();
	}
	
	public NodeConstraint clone(){
		NodeConstraint clon= new NodeConstraint(this.getId(), this.getConstraints().get(0));
		return clon;
				
	}
	



}
