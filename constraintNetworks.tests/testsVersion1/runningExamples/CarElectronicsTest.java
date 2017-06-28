package runningExamples;

import cases.CarElectronics;
import common.TestCase;
import graph.ConstraintGraph;

public class CarElectronicsTest {
	
	public static void main(String[] args){
		//to run a test:
		// create an instance of the problem, or read from the file
		CarElectronics problem = new CarElectronics ();
		
		//cerate an instance of the testCase class, to make the tests
		// the inout is the path and the name of the problem
		TestCase test= new TestCase("C:/Users/Angela Villota/Documents/Tests/CarElectronics/","CarElectronics");
		
		
		//start the window if you want a graphic test
		ConstraintGraph net= test.startWindow(problem.getCSP());
		test.printCSP();
		
		//start the test, te input is the constraint graph and the amount of tests to perform
		test.startTests(net, 1, "Car_electronics");
	}
}
