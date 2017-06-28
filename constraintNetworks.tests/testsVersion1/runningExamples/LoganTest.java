package runningExamples;

import java.util.ArrayList;

import cases.Felferning;
import cases.RenaultLogan;
import common.TestCase;
import cspElements.CSP;
import cspElements.Constraint;
import graph.ConstraintGraph;
import minimalSets.MinimalSetsBFS;
import minimalSets.MinimalSetsDFS;

public class LoganTest {
	public static void main(String[] args){
		//to run a test:
				// create an instance of the problem, or read from the file
				RenaultLogan problem = new RenaultLogan ();
				
				//cerate an instance of the testCase class, to make the tests
				// the inout is the path and the name of the problem
				TestCase test= new TestCase("C:/Users/Angela Villota/Documents/Tests/Renault/","Renault");
				
				
				//start the window if you want a graphic test
				ConstraintGraph net= test.startWindow(problem.getCSP());
				test.printCSP();
				
				//start the test, the input is the constraint graph and the amount of tests to perform
				test.startTests(net, 1, "Carbody");
			}
}
