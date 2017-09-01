package graphHLCL;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

import com.variamos.hlcl.BooleanExpression;
import com.variamos.hlcl.HlclProgram;

import cspElements.Constraint;

/**
 * Class representing a vertex for a constraint network
 * @author Angela Villota <angievig@gmail.com>
 * @version 1.0 we  included the attribute color for the depth first search
 * @since 0
 *
 */

public abstract class VertexHLCL implements Comparable<VertexHLCL>{
	public static final boolean CONSTRAINT_TYPE=true;
	public static final boolean VARIABLE_TYPE=false;
	public static final int VISITED=2;
	public static final int INSTACK=1;
	public static final int NOT_VISITED=0;
	private  String id;
	//private ArrayList<Constraint> constraints;
	private HlclProgram constraints;


	private TreeSet <VertexHLCL> neighbors;
	private int searchState;
	private VertexHLCL parent;
	




	public void initialize(String id){
		this.id=id;
		neighbors= new TreeSet<VertexHLCL>(); 
		constraints= new HlclProgram();
		searchState= NOT_VISITED;
		parent= null;
	}
	

	
	public String getId(){
		return id;
	}
	
	public boolean addNeighbor(VertexHLCL v){
		return neighbors.add(v);
	}
	
	public TreeSet<VertexHLCL> getNeighbors(){
		return neighbors;
	}
	
	public int compareTo(VertexHLCL o) {
		// TODO Auto-generated method stub
		 return id.compareTo(o.getId());
	}
	
	public int getSearchState() {
		return searchState;
	}

	public void setSearchState(int searchState) {
		this.searchState = searchState;
	}
	
	public VertexHLCL getParent() {
		return parent;
	}

	public void setParent(VertexHLCL parent) {
		this.parent = parent;
	}
	

	
	public abstract VertexHLCL clone();
	/**
	 * 
	 */
//	public boolean equals(Object o){
//		Vertex v = (Vertex) o;
//		return v.equals(id);
//		
//	}
	
	public HlclProgram getConstraints(){
		return constraints;
	}


	public void setConstraints(HlclProgram constraints) {
		this.constraints = constraints;
	}
	public boolean addConstraint(BooleanExpression c){
		return constraints.add(c);
	}
	


	
}
