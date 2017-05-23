package individual;

import java.util.ArrayList;

import cases.RenaultLogan;
import cspElements.CSP;
import cspElements.Constraint;

import minimalSets.MinimalSetsBFS;
import minimalSets.MinimalSetsDFS;

public class MinimalRenault {
	static String path= "C:/Users/Angela Villota/Documents/Tests/";
	public MinimalRenault(){
		csp= new CSP();

	}
	
	private static CSP csp;
	
	public static void main(String[] args) {
	//Seting up the scenario
			MinimalRenault m= new MinimalRenault();
			RenaultLogan problem1= new RenaultLogan();
			csp= problem1.getCSP();
			
			//Starting the algorithm
			MinimalSetsDFS minimal = new MinimalSetsDFS(csp, path);
			//MinimalSetsBFS minimal = new MinimalSetsBFS(csp, path);
			ArrayList<Constraint> set=minimal.startAlgorithm(problem1.getStart());
			
			if (set.isEmpty()){
				System.out.println("consistent CSP");
			}else{


				System.out.println("conflict constraints");
				for (Constraint constraint : set) {
					System.out.println(constraint.getExpression());
				}
			}
			
			

		}



}
