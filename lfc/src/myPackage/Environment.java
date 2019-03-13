package myPackage;

import java.util.ArrayList;
import java.util.Hashtable;

import org.antlr.runtime.*;


public class Environment {
	public Hashtable<String, Variable> symbols;
	public ArrayList<String> errorList;
	
	public ArrayList<String> warningList;
	public String translation;
	
	public Environment () {
		symbols = new Hashtable<String, Variable>(101);
		errorList = new ArrayList<String> ();
		warningList = new ArrayList<String>  ();
		translation = "";
	}
	
	public void insertVariable (Token var) {
		if (symbols.containsKey(var.getText()))
			errorList.add("Semantic Error [" + var.getLine() + 
						", " + var.getCharPositionInLine() + "] :" +
						" variable " + var.getText() + " has been already defined");
		else {
			Variable v = new Variable (var.getText());
			symbols.put(v.name, v);
		}
	}
			
	public float checkReference(Token ref) {
		if (!symbols.containsKey(ref.getText()))		{
			errorList.add("Semantic Error [" + ref.getLine() + 
					", " + ref.getCharPositionInLine() + "] :" +
					" variable " + ref.getText() + " has not defined yet");
				return 0;
		}
		return symbols.get(ref.getText()).value;
	}

	public float getInt (Token ref) {return Integer.parseInt(ref.getText());}
	public float getChar (Token ref) {System.out.println(ref.getText());return (int) ref.getText().charAt(1);}
	public float getFloat (Token ref) {return Float.parseFloat(ref.getText());}
	
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
	
	public int getValue (Token var) {
		if (!symbols.containsKey(var.getText()))
			errorList.add("Semantic Error [" + var.getLine() + 
						", " + var.getCharPositionInLine() + "] :" +
						" variable " + var.getText() + " not defined");
		else {
			Variable v = symbols.get(var.getText());
			if (v.value == null)
				errorList.add("Semantic Error [" + var.getLine() + 
						", " + var.getCharPositionInLine() + "] :" +
						" variable " + var.getText() + " has not been initalizated");
			else
				return v.getValue();
		}
		
		return 1;
	}
	
	
	public void putVarValue (Token var, Param e) {
		if (!symbols.containsKey(var.getText()))
			errorList.add("Semantic Error [" + var.getLine() + 
						", " + var.getCharPositionInLine() + "] :" +
						" variable " + var.getText() + " not defined");
		else {
			Variable v = symbols.get(var.getText());
			v.value=e.value;		
		}
			
		
	}
}
