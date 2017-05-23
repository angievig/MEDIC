package minimalSets;

import java.util.LinkedList;

import constraintNetwork.Network;
import constraintNetwork.Vertex;

public class Path {


	private Network subset;
	private LinkedList <Vertex> path;
	
	public Path(){
		
	}
	
	public Path(Network inNet, LinkedList <Vertex> inPath){
		subset= inNet;
		path= inPath;
	}
	
	public Network getSubset() {
		return subset;
	}

	public void setSubset(Network subset) {
		this.subset = subset;
	}

	public LinkedList<Vertex> getPath() {
		return path;
	}

	public void setPath(LinkedList<Vertex> path) {
		this.path = path;
	}


}
