package paper;

import java.io.File;
import java.util.ArrayList;

import cases.Felferning2;
import constraintNetwork.Network;
import cspElements.CSP;
import cspElements.Constraint;
import cspElements.Variable;
import graphicConstraintNetwork.Window;
import individual.MinimalFelferning;
import minimalSets.LogManager;
import minimalSets.MinimalSetsDFS;
import minimalSets.MinimalSetsDFSNoLog;
import prologParser.XCSPPrologParser;
import transform.CSP2Network;

public class SmallPos {
	static String path= "C:/Users/Angela Villota/Documents/Tests/Grupo/Execution/Problema4/";
	private static CSP csp;
	static private XCSPPrologParser Xparser;
	static String filename= "Renault-small-pos.xml";
	static private LogManager fman= new LogManager(path, filename);
	
	
	public static void main(String[] args) throws Exception{
	//Seting up the scenario

			System.out.println("inicio");
			Xparser = new XCSPPrologParser(path+"in/"+ filename);
			csp= Xparser.getCSP();
			

			
			


			//inicio grafico
//			System.out.println("creando una red");
//			CSP2Network csp2net= new CSP2Network(csp);
//			Network net= csp2net.transform();
//			Window mainWindow= new Window(net);
			
			//nuevas restricciones
			nuevasRestricciones();
			
			//inicio pruebas

			tests();


		}
	
	static void nuevasRestricciones(){
		//nuevas restricciones
		Constraint cn1, cn2, cn3, cn4, cn5, cn6; 
//		X3_4 #=0,
//		X5_0 #=0,
//		X1_5 #=1,
//		X7_72#=1,
//		X7_75#=1,
//		X7_80#=1,
		cn1= new Constraint("CN1","X3_4 #=0");
		csp.addConstaint(cn1);
		csp.addVarToConstraint(cn1, "X3_4");
		
		cn2= new Constraint("CN2","X5_0 #=0");
		csp.addConstaint(cn2);
		csp.addVarToConstraint(cn2, "X5_0");

		cn3= new Constraint("CN3","X1_5 #=1");
		csp.addConstaint(cn3);
		csp.addVarToConstraint(cn3, "X1_5");

		cn4= new Constraint("CN4","X7_72 #=1");
		csp.addConstaint(cn4);
		csp.addVarToConstraint(cn4, "X7_72");

		cn5= new Constraint("CN5","X7_75#=1");
		csp.addConstaint(cn5);
		csp.addVarToConstraint(cn5, "X7_75");

		cn6= new Constraint("CN6","X7_80#=1");
		csp.addConstaint(cn6);
		csp.addVarToConstraint(cn6, "X7_80");
	}
	static void  purgeDirectory(File dir) {
	    for (File file: dir.listFiles()) {
	        if (file.isDirectory()) purgeDirectory(file);
	        file.delete();
	    }
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
	
	static void tests(){
		//Starting the algorithm
		//MinimalSetsBFS minimal = new MinimalSetsBFS(csp, path);
		System.out.println("inicio tests");
		fman.initLog();
		File dirOut= new File(path+"out/");
		
		for (int i = 0; i < 10; i++) {
			
			//printCSP();
			//fman.writeInFile("\ncon cn1, cn2, cn3, cn4, cn5, cn6\n");
			//fman.writeInFile("\ncon cn4, cn5, cn6\n");

			//fman.writeInFile("\niteracion: " + i+",  tiempo: ");
			
		
			MinimalSetsDFSNoLog minimal = new MinimalSetsDFSNoLog(csp,path+"out/", fman);
			ArrayList<Constraint> set=minimal.startAlgorithm("X0_0");
			
			//TODO imprime la salida, esta afuera para la ejecucion

//			if (set.isEmpty()){
//				fman.writeInFile("consistent CSP");
//			}else{
//				fman.writeInFile("conflict constraints: ");
//				for (Constraint constraint : set) {
//					fman.writeInFile(constraint.getId()+": " +constraint.getExpression()+ " ");
//				}
//				fman.writeInFile("\n");
//			}
			purgeDirectory(dirOut); 
			//System.out.println("directorio limpio");
		}
		fman.closeLog();
		System.out.println("ending test " + filename);

	}
	



}
