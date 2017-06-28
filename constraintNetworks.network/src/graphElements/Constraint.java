package graphElements;

import java.util.ArrayList;

import graphElements.Variable;

public interface Constraint {
//	public Constraint(String inId, String e);
//	public Constraint(String inId, String e, ArrayList<Variable> inVars);
	public String getExpression();
	
	public String getId();
	
	public boolean addVariable(Variable var);
	
	public ArrayList <Variable> getVars();


	public int compareTo(Constraint o);
	
	public String toString();

}
