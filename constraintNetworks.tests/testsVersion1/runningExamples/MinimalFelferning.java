package runningExamples;

import java.util.ArrayList;

import cases.Felferning;
import cases.Felferning2;
import cases.RenaultLogan;
import cspElements.CSP;
import cspElements.Constraint;
import minimalSets.MinimalSetsBFS;
import minimalSets.MinimalSetsDFS;

public class MinimalFelferning {
	static String path= "C:/Users/Angela Villota/Documents/Tests/Felferning/";
	private static CSP csp;
	
	public static void main(String[] args) {
	//Seting up the scenario
			MinimalFelferning m= new MinimalFelferning();
			Felferning problem1= new Felferning();
			csp= problem1.getCSP();
			
			//Starting the algorithm
			
			MinimalSetsDFS minimal = new MinimalSetsDFS(csp, path);
			ArrayList<Constraint> set=minimal.startAlgorithm(problem1.getStart());
			
			if (set.isEmpty()){
				System.out.println("consistent CSP");
			}else{


				System.out.println("conflict constraints");
				for (Constraint constraint : set) {
					System.out.println(constraint.getId()+" :" +constraint.getExpression());
				}
			}
			
			

		}
	
	



}


