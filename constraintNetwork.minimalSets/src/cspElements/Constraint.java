package cspElements;

import java.util.ArrayList;

public class Constraint {
	private String expression;
	private ArrayList<Variable> vars; 
	
	public Constraint(String e){
		expression= e;
		vars= new ArrayList<Variable>();
	}
	
	public String getExpression(){
		return expression;
	}
	
	public boolean addVariable(Variable var){
		return vars.add(var);
	}
	
	public ArrayList <Variable> getVars(){
		return vars;
	}

}
