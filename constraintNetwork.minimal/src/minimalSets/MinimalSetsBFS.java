package minimalSets;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
//import java.util.HashSet;
//import java.util.Set;
import java.util.Stack;

import constraintNetwork.Network;
import constraintNetwork.NodeConstraint;
import constraintNetwork.NodeVariable;
import constraintNetwork.Vertex;
import cspElements.CSP;
import cspElements.Constraint;
import transform.CSP2File;
import transform.CSP2Network;



/**
 * This class implements the algoritm for find a minimal set of inconsistent constraints
 * when a CSP is inconsistent, we say the variability model is empty
 * 
 * @author Angela Villota <Angela Villota>
 * @version 0
 * @since 0
 *
 */
public class MinimalSetsBFS {
	public static final String LOGNAME="logFiles/ExecutionLog";
	public static final String LOGEXT=".log";
	public static final String FORWARD = "forward";
	public static final String BACKWARDS= "backwards";
	
	

	private BufferedWriter out;

	private CSP inconsistent;
	private Network constraintNetwork;
	public ArrayList<Vertex> forwardPath;
	public ArrayList<Vertex> backwardsdPath;
	public String problemPath; 
	

	
	public MinimalSetsBFS(CSP csp, String path){
		inconsistent= csp;
		constraintNetwork= csp2network(inconsistent);
		problemPath=path;
	}
	/**
	 * 
	 * @param source is the variable where the algorithm must start
	 * @return the set of constraints causing inconsistencies
	 */
	public ArrayList<Constraint> startAlgorithm(String source){
		ArrayList<Constraint> cc= new ArrayList<Constraint>();  // this is the set for the inconsistent constraints, it is initialized as empty 
		CSP csp = new CSP(); // this is the csp for the algorithm, it has an empty set of constraints, but all the variables and domains form the inconsistent CSP
		csp.setVariables(inconsistent.getVariables()); // CSP with the variables and domains of the inconsistent csp
		try{
			initLog();
			sourceOfVM(source, csp, cc, FORWARD); //first call 
			closeLog();
		}catch (Exception e){
			writeInFile("Execution suspended, runTime error \n"+ e.toString());
			//writeInFile(e.getMessage());
			e.printStackTrace();
			closeLog();
			
		}
		
		System.out.print("backwards path ->>");
		for (Vertex v : backwardsdPath) {
			System.out.print(v.getId()+", ");
			
		}
		System.out.println();
		System.out.print("forward path ->>");
		for (Vertex v : forwardPath) {
			System.out.print(v.getId()+", ");	
		}
		System.out.println();
		
		return cc;
	}
	
