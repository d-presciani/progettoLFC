package solver;

import java.util.LinkedList;
import java.util.List;

public class Stato {
	static int counter = 1;
	List<RegolaDiProduzione> regoleCore;
	List<RegolaDiProduzione> regoleCompletamenti;
	int numeroStato;
	boolean erroreLR1;
	
	
	public Stato() {
		numeroStato = counter;
		counter++;
		regoleCore = new LinkedList<RegolaDiProduzione>();
		regoleCompletamenti = new LinkedList<RegolaDiProduzione>();
		erroreLR1 = false;
	}
	
	// Aggiunge una regola
	public void aggiungiCore(RegolaDiProduzione rdp){
		// Aggiungo la regola ricevuta alle regole core
		regoleCore.add(new RegolaDiProduzione(rdp));
		
		// Popolazione dei seguiti dei completamenti generati dalla nuova regola core
		if(rdp.parteDX.size()>0) { // Controllo che la regola di produzione abbia effettivamente qualcosa a destra
			if(rdp.parteDX.size()>rdp.indice && rdp.parteDX.get(rdp.indice).getRegole()!=null) {
				for(RegolaDiProduzione regComp : rdp.parteDX.get(rdp.indice).getRegole()) { // Ciclo su tutte le regole (possono non esserci) generate dal carattere con il puntino
					RegolaDiProduzione tmp = new RegolaDiProduzione(regComp); // Creo una var temporanea con la regola appena trovata per poterla modificare prima di aggiungerla alla lista delle regole e per duplicarla
					tmp.seguiti.clear();
					if(rdp.indice+1<rdp.parteDX.size()) { // Controllo di avere degli altri caratteri a destra del carattere con il puntino
						int i = 0;
						// Per ogni carattere a destra di quello con il puntino calcolo gli inizi e continuo finché non trovo un carattere non annullabile
						do{
							LinkedList<String> emptyList = new LinkedList<String>();
							if(!rdp.parteDX.get(rdp.indice+i+1).calcolaInizi(new LinkedList<RegolaDiProduzione>()).equals(emptyList)) {
								for(String seg : rdp.parteDX.get(rdp.indice+i+1).calcolaInizi(new LinkedList<RegolaDiProduzione>())) {
									if(!tmp.seguiti.contains(seg)) {
										tmp.seguiti.add(seg);
									}
								}
							}
							i++;
						} while(rdp.parteDX.get(rdp.indice+i).isAnnullabile() && rdp.indice+i+1<rdp.parteDX.size());
						
						if(rdp.annullabile) { // Se tutti i caratteri son annullabili aggiungo anche i seguiti della regola padre
							for(String seg : rdp.seguiti) {
								if(!tmp.seguiti.contains(seg)) {
									tmp.seguiti.add(seg);
								}
							}
						}
					} else { // Se non ho nulla a destra aggiungo i seguiti del padre
						for(String seg : rdp.seguiti) {
							tmp.seguiti.add(seg);
						}
					}
					
					
					// Cerco se la regola è già presente nei completamenti
					boolean regPresente = false;
					for(RegolaDiProduzione reg: regoleCompletamenti) {
						if(reg.parteSX.lettera.equals(tmp.parteSX.lettera) && reg.parteDX.toString().equals(tmp.parteDX.toString())) {
							for(String seguito : tmp.seguiti) {
								if(!reg.seguiti.contains(seguito)) {
									reg.seguiti.add(seguito);
								}
							}
							regPresente = true;
							break;
						}
					}
					if(!regPresente) {
						regoleCompletamenti.add(tmp); // Una volta aggiunti i seguiti aggiungo la regola alle regole di completamento
					}
					
				}
				// Controllo per espansione regole ogni regola di completamento
				int i = 0;
				while(i<regoleCompletamenti.size()) { // Non posso usare un foreach dato che la dimensione di questa lista continua a variare ad ogni iterazione
					RegolaDiProduzione reg = regoleCompletamenti.get(i); // Prendo la i-esima regola
					if(reg.parteDX.size()!=0 && reg.parteDX.get(reg.indice).getRegole()!=null) { // Controllo che abbia delle regole espandibili puntate dal puntino
						for(RegolaDiProduzione nuoveRegole: reg.parteDX.get(reg.indice).getRegole()) { // Per ogni regola di produzione che ritorna il carattere con il puntino controllo che questa non sia già inserita
							boolean trovato = false;
							for(RegolaDiProduzione regCk : regoleCompletamenti) { // Controllo che la regola non sia già stata inserita
								if(nuoveRegole.parteSX.toString().equals(regCk.parteSX.toString()) && nuoveRegole.parteDX.toString().equals(regCk.parteDX.toString())) {
									trovato = true;
									for(String seguito : reg.seguiti) {
										if(!regCk.seguiti.contains(seguito)) {
											regCk.seguiti.add(seguito);
										}
									}
									break;
								}
							}
							if(!trovato) { // Se non è ancor astata inserita inserisco la nuova regola
								this.aggiungiCompletamento(nuoveRegole, reg);
							}
						}
					}		
					i++;
				}	
				
				//Ricalcolo i seguiti
				boolean modificato = true;
				while(modificato) {
					modificato = false;
					for(int indiceSx = 0; indiceSx<regoleCompletamenti.size(); indiceSx++) {
						for(int indiceDx = 0 ; indiceDx < regoleCompletamenti.size(); indiceDx++) {
							
							if(regoleCompletamenti.get(indiceDx).indice<regoleCompletamenti.get(indiceDx).parteDX.size()) {
								if(regoleCompletamenti.get(indiceSx).parteSX.lettera.equals(regoleCompletamenti.get(indiceDx).parteDX.get(regoleCompletamenti.get(indiceDx).indice).getLettera())) {
									if(regoleCompletamenti.get(indiceDx).indice+1<regoleCompletamenti.get(indiceDx).parteDX.size()) {
										for(String seg: regoleCompletamenti.get(indiceDx).parteDX.get(regoleCompletamenti.get(indiceDx).indice+1).calcolaInizi(new LinkedList<RegolaDiProduzione>())) {
											if(!regoleCompletamenti.get(indiceSx).seguiti.contains(seg)) {
												regoleCompletamenti.get(indiceSx).seguiti.add(seg);
												modificato = true;
											}
										}
									} else {
										for(String seg: regoleCompletamenti.get(indiceDx).seguiti) {
											if(!regoleCompletamenti.get(indiceSx).seguiti.contains(seg)) {
												regoleCompletamenti.get(indiceSx).seguiti.add(seg);
												modificato = true;
											}
										}
									}
								}
							}
						}
					} 
				}
				
				
				
			}			
		}	
	}

