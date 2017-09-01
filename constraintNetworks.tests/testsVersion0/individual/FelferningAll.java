package individual;

import java.io.File;
import java.util.ArrayList;

import cases.Felferning2;
import cases.Felferning;
import cspElements.CSP;
import cspElements.Constraint;
import cspElements.Variable;
import graph.ConstraintGraph;
import graphicConstraintNetwork.Window;
import minimal.util.LogManager;
import minimalSets.MinimalSetsDFSBuilder;
import prologParser.XCSPPrologParser;
import transform.CSP2Network;

public class FelferningAll {
	static String path= "C:/Users/Angela Villota/Documents/Tests/Felferning/";
	private static CSP csp;
	
	static private LogManager fman= new LogManager(path, "Felferning");
	
	public static void main(String[] args) throws Exception{
		//Seting up the scenario

				System.out.println("start");
				MinimalFelferning m= new MinimalFelferning();
				Felferning problem1= new Felferning();
				csp= problem1.getCSP();

				

				
				


				//inicio grafico
				System.out.println("creando una red");
				CSP2Network csp2net= new CSP2Network(csp);
				
				//printCSP();
				ConstraintGraph net= csp2net.transform();
				Window mainWindow= new Window(net);
				

				
				//inicio pruebas

				tests(net);


			}
		

		
	
		
		static void printCSP(){
			fman.writeInFile("Problema a evaluar: \n");
			fman.writeInFile("Variables: \n");
			for (Variable var : csp.getVariables()) {
				fman.writeInFile(var.getId() +" "+ var.getDomain()+ "\n");
			}
			
			fman.writeInFile("Restricciones: \n");
			for (Constraint cons : csp.getConstraints()) {
				fman.writeInFile(cons.getId() +": ");
				for (Variable var : cons.getVars()) {
					fman.writeInFile(var.getId() +" ");
				}
				fman.writeInFile("\n");
			}
			fman.writeInFile("\n");
		}
		
		static void tests(ConstraintGraph net){
			//Starting the algorithm
			
			System.out.println("starting diagnosis");
			fman.initLog();

				
				
				
			printCSP();
				MinimalSetsDFSBuilder minimal = new MinimalSetsDFSBuilder(csp,path+"out/", fman, net);
				ArrayList<Constraint> set=minimal.startAlgorithm("Ubuntu");
	

			
			fman.closeLog();
			System.out.println("ending test ");

		}

}