	/**
	 * Algorithm for finding void models.  This version of the algorithm uses a stack for depth first search
	 * @param source is a string with the id of the vertex source of the search
	 * @param csp is a copy of the inconsistent CSP with the variables and domains of the problem
	 * @param cc is the list of inconsistent constraints 
	 */
	public void sourceOfVM(String source, CSP csp, ArrayList<Constraint> cc, String direction){
		LinkedList<Vertex> queue= new LinkedList<Vertex>();  //data structure used to perform the Depth First Search
		//System.out.println(source);
//		for (Vertex vertex : constraintNetwork.g) {
//			
//		}
		//System.out.println(" source "+constraintNetwork.getVertex(source));
		queue.addLast(constraintNetwork.getVertex(source)); // the data structure starts with the source vertex
		ArrayList<Vertex> visited= new ArrayList<Vertex> (); // table of visited vertices 
		
		boolean satisfiable=true; // all empty csp is satisfiable
		Vertex actual=queue.pollFirst(); //initializing the loop with a vertex
		visited.add(actual);
		int count=1;// number of iterations
		

		//While the CSP is satisfiable
		while(satisfiable){
			
			boolean empty=true;  //if the new set of constraints is empty, then there is no call to the solver
			ArrayList<Constraint> newConstraints= null; // new set of constraints
			if (actual instanceof NodeVariable){
				newConstraints= actual.getConstraints();
				if (!newConstraints.isEmpty()){
					empty=false;
					csp.addConstaintList(newConstraints);
				}
			}else if (actual instanceof NodeConstraint){
				newConstraints= actual.getConstraints();
				if (!newConstraints.isEmpty()){
					empty=false;
					csp.addConstaintList(newConstraints);
				}	
			}
			//if the set of constraints is different to empty, then the satisfiability of the SCP is evaluated
			if (!empty)
				satisfiable= evaluateSatisfatibility(csp, count, direction);
			
			writeLog(actual, count, satisfiable, newConstraints, direction);
			
			// if the csp is satisfiable, then the traverse of the constraint network continues.
			if(satisfiable){
				actual=getNextNode(queue, actual, visited);
				visited.add(actual);
				count++;
			}
			// if the csp is not satisfiable, then the unsatisfiable constraints are added to the
			// cc list.
			else{
				cc.addAll(newConstraints); 
				satisfiable=false;
			}
			
		}
		if (direction.equals(BACKWARDS)){
			backwardsdPath= visited;
		}else{
		// initialize the variables for call the algorithm
		//CSP newCSP = new CSP(); // se crea un nuevo CSP
		//newCSP.setVariables(csp.getVariables()); // //
		//newCSP.setDomains(csp.getDomains());
			forwardPath=visited;
		csp.setEmptyConstraints(); //the algorithm starts with an empty set of constraints
		writeInFile("Starting backwards iteration, actual node "+ actual.getId() +"\n");
		sourceOfVM(actual.getId(), csp,cc, BACKWARDS);
		
	
		}
		
	}
	/**
	 * To obtain the next node, first the set of adjacent vertices are added to the structure
	 * then the first node is obtained.
	 * @param structure is the data structure whit the nodes to perform a graph traverse
	 * @param actual is the actual vertex
	 * @return return the next vertex to be examined
	 */
	public Vertex getNextNode(LinkedList<Vertex> structure, Vertex actual, ArrayList<Vertex>  visited ){
		
		for(Vertex v: actual.getNeighbors()){
			if (!visited.contains(v)){
				structure.addLast(v);
			}
			
		}
		
		return structure.pollFirst();
	}
	/**
	 * 
	 * @param csp
	 * @return
	 */
	public boolean evaluateSatisfatibility(CSP csp, int count, String direction){
		//System.out.println("evaluando satisfacibilidad");
		CSP2File csp2file= new CSP2File(csp);  //transformimng re csp object into a prolog program
		String programName= csp2file.transform(problemPath, count, direction);
		//System.out.println(programName);
		EvaluateSatisfiability eval= new EvaluateSatisfiability();
		return eval.consult(programName);
		
		
	}
	/**
	 * 
	 * @param csp
	 * @return
	 */
	public Network csp2network(CSP csp){
		//System.out.println("transforming into constraint network");
		CSP2Network csp2net= new CSP2Network(csp);
		Network net= csp2net.transform();
		
		return net;
	}
	
	public void writeLog(Vertex actual, int count, boolean satisfiable, ArrayList<Constraint> newConstraints, String direction){
//		System.out.println(actual);
//		System.out.println(count);
//		System.out.println(satisfiable);
//		System.out.println(direction);
		String sentences= "Iteration No."+ count+  direction +" Vertex id= "+ actual.getId()+"\n" + "vertex type: "+
				          ((actual instanceof NodeConstraint)?"Constraint ": "Variable ");
		sentences += "the CSP in this iteration is "+ ((satisfiable)?"satisfiable\n": "not satisfiable\n");
		if (!satisfiable){
			sentences+= "The constraints that made inconsistent the csp are \n";
			for (Constraint constraint : actual.getConstraints()) {
				sentences+= "Constraint: "+ constraint.getExpression()+ "\n";
			}
			
		}
		writeInFile(sentences);
	}
	
	public void initLog(){
		 Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		 String time =timestamp.getTime()+"";
		String fileName= problemPath+ LOGNAME+"_"+ time+LOGEXT;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
			out.write("Log File : " + timestamp+ "File name " + fileName+ " \n");
		} catch (Exception e) {
			// TODO Auto-generated catch blo
			
			e.printStackTrace();
		}

	}
	/**
	 * 
	 * @param sentences is a string with the sentences in prolog 
	 */
	public void writeInFile(String sentences){
		try {
			out.write(sentences);
			//System.out.println(sentences);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void closeLog(){
	try{
		out.flush();
		out.close();
	}catch (Exception e){
		e.printStackTrace();
	}
	}

}
