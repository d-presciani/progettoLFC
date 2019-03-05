package solver;


import java.util.LinkedList;
import java.util.List;

public class RegolaDiProduzione {
	NonTerminale parteSX;
	List<Carattere> parteDX;
	List<String> seguiti;
	int indice;
	boolean annullabile;
	
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

	
	public void calcolaAnnullabilita() {
		for (int i=0; i<parteDX.size(); i++) {
			System.out.println("Carattere: " + parteDX.get(i).getLettera() + " | Annullabile: " + parteDX.get(i).isAnnullabile());
			if(!parteDX.get(i).isAnnullabile()) {
				annullabile = false;
				break;
			}
		}
		/*
		for (Carattere car : parteDX) {
			System.out.println(this.toString());
			
			if (!car.isAnnullabile()) {
				annullabile = false;
				break;
			}
		}
		*/
	}
	
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
