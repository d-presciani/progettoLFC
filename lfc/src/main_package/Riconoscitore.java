package main_package;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.FileReader;
import org.antlr.runtime.*;

import lr1package.*;

import java.util.Scanner;

public class Riconoscitore  {
	static PrototipoLR1Parser parser;
  
	public static void main(String[] args) {
		
		Scanner capitano = new Scanner(System.in);
	
		// Richiesta del file di input all'utente
		FileDialog dialog = new FileDialog((Frame)null, "Scegliere il file da aprire");
		dialog.setMode(FileDialog.LOAD);
		dialog.setFile("*.txt");
		dialog.setVisible(true);
		String file = dialog.getDirectory() + dialog.getFile();
		if(dialog.getFile() == null) {
			System.out.println("Nessun file selezionato, il programma verrà chiuso."); //NOPMD
			capitano.hasNextLine();
			capitano.close();
			System.exit(0);
		}
		dialog.dispose();
		
		CommonTokenStream tokens;
	  	String fileIn = file;
	
	  	boolean errore = false;
	  	try {
				System.out.println ("IDENTIFICAZIONE GRAMMATICHE LR(1)\n"); //NOPMD
				PrototipoLR1Lexer lexer = new PrototipoLR1Lexer(new ANTLRReaderStream(new FileReader(fileIn))); 
				tokens = new CommonTokenStream(lexer);
		    parser = new PrototipoLR1Parser(tokens);
	
		    parser.lr1();
		    
		    if(parser.getErrorList().size()>0) {
		    	errore = true;
		    }
			    for (int i=0;i<parser.getErrorList().size();i++) {
			    	System.out.println((i+1) + ".\t" + parser.getErrorList().get(i)); //NOPMD
			    }
			} catch (Exception e) {
				errore = true;
				System.out.println ("Parsing con ANTLR abortito\n\n"); //NOPMD
			}
	  	if(!errore) {
	  		parser.solve();
	  	}
	  	
	  	System.out.println("\nPremere INVIO per uscire dal programma."); //NOPMD
	  	capitano.nextLine();
	  	capitano.close();
	}
}
