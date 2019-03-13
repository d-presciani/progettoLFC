package solver;

import java.util.LinkedList;

public class Solver {
	
	
	public void solve(LinkedList<NonTerminale> listaNT, LinkedList<RegolaDiProduzione> listaReg) {
		// Aggiungo ad ogni non terminale le sue regole
		for(RegolaDiProduzione reg : listaReg) {
			for(NonTerminale nt : listaNT) {
				if(reg.parteSX.lettera.equals(nt.lettera)) {
					nt.addRegola(reg);
					break;
				}
			}	
		}
		
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
		mom.aggiungiCore(listaReg.get(0));
		listaStati.add(mom);
		
		int i=0;
		
		while(i<listaStati.size()) {
			listaStati.get(i).espandiStato(listaStati, i, listaTransizioni);
		}
		
		
		System.out.println(mom.toString());
	}
}