package solver;

import java.util.LinkedList;
import java.util.List;

public class Stato {
	static int counter = 0;
	List<RegolaDiProduzione> regoleCore;
	List<RegolaDiProduzione> regoleCompletamenti;
	int numeroStato;
	
	
	public Stato() {
		numeroStato = counter;
		counter++;
		regoleCore = new LinkedList<RegolaDiProduzione>();
		regoleCompletamenti = new LinkedList<RegolaDiProduzione>();
	}
	
	// Da sistemare
	public void agiungiCore(RegolaDiProduzione rdp) {
		regoleCore.add(new RegolaDiProduzione(rdp));
		
		//Codice che potrei splittare per usarlo da tutti per popolazione dei seguiti???? Controllare
		if(rdp.parteDX!=null) {
			for(RegolaDiProduzione regComp : rdp.parteDX.get(rdp.indice).getRegole()) {
				RegolaDiProduzione tmp = new RegolaDiProduzione(regComp);
				if(rdp.indice<rdp.parteDX.size()) {
					int i = 0;
					while(rdp.parteDX.get(rdp.indice+i).isAnnullabile()) {
						tmp.seguiti.addAll(rdp.parteDX.get(rdp.indice+i+1).calcolaInizi());
					}
					if(rdp.annullabile) {
						tmp.seguiti.addAll(rdp.seguiti);
					}
				} else {
					tmp.seguiti.addAll(rdp.seguiti);
				}
				// Questo andrebbe spostato (potrei creare una funzione che agiunge i seguiti e alla fine ritorna la regola per l'inserimento già processata)
				regoleCompletamenti.add(tmp);
			}
			
			// Controllo per espansione regole completamento
			for(RegolaDiProduzione reg : regoleCompletamenti) {
				for(RegolaDiProduzione nuoveRegole: reg.parteDX.get(reg.indice).getRegole()) {
					boolean trovato = false;
					for(RegolaDiProduzione regCk : regoleCompletamenti) {
						if(nuoveRegole.parteSX.toString().equals(regCk.parteSX.toString()) && nuoveRegole.parteDX.toString().equals(regCk.parteDX.toString())) {
							trovato = true;
							break;
						}
					}
					if(!trovato) {
						// Manca inserimento seguiti, rivedere sopra e magari splittare il codice in una funzione dato che viene eseguito da tutti uguali
						this.aggiungiCompletameno(nuoveRegole);
					}
				}
			}
		}
	}
	
	// Da sistemare
	// Circa funziona come quello sopra, quindi sistemato quello sopra si sistema anche questo
	public void aggiungiCompletameno(RegolaDiProduzione rdp) {
		regoleCompletamenti.add(new RegolaDiProduzione(rdp));
		if(rdp.parteDX!=null && rdp.parteDX.size()!=0) {
			if(rdp.parteDX.get(rdp.indice) instanceof NonTerminale) {
				for(RegolaDiProduzione regComp : rdp.parteDX.get(rdp.indice).getRegole()) {
					RegolaDiProduzione tmp = new RegolaDiProduzione(regComp);
					if(rdp.indice<rdp.parteDX.size()) {
						int i = 0;
						while(rdp.parteDX.get(rdp.indice+i).isAnnullabile()) {
							tmp.seguiti.addAll(rdp.parteDX.get(rdp.indice+i+1).calcolaInizi());
						}
						if(rdp.annullabile) {
							tmp.seguiti.addAll(rdp.seguiti);
						}
					} else {
						tmp.seguiti.addAll(rdp.seguiti);
					}
					regoleCompletamenti.add(tmp);
				}
				for(RegolaDiProduzione reg : regoleCompletamenti) {
					boolean trovato = false;
					for(RegolaDiProduzione regCk : regoleCompletamenti) {
						if(reg.parteSX.toString().equals(regCk.parteSX.toString()) && reg.parteDX.toString().equals(regCk.parteDX.toString())) {
							trovato = true;
							break;
						}
					}
					if(!trovato) {
						this.aggiungiCompletameno(reg);
					}
				}
			}
		}
	}
	
	public void espandiStato() {
		
	}
	
	@Override
	public String toString() {
		String temp = "";
		temp += "Stato: S" + numeroStato + "\nRegole Core:\n";
		for(RegolaDiProduzione reg : regoleCore) {
			temp += reg.toString() + " {" + reg.seguiti + "} \n";
		}
		temp += "\n-----\nRegole completamento:\n" ;
		for(RegolaDiProduzione reg : regoleCompletamenti) {
			temp += reg.toString() + " {" + reg.seguiti + "} \n";
		}
		return temp;
	}
	
		
}
