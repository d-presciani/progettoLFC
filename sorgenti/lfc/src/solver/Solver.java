package solver;

import java.util.LinkedList;

public class Solver {
	
	
	public Risultati solve(LinkedList<NonTerminale> listaNT, LinkedList<RegolaDiProduzione> listaReg) {
		Stato.counter = 1;
		LinkedList<Stato> listaStati = new LinkedList<Stato>();
		LinkedList<Transizione> listaTransizioni = new LinkedList<Transizione>();
		
		//Calcolo annullabilit� regole
		for(RegolaDiProduzione reg : listaReg) {
			reg.calcolaAnnullabilita();
		}
		
		//Calcolo annullabilit� caratteri
		for (NonTerminale nt: listaNT) {
			nt.calcolaAnnullabile();
		}
		
		//Creo il primo stato
		Stato mom = new Stato();
		mom.aggiungiCore(listaReg.get(0));
		listaStati.add(mom);
		
		int i=0;
		
		// Espando tutti gli stati
		while(i<listaStati.size()) {
			listaStati.get(i).espandiStato(listaStati, listaTransizioni);
			i++;
		}
		
		// OLD Stampo in console
		/*
		System.out.println("Numero stati: " + listaStati.size()); //NOPMD
		
		System.out.println("\nElenco degli stati:"); //NOPMD
		for(Stato stt : listaStati) {
			System.out.println(stt.toString()); //NOPMD
		}
		
		System.out.println("\nElenco delle transizioni"); //NOPMD
		for(Transizione transizione : listaTransizioni) {
			System.out.println(transizione.toString()); //NOPMD
		}
		*/
		
		
		//NEW: Stampo il grafo
		LinkedList<String> nodi = new LinkedList<String>();
		for(Stato st : listaStati) {
			nodi.add(st.toGraph());
		}
		
		// OLD test per disegnare grafico
		/*
		JGraphXDrawer drawer = new JGraphXDrawer();
		drawer.draw(nodi, listaTransizioni, fileName);
		*/
		
		// Controllo che la grammatica sia LR(1)
		boolean isLR1 = true;
		for(Stato stt : listaStati) {
			if(stt.erroreLR1) {
				isLR1 = false;
			}
		}
		
		// Stampo risultato controllo
		if(isLR1) {
			/*
			System.out.println("\nLa grammatica inserita � LR(1)"); //NOPMD
			return true;
			*/
			return new Risultati(nodi, listaTransizioni, "La grammatica inserita � LR(1)\n");
			
		} else {
			/*
			System.out.println("\nLa grammatica inserita non � LR(1), gli stati che contengono conflitti sono:\n"); //NOPMD
			for(Stato stt : listaStati) {
				if(stt.erroreLR1) {
					System.out.println("S"+stt.numeroStato); //NOPMD
				}
			}
			return false;
			*/
			String tempRis = "";
			tempRis += "La grammatica inserita non � LR(1), gli stati che contengono conflitti sono:\n";
			for(Stato stt : listaStati) {
				if(stt.erroreLR1) {
					tempRis += "S"+stt.numeroStato + " ";
				}
			}
			Stato.resetCounter();
			return new Risultati(nodi, listaTransizioni, tempRis);
		}
		
	}
}