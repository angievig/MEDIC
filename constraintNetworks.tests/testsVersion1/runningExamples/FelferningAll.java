package runningExamples;




import common.TestCase;
import cases.CarElectronics;
import cases.Felferning;
import cspElements.CSP;
import cspElements.Constraint;
import cspElements.Variable;
import graph.ConstraintGraph;
import graph.Vertex;
import graphicConstraintNetwork.Window;
import minimalSets.LogManager;
import minimalSets.MinimalSetsDFSBuilder;
import minimalSets.MinimalSetsDFSIterations;
import prologParser.XCSPPrologParser;
import transform.CSP2Network;

public class FelferningAll {
	public static void main(String[] args){
	//to run a test:
			// create an instance of the problem, or read from the file
			Felferning problem = new Felferning ();
			
			//cerate an instance of the testCase class, to make the tests
			// the inout is the path and the name of the problem
			TestCase test= new TestCase("C:/Users/Angela Villota/Documents/Tests/Felferning/","Felferning");
			
			
			//start the window if you want a graphic test
			ConstraintGraph net= test.startWindow(problem.getCSP());
			test.printCSP();
			
			//start the test, te input is the constraint graph and the amount of tests to perform
			test.startTests(net, 1, "Ubuntu");
		}
	}
