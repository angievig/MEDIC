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
import transform.CSP2FileRandom;
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
public class MinimalSetsDFSRecursive {
	public static final String LOGNAME="logFiles/ExecutionLog";
	public static final String LOGEXT=".log";
	public static final String FORWARD = "forward";
	public static final String BACKWARDS= "backwards";
	
	
	
	//StringBuilder 
	private StringBuilder sb;
	
	//Objeto que se encarga de convertir un CSP en un archivo desde cero, o de usar un stringBuilder
	// para escribirlo en un archivo
	private CSP2File csp2file;
	EvaluateSatisfiability eval;
	
	//private BufferedWriter out;
	private CSP inconsistent;
	private Network constraintNetwork;
	public ArrayList<Vertex> forwardPath;
	public ArrayList<Vertex> backwardsdPath;
	public String problemPath; 
	
	private LogManager logMan;
	
	//TODO comentar estas lineas para pruebas de performance
	private ArrayList<String> times;

	private int iterations;

	
	public MinimalSetsDFSRecursive(CSP csp, String path,  LogManager man, Network net){
		inconsistent= csp;
		logMan= man;
		constraintNetwork= net;
		//TODO imprime la red de restricciones
		//printNetwork(constraintNetwork);
		problemPath= path;
		sb= new StringBuilder();
		csp2file= new CSP2File();
		eval= new EvaluateSatisfiability();
		
		//TODO comentar estas lineas para pruebas de performance
		times = new ArrayList<String>();
		
		iterations=0;
	}
	/**
	 * 
	 * @param csp
	 * @param path
	 * @param man
	 */
//	public MinimalSetsDFSBuilder(CSP csp, String path,  LogManager man){
//		inconsistent= csp;
//		logMan= man;
//		CSP2Network csp2net= new CSP2Network(csp);
//		constraintNetwork= csp2net.transform();
//		//TODO imprime la red de restricciones
//		//printNetwork(constraintNetwork);
//		problemPath= path;
//		
//	}
	/**
	 * 
	 * @param source is the variable where the algorithm must start
	 * @return the set of constraints causing inconsistencies
	 */
	public ArrayList<Constraint> startAlgorithm(String source){
		ArrayList<Constraint> cc= new ArrayList<Constraint>();  // this is the set for the inconsistent constraints, it is initialized as empty 
		//the next lines are commented because of the change using stringBuilder, there is no need of an empty csp
		//CSP csp = new CSP(); // this is the csp for the algorithm, it has an empty set of constraints, but all the variables and domains form the inconsistent CSP
		//csp.setVariables(inconsistent.getVariables()); // CSP with the variables and domains of the inconsistent csp
		
		//newLines
		//TODO llamado a un metodo que pone las variables y el dominio del CCP en el stringBuilder 
		// se inicializa con las variables y los dominios del csp inconsistente 
		csp2file.initCSPBuilder(inconsistent, sb);
		try{
			
			long startTime = System.currentTimeMillis();
			
			sourceOfVM(source, cc, FORWARD); //first call 
			long endTime = System.currentTimeMillis();

			//logMan.writeInFile((endTime - startTime) + "\t");
			//TODO comentar estas lineas para pruebas de performance
			logMan.writeInFile("Total execution: "+(endTime - startTime) + "\n");
		}catch (Exception e){
			logMan.writeInFile("Execution suspended, runTime error \n"+ e.toString());
			//writeInFile(e.getMessage());
			e.printStackTrace();
			
			
		}
		
		//TODO Estas lineas están ocultas para la ejecución de pruebas
		logMan.writeInFile("Number of iterations: "+ iterations+"\n"); 
		logMan.writeInFile("Backwards path: \n"); 
		for (Vertex v : backwardsdPath) {
			logMan.writeInFile(v.getId()+", ");
			
		}
		
		logMan.writeInFile("\nforward path: \n");
		for (Vertex v : forwardPath) {
			logMan.writeInFile(v.getId()+", ");
			
		}
		
		logMan.writeInFile("\nExecution times: \n");
		logMan.writeInFile("File - Prolog \n");
		for (String s : times) {
			logMan.writeInFile(s+"\n");
			
		}
		logMan.writeInFile("\n");
		
		return cc;
	}
	
