package test;

import java.io.FileReader;

import org.antlr.runtime.*;

import lr1Package.*;


public class ParserTester  {
	static PrototipoLR1Parser parser;
  
	public static void main(String[] args) {
		CommonTokenStream tokens;
  	String fileIn = ".\\resources\\input.file";
  	
  	try {
			System.out.println ("LR1 Parsing con ANTLR\n");
			PrototipoLR1Lexer lexer = new PrototipoLR1Lexer(new ANTLRReaderStream(new FileReader(fileIn))); 
			tokens = new CommonTokenStream(lexer);
	    parser = new PrototipoLR1Parser(tokens);

	    parser.lr1();

	    System.out.println("ERRORI:" + parser.getErrorList().size());
	    
	    for (int i=0;i<parser.getErrorList().size();i++) {
	    	System.out.println((i+1) + ".\t" + parser.getErrorList().get(i));
	    }
		} catch (Exception e) {
			System.out.println ("Parsing con ANTLR abortito\n\n");
			e.printStackTrace();
		}
  }
}
