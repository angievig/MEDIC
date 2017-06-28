package minimalSets;

import java.util.LinkedList;

import graph.ConstraintGraph;
import graph.Vertex;

public class Path {


	private ConstraintGraph subset;
	private LinkedList <Vertex> path;
	
	public Path(){
		
	}
	
	public Path(ConstraintGraph inNet, LinkedList <Vertex> inPath){
		subset= inNet;
		path= inPath;
	}
	
	public ConstraintGraph getSubset() {
		return subset;
	}

	public void setSubset(ConstraintGraph subset) {
		this.subset = subset;
	}

	public LinkedList<Vertex> getPath() {
		return path;
	}

	public void setPath(LinkedList<Vertex> path) {
		this.path = path;
	}


}
