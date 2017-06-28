package common;

import java.util.LinkedList;

import cspElements.CSP;
import cspElements.Constraint;
import cspElements.Variable;
import graph.ConstraintGraph;
import graph.Vertex;
import graphicConstraintNetwork.Window;
import minimalSets.LogManager;
import minimalSets.MinimalSetsDFSIterations;
import transform.CSP2Network;

/**
 * Class to test a case study.
 * @author Angela Villota
 *
 */
public class TestCase {
	/**
	 * problemPath is the pat where the files will be placed
	 */
	private String problemPath;
	/**
	 * CSP representing the problem to be solved
	 */
	private static CSP csp;
	
	/**
	 * fman is the object of class LogManager to create the log files
	 */

	private static  LogManager fman;
	
	/**
	 * Initializes the test case with the path and the name of the problem
	 * @param problem
	 * @param name
	 */
	public TestCase(String problem, String name){
		problemPath= problem;
		fman= new LogManager(problemPath, name);
		fman.initLog();	
		
	}
	
	/**
	 * Starts the GUI with the constraint graph
	 * @param cspProblem
	 * @return
	 */

	public ConstraintGraph startWindow(CSP cspProblem){
		//inicio grafico
	    System.out.println("building a constraint graph");
		CSP2Network csp2net= new CSP2Network(cspProblem);
		ConstraintGraph net= csp2net.transform();
		Window mainWindow= new Window(net);
		return net;
	}
	/**
	 * 
	 * @param net
	 * @param cant
	 * @param source
	 */
	public void startTests(ConstraintGraph net, int cant, String source){
		System.out.println("starting diagnosis");
		for (int i = 0; i < cant; i++) {
		MinimalSetsDFSIterations minimal = new MinimalSetsDFSIterations(csp,problemPath+"out/", fman, net);
		LinkedList<Vertex> pathNodes =minimal.sourceOfInconsistentConstraints(source, 10);
		}
		
		//TODO To print the path
//		System.out.println("Path " + pathNodes.size());
//		for (Vertex vertex : pathNodes) {
//			System.out.print(vertex.getId()+ " ");
//		}
//		System.out.println();
		
		fman.closeLog();
		System.out.println("closing log file, ending test ");

	}

	public void printCSP(){
		fman.writeInFile("\n Problem: \n");
		fman.writeInFile("Variables: \n");
		for (Variable var : csp.getVariables()) {
			fman.writeInFile(var.getId() +" "+ var.getDomain()+ "\n");
		}

		fman.writeInFile("Constraints: \n");
		for (Constraint cons : csp.getConstraints()) {
			fman.writeInFile(cons.getId() +": Expresion -> "+ cons.getExpression());
			fman.writeInFile("  Variables ->");
			for (Variable var : cons.getVars()) {
				fman.writeInFile(var.getId() +", ");
			}
			
			fman.writeInFile("\n");
		}
		fman.writeInFile("\n");
	}
}
