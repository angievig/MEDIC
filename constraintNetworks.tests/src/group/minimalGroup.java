package group;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cspElements.CSP;
import cspElements.Constraint;
import minimalSets.MinimalSetsDFS;
import prologParser.XCSPPrologParser;
import transform.CSP2File;

public class minimalGroup {
	static String pathIn= "C:/Users/Angela Villota/Documents/Tests/Grupo/in/";
	static String pathOut="C:/Users/Angela Villota/Documents/Tests/Grupo/Execution/";
	static XCSPPrologParser Xparser;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		leerCarpeta(pathIn, pathOut);
		
		
		
		

	}
	
	private static void leerCarpeta(String directoryPath,  String outputDirectoryPath) {
		List<File> fileList = FileUtils.readFileFromDirectory(directoryPath);

		try {
			String nombre= "Problema";
			int i=1;
			for (File file : fileList) {
				// Se toma el tiempo inicial
				if (file.getPath().endsWith("sxfm")
						|| file.getPath().endsWith("splx")
						|| file.getPath().endsWith("xml")) {
					String name= file.getName();
					System.out.println("Staring Problem "+ name);
					long startTime = System.currentTimeMillis();
					Xparser = new XCSPPrologParser(file.getPath());
					long endTime = System.currentTimeMillis();
					System.out.println("Parsing " + name +" "+(endTime - startTime) + " milliseconds");
					
					CSP csp= Xparser.getCSP();
					MinimalSetsDFS minimal = new MinimalSetsDFS(csp, pathOut+nombre+i+"/");
					
					startTime = System.currentTimeMillis();
					ArrayList<Constraint> set=minimal.startAlgorithm(csp.getStart());	
					endTime = System.currentTimeMillis();
					System.out.println("Evaluating time " + name +" "+(endTime - startTime) + " milliseconds");
					
					if (set.isEmpty()){
						System.out.println("consistent CSP");
					}else{
						System.out.println("conflict constraints");
						for (Constraint constraint : set) {
							System.out.println(constraint.getId()+" :" +constraint.getExpression());
						}
					}
					
					CSP2File converter= new CSP2File(Xparser.getCSP());
					converter.transform(outputDirectoryPath, name.substring(0, name.length()-4));
					
					
				i++;	
				}

			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}


}
