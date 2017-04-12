package constraintNetwork;

import java.util.ArrayList;
import java.util.HashSet;

import cspElements.Constraint;

/**
 * Class representing a vertex for a constraint network
 * @author Angela Villota <angievig@gmail.com>
 * @version 0
 * @since 0
 *
 */

public abstract class Vertex {
	public static final boolean CONSTRAINT_TYPE=true;
	public static final boolean VARIABLE_TYPE=false;
	private  String id;
	private ArrayList<Constraint> constraints;
	private HashSet <Vertex> neighbors;
	
	public void initialize(String id){
		this.id=id;
		constraints= new ArrayList<Constraint>();
		neighbors= new HashSet<Vertex>(); 
	}
	
	public  ArrayList<Constraint> getConstraints(){
		return constraints;
	}
	public boolean addConstraint(Constraint c){
		return constraints.add(c);
	}
	
	public String getId(){
		return id;
	}
	
	public boolean addNeighbor(Vertex v){
		return neighbors.add(v);
	}
	
	public HashSet<Vertex> getNeighbors(){
		return neighbors;
	}
	
	/**
	 * 
	 */
//	public boolean equals(Object o){
//		Vertex v = (Vertex) o;
//		return v.equals(id);
//		
//	}
	


	
}
