package cspElements;

import java.util.ArrayList;

public class Constraint implements Comparable<Constraint> {
	private String expression;
	private ArrayList<Variable> vars;
	private String id;
	
	public Constraint(String inId, String e){
		expression= e;
		id=inId;
		vars= new ArrayList<Variable>();
	}
	public Constraint(String inId, String e, ArrayList<Variable> inVars){
		expression= e;
		vars= inVars;
		id= inId;
	}
	public String getExpression(){
		return expression;
	}
	
	public String getId(){
		return id;
	}
	
	public boolean addVariable(Variable var){
		return vars.add(var);
	}
	
	public ArrayList <Variable> getVars(){
		return vars;
	}

	@Override
	public int compareTo(Constraint o) {
		// TODO Auto-generated method stub
		 return id.compareTo(o.getId());
	}
	
	public String toString(){
		return id+ ": "+ expression;
	}

}
