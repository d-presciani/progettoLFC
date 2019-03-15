package solver;

import java.util.LinkedList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Tester {
	// Liste di regole e NT
	static LinkedList<NonTerminale> listaNT = new LinkedList<NonTerminale>();
	static LinkedList<RegolaDiProduzione> listaReg = new LinkedList<RegolaDiProduzione>();

	// Quello che c'è qui va portato in ANTLR
	public static void leggiFile() {
		try {
			Scanner scanner = new Scanner(new File(".\\resources\\input.txt"));
			while (scanner.hasNextLine()) {
				String mom = scanner.nextLine(); 
				String charTemp = String.valueOf(mom.charAt(0)); // Questo sarebbe tipo il token
				boolean trovato = false; 
				NonTerminale ntSX = null;
				// Verifico che il non terminale non sia già presente, se già presente uso quello già presente (altrimenti si hanno problemi di consistenza)
				for(NonTerminale nt: listaNT) {
					if(nt.lettera.equals(charTemp)) {
						trovato = true;
						ntSX = nt;
						break;
					}
				}
				if(!trovato) {
					ntSX = new NonTerminale(charTemp);
					listaNT.add(ntSX);
				}
				
				List<Carattere> listaMom = new LinkedList<Carattere>(); // Lista di caratteri che formerà la parte DX della regola di produzione
				
				if(mom.length()==2) { // Su ANTLR idea: Creare una variabile sul -> e poi verificare su ; se uguale, in caso impostare la regola di produzione con null sulla parte dx
					ntSX.setAnnullabile();
					listaReg.add(new RegolaDiProduzione(ntSX, null)); 
				} else {
					for(int i=2; i<mom.length();i++) {
						if(Character.isLowerCase(mom.charAt(i))) { // Questo su ANTLR è gratise dato che te li differenzia il lexer
							Terminale terTemp = new Terminale(String.valueOf(mom.charAt(i)));
							// Aggiungo il terminale alla lista momentanea che andrà a formare la parte dx della regola di produzione
							listaMom.add(terTemp);
						} else {
							// Stessa verifica come sopra per i NT
							String mom2 = String.valueOf(mom.charAt(i));
							NonTerminale ntTemp = null;
							trovato = false;
							
							for(NonTerminale nt : listaNT) {
								if(nt.lettera.equals(mom2)) {
									ntTemp = nt;
									trovato = true;
									break;
								}
							}
							if(!trovato) {
								ntTemp = new NonTerminale(mom2);
								listaNT.add(ntTemp);
							}
							// Aggiungo il non terminale alla lista momentanea che andrà a formare la parte dx della regola di produzione
							listaMom.add(ntTemp);
						}
					}
					//Creo la regola di produzione e la aggiungo alla lista delle regole
					listaReg.add(new RegolaDiProduzione(ntSX, listaMom));
				}
				
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		NonTerminale mom = new NonTerminale("S0");
		List<Carattere> listaMom = new LinkedList<Carattere>();
		NonTerminale mom2 = new NonTerminale("S");
		listaNT.add(mom);
		listaNT.add(mom2);
		listaMom.add(mom2);
		listaMom.add(new Terminale("/cjswa"));
		RegolaDiProduzione regTemp = new RegolaDiProduzione(mom,listaMom);
		regTemp.seguiti.add("/emptyset");
		listaReg.add(regTemp);
		
		leggiFile();
		System.out.println("Inizio solver");
		Solver risolutore = new Solver();
		risolutore.solve(listaNT, listaReg);
		
		/*
		// Test funzionalità
		// Stampo per ogni non terminale le sue regole di produzione
		System.out.println("\nElenco regole di produzione per NT");
		for(NonTerminale nt : listaNT) {
			System.out.println(nt.getRegole());
		}
		
		// Stampo per ogni non regole di produzione se è annullabile
		System.out.println("\nAnnullabilità per ogni regola di produzione");
		for(RegolaDiProduzione reg : listaReg) {
			System.out.println(reg.toString() + " | annullabile: "+ reg.annullabile);
		}
		
		// Stampo per ogni NT se è annullabile
		System.out.println("\nAnnullabiltà per ongi NT");
		for(NonTerminale nt : listaNT) {
			System.out.println(nt.lettera + " isAnnullabile(): "+ nt.isAnnullabile());
		}
		
		// Controllo inizi di ogni NT
		System.out.println("\nInsieme degli inizi per ogni NT");
		for(NonTerminale nt: listaNT) {
			System.out.println("Insieme degli inizi di " + nt.lettera + ": " + nt.calcolaInizi().toString());
		}	
		*/
	}

}