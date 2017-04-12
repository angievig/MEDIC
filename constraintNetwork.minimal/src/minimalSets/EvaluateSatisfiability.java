package minimalSets;

import java.util.Hashtable;
import java.util.Map;

import org.jpl7.*;

public class EvaluateSatisfiability {
	
	public static void main(String args[]){
		
		String t0 = "consult('prologFiles/csp.pl')";
		Query q1= new Query(t0);
		if (!q1.hasSolution()) {
			System.out.println(t0 + " failed");
			// System.exit(1);
		}else
		{
			System.out.println("passed");
		}
		//System.out.println("passed");
		String consulta= "productline(L).";
		Query q2 = new Query(consulta);
		System.out.println(q2.hasSolution());
		Map<String, Term>[] table = q2.allSolutions();
		System.out.println(table.length);
		
	}
	
	public boolean consult(String programName){
		boolean salida=false;
		String query1= "consult('"+programName+"')";
		Query q1= new Query(query1);
		q1.hasSolution();
		//System.out.println("passed");
		String consulta= "productline(L).";
		Query q2 = new Query(consulta);
		//System.out.println(q2.hasSolution());
//		Map<String, Term>[] table = q2.nSolutions(1);
//		salida= (table.length >= 1);
		salida= q2.hasSolution();
		return salida;
		//System.out.println(table.length);
		//return Query.hasSolution(query);
	}

}
