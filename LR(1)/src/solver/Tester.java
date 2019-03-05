package solver;

import java.util.LinkedList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Tester {
	static LinkedList<NonTerminale> listaNT = new LinkedList<NonTerminale>();
	static LinkedList<RegolaDiProduzione> listaReg = new LinkedList<RegolaDiProduzione>();
	
	//Codice che andrebbe buttato in ?antlr?
	
	//TODO: SISTEMARE IL FATTO CHE OGNI REGOLA DI PRODUZIONE PUNTI A DEI NON TERMINALI A CASO CHE NON SONO QUELLI PRESENTI IN listaNT!!!
	//Questo perché quando vado a calcolare l'annullabilità della regola non le calcola correttamente
	public static void leggiFile() {
		try {
			Scanner scanner = new Scanner(new File(".\\resources\\input.txt"));
			while (scanner.hasNextLine()) {
				String mom = scanner.nextLine();
				NonTerminale ntSX = new NonTerminale(String.valueOf(mom.charAt(0)));
				boolean trovato = false;
				for(NonTerminale nt : listaNT) {
					if(nt.lettera.equals(ntSX.lettera)) {
						trovato = true;
					}
				}
				if(!trovato) {
					listaNT.add(ntSX);
				} else {
					for(NonTerminale nt: listaNT) {
						if(nt.lettera.equals(ntSX.lettera)) {
							ntSX=nt;
						}
					}
				}
				List<Carattere> listaMom = new LinkedList<Carattere>();
				if(mom.length()==2) {
					ntSX.setAnnullabile();
					listaReg.add(new RegolaDiProduzione(ntSX, null));
				} else {
					for(int i=2; i<mom.length();i++) {
						if(Character.isLowerCase(mom.charAt(i))) {
							Terminale terTemp = new Terminale(String.valueOf(mom.charAt(i)));
							listaMom.add(terTemp);
						} else {
							NonTerminale ntTemp = new NonTerminale(String.valueOf(mom.charAt(i)));
							listaMom.add(ntTemp);
							trovato = false;
							for(NonTerminale nt : listaNT) {
								if(nt.lettera.equals(ntSX.lettera)) {
									trovato = true;
								}
							}
							if(!trovato) {
								listaNT.add(ntSX);
							}
						}
					}
					listaReg.add(new RegolaDiProduzione(ntSX, listaMom));
				}
				
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		leggiFile();
		/*
		System.out.println(listaNT.toString());
		for(RegolaDiProduzione reg : listaReg) {
			System.out.println(reg.toString());
		}
		*/
		
		for(RegolaDiProduzione reg : listaReg) {
			for(NonTerminale nt : listaNT) {
				if(reg.parteSX.lettera.equals(nt.lettera)) {
					nt.addRegola(reg);
					break;
				}
			}
		}
		
		for(RegolaDiProduzione reg : listaReg) {
			reg.calcolaAnnullabilita();
		}
		
		// Controllo assegnazione regole
		for(NonTerminale nt : listaNT) {
			System.out.println(nt.getRegole());
		}
		
		//Controllo annullabilità regole
		for(RegolaDiProduzione reg : listaReg) {
			System.out.println(reg.toString() + " | annullabile: "+ reg.annullabile);
		}
		
		/*
		NonTerminale NTS = new NonTerminale("S");
		NonTerminale NTA = new NonTerminale("A");
		NonTerminale NTB = new NonTerminale("B");
		Terminale CTA = new Terminale("a");
		Terminale CTB = new Terminale("b");
		List<Carattere> mom;
		
		//Creazione regola di produzione S->A
		mom = new LinkedList<Carattere>();
		mom.add(NTA);
		RegolaDiProduzione REG1 = new RegolaDiProduzione(NTS, mom);
		
		//Creazione regola di produzione S->b
		mom.clear();
		mom.add(CTB);
		RegolaDiProduzione REG2 = new RegolaDiProduzione(NTS, mom);
		
		//Creazione regola di produzione S->a
		mom.clear();
		mom.add(CTA);
		RegolaDiProduzione REG4 = new RegolaDiProduzione(NTS, mom);
		
		
		//Creazione regola di produzione A->a
		mom.clear();
		mom.add(CTA);
		RegolaDiProduzione REG3 = new RegolaDiProduzione(NTA, mom);
		
		NTB.setAnnullabile();
		mom.clear();
		mom.add(NTB);
		//mom.add(NTS);
		RegolaDiProduzione REG5 = new RegolaDiProduzione(NTA, mom);
		
		//System.out.println("Reg 5 completamente annullabile?: " + REG5.annullabile);
		
		//Inserimento sul non terminale S la sua regola
		NTS.addRegola(REG1);
		NTS.addRegola(REG2);
		NTS.addRegola(REG4);
		
		//Inserimento sul non terminale A la sua regola
		NTA.addRegola(REG3);
		
		
		/*
		//Calcolo inizi di S 
		System.out.println("Regole di produzione di S: ");
		NTS.stampaRegole();
		System.out.println("\nRegole di produzione di A: ");
		NTA.stampaRegole();
		System.out.println("\nInizi di S: ");
		System.out.println(NTS.calcolaInizi());
		*/
		
	}

}
