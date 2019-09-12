package main_package;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.FileReader;
import org.antlr.runtime.*;

import lr1package.*;

import java.util.Scanner;

public final class Riconoscitore  {
	

	private static PrototipoLR1Parser parser;
	
	private Riconoscitore() {}
	
	public static void main(final String[] args) {
		
		final Scanner inputUtente = new Scanner(System.in);
	
		// Richiesta del file di input all'utente
		final FileDialog dialog = new FileDialog((Frame)null, "Scegliere il file da aprire");
		dialog.setMode(FileDialog.LOAD);
		dialog.setFile("*.txt");
		dialog.setVisible(true);
		final String file = dialog.getDirectory() + dialog.getFile();
		String fileName = dialog.getFile(); //Salvataggio nomeFile per print successivo nel grafico
		dialog.dispose();
		if(dialog.getFile() == null) {
			System.out.println("Nessun file selezionato, premere INVIO per chiudere il programma."); //NOPMD
			inputUtente.hasNextLine();
			inputUtente.close();
		} else {
					
			CommonTokenStream tokens;
		
		  	boolean errore = false;
		  	try {
					System.out.println ("IDENTIFICAZIONE GRAMMATICHE LR(1)\n"); //NOPMD
					PrototipoLR1Lexer lexer = new PrototipoLR1Lexer(new ANTLRReaderStream(new FileReader(file)));
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
		  		parser.solve(fileName);
		  	}
		  	
		  	System.out.println("\nPremere INVIO per uscire dal programma."); //NOPMD
		  	inputUtente.nextLine();
		  	inputUtente.close();
		}
	}
}
