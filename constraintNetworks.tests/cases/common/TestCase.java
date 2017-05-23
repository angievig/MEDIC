package common;

import java.util.LinkedList;


import constraintNetwork.Network;
import constraintNetwork.Vertex;
import cspElements.CSP;
import cspElements.Constraint;
import cspElements.Variable;
import graphicConstraintNetwork.Window;
import minimalSets.LogManager;
import minimalSets.MinimalSetsDFSIterations;
import transform.CSP2Network;

public class TestCase {
	private String problemPath;//= "C:/Users/Angela Villota/Documents/Tests/Felferning/";
	private static CSP csp;

	private static  LogManager fman;//= new LogManager(path, "Felferning");
	
	public TestCase(String problem, String name){
		problemPath= problem;
		fman= new LogManager(problemPath, name);
		fman.initLog();	
		
	}

	public Network startWindow(CSP cspProblem){
		//inicio grafico
	    System.out.println("building a constraint graph");
	    csp= cspProblem;
		CSP2Network csp2net= new CSP2Network(csp);
		Network net= csp2net.transform();
		Window mainWindow= new Window(net);
		return net;
	}
	
	



	public void startTests(Network net, int cant, String source){


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
