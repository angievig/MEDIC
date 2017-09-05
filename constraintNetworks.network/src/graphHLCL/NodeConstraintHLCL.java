package graphHLCL;



import java.util.TreeSet;

import com.variamos.hlcl.BooleanExpression;
//import com.variamos.hlcl.HlclProgram;

//import com.variamos.hlcl.BooleanExpression;



public class NodeConstraintHLCL extends VertexHLCL {
	private BooleanExpression constraint;

	/**
	 * Creates a new constraint node.  
	 * The id in a constraint node is formed by concatening variables id's 
	 * @param id String, an id 
	 */
	//private Constraint cons;
	private TreeSet <NodeVariableHLCL> neighbors;
	
	//FIXME el ide debe ser el id de la interfaz de variamos
	
	public BooleanExpression getConstraint(){
		return constraint;
	}
	public NodeConstraintHLCL (String id, BooleanExpression c){
		initialize(id);
		constraint= c;
		addConstraint(c);
		//cons= c;
	}
	
	
	public NodeConstraintHLCL clone(){
		NodeConstraintHLCL clon= new NodeConstraintHLCL(this.getId(), this.constraint);
		return clon;
				
	}
	
	public String toString(){
		return getId()+ " "+ constraint.toString();
	}
	



}