	/**
	 * Algorithm for finding void models.  This version of the algorithm uses a stack for depth first search
	 * @param source is a string with the id of the vertex source of the search
	 * @param csp is a copy of the inconsistent CSP with the variables and domains of the problem
	 * @param cc is the list of inconsistent constraints 
	 * 
	 * V1: quitamos el ccp porque estamos haciendo un stringBuilder
	 */
	public void sourceOfVM(String source, ArrayList<Constraint> cc, String direction){
		Stack<Vertex> stack= new Stack<Vertex>();  //data structure used to perform the Depth First Search
		//		for (Vertex vertex : constraintNetwork.g) {
		//			
		//		}
		stack.push(constraintNetwork.getVertex(source)); // the data structure starts with the source vertex
		ArrayList<Vertex> visited= new ArrayList<Vertex> (); // table of visited vertices 

		boolean satisfiable=true; // all empty csp is satisfiable
		Vertex actual=stack.pop(); //initializing the loop with a vertex
		visited.add(actual);
		int count=1;// number of iterations


		//While the CSP is satisfiable
		while(satisfiable){
			iterations++;
			//System.out.println("iteration: "+ count +" direction: "+ direction+" actual: "+ actual.getId());

			boolean empty=true;  //if the new set of constraints is empty, then there is no call to the solver
			ArrayList<Constraint> newConstraints= null; // new set of constraints
			if (actual instanceof NodeVariable){
				newConstraints= actual.getConstraints();
				if (!newConstraints.isEmpty()){
					empty=false;
					//changed
					//csp.addConstaintList(newConstraints);
				}
			}else if (actual instanceof NodeConstraint){
				newConstraints= actual.getConstraints();
				if (!newConstraints.isEmpty()){
					empty=false;
					//changed
					//csp.addConstaintList(newConstraints);
				}	
			}
			//if the set of constraints is different to empty, then the satisfiability of the SCP is evaluated
			if (!empty)
				satisfiable= evaluateSatisfatibility(newConstraints, actual);

			//System.out.println(satisfiable);
			//writeLog(actual, count, satisfiable, newConstraints, direction);

			// if the csp is satisfiable, then the traverse of the constraint network continues.
			if(satisfiable){
				actual=getNextNode(stack, actual, visited);
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
		//System.out.println("Fin while");
		if (direction.equals(BACKWARDS)){
			backwardsdPath= visited;
			//TODO quitar estas lineas para la prueba de performance
			logMan.writeInFile("Number of backwards iterations: "+ count+ "\n"); 
		}
		else{
			//System.out.println("llamado backwards");
			// initialize the variables for call the algorithm
			//CSP newCSP = new CSP(); // se crea un nuevo CSP
			//newCSP.setVariables(csp.getVariables()); // //
			//newCSP.setDomains(csp.getDomains());
			forwardPath=visited;
			sb= new StringBuilder();
			csp2file.initCSPBuilder(inconsistent, sb);
			//csp.setEmptyConstraints(); //the algorithm starts with an empty set of constraints
			//writeInFile("Starting backwards iteration, actual node "+ actual.getId() +"\n");
			//TODO quitar estas lineas para la prueba de performance
			
			logMan.writeInFile("Number of fordwards iterations: "+ count+ "\n"); 
			sourceOfVM(actual.getId(),cc, BACKWARDS);


		}

	}
	/**
	 * To obtain the next node, first the set of adjacent vertices are added to the structure
	 * then the first node is obtained.
	 * @param structure is the data structure whit the nodes to perform a graph traverse
	 * @param actual is the actual vertex
	 * @return return the next vertex to be examined
	 */
	public Vertex getNextNode(Stack<Vertex> structure, Vertex actual, ArrayList<Vertex>  visited ){
		
		for(Vertex v: actual.getNeighbors()){
			if (!visited.contains(v)){
				structure.push(v);
			}	
		}
		return structure.pop();
	}
	/**
	 * Modificado para añadir las restricciones y crear el archivo a partir del stringBuilder
	 * @param csp
	 * @return
	 */
	public boolean evaluateSatisfatibility(ArrayList<Constraint> constraints, Vertex actual){
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
	public Network csp2network(CSP csp){
		
		CSP2Network csp2net= new CSP2Network(csp);
		Network net= csp2net.transform();
		
		return net;
	}
	
	public void printNetwork(Network net){
		
	
		logMan.writeInFile("\nConstraint network: \n");

	
		
		logMan.writeInFile("Problem variables\n");
		 HashMap<String,NodeVariable> vars= net.getVariables();
		 for (String id : vars.keySet()) {
			 logMan.writeInFile("adjacent nodes of variable "+ id + " : " );
			 for (Vertex v: net.getNeighbors(id, Vertex.VARIABLE_TYPE)){
				 logMan.writeInFile(v.getId()+" ");	 
			 }
			 logMan.writeInFile("\n");
		}
		 
		//neighbors for constraint nodes
		 logMan.writeInFile("Problem constraints\n");
		 HashMap<String,NodeConstraint> cons= net.getConstraints();
		 for (String id : cons.keySet()) {
			 logMan.writeInFile("adjacent nodes of constraint "+ id + " : " );
			 for (Vertex v: net.getNeighbors(id, Vertex.CONSTRAINT_TYPE)){
				 logMan.writeInFile(v.getId()+" ");	 
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
