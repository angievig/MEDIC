package utilTests;

import java.util.HashMap;

import constraintNetwork.Network;
import constraintNetwork.NodeConstraint;
import constraintNetwork.NodeVariable;
import constraintNetwork.Vertex;
import constraintNetworkTests.Example;
import cspElements.Constraint;
import cspElements.Variable;

public class CSP2NetworkTest {
	//Variables
	
		private Variable A, B, C, D, E;
		
		//Constraints
		private Constraint C1,C2, C3, C4, C5, C6;
		
		// Variables for constraints
		private Variable[] varsC1, varsC2, varsC3, varsC4, varsC5, varsC6;
		
		//Version constraints
		private Constraint VC1, VC2;
		
		//Variables for version constraints
		private Variable varVC1, varVC2;
		
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
			 		 
			 		
			 VC2= new Constraint("E#\\= 1"); // en Java hay que escapar el control \
			 VC2.addVariable(E);
		}
		 
			/**
			 * This method tests:
			 * 1. the network has the right number of constraints
			 * 2. the right number of variables
			 * 3. the right number of constraintNodes
			 * 4. the right number of variableNodes
			 * @return true if all the test are satisfied
			 */
			 public boolean test1(Network net){
				 boolean tests=false;
				 tests= (net.getConstraintsCount() == 6)
						 &&
						 (net.getVariablesCount() ==5)
						 &&
						 (net.constraintsSize() ==5)
						 &&
						 (net.variablesSize() ==5);
				return tests;
			}
			 
			 /**
			  * This method tests:
			  * 1. all edges are correctly created.  For this test we print the id of the neighbors for each vertex. 
			  * @return true if all the tests are satisfied
			  */
			 public void test2(Network net){
				//boolean tests=false;
				 
				//neighbors for variables nodes
				 System.out.println("Problem variables");
				 HashMap<String,NodeVariable> vars= net.getVariables();
				 for (String id : vars.keySet()) {
					 System.out.print("adjacent nodes of variable "+ id + " : " );
					 for (Vertex v: net.getNeighbors(id, Vertex.VARIABLE_TYPE)){
						 System.out.print(v.getId()+" ");	 
					 }
					 System.out.println();
				}
				 
				//neighbors for constraint nodes
				 System.out.println("Problem constraints");
				 HashMap<String,NodeConstraint> cons= net.getConstraints();
				 for (String id : cons.keySet()) {
					 System.out.print("adjacent nodes of constraint "+ id + " : " );
					 for (Vertex v: net.getNeighbors(id, Vertex.CONSTRAINT_TYPE)){
						 System.out.print(v.getId()+" ");	 
					 }
					 System.out.println();
				} 
				 
				// neighbors for constraint nodes
				 
				
				//return tests;
			 }
			 
			 /**
			  * This method tests:
			  * 1. the list of constraints in the nodes are correct
			  * @param net
			  */
			 public void test3(Network net){
				 
					//Domain constraints
				 System.out.println("Domain constraints for variables");
				 HashMap<String,NodeVariable> vars= net.getVariables();
				 for (String id : vars.keySet()) {
					 System.out.println("Domain constraints for variable "+ id + " : " );
					 NodeVariable var= vars.get(id);
					 for(Constraint cons: var.getConstraints()){
						 System.out.println(cons.getExpression());
					 }
					 System.out.println("----");
				}
				 
				 // Constraints
				 System.out.println("Constraints in constraint nodes");
				 HashMap<String,NodeConstraint> constraints= net.getConstraints();
				 for (String id : constraints.keySet()) {
					 System.out.println("Constraints for constraint node "+ id + " : " );
					 NodeConstraint node= constraints.get(id);
					 for(Constraint cons: node.getConstraints()){
						 System.out.println(cons.getExpression());
					 }
					 System.out.println("----");
				}
				 
			 }
			 
				public static void main(String[] args) {
					// TODO Auto-generated method stub
					
					Network net= new Network();
					Example e= new Example();
					e.setUp();
					e.load1(net);
					System.out.println(e.test1(net));
					e.printNetwork(net);
					e.test2(net);
					e.test3(net);
					
					

					
					
					


				}


}
