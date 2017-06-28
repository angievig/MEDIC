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
	public NodeConstraintHLCL (String id, BooleanExpression c){
		initialize(id);
		constraint= c;
		
		//cons= c;
	}
	
	public String toString(){
		return  this.constraint.toString();
	}
	
	public NodeConstraintHLCL clone(){
		NodeConstraintHLCL clon= new NodeConstraintHLCL(this.getId(), this.constraint);
		return clon;
				
	}
	



}
