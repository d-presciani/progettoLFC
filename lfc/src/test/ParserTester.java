package test;

import java.io.FileReader;

import org.antlr.runtime.*;

import lr1Package.*;


public class ParserTester  {
	static PrototipoLR1Parser parser;
  
	public static void main(String[] args) {
		CommonTokenStream tokens;
  	String fileIn = ".\\resources\\te31032015.txt";

  	boolean errore =false;
  	try {
			System.out.println ("IDENTIFICAZIONE GRAMMATICHE LR(1)\n");
			PrototipoLR1Lexer lexer = new PrototipoLR1Lexer(new ANTLRReaderStream(new FileReader(fileIn))); 
			tokens = new CommonTokenStream(lexer);
	    parser = new PrototipoLR1Parser(tokens);

	    parser.lr1();
	    
	    if(parser.getErrorList().size()>0) {
	    	errore = true;
	    }
		    for (int i=0;i<parser.getErrorList().size();i++) {
		    	System.out.println((i+1) + ".\t" + parser.getErrorList().get(i));
		    }
		} catch (Exception e) {
			errore = true;
			System.out.println ("Parsing con ANTLR abortito\n\n");
		}
  	if(!errore) {
  		parser.solve();
  	}
  }
}
