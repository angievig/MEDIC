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

import com.variamos.compiler.prologEditors.Hlcl2SWIProlog;
import com.variamos.hlcl.BooleanExpression;
import com.variamos.hlcl.HlclProgram;

//import cspElements.CSP;
//import cspElements.Constraint;
import graphHLCL.ConstraintGraphHLCL;
import graphHLCL.NodeConstraintHLCL;
import graphHLCL.NodeVariableHLCL;
import graphHLCL.VertexHLCL;
//import transform.CSP2File;
//import transform.CSP2FileRandom;
import transform.HLCL2Graph;


/**
 * This class implements the algorithm for find a minimal set of inconsistent constraints
 * when a CSP is inconsistent.
 * 
 * General changes in this version:
 * 1. The main loop does  a number of  DFS determined by an input or until the path reaches a fixed point: there is no shorter path.
 * 2. The size of the graph changes, the next DFS uses only the previous visited graph
 * 3. The output is the path, the last vertex in the path is the last visited vertex 
 * Specific changes:
 * for the DFS
 * 1. we included an attribute in the node for determine if it's visited or not
 * 2. the isVisited subroutine will not search in a list
 * 
 * 
 * @author Angela Villota <Angela Villota>
 * @version 1.0
 * @since 0
 *
 */
public class MinimalSetsDFSIterationsHLCL {
	public static final String LOGNAME="logFiles/ExecutionLog";
	public static final String LOGEXT=".log";
	public static final String FORWARD = "forward";
	public static final String BACKWARDS= "backwards";
	
	
	
	//StringBuilder 
//	private StringBuilder sb;
	
	//Objeto que se encarga de convertir un CSP en un archivo desde cero, o de usar un stringBuilder
	// para escribirlo en un archivo
//	private CSP2File csp2file;
	EvaluateSatisfiability eval;
	
	private HlclProgram cspIn;
	private ConstraintGraphHLCL graph;
	private String problemPath; 
	
	private LogManager logMan;
	
	//TODO comentar estas lineas para pruebas de performance
	private ArrayList<String> times;

	private int iterations;

	/**
	 * Method used to build an object of this class
	 * 
	 * @param csp the inconsistent CSP
	 * @param path the path of the problem for tests and etc
	 * @param man is the object used to make the logfile to report the execution of the algorithm
	 * @param net is the graph generated from the csp
	 */
	public MinimalSetsDFSIterationsHLCL(HlclProgram csp, String path,  LogManager man, ConstraintGraphHLCL net){
		cspIn= csp;
		logMan= man;
		graph= net;
		problemPath= path;
		//TODO imprime la red de restricciones
		//printNetwork(constraintNetwork);
		
		//sb= new StringBuilder();
		
		eval= new EvaluateSatisfiability();
		
		//TODO comentar estas lineas para pruebas de performance
		times = new ArrayList<String>();
		iterations=0;
	}

/**
 * 
 * @param source
 * @param maxIterations
 * @return
 */
	public LinkedList<VertexHLCL> sourceOfInconsistentConstraints(String source, int maxIterations){

		Path<ConstraintGraphHLCL,VertexHLCL> path = new Path<ConstraintGraphHLCL,VertexHLCL>();
		//newLines
		//TODO llamado a un metodo que pone las variables y el dominio del CCP en el stringBuilder 
		// se inicializa con las variables y los dominios del csp inconsistente 

		//csp2file.initCSPBuilder(cspIn, sb);
		
		try{
			
			long startTime = System.currentTimeMillis();
	
			// previous length is initialized with the number of vertices (max length in a path)
			
			int count=1;
			iterations++;
			path = searchPath(source, graph);
			source= path.getPath().getLast().getId();
			int previousSize;
			//list = recoverPath( path0.getPath().getLast(), list);
			ConstraintGraphHLCL subGraph= path.getSubset();
			System.out.println("Size of path iteration No.1: " + path.getPath().size());
			
			for (VertexHLCL vertex : path.getPath()) {
				System.out.print(vertex.getId()+ " ");
			}
			System.out.println();
			

			do{
				System.out.println("Iteration "+ count);
				previousSize= path.getPath().size();
				path = searchPath(source, subGraph);
				source= path.getPath().getLast().getId();
				subGraph= path.getSubset();
				maxIterations--;
				count++;
				System.out.println("Size of path iteration No.: " + path.getPath().size());
				for (VertexHLCL vertex : path.getPath()) {
					System.out.print(vertex.getId()+ " ");
				}
				System.out.println();
				iterations++;
			}
			while(maxIterations>0 && previousSize> path.getPath().size());
			
			if (! (maxIterations>0)){
				System.out.println("Execution termined for reaching a the maximun of iterations");
			}else{
				System.out.println("Execution termided for reaching a fixed point after " + count+ " iterations" );
			}
			
			
			long endTime = System.currentTimeMillis();

			
			//TODO comentar estas lineas para pruebas de performance
			//logMan.writeInFile((endTime - startTime) + "\t");
			logMan.writeInFile("Total execution: "+(endTime - startTime) + "\n");
		}catch (Exception e){
			logMan.writeInFile("Execution suspended, runTime error \n"+ e.toString());
			//writeInFile(e.getMessage());
			e.printStackTrace();
		}
		return path.getPath();
		
		//TODO Estas lineas están ocultas para la ejecución de pruebas
		
		//FIXME 
//		logMan.writeInFile("Number of iterations: "+ iterations+"\n"); 
//		logMan.writeInFile("Backwards path: \n"); 
//		for (Vertex v : backwardsdPath) {
//			logMan.writeInFile(v.getId()+", ");
//			
//		}
//		
//		logMan.writeInFile("\nforward path: \n");
//		for (Vertex v : forwardPath) {
//			logMan.writeInFile(v.getId()+", ");
//			
//		}
//		
//		logMan.writeInFile("\nExecution times: \n");
//		logMan.writeInFile("File - Prolog \n");
//		for (String s : times) {
//			logMan.writeInFile(s+"\n");
//			
//		}
//		logMan.writeInFile("\n");
		
	
	}
	
