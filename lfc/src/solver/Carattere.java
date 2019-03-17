package solver;

import java.util.LinkedList;
import java.util.List;


public abstract class Carattere {
	public abstract List<String> calcolaInizi(LinkedList<RegolaDiProduzione> prevReg) throws ErroreSemantico;
	
	public abstract boolean isAnnullabile();
	
	public abstract String getLettera();
	
	public abstract List<RegolaDiProduzione> getRegole();
	
	public abstract boolean isTerminale();
}