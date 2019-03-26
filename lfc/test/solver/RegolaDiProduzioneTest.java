package solver;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

class RegolaDiProduzioneTest {

	@Test
	void generazione() {
		// Creo i non terminali
		NonTerminale nt = new NonTerminale("S");
		// Genero una regola
		Terminale dx1 = new Terminale("a");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(dx1);
		RegolaDiProduzione reg = new RegolaDiProduzione(nt, parteDx);
		RegolaDiProduzione reg1 = new RegolaDiProduzione(reg);
		assertTrue(reg.equals(reg1));
	}
	
	@Test
	void generazioneRegNulla() {
		NonTerminale nt = new NonTerminale("S");
		RegolaDiProduzione reg = new RegolaDiProduzione(nt, null);
		reg.addSeguito("a");
		reg.addSeguito("b");
		RegolaDiProduzione reg1 = new RegolaDiProduzione(reg);
		assertTrue(reg.equals(reg1));
	}
	
	@Test
	void avanzamentoPunto() {
		NonTerminale nt = new NonTerminale("S");
		Terminale dx1 = new Terminale("a");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(dx1);
		RegolaDiProduzione reg = new RegolaDiProduzione(nt, parteDx);
		assertEquals(0, reg.indice);
		reg.avanzaPuntino();
		assertEquals(1, reg.indice);
	}
	
	@Test
	void calcoloAnnull() {
		NonTerminale nt = new NonTerminale("S");
		Terminale dx1 = new Terminale("a");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(dx1);
		RegolaDiProduzione reg = new RegolaDiProduzione(nt, parteDx);
		reg.calcolaAnnullabilita();
		assertFalse(reg.annullabile);
		NonTerminale nt1 = new NonTerminale("A");
		nt1.setAnnullabile();
		parteDx.clear();
		parteDx.add(nt1);
		RegolaDiProduzione reg1 = new RegolaDiProduzione(nt, parteDx);
		reg1.calcolaAnnullabilita();
		assertTrue(reg1.annullabile);
	}
	
	@Test
	void testEquals() {
		// Regola 1
		NonTerminale nt = new NonTerminale("S");
		Terminale dx1 = new Terminale("a");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(dx1);
		RegolaDiProduzione reg = new RegolaDiProduzione(nt, parteDx);
		// Regola 2 (SX diverso)
		NonTerminale nt1 = new NonTerminale("A");
		parteDx.clear();
		parteDx.add(dx1);
		RegolaDiProduzione reg1 = new RegolaDiProduzione(nt1, parteDx);
		assertFalse(reg.equals(reg1));
		// Regola 3 (DX diverso)
		NonTerminale nt2 = new NonTerminale("S");
		Terminale dx2 = new Terminale("b");
		parteDx.clear();
		parteDx.add(dx2);
		RegolaDiProduzione reg2 = new RegolaDiProduzione(nt2, parteDx);
		assertFalse(reg.equals(reg2));
		// Regola 3 (seguiti diversi)
		parteDx.clear();
		parteDx.add(dx1);
		RegolaDiProduzione reg3 = new RegolaDiProduzione(nt2, parteDx);
		reg3.addSeguito("a");
		assertFalse(reg.equals(reg3));
		// Regola 3 (indici diversi)
		parteDx.clear();
		parteDx.add(dx1);
		RegolaDiProduzione reg4 = new RegolaDiProduzione(nt2, parteDx);
		reg4.avanzaPuntino();
		assertFalse(reg.equals(reg4));
		// Regola 4 (tutto uguale)
		Terminale dx3 = new Terminale("a");
		parteDx.clear();
		parteDx.add(dx3);
		RegolaDiProduzione reg5 = new RegolaDiProduzione(nt2, parteDx);
		assertTrue(reg.equals(reg5));
	}
	
	@Test
	void testToString() {
		NonTerminale nt = new NonTerminale("S");
		Terminale dx1 = new Terminale("a");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(dx1);
		RegolaDiProduzione reg = new RegolaDiProduzione(nt, parteDx);
		assertEquals("S->.a", reg.toString());
		reg.avanzaPuntino();
		assertEquals("S->a.", reg.toString());
	}

}