	public LinkedList<VertexHLCL> recoverPath(VertexHLCL v, LinkedList<VertexHLCL> path){
		
		
		if (v.getParent() == null){
			path.add(v);
		}
		else{
			path.add(v);
			recoverPath(v.getParent(), path);
		}
		return path;
	}
	

	/**
	 * This method performs a DFS until an insatisfiable constraint is found
	 * @param source is a string with the id of the vertex source of the search
	 * @param csp is a copy of the inconsistent CSP with the variables and domains of the problem
	 * @param cc is the list of inconsistent constraints 
	 * @return path is a linked-list containing the sequence of visited vertices.  
	 * The last vertex in path is the inconsistent contains the inconsistent constraint
	 */
	public Path<ConstraintGraphHLCL,VertexHLCL> searchPath(String source, ConstraintGraphHLCL graphIn) throws Exception{
		
		logMan.writeInFile("\nConstraint graph in iteration "+ iterations+ "\n");
		printNetwork(graphIn);
		ConstraintGraphHLCL subGraph = new ConstraintGraphHLCL();
		Path<ConstraintGraphHLCL,VertexHLCL> output= null;
		//Estas lineas permiten que la creacion del archivo sea incremental, se crea el string builder 
		//Las siguientes lineas crean la parte inicial del archivo
		StringBuilder sb= new StringBuilder();
		Hlcl2SWIProlog parser= new Hlcl2SWIProlog();
		parser.w
//		CSP2File csp2file= new CSP2File();
//		csp2file.initCSPBuilder(cspIn, sb);
		
		LinkedList<VertexHLCL> path= new LinkedList<VertexHLCL>(); // the output
		Stack<VertexHLCL> stack= new Stack<VertexHLCL>();  //data structure used to perform the Depth First Search

		stack.push(graphIn.getVertex(source)); // the data structure starts with the source vertex
		//ArrayList<Vertex> visited= new ArrayList<Vertex> (); // table of visited vertices 

		boolean satisfiable=true; // all empty csp is satisfiable
		VertexHLCL actual=stack.pop(); //initializing the loop with a vertex
		VertexHLCL clon= actual.clone();
		//System.out.println("root: " + actual.getId()+", search state: "+ actual.getSearchState()+", actual: "+ clon.getParent());
		//System.out.println("root clon: " + clon.getId()+", search state: "+ clon.getSearchState()+", parent: "+ clon.getParent());
		subGraph.addVertex(actual.clone());
		//printNetwork(subGraph);
		//visited.add(actual);
		
		int count=1;// number of iterations


		//While the CSP is satisfiable
		while(satisfiable){
			path.addLast(actual);
			boolean empty=true;  //if the new set of constraints is empty, then there is no call to the solver
			ArrayList<BooleanExpression> newConstraints= new ArrayList<BooleanExpression>() ; // new set of constraints
			if (actual instanceof NodeVariableHLCL){
				newConstraints= actual.getConstraints();
				if (!newConstraints.isEmpty()){
					empty=false;
				}
			}else if (actual instanceof NodeConstraintHLCL){
				newConstraints= actual.getConstraints();
				if (!newConstraints.isEmpty()){
					empty=false;
				}	
			}
			//if the set of constraints is different to empty, then the satisfiability of the SCP is evaluated
			if (!empty)
				satisfiable= evaluateSatisfatibility(newConstraints, actual, sb, csp2file);
//			writeLog(actual, count, satisfiable, newConstraints, direction);

			// if the csp is satisfiable, then the traverse of the constraint network continues.
			if(satisfiable){
				actual=getNextNode(stack, actual,subGraph);
				//actual=getNextNode(stack, actual, visited, subGraph);
				//visited.add(actual);
				count++;
			}
		}
		
//		logMan.writeInFile("Initial graph"+"\n");
//		printNetwork(graph);
//		
		logMan.writeInFile("Graph built in iteration"+ iterations+ "\n");
		//printNetwork(subGraph);
	
		output= new Path<ConstraintGraphHLCL,VertexHLCL>(subGraph, path);
		return output;
		//System.out.println("Fin while");
//		if (direction.equals(BACKWARDS)){
//			backwardsdPath= visited;
//			//TODO quitar estas lineas para la prueba de performance
//			logMan.writeInFile("Number of backwards iterations: "+ count+ "\n"); 
//		}
//		else{
//			//System.out.println("llamado backwards");
//			// initialize the variables for call the algorithm
//			//CSP newCSP = new CSP(); // se crea un nuevo CSP
//			//newCSP.setVariables(csp.getVariables()); // //
//			//newCSP.setDomains(csp.getDomains());
//			forwardPath=visited;
//			sb= new StringBuilder();
//			csp2file.initCSPBuilder(cspIn, sb);
//			//csp.setEmptyConstraints(); //the algorithm starts with an empty set of constraints
//			//writeInFile("Starting backwards iteration, actual node "+ actual.getId() +"\n");
//			//TODO quitar estas lineas para la prueba de performance
//			
//			logMan.writeInFile("Number of fordwards iterations: "+ count+ "\n"); 
//			searchPath(actual.getId(),cc, BACKWARDS);
//
//
//		}


	}
	/**
	 * To obtain the next node, first the set of adjacent vertices are added to the structure
	 * then the first node is obtained.
	 * @param structure is the data structure whit the nodes to perform a graph traverse
	 * @param actual is the actual vertex
	 * @return return the next vertex to be examined
	 */
	public VertexHLCL getNextNode(Stack<VertexHLCL> structure, VertexHLCL actual, ConstraintGraphHLCL newG )throws Exception{
		
		actual.setSearchState(VertexHLCL.VISITED);
		VertexHLCL next;
		//System.out.print(actual.getId()+ ": ");

		for(VertexHLCL v: actual.getNeighbors()){
			
			if (v.getSearchState()== VertexHLCL.NOT_VISITED){
				//System.out.print(v.getId()+ ", ");
				v.setParent(actual);
				structure.push(v);
				v.setSearchState(VertexHLCL.INSTACK);
			}	
		}
		//System.out.println();
		next= structure.pop();
		//System.out.println();
		//System.out.println("Vertex examined: " + next.getId()+", search state: "+ next.getSearchState()+", parent: "+ next.getParent().getId());
		VertexHLCL nV= next.clone();
		VertexHLCL parent = newG.getVertex(next.getParent().getId());
		newG.addVertex(nV);
		newG.addEdge(nV, parent);
		//System.out.println("New Vertex: " + nV.getId()+", search state: "+ nV.getSearchState()+", parent: "+ nV.getParent());
		//System.out.println("New Edge: "+ nV.getId() + ", "+parent.getId());
		return next;
	}
	/**
	 * Modificado para añadir las restricciones y crear el archivo a partir del stringBuilder
	 * @param csp
	 * @return
	 */
	public boolean evaluateSatisfatibility(ArrayList<Constraint> constraints, VertexHLCL actual, StringBuilder sb, CSP2File csp2file){
		//Old version
//		CSP2FileRandom csp2file= new CSP2FileRandom(csp);  //transformimng re csp object into a prolog program
//		String programName= csp2file.transform(problemPath,count, direction);
//		EvaluateSatisfiability eval= new EvaluateSatisfiability();
//		return eval.consult(programName);
		
		boolean evaluation;
		
		//TODO quitar la medicion de tiempos
		String time= "Iteration No. "+ iterations + " Node: "+ actual.getId();
		long startTime = System.currentTimeMillis();
		csp2file.appendConstraints(sb, constraints);
		String programName= csp2file.createFile(sb, problemPath);
		long endTime = System.currentTimeMillis();
		time+= "; " + (endTime - startTime); 
	

		startTime = System.currentTimeMillis();
		evaluation= eval.consult(programName);
		endTime = System.currentTimeMillis();
		time+= "; " + (endTime - startTime);
		times.add(time);
		return evaluation;
	}
	/**
	 * 
	 * @param csp
	 * @return
	 */
	public ConstraintGraphHLCL csp2network(HlclProgram csp){
		
		HLCL2Graph o= new HLCL2Graph(csp);
		ConstraintGraphHLCL net=  o.transform();
		
		return net;
	}
	
	
	public void printNetwork(ConstraintGraphHLCL net){
		logMan.writeInFile("\nConstraint network: \n");
		logMan.writeInFile("Total vertices: "+net.numVertices()+
				           " vars: "+net.getVariablesCount()+
				           " cons: "+net.getConstraintsCount()+
				           " Total edges: "+net.numEdges()+  "\n");
		
		logMan.writeInFile("Problem variables\n");
		 HashMap<String,NodeVariable> vars= net.getVariables();
		 for (String id : vars.keySet()) {
			 logMan.writeInFile("adjacent nodes of variable "+ id + ": " );
			 for (VertexHLCL v: net.getNeighbors(id, VertexHLCL.VARIABLE_TYPE)){
				 logMan.writeInFile(v.getId()+",  ");	 
			 }
			 logMan.writeInFile("\n");
		}
		 
		//neighbors for constraint nodes
		 logMan.writeInFile("Problem constraints\n");
		 HashMap<String,NodeConstraint> cons= net.getConstraints();
		 for (String id : cons.keySet()) {
			 logMan.writeInFile("adjacent nodes of constraint "+ id + ": " );
			 for (VertexHLCL v: net.getNeighbors(id, VertexHLCL.CONSTRAINT_TYPE)){
				 logMan.writeInFile(v.getId()+",  ");	 
			 }
			 logMan.writeInFile("\n");
		}
	}
//	public void writeLog(Vertex actual, int count, boolean satisfiable, ArrayList<Constraint> newConstraints, String direction){
//
//		String sentences= "Iteration No."+ count+  direction +" Vertex id= "+ actual.getId()+"\n" + "vertex type: "+
//				          ((actual instanceof NodeConstraint)?"Constraint ": "Variable ");
//		sentences += "the CSP in this iteration is "+ ((satisfiable)?"satisfiable\n": "not satisfiable\n");
//		if (!satisfiable){
//			sentences+= "The constraints that made inconsistent the csp are \n";
//			for (Constraint constraint : actual.getConstraints()) {
//				sentences+= "Constraint: "+ constraint.getExpression()+ "\n";
//			}
//			
//		}
//		writeInFile(sentences);
//	}
//	
//	public void initLog(){
//		 Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//		 String time =timestamp.getTime()+"";
//		String fileName= LOGNAME+"_"+ time+LOGEXT;
//		try {
//			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
//			out.write("Log File : " + timestamp+ "File name " + fileName+ " \n");
//		} catch (Exception e) {
//			// 
//			
//			e.printStackTrace();
//		}
//
//	}
//	/**
//	 * 
//	 * @param sentences is a string with the sentences in prolog 
//	 */
//	public void writeInFile(String sentences){
//		try {
//			out.write(sentences);
//			//System.out.println(sentences);
//		} catch (IOException e) {
//			// 
//			e.printStackTrace();
//		}
//		
//		
//	}
//	
//	public void closeLog(){
//	try{
//		out.flush();
//		out.close();
//	}catch (Exception e){
//		e.printStackTrace();
//	}
//	}

}
