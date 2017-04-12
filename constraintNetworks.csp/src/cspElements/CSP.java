package cspElements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class CSP {
	private TreeSet<Variable> variables;
	//private Set<Stri> domains;
	private TreeSet<Constraint> constraints;
	
	public CSP(){
		variables= new TreeSet<Variable>();
		//domains= new HashSet<String>();
		constraints = new TreeSet<Constraint>();
	}
	
	public TreeSet<Variable> getVariables() {
		return variables;
	}
	public void setVariables(TreeSet<Variable> variables) {
		this.variables = variables;
	}
	public TreeSet<Constraint> getConstraints() {
		return constraints;
	}
	public void setConstraints(TreeSet<Constraint> constraints) {
		this.constraints = constraints;
	}
	
	public void setEmptyConstraints(){
		constraints = new TreeSet<Constraint>();
	}
	
	public boolean addConstaint(Constraint cons){
		return constraints.add(cons);
	}
	
	public boolean addConstaintList(ArrayList<Constraint> list){
		return constraints.addAll(list);
	}
	
	public boolean addVarToConstraint(Constraint constraint, String varId){
	 boolean cons= constraints.contains(constraint);
	 for (Variable var : variables) {
		 if ((var.getId().equals(varId))&& cons){
			 constraint.addVariable(var);
		 }
		
	}
	 
	 return cons;
		
	}
	
	public String getStart(){
		
		return variables.first().getId();
	}


	

}
