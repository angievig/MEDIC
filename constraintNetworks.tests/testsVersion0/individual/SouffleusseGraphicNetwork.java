package individual;

import java.util.HashSet;

import cspElements.CSP;
import cspElements.Constraint;
import cspElements.Variable;
import graph.ConstraintGraph;
import graphicConstraintNetwork.GraphicNetwork;
import graphicConstraintNetwork.Window;
import prologParser.XCSPPrologParser;
import transform.CSP2File;
import transform.CSP2Network;

public class SouffleusseGraphicNetwork {
	private static CSP csp;
	static private String path= "C:/Users/Angela Villota/Documents/Tests/Souffleuse/";
	static String filename="C:/Users/Angela Villota/Documents/Tests/Souffleuse/Renault-souffleuse-pos.xml";
	static private XCSPPrologParser Xparser;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Seting up the scenario desde el archivo
		
		try {
			Xparser = new XCSPPrologParser(filename);
			csp= Xparser.getCSP();
			SouffleusseGraphicNetwork m= new SouffleusseGraphicNetwork();
			ConstraintGraph net= m.csp2network(csp);
			Window mainWindow= new Window(net);
			prologFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		GraphicNetwork gnet= new GraphicNetwork(net);

	}
	public ConstraintGraph csp2network(CSP csp){
		System.out.println("transforming into constraint network");
		CSP2Network csp2net= new CSP2Network(csp);
		ConstraintGraph net= csp2net.transform();
		
		
		return net;
	}
	
	static public void prologFile(){
		CSP2File converter= new CSP2File(Xparser.getCSP());
		converter.transform(path, 1, "Renault-souffleuse-pos");
		System.out.println("ok_" +"Renault-souffleuse-pos" );
	}

}
