package solver;


import java.util.LinkedList;
import java.util.List;

public class RegolaDiProduzione {
	NonTerminale parteSX; // Singolo non terminale
	List<Carattere> parteDX; // Lista di caratteri (pu� essere null)
	List<String> seguiti; // TODO: DA definire
	int indice; // Indice per il puntino
	boolean annullabile; // Indica se la regola � annullabile (usata per il ricalcolo annullabilit� dei non terminali)
	
	public RegolaDiProduzione() {
		parteDX = new LinkedList<Carattere>();
		seguiti = new LinkedList<String>();
	}
	
	public RegolaDiProduzione(NonTerminale parSX, List<Carattere> parDX) {
		this();
		this.parteSX = parSX;
		this.annullabile = true;
		if(parDX != null) {
			this.parteDX.addAll(parDX);
		}
		this.indice = 0;
	}

	// TODO: Creare copy constructor per espansione stati e creazione nuove regole di produzione (Pensare come spostare indice)
	
	
	// Calcolo l'annullabilit� della funzione
	public void calcolaAnnullabilita() {
		for (int i=0; i<parteDX.size(); i++){ 
			if(!parteDX.get(i).isAnnullabile()) { // Se trovo anche solo un carattere (terminale o nonterminale) che � annullabile allora la regola non � annullabile
				annullabile = false;
				break;
			}
		}
	}
	
	//TODO DA EDITARE
	public Carattere getProssimochar() { 
		return parteDX.get(indice++);
	}
	
	@Override
	public String toString() {
		String mom = "";
		for(int i=0; i<parteDX.size(); i++) {
			mom += parteDX.get(i).toString();
		}
		mom = mom.substring(0,indice) + "." + mom.substring(indice, mom.length());
		
		return parteSX.toString()+"->"+mom;
	}
	
}