	// Inserimento regola di completamento
	private void aggiungiCompletamento(RegolaDiProduzione nuovaRdp, RegolaDiProduzione regolaPadre) {
		// Inserimento seguiti sulle nuove regole
		RegolaDiProduzione regolaTemp = new RegolaDiProduzione(nuovaRdp);
		
		if(regolaPadre.indice+1<regolaPadre.parteDX.size()) {
			int i = 0;						
			do{
				for(String seg : regolaPadre.parteDX.get(regolaPadre.indice+i+1).calcolaInizi(new LinkedList<RegolaDiProduzione>())) {
					if(!regolaTemp.seguiti.contains(seg)) {
						regolaTemp.seguiti.add(seg);
					}
				}
				i++;
			} while(regolaPadre.parteDX.get(regolaPadre.indice+i).isAnnullabile() && regolaPadre.indice+i+1<regolaPadre.parteDX.size());

			boolean verificaSegAnn = true;
			for(int j = regolaPadre.indice+1; j < regolaPadre.parteDX.size(); j++) {
				verificaSegAnn = verificaSegAnn && regolaPadre.parteDX.get(j).isAnnullabile();
			}
			
			if(regolaPadre.annullabile || regolaPadre.parteDX.size()==regolaPadre.indice+1 || verificaSegAnn) {
				for(String seg : regolaPadre.seguiti) {
					if(!regolaTemp.seguiti.contains(seg)) {
						regolaTemp.seguiti.add(seg);
					}
				}
			}
		} else {
			for(String seg : regolaPadre.seguiti) {
				if(!regolaTemp.seguiti.contains(seg)) {
					regolaTemp.seguiti.add(seg);
				}
			}
		}
		
		regoleCompletamenti.add(regolaTemp); // Una volta aggiunti i seguiti agiungo la regola alle regole di completamento
		
	}
	
	
	// Funzione per generazione di nuovi stati
	// TODO: Aggiungere caso che una regola di completamento porta ad aggiungre nuovi seguiti alle altre regole di completamento
	public void espandiStato(LinkedList<Stato> listaStati, LinkedList<String> listaTransizioni) {
		LinkedList<String> caratteriParsati = new LinkedList<String>(); // Variabile utilizata per memorizzare i vari caratteri man mano li parso
		
		// Scorro tutte le regole core
		for(int i=0; i<regoleCore.size(); i++) {
			
			if(regoleCore.get(i).parteDX.size()!=regoleCore.get(i).indice) {
				
				boolean giapParsato = false;
				for(String str : caratteriParsati) {
					if(str.equals(regoleCore.get(i).parteDX.get(regoleCore.get(i).indice).getLettera())) {
						giapParsato = true;
						break;
					}
				}
				if(!giapParsato) {
					LinkedList<RegolaDiProduzione> listaRegoleNuovoStato = new LinkedList<RegolaDiProduzione>();
					listaRegoleNuovoStato.add(new RegolaDiProduzione(regoleCore.get(i)));
					
					//if(regoleCore.get(i).parteDX.size()>regoleCore.get(i).indice+1) {
						// Scorro tutte le regole core per vedere se devo muovere lo stesso carattere
						for(int j = i+1; j<regoleCore.size(); j++) {	
							// Controllo se il puntino della i-esima regola core e della j-esima regola core punta alla stessa lettera
							if(regoleCore.get(j).parteDX.size()!=regoleCore.get(j).indice && regoleCore.get(i).parteDX.get(regoleCore.get(i).indice).getLettera().equals(regoleCore.get(j).parteDX.get(regoleCore.get(j).indice).getLettera())) {
								listaRegoleNuovoStato.add(new RegolaDiProduzione(regoleCore.get(j)));
							}
						}
						
						// Scorro tutte le regole completamento per vedere se devo muovere lo stesso carattere
						for(int j = 0; j<regoleCompletamenti.size(); j++) {	
							// Controllo se il puntino della i-esima regola core e della j-esima regola completamento punta alla stessa lettera
							if(regoleCompletamenti.get(j).parteDX.size()!=regoleCompletamenti.get(j).indice && regoleCore.get(i).parteDX.get(regoleCore.get(i).indice).getLettera().equals(regoleCompletamenti.get(j).parteDX.get(regoleCompletamenti.get(j).indice).getLettera())) {
								listaRegoleNuovoStato.add(new RegolaDiProduzione(regoleCompletamenti.get(j)));
							}
						}
					//}
					// Salvo il caratere parsato per aggiungerlo alla lista dei parsati 
					String chpasr = listaRegoleNuovoStato.get(0).parteDX.get(listaRegoleNuovoStato.get(0).indice).getLettera();
					caratteriParsati.add(chpasr);
					
					// A questo punto in listaRegoleNuovoStato ho un elenco che contiene tutte le regole che formeranno la core del nuovo stato, quindi avanzo di tutte il puntino
					for(RegolaDiProduzione reg : listaRegoleNuovoStato) {
						reg.avanzaPuntino();
					}
					
					// Controllo che non esista nessuno stato nella lista stati con le stesse regole.
					boolean trovato = false;
					int nroStatoDup = -1;
					for(Stato stt: listaStati) {
						if(stt.regoleCore.size()==listaRegoleNuovoStato.size()) {
							int j = 0;
							while(j<stt.regoleCore.size()) {
								if(!stt.regoleCore.get(j).equals(listaRegoleNuovoStato.get(j))) {
									nroStatoDup = stt.numeroStato;
									break;
								}
								j++;
							}
							
							if(j==stt.regoleCore.size()) {
								trovato = true;
							}
						}
					}
					
					if(!trovato) {
						Stato temp = new Stato();
						for(RegolaDiProduzione reg : listaRegoleNuovoStato) {
							temp.aggiungiCore(reg);
						}
						listaStati.add(temp);
						
						String transizione = "S" + numeroStato + " -- " + chpasr + " --> S"+temp.numeroStato;
						listaTransizioni.add(transizione);
					} else {
						String transizione = "S" + numeroStato + " -- " + chpasr + " --> S" + nroStatoDup;
						listaTransizioni.add(transizione);
					}
				}			
			}			
		}
		
		// Scorro tutte le regole completamento
		for(int i=0; i<regoleCompletamenti.size(); i++) {
			
			if(regoleCompletamenti.get(i).parteDX.size()!=regoleCompletamenti.get(i).indice) {
				
				boolean giapParsato = false;
				for(String str : caratteriParsati) {
					if(str.equals(regoleCompletamenti.get(i).parteDX.get(regoleCompletamenti.get(i).indice).getLettera())) {
						giapParsato = true;
						break;
					}
				}
				if(!giapParsato) {
					LinkedList<RegolaDiProduzione> listaRegoleNuovoStato = new LinkedList<RegolaDiProduzione>();
					listaRegoleNuovoStato.add(new RegolaDiProduzione(regoleCompletamenti.get(i)));
					
					//if(regoleCompletamenti.get(i).parteDX.size()>regoleCompletamenti.get(i).indice+1) {						
						// Scorro tutte le regole completamento per vedere se devo muovere lo stesso carattere
						for(int j = i+1; j<regoleCompletamenti.size(); j++) {	
							// Controllo se il puntino della i-esima regola core e della j-esima regola completamento punta alla stessa lettera
							if(regoleCompletamenti.get(j).parteDX.size()!=regoleCompletamenti.get(j).indice && regoleCompletamenti.get(i).parteDX.get(regoleCompletamenti.get(i).indice).getLettera().equals(regoleCompletamenti.get(j).parteDX.get(regoleCompletamenti.get(j).indice).getLettera())) {
								listaRegoleNuovoStato.add(new RegolaDiProduzione(regoleCompletamenti.get(j)));
							}
						}						
					//}
					// Salvo il caratere parsato per aggiungerlo alla lista dei parsati 
					String chpasr = listaRegoleNuovoStato.get(0).parteDX.get(listaRegoleNuovoStato.get(0).indice).getLettera();
					caratteriParsati.add(chpasr);
					
					// A questo punto in listaRegoleNuovoStato ho un elenco che contiene tutte le regole che formeranno la core del nuovo stato, quindi avanzo di tutte il puntino
					for(RegolaDiProduzione reg : listaRegoleNuovoStato) {
						reg.avanzaPuntino();
					}
					
					// Controllo che non esista nessuno stato nella lista stati con le stesse regole.
					boolean trovato=false;
					int nroStatoDup = -1;
					for(Stato stt: listaStati) {
						if(stt.regoleCore.size()==listaRegoleNuovoStato.size()) {
							int j = 0;
							while(j<stt.regoleCore.size()) {
								if(!stt.regoleCore.get(j).equals(listaRegoleNuovoStato.get(j))) {
									break;
								} else {
									nroStatoDup = stt.numeroStato;
								}
								j++;
							}
							
							if(j==stt.regoleCore.size()) {
								trovato = true;
								break;
							}
						}
					}
					
					if(!trovato) {
						Stato temp = new Stato();
						for(RegolaDiProduzione reg : listaRegoleNuovoStato) {
							temp.aggiungiCore(reg);
						}
						listaStati.add(temp);
						
						String transizione = "S" + numeroStato + " -- " + chpasr + " --> S"+temp.numeroStato;
						listaTransizioni.add(transizione);
					} else {
						String transizione = "S" + numeroStato + " -- " + chpasr + " --> S" + nroStatoDup;
						listaTransizioni.add(transizione);
					}
				}			
			}			
		}
		
		// Controllo LR(1)
		LinkedList<String> sovrapposizioni = new LinkedList<String>();
		for(RegolaDiProduzione reg : regoleCore) {
			if(reg.indice==reg.parteDX.size()) {
				for(String seg : reg.seguiti) {
					if(sovrapposizioni.contains(seg)) {
						erroreLR1 = true;
						break;
					} else {
						sovrapposizioni.add(seg);
					}
				}
			}
		}
		for(RegolaDiProduzione reg : regoleCompletamenti) {
			if(reg.indice==reg.parteDX.size()) {
				for(String seg : reg.seguiti) {
					if(sovrapposizioni.contains(seg)) {
						erroreLR1 = true;
						break;
					} else {
						sovrapposizioni.add(seg);
					}
				}
			}
		}
		for(String carattereMosso : caratteriParsati) {
			if(sovrapposizioni.contains(carattereMosso)) {
				erroreLR1 = true;
				break;
			}
		}
		
	}
	
	@Override
	public String toString() {
		String temp = "";
		temp += "\nStato: S" + numeroStato + "\nRegole Core:\n";
		for(RegolaDiProduzione reg : regoleCore) {
			temp += reg.toString() + " {" + reg.seguiti + "} \n";
		}
		if(regoleCompletamenti.size()!=0) {
			temp += "-----\nRegole completamento:\n" ;
			for(RegolaDiProduzione reg : regoleCompletamenti) {
				temp += reg.toString() + " {" + reg.seguiti + "} \n";
			}
		}
		return temp;
	}
}