package transform;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;

import cspElements.CSP;
import cspElements.Constraint;
import cspElements.Variable;

public class CSP2FileRandom {
	public static final String PATH="prologFiles/";
	public static final String START=":-use_module(library(clpfd)).\nproductline(L):-\n" ;
	public static final String FINAL="labeling([ff],L).";
	public static final String FILE="csp";
	public static final String EXT=".pl";
	
	private BufferedWriter out;
	private RandomAccessFile rout;

	
	
	private CSP csp;
	
	public CSP2FileRandom(CSP csp){
		this.csp=csp;
	}
	
	public String transform(String path, int count, String direction){
		String fileName=initFile(path, count, direction);
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
		String fileName= path+FILE+count+direction+EXT;
		try {
			System.out.println(count);
//			//si el contador es 1 entonces es la primera iteración, no hay primera línea que eliminar
//			if (count==1){
//				System.out.println("en el if");
//				rout= new RandomAccessFile(path+FILE+EXT, "rw");
//				rout.seek(0);
//				rout.writeUTF("Primera Linea \n");
//				rout.writeUTF("Segunda linea .");
//				//rout.writeUTF("\nSegunda Linea que termina por . y que va a cambiar por .");
//
//			}
//			else{
////				System.out.println(count);
//     			rout= new RandomAccessFile(path+FILE+EXT, "rw");
////				rout.seek(rout.length());
////				rout.read();
////				rout.writeUTF(",");
////				rout.writeUTF("\n otra linea .");
////				
//				long length = rout.length();
//				byte b;
//				do {                     
//					length -= 1;
//					rout.seek(length);
//					b = rout.readByte();
//				} while(b != 10 && length > 0);
//				if (length == 0) { 
//					rout.setLength(length);
//				} else {
//					rout.setLength(length + 1);
//				}
//				rout.writeUTF("\n otra linea  "+ count);
//			}
//			
//			rout.close();
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		writeInFile(START);
		
		//System.out.println(START);
		return fileName;
	}
	
	public void quitarPunto(){
		
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
	
	
	
	

}
