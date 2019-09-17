package solver;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import solver.Carattere;
import solver.NonTerminale;
import solver.RegolaDiProduzione;
import solver.Terminale;

class RegolaDiProduzioneTest {

	@Test
	void generazione() {
		// Creo i non terminali
		NonTerminale nts = new NonTerminale("S");
		// Genero una regola
		Terminale ta = new Terminale("a");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(ta);
		RegolaDiProduzione reg = new RegolaDiProduzione(nts, parteDx);
		RegolaDiProduzione reg1 = new RegolaDiProduzione(reg);
		assertTrue(reg.equals(reg1));
	}
	
	@Test
	void generazioneRegNulla() {
		NonTerminale nts = new NonTerminale("S");
		RegolaDiProduzione reg = new RegolaDiProduzione(nts, null);
		reg.addSeguito("a");
		reg.addSeguito("b");
		RegolaDiProduzione reg1 = new RegolaDiProduzione(reg);
		assertTrue(reg.equals(reg1));
	}
	
	@Test
	void avanzamentoPunto() {
		NonTerminale nts = new NonTerminale("S");
		Terminale ta = new Terminale("a");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(ta);
		RegolaDiProduzione reg = new RegolaDiProduzione(nts, parteDx);
		assertEquals(0, reg.indice);
		reg.avanzaPuntino();
		assertEquals(1, reg.indice);
	}
	
	@Test
	void calcoloAnnull() {
		NonTerminale nts = new NonTerminale("S");
		Terminale ta = new Terminale("a");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(ta);
		RegolaDiProduzione reg = new RegolaDiProduzione(nts, parteDx);
		reg.calcolaAnnullabilita();
		assertFalse(reg.annullabile);
		NonTerminale nt1 = new NonTerminale("A");
		nt1.setAnnullabile();
		parteDx.clear();
		parteDx.add(nt1);
		RegolaDiProduzione reg1 = new RegolaDiProduzione(nts, parteDx);
		reg1.calcolaAnnullabilita();
		assertTrue(reg1.annullabile);
	}
	
	@Test
	void testEquals() {
		// Regola 1
		NonTerminale nts = new NonTerminale("S");
		Terminale ta = new Terminale("a");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(ta);
		RegolaDiProduzione reg = new RegolaDiProduzione(nts, parteDx);
		// Regola 2 (SX diverso)
		NonTerminale nta = new NonTerminale("A");
		parteDx.clear();
		parteDx.add(ta);
		RegolaDiProduzione reg1 = new RegolaDiProduzione(nta, parteDx);
		assertFalse(reg.equals(reg1));
		// Regola 3 (DX diverso)
		Terminale tb = new Terminale("b");
		parteDx.clear();
		parteDx.add(tb);
		RegolaDiProduzione reg2 = new RegolaDiProduzione(nts, parteDx);
		assertFalse(reg.equals(reg2));
		// Regola 3 (seguiti diversi)
		parteDx.clear();
		parteDx.add(ta);
		RegolaDiProduzione reg3 = new RegolaDiProduzione(nts, parteDx);
		reg3.addSeguito("a");
		assertFalse(reg.equals(reg3));
		// Regola 3 (indici diversi)
		parteDx.clear();
		parteDx.add(ta);
		RegolaDiProduzione reg4 = new RegolaDiProduzione(nts, parteDx);
		reg4.avanzaPuntino();
		assertFalse(reg.equals(reg4));
		// Regola 4 (tutto uguale)
		parteDx.clear();
		parteDx.add(ta);
		RegolaDiProduzione reg5 = new RegolaDiProduzione(nts, parteDx);
		assertTrue(reg.equals(reg5));
	}
	
	@Test
	void testToString() {
		NonTerminale nts = new NonTerminale("S");
		Terminale ta = new Terminale("a");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(ta);
		RegolaDiProduzione reg = new RegolaDiProduzione(nts, parteDx);
		assertEquals("S->.a", reg.toString());
		reg.avanzaPuntino();
		assertEquals("S->a.", reg.toString());
	}

}
