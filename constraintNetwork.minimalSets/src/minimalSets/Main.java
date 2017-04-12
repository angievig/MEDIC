package minimalSets;

import java.util.ArrayList;
import java.util.HashSet;

import cspElements.CSP;
import cspElements.Constraint;
import cspElements.Variable;

/**
 * 
 * @author Angela Villota Gomez
 * date: November 16th 2016
 *
 */
public class Main {
	private static CSP csp;
	private HashSet<Variable> vars;
	private HashSet<Constraint> constraints;
	
	public static void main(String[] args) {
		//Seting up the scenario
		Main m= new Main();
		m.cspProblem2();
		m.makeCSP();
		
		//Starting the algorithm
		MinimalSetsDFS minimal = new MinimalSetsDFS(csp);
		ArrayList<Constraint> set=minimal.startAlgorithm("A");
		
		if (set.isEmpty()){
			System.out.println("consistent CSP");
		}else{


			System.out.println("conflict constraints");
			for (Constraint constraint : set) {
				System.out.println(constraint.getExpression());
			}
		}
		
		

	}

	public Main(){
		csp= new CSP();

	}

	public void cspProblem2(){
		//Variables
		Variable A, B, C, D, E;
		//Constraints
		Constraint C1,C2, C3, C4, C5, C6;
		//Version constraints
		Constraint VC1, VC2;
		
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

		VC2= new Constraint("E#\\= 1"); // en Java hay que escapar el control \
		VC2.addVariable(E);
		
		// variables
		vars= new HashSet<Variable>();
		vars.add(A);
		vars.add(B);
		vars.add(C);
		vars.add(D);
		vars.add(E);
		//constraints
		constraints= new HashSet<Constraint>();
		constraints.add(C1);
		constraints.add(C2);
		constraints.add(C3);
		constraints.add(C4);
		constraints.add(C5);
		constraints.add(C6);
		// version constraints
		constraints.add(VC1);
		constraints.add(VC2);

	}
	
	public  void makeCSP(){
		csp= new CSP();
		csp.setVariables(vars);
		csp.setConstraints(constraints);
	}


}
