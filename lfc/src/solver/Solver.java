package solver;

import java.util.LinkedList;

public class Solver {
	
	
	public boolean solve(LinkedList<NonTerminale> listaNT, LinkedList<RegolaDiProduzione> listaReg) {
		
		LinkedList<Stato> listaStati = new LinkedList<Stato>();
		LinkedList<String> listaTransizioni = new LinkedList<String>();
		
		//Calcolo annullabilità regole
		for(RegolaDiProduzione reg : listaReg) {
			reg.calcolaAnnullabilita();
		}
		
		//Calcolo annullabilità caratteri
		for (NonTerminale nt: listaNT) {
			nt.calcolaAnnullabile();
		}
		
		//Creo il primo stato
		Stato mom = new Stato();
		try {
			mom.aggiungiCore(listaReg.get(0));
		} catch (ErroreSemantico e) {
			System.err.println("ERRORE SEMANTICO! " + e.getMessage());
			System.exit(0);
		}
		listaStati.add(mom);
		
		int i=0;
		
		// Espando tutti gli stati
		while(i<listaStati.size()) {
			try {
				listaStati.get(i).espandiStato(listaStati, listaTransizioni);
			} catch (ErroreSemantico e) {
				System.err.println("ERRORE SEMANTICO!" + e.getMessage());
				System.exit(0);
			}
			i++;
		}
		
		System.out.println("Numero stati: " + listaStati.size());
		
		System.out.println("\nElenco degli stati:");
		for(Stato stt : listaStati) {
			System.out.println(stt.toString());
		}
		
		System.out.println("\nElenco delle transizioni");
		for(String transizione : listaTransizioni) {
			System.out.println(transizione);
		}
		
		// Controllo che la grammatica sia LR(1)
		boolean isLR1 = true;
		for(Stato stt : listaStati) {
			if(stt.erroreLR1) {
				isLR1 = false;
			}
		}
		
		// Stampo risultato controllo
		if(isLR1) {
			System.out.println("\nLa grammatica inserita è LR(1)");
			return true;
		} else {
			System.out.println("\nLa grammagita inserita non è LR(1), gli stati che contengono conflitti sono:\n");
			for(Stato stt : listaStati) {
				if(stt.erroreLR1) {
					System.out.println("S"+stt.numeroStato);
				}
			}
			return false;
		}
		
	}
}