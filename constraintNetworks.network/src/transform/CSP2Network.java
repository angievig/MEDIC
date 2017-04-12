package transform;

import constraintNetwork.Network;
import cspElements.CSP;
import cspElements.Constraint;
import cspElements.Variable;

public class CSP2Network {
	private CSP csp;
	private Network net;
	
	public CSP2Network(CSP csp){
		this.csp= csp;
		net= new Network();
	}
	
	public Network transform(){
		
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
				net.addVersionConstraint(cons, cons.getVars().get(0));
			}
			//if is a regular constraint
			else{
				net.addConstraint(cons);
			}
			
		}
		return net;
	}
	

}
