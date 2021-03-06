package solver;


import java.util.LinkedList;
import java.util.List;

public class RegolaDiProduzione {
	NonTerminale parteSX; // Singolo non terminale
	List<Carattere> parteDX; // Lista di caratteri (pu� essere null)
	List<String> seguiti;
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

	
	public RegolaDiProduzione(RegolaDiProduzione reg) {
		this();
		this.parteSX = reg.parteSX;
		if(reg.parteDX.size()>0) {
			for(Carattere ch : reg.parteDX) {
				this.parteDX.add(ch);
			}
		} else {
			this.annullabile = true;
		}
		this.indice = reg.indice;
		this.annullabile = reg.annullabile;
		for(String seg : reg.seguiti) {
			this.seguiti.add(seg);
		}
	}
	
	public void avanzaPuntino() {
		indice++;
	}

	// Calcolo l'annullabilit� della funzione
	public void calcolaAnnullabilita() {
		for (int i=0; i<parteDX.size(); i++){ 
			if(!parteDX.get(i).isAnnullabile()) { // Se trovo anche solo un carattere (terminale o nonterminale) che non � annullabile allora la regola non � annullabile
				annullabile = false;
				break;
			}
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof RegolaDiProduzione) {
			RegolaDiProduzione reg = (RegolaDiProduzione)o;
			if (parteSX.getLettera().equals(reg.parteSX.getLettera()) && parteDX.toString().equals(reg.parteDX.toString()) && seguiti.toString().equals(reg.seguiti.toString()) && indice==reg.indice) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		assert false : "hashCode not designed";
		return 0;
	}
	
	@Override
    public String toString() {
        String mom = "";
        for(int i=0; i<indice; i++) {
            mom += parteDX.get(i).toString();
        }
        mom += ".";
        for(int i = indice; i<parteDX.size(); i++) {
            mom += parteDX.get(i).toString();
        }

        return parteSX.toString()+"->"+mom;
    }
	
	
	public void addSeguito(String s) {
		this.seguiti.add(s);
	}
	
}
