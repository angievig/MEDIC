package constraintNetwork;


import cspElements.Variable;

public class NodeVariable extends Vertex {
	
	
	private Variable var;
	
	public NodeVariable (Variable v){
		var=v;
		initialize(var.getId());
	}
	
	public Variable getVar(){
		return var;
	}
	
	



}
