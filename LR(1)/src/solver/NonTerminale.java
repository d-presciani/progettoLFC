package solver;

import java.util.LinkedList;
import java.util.List;

public class NonTerminale extends Carattere{
	String lettera; // Rappresentazione sottoforma di stringa del non terminale
	List<RegolaDiProduzione> rdp; // Regole di produzione che hanno il non terminale come parte SX
	boolean annullabile;
	
	public NonTerminale (String lettera) {
		this.lettera = lettera;
		rdp = new LinkedList<RegolaDiProduzione>();
		annullabile = false;
	}
	
	@Override
	//TODO DA EDITARE e ricordarsi che dal chiamante bisogna verificare che il NT non sia annullabile, altrimenti controllare anche il carattere dopo
	public List<String> calcolaInizi() {
		List<String> inizi = new LinkedList<String>();
		for (RegolaDiProduzione reg : rdp) {
			for(String car : reg.getProssimochar().calcolaInizi()) {
				if(!inizi.contains(car)) {
					inizi.add(car);
				}
			}
		}
		return inizi;
	}
	
	
	public boolean isAnnullabile() {
		return this.annullabile;
	}
	
	public void setAnnullabile() {
		this.annullabile = true;
	}
	
	// Ricalcola annullabilità considerando l'annullabilità delle regole di produzione
	public void calcolaAnnullabile() {
		for(RegolaDiProduzione reg: rdp) {
			if(reg.annullabile) {
				this.annullabile = true;
				break;
			}
		}
	}
	
	public List<RegolaDiProduzione> getRegole(){
		return rdp;
	}
	
	public void addRegola (RegolaDiProduzione reg) {
		rdp.add(reg);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return lettera;
	}
	
	public void stampaRegole() {
		for(RegolaDiProduzione reg : rdp) {
			System.out.println(reg.toString());
		}
	}

	@Override
	public String getLettera() {
		// TODO Auto-generated method stub
		return lettera;
	}
}
