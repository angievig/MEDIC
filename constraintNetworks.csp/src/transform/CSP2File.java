package transform;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import cspElements.CSP;
import cspElements.Constraint;
import cspElements.Variable;

public class CSP2File {
	public static final String PATH="prologFiles/";
	public static final String START=":-use_module(library(clpfd)).\nproductline(L):-\n" ;
	public static final String FINAL="labeling([ff],L).";
	public static final String LABELING="labeling([ff],L)";
	public static final String FILENAME="csp";
	public static final String EXT=".pl";
	
	private BufferedWriter out;

	
	
	private CSP csp;
	
	public CSP2File(CSP csp){
		this.csp=csp;
	}
	
	public CSP2File(){
		
	}
	
	
	
	public String transform(String path, int count, String direction){
		String fileName=initFile( path, count, direction);
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
	
	public String transform(String path, String filename){
		String fileName=initFile(path, filename);
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
	public String initFile( String path, String name){
		String fileName= path+name+EXT;
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
	
	public String initFile(String path, int count, String direction){
		String fileName= path+FILENAME+count+direction+EXT;
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
			//System.out.println(sentences);
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
	
	//------------------------------------------------------------------>>>>>
	// StringBuilder methods
	
	/**
	 * Pone las declaraciones: variables y dominios en el string builder
	 * @param csp 
	 * @param sb
	 * @return
	 */
	public StringBuilder initCSPBuilder(CSP csp, StringBuilder sb){
		
		sb.append(START);
		
		//poner variables
		sb.append("L=[");
		String variables= "";
		for(Variable var: csp.getVariables()){
			variables+=" "+var.getId() + ",";
		}
		variables=variables.substring(0, variables.length()-1); // take out the last comma
		sb.append(variables);
		sb.append("], \n");

		//poner dominios
		
		for(Variable var: csp.getVariables()){
			sb.append(var.getId()+" in "+var.getDomain() + ", \n");
		}
		

		
		return sb;
	}
	
	/**
	 * incluye las nuevas restricciones en el stringBuilder
	 * @param sb
	 * @return
	 */
	public StringBuilder appendConstraints(StringBuilder sb, ArrayList<Constraint> constraints){
		
		for(Constraint cons: constraints){
			//System.out.println("nueva restricción: " + cons.getExpression());
			sb.append( cons.getExpression()+ ", \n");
		}
		return sb;
	}
	
	public String createFile(StringBuilder sb, String path){
		String fileName= path+FILENAME+EXT;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
			//poner labeling
			//sb.append(LABELING);
			out.write(sb.toString()+LABELING+".");
			//System.out.println(sb.toString()+".");
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		
		return fileName;
	}
	
	

}
