package group;

import java.io.File;
import java.util.List;

import prologParser.XCSPPrologParser;
import transform.CSP2File;

public class parserGroup {
	static String pathIn= "/Users/Angela/Google Drive/Doctorate_Angela/Publications/In_preparation/Renault/Desarrollo/Test/Test1/in/";
	static String pathOut="/Users/Angela/Google Drive/Doctorate_Angela/Publications/In_preparation/Renault/Desarrollo/Test/Test1/out/";
	static XCSPPrologParser Xparser;


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		leerCarpeta(pathIn, pathOut);
		
		
		
		

	}
	
	private static void leerCarpeta(String directoryPath,  String outputDirectoryPath) {
		List<File> fileList = FileUtils.readFileFromDirectory(directoryPath);

		try {
			for (File file : fileList) {
				// Se toma el tiempo inicial
				if (file.getPath().endsWith("sxfm")
						|| file.getPath().endsWith("splx")
						|| file.getPath().endsWith("xml")) {
					String name= file.getName();
					long startTime = System.currentTimeMillis();
					Xparser = new XCSPPrologParser(file.getPath());
					long endTime = System.currentTimeMillis();
					System.out.println("Parsing " + name +" "+(endTime - startTime) + " milliseconds");
					CSP2File converter= new CSP2File(Xparser.getCSP());
					converter.transform(outputDirectoryPath, name.substring(0, name.length()-4));
					
					
					
				}

			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	

}
