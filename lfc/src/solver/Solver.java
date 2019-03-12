package solver;

import java.util.List;
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
		
		List<Stato> listaStati = new LinkedList<Stato>();
		
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
		mom.agiungiCore(listaReg.get(0));
		listaStati.add(mom);
		
		System.out.println(mom.toString());
		
	}
}
