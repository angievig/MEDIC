package transform;

import java.util.Set;

import com.variamos.hlcl.BooleanExpression;
import com.variamos.hlcl.HlclProgram;
import com.variamos.hlcl.HlclUtil;
import com.variamos.hlcl.Identifier;

import graphHLCL.ConstraintGraphHLCL;

public class HLCL2Graph {
	private HlclProgram prog;
	private ConstraintGraphHLCL net;
	
	public HLCL2Graph( HlclProgram prog){
		this.prog= prog;
		net= new ConstraintGraphHLCL();
	}
	
	public ConstraintGraphHLCL transform(){
		
		
		for (BooleanExpression booleanExpression : prog) {
			Set<Identifier> vars=  HlclUtil.getUsedIdentifiers(booleanExpression);
			
			//in the case a constraint is a version constraint, version constraints contains just one variable
//			//FIXME there might be a better form to find a version constraint
			
			if (vars.size() ==1){
				for (Identifier identifier : vars) {
					net.addUnaryConstraint(booleanExpression, identifier);
				}
				
			}
			//if is a regular constraint
			else{
				net.addConstraint(booleanExpression);
			}
			
		}
		


			
		
		return net;
	}

}
