package minimalSets;

import java.util.LinkedList;

import graph.ConstraintGraph;
import graph.Vertex;

public class Path<G,V> {


	private G subset;
	private LinkedList <V> path;
	
	public Path(){
		
	}
	
	public Path(G inNet, LinkedList <V> inPath){
		subset= inNet;
		path= inPath;
	}
	
	public G getSubset() {
		return subset;
	}

	public void setSubset(G subset) {
		this.subset = subset;
	}

	public LinkedList<V> getPath() {
		return path;
	}

	public void setPath(LinkedList<V> path) {
		this.path = path;
	}


}
