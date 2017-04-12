package utilTests;


import java.util.HashSet;

import constraintNetwork.Network;
import cspElements.CSP;
import cspElements.Constraint;
import cspElements.Variable;
import util.CSP2File;
/**
 * Class for testing the methods in the CSP2File class
 * @author Angela Villota Gomez <angievig@gmail.com>
 * @version 0
 * @since 0
 *
 */

public class CSP2File_Test {
	//Variables
	
	private Variable A, B, C, D, E;
	
	//Constraints
	private Constraint C1,C2, C3, C4, C5, C6;
	
	// Variables for constraints
	private Variable[] varsC1, varsC2, varsC3, varsC4, varsC5, varsC6;
	
	//Version constraints
	private Constraint VC1, VC2;
	
	

	
	/**
	 * Method for setup the scenario
	 */
	 public void setUp(){
		 /* Variables for this example each variable is an array of strings
		  * first position in the array contains the variable's id
		  * second position in the array contains the variable's domain
		  */
		 A= new Variable("A", "1..3");
		 B= new Variable ("B", "1..3");
		 C= new Variable("C", "1..2");
		 D= new Variable ("D", "1..2");
		 E= new Variable ("E", "1..3");
		 
		 /*
		  * Constraints in this examples are represented by 
		  * an array of strings with the names of variables, and a string with the constraint expression 
		  */
		 C1= new Constraint("A#= 1 #==> B#=1");
		 C1.addVariable(A);
		 C1.addVariable(B);

		 C2= new Constraint("A#= 2 #==> B#=2");
		 C2.addVariable(A);
		 C2.addVariable(B);

		 C3= new Constraint("A#= 3 #==> B#=3 #/\\ C#=2"); // en Java hay que escapar el control \
		 C3.addVariable(A);
		 C3.addVariable(B);
		 C3.addVariable(C);

		 C4= new Constraint("A#= 1 #==> D#\\=1"); // en Java hay que escapar el control \
		 C4.addVariable(A);
		 C4.addVariable(D);

		 C5= new Constraint("B#= 1 #==> C#\\=2"); // en Java hay que escapar el control \
		 C5.addVariable(B);
		 C5.addVariable(C);

		 C6= new Constraint ("C#=1  #==> E#=1"); 
		 C6.addVariable(C);
		 C6.addVariable(E);

		 /*
		  * Version constraints
		  */

		 		 
		 VC1=new Constraint("A#=1");
		 VC1.addVariable(A);
		 		 
		 		
//		 VC2= new Constraint("E#\\= 1"); // en Java hay que escapar el control \
//		 VC2.addVariable(E);
	}
	 
		/**
		 * This method creates a CSP
		 */
		 public void load1(CSP csp){
			 
			 //variables
			HashSet<Variable> vars= new HashSet<Variable>();
			vars.add(A);
			vars.add(B);
			vars.add(C);
			vars.add(D);
			vars.add(E);
			csp.setVariables(vars);

			//constraints
			csp.addConstaint(C1);
			csp.addConstaint(C2);
			csp.addConstaint(C3);
			csp.addConstaint(C4);
			csp.addConstaint(C5);
			csp.addConstaint(C6);
			
			
			//Version Constraints
			csp.addConstaint(VC1);
			csp.addConstaint(VC2);
		 }
		 
			public static void main(String args[])  {
				
				CSP2File_Test o= new CSP2File_Test();
				o.setUp();
				CSP csp= new CSP();
				o.load1(csp);
				CSP2File csp2file= new CSP2File(csp);
				csp2file.transform(1, "Test");
			}
		

}
