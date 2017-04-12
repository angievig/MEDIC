package constraintNetwork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import cspElements.Constraint;
//import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import cspElements.Variable;

/**
 * Esta es la clase que implementa una constraint network
 * using an adjacencies list.
 * Un vértice en esta clase es una variable o una constraint
 * Edges in this class represent the aparition of a constraint containing a variable
 * @author Angela Villota <Angela Villota>
 *
 */
public class Network {
	
	
	//private UndirectedSparseGraph<Vertex,String> graph;
	private HashMap<String,NodeConstraint> constraints;
	private HashMap<String,NodeVariable> variables;
	/**
	 * ConstraintsCount is the Amount of constraints in the problem, this may be different than the amount
	 *  of vertices in the network.
	 */
	private int constraintsCount;
	/**
	 * variablesCount is the Amount of variables in the problem, this may be different than the amount
	 *  of vertices in the network.
	 */
	private int variablesCount;
	
	private int constraintVertex;
	
	public Network(){
		constraintVertex= 0;
		constraintsCount=0;
		variablesCount=0;
		variables=new HashMap<String,NodeVariable>();
		constraints=new HashMap<String,NodeConstraint>();
		//graph= new UndirectedSparseGraph<Vertex,String>();
	}
	
	/**
	 * @param id el id de la variable, ya no se crea el id, esto puede tomar mucho tiempo
	 * @param expression, es una expresion con la restricción.  En la versión 0 es un string.
	 * @param vars debe ser una lista o arreglo de expresiones de variables.  
	 * En la versión 0 las expresiones variable son objetos de la clase Variable
	 * Este m
	 */
	public void addConstraint(String id, String expression, Variable[] vars){
		//String id="";  //the id of the node constraint
		NodeConstraint newNode =null; //the new constraint node
		constraintsCount++; //number of constraints in the problem, different than the number of nodes 
		//The id is formed by concatening the names of vars
//		for (int i = 0; i < vars.length; i++) {
//			id+=vars[i].getId();
//		}
		// Th id is sorted to avoid anagrams
//		char[] array= id.toCharArray();
//		Arrays.sort(array);
//		String sortedId= new String(array);
		NodeConstraint node= constraints.get(id);
		
		
		//if there is a constraint node with same set of variables, 
		//then add the constraint expression into the list of constraints
		//also, if there is a constraint node it means that the nodes corresponding the variables in the constraint exist.
		if (node!=null){
			node.addConstraint(new Constraint(id, expression));
		}
		/*
		 * Si el nodo constraint no existe, entonces 
		 */
		else{
			newNode= new NodeConstraint(id, new Constraint(id, expression));  // new node
			// for each variable expression
			for (int i = 0; i < vars.length; i++) {
				NodeVariable var= variables.get(vars[i].getId());
				// if the variable does not exits, then create the variable.
				if (var==null){
					var= new NodeVariable(vars[i]);
				}
				// in all cases, add the new constraint to the variables neighbors
				// add all variables to the constraint neighbors
				var.addNeighbor(newNode);
				newNode.addNeighbor(var);
			}
			constraints.put(id, newNode);
		}
		
	}
	
	/**
	 * 
	 * @param expression, es una expresion con la restricción.  En la versión 0 es un string.
	 * @param vars debe ser una lista o arreglo de expresiones de variables.  
	 * En la versión 0 las expresiones variable son objetos de la clase Variable
	 */
	public void addConstraint(Constraint cons){
		String id=cons.getId();  //the id of the node constraint is the same id of the constraint
		NodeConstraint newNode =null; //the new constraint node
		constraintsCount++; //number of constraints in the problem, different than the number of nodes 
		ArrayList<Variable> vars= cons.getVars();
		
//		String expression = cons.getExpression();

//		//The id is formed by concatening the names of vars
//		for (int i = 0; i < vars.size(); i++) {
//			id+=vars.get(i).getId();
//		}
//		// Th id is sorted to avoid anagrams
//		char[] array= id.toCharArray();
//		Arrays.sort(array);
//		String sortedId= new String(array);
		NodeConstraint node= constraints.get(id);
		
		
		//if there is a constraint node with same set of variables, 
		//then add the constraint expression into the list of constraints
		//also, if there is a constraint node it means that the nodes corresponding the variables in the constraint exist.
		if (node!=null){
			node.addConstraint(cons);
		}
		/*
		 * Si el nodo constraint no existe, entonces 
		 */
		else{
			newNode= new NodeConstraint(id, cons);  // new node
			// for each variable expression
			for (int i = 0; i < vars.size(); i++) {
			
				NodeVariable var= variables.get(vars.get(i).getId());
				// if the variable does not exits, then create the variable.
				if (var==null){
					var= new NodeVariable(vars.get(i));
				}
				// in all cases, add the new constraint to the variables neighbors
				// add all variables to the constraint neighbors
				var.addNeighbor(newNode);
				newNode.addNeighbor(var);
			}
			constraints.put(id, newNode);
		}
		
	}
	
//	public boolean addVersionConstraint(String expression, String id){
//		boolean exit=false;
//		
//		NodeVariable var=variables.get(id);
//		
//		if (var!=null)
//			exit= var.addConstraint(new Constraint(expression));
//		return exit;
//	}
	
	public boolean addVersionConstraint(Constraint cons, Variable var){
		boolean exit=false;
		String id= var.getId();
		NodeVariable varN=variables.get(id);
		
		if (var!=null)
			exit= varN.addConstraint(cons);
		return exit;
	}
	
	/**
	 * 
	 * @param id variable's identifier 
	 * @param domain name of the domain
	 */
	public boolean addVariable(Variable var){
		boolean exit=true;
		
		NodeVariable varNode = new NodeVariable(var);
		if (!variables.containsKey(var.getId())){
			variables.put(var.getId(), varNode);
			variablesCount++;
		}
		else
			exit= false;
		return exit;
	}
	
	public int getConstraintsCount(){
		return constraintsCount;
	}
	
	public int getVariablesCount(){
		return variablesCount;
	}
	
	public int constraintsSize(){
		return constraints.size();
	}
	public int variablesSize(){
		return variables.size();
	}
	
	public TreeSet<Vertex> getNeighbors(String id, boolean type){
		TreeSet<Vertex> tmp= null;
		if (type==Vertex.CONSTRAINT_TYPE)
			tmp= constraints.get(id).getNeighbors();
		
		else
			tmp= variables.get(id).getNeighbors();
		
		return tmp;
	}
	
	public HashMap<String,NodeVariable> getVariables(){
		return variables;
	}  
	public HashMap<String,NodeConstraint> getConstraints(){
		return constraints;
	} 
	
	public Vertex getVertex(String id){
		//TODO fix here
		Vertex ver= variables.get(id);
		if(ver==null){
			ver=constraints.get(id);
		}
		 
		return ver;
	}


}
