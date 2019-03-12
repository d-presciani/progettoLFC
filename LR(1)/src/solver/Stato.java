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
	
	public void agiungiCore(RegolaDiProduzione rdp) {
		regoleCore.add(new RegolaDiProduzione(rdp));
		// Popolazione dei seguiti delle nuove regole
		if(rdp.parteDX!=null) {
			for(RegolaDiProduzione regComp : rdp.parteDX.get(rdp.indice).getRegole()) {
				RegolaDiProduzione tmp = new RegolaDiProduzione(regComp);
				if(rdp.indice<rdp.parteDX.size()) {
					int i = 0;
					do{
						tmp.seguiti.addAll(rdp.parteDX.get(rdp.indice+i+1).calcolaInizi());
						i++;
					} while(rdp.parteDX.get(rdp.indice+i).isAnnullabile() && rdp.indice+i<rdp.parteDX.size());
					if(rdp.annullabile) {
						tmp.seguiti.addAll(rdp.seguiti);
					}
				} else {
					tmp.seguiti.addAll(rdp.seguiti);
				}
				regoleCompletamenti.add(tmp);
			}
			
			// Controllo per espansione regole completamento
			int i = 0;
			while(i<regoleCompletamenti.size()) {
				RegolaDiProduzione reg = regoleCompletamenti.get(i);
				if(reg.parteDX.size()!=0 && reg.parteDX.get(reg.indice).getRegole()!=null) {
					for(RegolaDiProduzione nuoveRegole: reg.parteDX.get(reg.indice).getRegole()) {
						boolean trovato = false;
						for(RegolaDiProduzione regCk : regoleCompletamenti) {
							if(nuoveRegole.parteSX.toString().equals(regCk.parteSX.toString()) && nuoveRegole.parteDX.toString().equals(regCk.parteDX.toString())) {
								trovato = true;
								break;
							}
						}
						if(!trovato) {
							this.aggiungiCompletameno(nuoveRegole, reg);
						}
					}
				}		
				i++;
			}
		}
	}
	
	
	public void aggiungiCompletameno(RegolaDiProduzione nuovaRdp, RegolaDiProduzione regolaPadre) {
		// Inserimento seguiti sulle nuove regole
		if(regolaPadre.indice<regolaPadre.parteDX.size()) {
			int i = 0;
			while(regolaPadre.parteDX.get(regolaPadre.indice+i).isAnnullabile() && regolaPadre.indice+i+1<regolaPadre.parteDX.size()) {
				nuovaRdp.seguiti.addAll(regolaPadre.parteDX.get(regolaPadre.indice+i+1).calcolaInizi());
				i++;
			}
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
