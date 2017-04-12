package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import cspElements.CSP;
import cspElements.Constraint;
import cspElements.Variable;

public class CSP2File {
	public static final String PATH="prologFiles/";
	public static final String START=":-use_module(library(clpfd)).\nproductline(L):-\n" ;
	public static final String FINAL="labeling([ff],L).";
	public static final String FILE="csp";
	public static final String EXT=".pl";
	
	private BufferedWriter out;

	
	
	private CSP csp;
	
	public CSP2File(CSP csp){
		this.csp=csp;
	}
	
	public String transform(int count, String direction){
		String fileName=initFile(count, direction);
		writeVariables();
		writeDomains();
		writeConstraints();

		try {
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileName;
		
	}
	/**
	 * Method for create the prolog file
	 */
	public String initFile(int count, String direction){
		String fileName= PATH+FILE+count+direction+EXT;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		writeInFile(START);
		//System.out.println(START);
		return fileName;
	}
	/**
	 * 
	 * @param sentences is a string with the sentences in prolog 
	 */
	public void writeInFile(String sentences){
		try {
			out.write(sentences);
			System.out.println(sentences);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * method for build the variables declarations
	 */
	public void writeVariables(){
		String sentences="L=[";
		
		for(Variable var: csp.getVariables()){
			sentences+=" "+var.getId() + ",";
		}
		sentences=sentences.substring(0, sentences.length()-1); // take out the last comma
		sentences+="], \n";
		
		//escribir en el archivo
		writeInFile(sentences);
	}
	
	public void writeDomains(){
		String sentences="";
		String domain="fd_domain(";
		
		for(Variable var: csp.getVariables()){
			sentences+= var.getId()+" in "+var.getDomain() + ", \n";
		}
		
		//escribir en el archivo
		writeInFile(sentences);
	}
	
	public void writeConstraints(){
		String sentences="";
		//String domain="fd_domain (";
		
		for(Constraint cons: csp.getConstraints()){
			sentences+= cons.getExpression() + ", \n";
		}
		sentences+=FINAL;
		//escribir en el archivo
		writeInFile(sentences);
		
	}
	
	
	
	

}
