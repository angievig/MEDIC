package minimalSets;

import java.util.LinkedList;

import com.variamos.hlcl.HlclProgram;

import graph.ConstraintGraph;
import graph.Vertex;

public class Path<G,V> {


	private G subset;
	private LinkedList <V> path;
	private  HlclProgram subProblem;
	

	


	public Path(){
		
	}
	public Path(G inNet, LinkedList <V> inPath){
		subset= inNet;
		path= inPath;
	}
	
	public Path(G inNet, LinkedList <V> inPath, HlclProgram sub){
		subset= inNet;
		path= inPath;
		subProblem= sub;
	}
	
	public HlclProgram getSubProblem() {
		return subProblem;
	}

	public void setSubProblem(HlclProgram subProblem) {
		this.subProblem = subProblem;
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
