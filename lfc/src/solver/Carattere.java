package solver;

import java.util.List;


public abstract class Carattere {
	public abstract List<String> calcolaInizi();
	
	public abstract boolean isAnnullabile();
	
	public abstract String getLettera();
	
	public abstract List<RegolaDiProduzione> getRegole();
}