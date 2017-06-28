package transform;

import cspElements.CSP;
import cspElements.Constraint;
import cspElements.Variable;
import graph.ConstraintGraph;

public class CSP2Network {
	private CSP csp;
	private ConstraintGraph net;
	
	public CSP2Network(CSP csp){
		this.csp= csp;
		net= new ConstraintGraph();
	}
	
	public ConstraintGraph transform(){
		
		//loading variables
		for (Variable var : csp.getVariables()) {
			net.addVariable(var);
		}
		//loading constraints
		for (Constraint cons : csp.getConstraints()) {
//			//in the case a constraint is a version constraint, version constraints contains just one variable
//			//FIXME there might be a better form to find a version constraint
//	
//			 net.addConstraint(cons);
			
			
			//in the case a constraint is a version constraint, version constraints contains just one variable
			//FIXME there might be a better form to find a version constraint
			if (cons.getVars().size()==1){
				net.addUnaryConstraint(cons, cons.getVars().get(0));
			}
			//if is a regular constraint
			else{
				net.addConstraint(cons);
			}
			
		}
		return net;
	}
	

}
