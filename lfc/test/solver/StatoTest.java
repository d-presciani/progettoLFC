package solver;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

class StatoTest {

	@Test
	void generazione() {
		Stato s = new Stato();
		assertFalse(s.erroreLR1);
		List<RegolaDiProduzione> vuota = new LinkedList<RegolaDiProduzione>();
		assertEquals(vuota,s.regoleCompletamenti);
		assertEquals(vuota,s.regoleCore);
		Stato s1 = new Stato();
		assertEquals(s.numeroStato + 1, s1.numeroStato);
	}
	
	@Test
	void aggiuntaRegolaCore() {
		boolean aggiunta = false;
		Stato s = new Stato();
		NonTerminale nt = new NonTerminale("S");
		Terminale dx1 = new Terminale("a");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(dx1);
		RegolaDiProduzione reg = new RegolaDiProduzione(nt, parteDx);
		try {
			s.aggiungiCore(reg);
			aggiunta = true;
		} catch (ErroreSemantico e) {}
		assertTrue(aggiunta);
	}
	
	@Test
	void aggiuntaRegolaCoreNulla() {
		boolean aggiunta = false;
		Stato s = new Stato();
		NonTerminale nt = new NonTerminale("S");
		RegolaDiProduzione reg = new RegolaDiProduzione(nt, null);
		try {
			s.aggiungiCore(reg);
			aggiunta = true;
		} catch (ErroreSemantico e) {}
		assertTrue(aggiunta);
	}
	
	@Test
	void aggiuntaRegolaCoreIndice() {
		// Aggiunta regola core con indice >= parteDx.size()
		boolean aggiunta = false;
		Stato s = new Stato();
		NonTerminale nt = new NonTerminale("S");
		Terminale dx1 = new Terminale("a");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(dx1);
		RegolaDiProduzione reg = new RegolaDiProduzione(nt, parteDx);
		while(reg.indice < reg.parteDX.size()) {
			reg.avanzaPuntino();
		}
		try {
			s.aggiungiCore(reg);
			aggiunta = true;
		} catch (ErroreSemantico e) {}
		assertTrue(aggiunta);
	}
	
	@Test
	void aggiuntaRegolaCoreNT() {
		boolean aggiunta = false;
		Stato s = new Stato();
		NonTerminale nt = new NonTerminale("S");
		Terminale dx1 = new Terminale("a");
		NonTerminale dx2 = new NonTerminale("A");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(dx1);
		RegolaDiProduzione reg1 = new RegolaDiProduzione(dx2,parteDx);
		dx2.addRegola(reg1);
		parteDx.clear();
		parteDx.add(dx2);
		parteDx.add(dx1);
		RegolaDiProduzione reg = new RegolaDiProduzione(nt, parteDx);
		nt.addRegola(reg);
		try {
			s.aggiungiCore(reg);
			aggiunta = true;
		} catch (ErroreSemantico e) {}
		assertTrue(aggiunta);
	}
	
	@Test
	void aggiuntaCoreCarattereSingolo() {
		boolean aggiunta = false;
		Stato s = new Stato();
		NonTerminale nt = new NonTerminale("S");
		NonTerminale dx1 = new NonTerminale("A");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(dx1);
		RegolaDiProduzione reg1 = new RegolaDiProduzione(dx1, parteDx);
		dx1.addRegola(reg1);
		RegolaDiProduzione reg = new RegolaDiProduzione(nt, parteDx);
		nt.addRegola(reg);
		try {
			s.aggiungiCore(reg);
			aggiunta = true;
		} catch (ErroreSemantico e) {}
		assertTrue(aggiunta);
	}

}
