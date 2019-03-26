package myPackage;

import java.util.ArrayList;

import org.antlr.runtime.*;


public class Environment {
	public ArrayList<String> errorList;
	
	public ArrayList<String> warningList;
	public String translation;
	
	public Environment () {
		errorList = new ArrayList<String> ();
		warningList = new ArrayList<String>  ();
		translation = "";
	}

	public void addError (String msg) {
		errorList.add(msg); 		
	}
	
	public void handleError(String[] tokenNames,
      RecognitionException e, String h, String m){
		
		String st = "Sintax Error. at [" + e.token.getLine() + 
        ", " + e.token.getCharPositionInLine()+"] : "
        + "Found " + tokenNames[e.token.getType()] + 
         "(" + e.token.getText() + ")"  
        
        ;
		if (e instanceof MissingTokenException)
		   st = st + m;		
		//st= st + "\n"+ e.getUnexpectedType()+ "\n" + h + " \n" + m ;
		
		addError(st); 
	}
}
