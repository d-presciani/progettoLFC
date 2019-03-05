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
				String charTemp = String.valueOf(mom.charAt(0));
				boolean trovato = false;
				NonTerminale ntSX = null;
				for(NonTerminale nt: listaNT) {
					if(nt.lettera.equals(charTemp)) {
						trovato = true;
						ntSX = nt;
						break;
					}
				}
				
				/*
				for(int i=0; i<listaNT.size(); i++) {
					if(listaNT.get(i).lettera.equals(charTemp)) {
						trovato = true;
						ntSX=listaNT.get(i);
					}
				}
				*/
				
				if(!trovato) {
					ntSX = new NonTerminale(charTemp);
					listaNT.add(ntSX);
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
							
							/*
							for(int j=0; j<listaNT.size();j++) {
								if(listaNT.get(j).lettera.equals(mom2)) {
									ntTemp = listaNT.get(j);
									trovato = true;
								}
							}
							*/
							
							if(!trovato) {
								ntTemp = new NonTerminale(mom2);
								listaNT.add(ntTemp);
							}
							listaMom.add(ntTemp);
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
		
		//Calcolo annullabilità regole
		for(RegolaDiProduzione reg : listaReg) {
			reg.calcolaAnnullabilita();
		}
		
		//Calcolo annullabilità caratteri
		for (NonTerminale nt: listaNT) {
			nt.calcolaAnnullabile();
		}
		
		
		// Controllo assegnazione regole
		for(NonTerminale nt : listaNT) {
			System.out.println(nt.getRegole());
		}
		
		//Controllo annullabilità regole
		for(RegolaDiProduzione reg : listaReg) {
			System.out.println(reg.toString() + " | annullabile: "+ reg.annullabile);
		}
		
		
		//Controllo annullabilità NT
		for(NonTerminale nt : listaNT) {
			System.out.println(nt.lettera + " isAnnullabile(): "+ nt.isAnnullabile());
		}
		
	}

}
