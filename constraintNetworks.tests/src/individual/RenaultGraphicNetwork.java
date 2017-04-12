package individual;

import java.util.HashSet;

import cases.Felferning;
import cases.RenaultLogan;
import constraintNetwork.Network;
import cspElements.CSP;
import cspElements.Constraint;
import cspElements.Variable;
import graphicConstraintNetwork.GraphicNetwork;
import graphicConstraintNetwork.Window;
import transform.CSP2File;
import transform.CSP2Network;

public class RenaultGraphicNetwork {
	private HashSet<Variable> vars;
	private HashSet<Constraint> constraints;
	private static CSP csp;
	static String pathOut="/Users/Angela/Google Drive/Doctorate_Angela/Publications/In_preparation/Renault/Desarrollo/Test/Test1/out/";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Seting up the scenario
		
		RenaultGraphicNetwork m= new RenaultGraphicNetwork();
		//creando el csp de renault
		RenaultLogan problem1= new RenaultLogan();
		csp= problem1.getCSP();
		Network net= m.csp2network(csp);
		Window mainWindow= new Window(net);
		CSP2File converter= new CSP2File(csp);
		converter.transform(pathOut, "Renault" );
//		GraphicNetwork gnet= new GraphicNetwork(net);

	}
	public Network csp2network(CSP csp){
		System.out.println("transforming into constraint network");
		CSP2Network csp2net= new CSP2Network(csp);
		Network net= csp2net.transform();
		
		
		return net;
	}

}
