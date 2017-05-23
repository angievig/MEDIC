package constraintNetwork;


import java.util.TreeSet;

import cspElements.Variable;

public class NodeVariable extends Vertex {
	
	private TreeSet <NodeConstraint> neighbors;
	private Variable var;
	
	public NodeVariable (Variable v){
		var=v;
		initialize(var.getId());
	}
	
	public Variable getVar(){
		return var;
	}
	
	public NodeVariable clone(){
		//NodeConstraint clon= new NodeConstraint(this.getId(), this.getConstraints());
		NodeVariable clon= new NodeVariable(this.var);
		clon.setConstraints(this.getConstraints());
		
		return clon;
	}



}
