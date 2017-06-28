package graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

import cspElements.Constraint;

/**
 * Class representing a vertex for a constraint network
 * @author Angela Villota <angievig@gmail.com>
 * @version 1.0 we  included the attribute color for the depth first search
 * @since 0
 *
 */

public abstract class Vertex implements Comparable<Vertex>{
	public static final boolean CONSTRAINT_TYPE=true;
	public static final boolean VARIABLE_TYPE=false;
	public static final int VISITED=2;
	public static final int INSTACK=1;
	public static final int NOT_VISITED=0;
	private  String id;
	private ArrayList<Constraint> constraints;


	private TreeSet <Vertex> neighbors;
	private int searchState;
	private Vertex parent;
	




	public void initialize(String id){
		this.id=id;
		constraints= new ArrayList<Constraint>();
		neighbors= new TreeSet<Vertex>(); 
		searchState= NOT_VISITED;
		parent= null;
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
	
	public TreeSet<Vertex> getNeighbors(){
		return neighbors;
	}
	
	public int compareTo(Vertex o) {
		// TODO Auto-generated method stub
		 return id.compareTo(o.getId());
	}
	
	public int getSearchState() {
		return searchState;
	}

	public void setSearchState(int searchState) {
		this.searchState = searchState;
	}
	
	public Vertex getParent() {
		return parent;
	}

	public void setParent(Vertex parent) {
		this.parent = parent;
	}
	
	public void setConstraints(ArrayList<Constraint> constraints) {
		this.constraints = constraints;
	}
	
	public abstract Vertex clone();
	/**
	 * 
	 */
//	public boolean equals(Object o){
//		Vertex v = (Vertex) o;
//		return v.equals(id);
//		
//	}
	


	
}
