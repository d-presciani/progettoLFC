package solver;

import java.util.LinkedList;
import java.util.List;

public class Terminale extends Carattere{
	String lettera;
	
	public Terminale(String car) {
		this.lettera = car;
	}
	
	@Override
	public List<String> calcolaInizi() {
		List<String> inizi = new LinkedList<String>();
		inizi.add(lettera);
		return inizi;
	}
	
	@Override
	public String toString() {
		return lettera;
	}

	@Override
	public boolean isAnnullabile() {
		return false;
	}

	@Override
	public String getLettera() {
		return lettera;
	}

	@Override
	public List<RegolaDiProduzione> getRegole() {
		return null;
	}
}
