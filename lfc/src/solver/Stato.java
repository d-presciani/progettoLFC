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
	
	// Aggiunge una regola
	public void aggiungiCore(RegolaDiProduzione rdp) {
		// Aggiungo la regola ricevuta alle regole core
		regoleCore.add(new RegolaDiProduzione(rdp));
		
		// Popolazione dei seguiti dei seguiti generati dalla nuova regola core
		if(rdp.parteDX!=null) { // Controllo che la regola di produzione abbia effettivamente qualcosa a destra
			for(RegolaDiProduzione regComp : rdp.parteDX.get(rdp.indice).getRegole()) { // Ciclo su tutte le regole (possono non esserci) generate dal carattere con il puntino
				RegolaDiProduzione tmp = new RegolaDiProduzione(regComp); // Creo una var temporanea con la regola appena trovata per poterla modificare prima di aggiungerla alla lista delle regole e per duplicarla
		
				if(rdp.indice+1<rdp.parteDX.size()) { // Controllo di avere degli altri caratteri a destra del carattere con il puntino
					int i = 0;
					// Per ogni carattere a destra di quello con il puntino calcolo gli inizi e continuo finch� non trovo un carattere non annullabile
					do{
						if(rdp.parteDX.get(rdp.indice+i+1).calcolaInizi()!=null) {
							tmp.seguiti.addAll(rdp.parteDX.get(rdp.indice+i+1).calcolaInizi());
						}
						i++;
					} while(rdp.parteDX.get(rdp.indice+i).isAnnullabile() && rdp.indice+i+1<rdp.parteDX.size());
					
					if(rdp.annullabile) { // Se tutti i caratteri son annullabili aggiungo anche i seguiti della regola padre
						tmp.seguiti.addAll(rdp.seguiti);
					}
				} else { // Se non ho nulla a destra aggiungo i seguiti del padre
					tmp.seguiti.addAll(rdp.seguiti);
				}
				regoleCompletamenti.add(tmp); // Una volta aggiunti i seguiti agiungo la regola alle regole di completamento
			}
			
			// Controllo per espansione regole ogni regola di completamento
			int i = 0;
			while(i<regoleCompletamenti.size()) { // Non posso usare un foreach dato che la dimensione di questa lista continua a variare ad ogni iterazione
				RegolaDiProduzione reg = regoleCompletamenti.get(i); // Prendo la i-esima regola
				if(reg.parteDX.size()!=0 && reg.parteDX.get(reg.indice).getRegole()!=null) { // Controllo che abbia delle regole espandibili puntate dal puntino
					for(RegolaDiProduzione nuoveRegole: reg.parteDX.get(reg.indice).getRegole()) { // Per ogni regola di produzione che ritorna il carattere con il puntino controllo che questa non sia gi� inserita
						boolean trovato = false;
						for(RegolaDiProduzione regCk : regoleCompletamenti) { // Controllo che la regola non sia gi� stata inserita
							if(nuoveRegole.parteSX.toString().equals(regCk.parteSX.toString()) && nuoveRegole.parteDX.toString().equals(regCk.parteDX.toString())) {
								trovato = true;
								break;
							}
						}
						if(!trovato) { // Se non � ancor astata inserita inserisco la nuova regola
							this.aggiungiCompletameno(nuoveRegole, reg);
						}
					}
				}		
				i++;
			}
		}
	}

	// Inserimento regola di completamento
	private void aggiungiCompletameno(RegolaDiProduzione nuovaRdp, RegolaDiProduzione regolaPadre) {
		// Inserimento seguiti sulle nuove regole
		if(regolaPadre.indice+1<regolaPadre.parteDX.size()) {
			int i = 0;						
			do{
				if(regolaPadre.parteDX.get(regolaPadre.indice+i+1).calcolaInizi()!=null) {
					
					nuovaRdp.seguiti.addAll(regolaPadre.parteDX.get(regolaPadre.indice+i+1).calcolaInizi());
				}
				i++;
			} while(regolaPadre.parteDX.get(regolaPadre.indice+i).isAnnullabile() && regolaPadre.indice+i+1<regolaPadre.parteDX.size());

			if(regolaPadre.annullabile || regolaPadre.parteDX.size()==regolaPadre.indice+1) {
				nuovaRdp.seguiti.addAll(regolaPadre.seguiti);
			}
		} else {
			nuovaRdp.seguiti.addAll(regolaPadre.seguiti);
		}
		regoleCompletamenti.add(new RegolaDiProduzione(nuovaRdp));		
	}
	
	
	public void espandiStato() {
		
	}
	
	@Override
	public String toString() {
		String temp = "";
		temp += "\n\nStato: S" + numeroStato + "\nRegole Core:\n";
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