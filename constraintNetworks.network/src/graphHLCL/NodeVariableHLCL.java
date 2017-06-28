package graphHLCL;


import java.util.ArrayList;
import java.util.TreeSet;

import com.variamos.hlcl.BooleanExpression;
import com.variamos.hlcl.HlclProgram;
import com.variamos.hlcl.Identifier;



public class NodeVariableHLCL extends VertexHLCL {
	
	private TreeSet <NodeConstraintHLCL> neighbors;
	private Identifier var;
	private HlclProgram unaryConstraints;
	
	public NodeVariableHLCL (Identifier v){
		var=v;
		unaryConstraints= new HlclProgram();
		initialize(var.getId());
	}
	
	public Identifier getVar(){
		return var;
	}
	
	public NodeVariableHLCL clone(){
		//NodeConstraint clon= new NodeConstraint(this.getId(), this.getConstraints());
		NodeVariableHLCL clon= new NodeVariableHLCL(this.var);
		clon.setConstraints(this.getConstraints());
		
		return clon;
	}
	
	public  HlclProgram getConstraints(){
		return unaryConstraints;
	}
	public boolean addConstraint(BooleanExpression c){
		return unaryConstraints.add(c);
	}
	public void setConstraints(HlclProgram constraints) {
		this.unaryConstraints = constraints;
	}



}
