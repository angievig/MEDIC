package individual;

import java.util.HashSet;

import cases.Felferning;
import cases.RenaultLogan;
import cspElements.CSP;
import cspElements.Constraint;
import cspElements.Variable;
import graph.ConstraintGraph;
import graphicConstraintNetwork.GraphicNetwork;
import graphicConstraintNetwork.Window;
import transform.CSP2File;
import transform.CSP2Network;

public class FelferningGraphicNetwork {
	private HashSet<Variable> vars;
	private HashSet<Constraint> constraints;
	private static CSP csp;
	static String pathOut="C:/Users/Angela Villota/Documents/Tests/Felferning/logFiles/";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Seting up the scenario
		
		FelferningGraphicNetwork m= new FelferningGraphicNetwork();
		//creando el csp de renault
		Felferning problem1= new Felferning();
		csp= problem1.getCSP();
		ConstraintGraph net= m.csp2network(csp);
		Window mainWindow= new Window(net);
		CSP2File converter= new CSP2File(csp);
		converter.transform(pathOut, "felferning" );
//		GraphicNetwork gnet= new GraphicNetwork(net);

	}
	public ConstraintGraph csp2network(CSP csp){
		System.out.println("transforming into constraint network");
		CSP2Network csp2net= new CSP2Network(csp);
		ConstraintGraph net= csp2net.transform();
		
		
		return net;
	}

}
