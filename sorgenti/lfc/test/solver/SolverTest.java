package solver;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import solver.Carattere;
import solver.NonTerminale;
import solver.RegolaDiProduzione;
import solver.Solver;
import solver.Terminale;

class SolverTest {

	@Test
	void soluzioneLR1() {
		NonTerminale nts = new NonTerminale("S");
		NonTerminale nta = new NonTerminale("A");
		Terminale ta = new Terminale("a");
		LinkedList<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(nta);
		RegolaDiProduzione reg = new RegolaDiProduzione(nts, parteDx);
		reg.calcolaAnnullabilita();
		nts.addRegola(reg);
		nts.calcolaAnnullabile();
		parteDx.clear();
		parteDx.add(ta);
		RegolaDiProduzione reg1 = new RegolaDiProduzione(nta, parteDx);
		reg1.calcolaAnnullabilita();
		nta.addRegola(reg1);
		nta.calcolaAnnullabile();
		LinkedList<NonTerminale> listaNT = new LinkedList<NonTerminale>();
		LinkedList<RegolaDiProduzione> listaRegole = new LinkedList<RegolaDiProduzione>();
		listaNT.add(nts);
		listaNT.add(nta);
		listaRegole.add(reg);
		listaRegole.add(reg1);
				
		Solver risolutore = new Solver();

		Risultati ris  = risolutore.solve(listaNT, listaRegole);
		assertTrue(ris.messaggi.contains("La grammatica inserita � LR(1)"));		
	}
	
	@Test
	void soluzioneNonLR1() {
		NonTerminale nts = new NonTerminale("S");
		NonTerminale nta = new NonTerminale("A");
		Terminale ta = new Terminale("a");
		Terminale tb = new Terminale("b");
		LinkedList<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(nta);
		RegolaDiProduzione reg = new RegolaDiProduzione(nts, parteDx);
		reg.calcolaAnnullabilita();
		reg.addSeguito("b");
		nts.addRegola(reg);
		nts.calcolaAnnullabile();
		parteDx.clear();
		parteDx.add(ta);
		RegolaDiProduzione reg1 = new RegolaDiProduzione(nta, parteDx);
		reg1.calcolaAnnullabilita();
		nta.addRegola(reg1);
		parteDx.add(tb);
		RegolaDiProduzione reg2 = new RegolaDiProduzione(nta, parteDx);
		reg2.calcolaAnnullabilita();
		nta.addRegola(reg2);
		nta.calcolaAnnullabile();
		LinkedList<NonTerminale> listaNT = new LinkedList<NonTerminale>();
		LinkedList<RegolaDiProduzione> listaRegole = new LinkedList<RegolaDiProduzione>();
		listaNT.add(nts);
		listaNT.add(nta);
		listaRegole.add(reg);
		listaRegole.add(reg1);
		
		Solver risolutore = new Solver();
		
		Risultati ris  = risolutore.solve(listaNT, listaRegole);
		assertFalse(ris.messaggi.contains("La grammatica inserita � LR(1)"));
	}

}
