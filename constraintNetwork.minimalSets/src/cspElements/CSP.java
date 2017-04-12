package cspElements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CSP {
	private Set<Variable> variables;
	//private Set<Stri> domains;
	private Set<Constraint> constraints;
	
	public CSP(){
		variables= new HashSet<Variable>();
		//domains= new HashSet<String>();
		constraints = new HashSet<Constraint>();
	}
	
	public Set<Variable> getVariables() {
		return variables;
	}
	public void setVariables(Set<Variable> variables) {
		this.variables = variables;
	}
	public Set<Constraint> getConstraints() {
		return constraints;
	}
	public void setConstraints(Set<Constraint> constraints) {
		this.constraints = constraints;
	}
	
	public void setEmptyConstraints(){
		constraints = new HashSet<Constraint>();
	}
	
	public boolean addConstaint(Constraint cons){
		return constraints.add(cons);
	}
	
	public boolean addConstaintList(ArrayList<Constraint> list){
		return constraints.addAll(list);
	}


	

}
