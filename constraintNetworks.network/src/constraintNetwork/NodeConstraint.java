package constraintNetwork;



import cspElements.Constraint;

public class NodeConstraint extends Vertex {

	/**
	 * Creates a new constraint node.  
	 * The id in a constraint node is formed by concatening variables id's 
	 * @param id String, an id 
	 */
	public NodeConstraint (String id, Constraint c){
		initialize(id);
		addConstraint(c);
	}
	
	public String toString(){
		return  getConstraints().get(0).toString();
	}
	



}
