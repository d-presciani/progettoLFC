package solver;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import solver.RegolaDiProduzione;
import solver.Terminale;

class TerminaleTest {

	@Test
	void costruttore() {
		Terminale ta = new Terminale("a");
		assertEquals("a", ta.lettera);
	}
	
	@Test
	void calcoloInizi() {
		Terminale ta = new Terminale("a");
		LinkedList<String> check = new LinkedList<String>();
		check.add("a");
		List<String> inizi = ta.calcolaInizi(new LinkedList<RegolaDiProduzione>());
		assertEquals(check, inizi);
	}
	
	@Test
	void testToString() {
		Terminale ta = new Terminale("a");
		assertEquals("a", ta.toString());
	}
	
	@Test
	void annullabile() {
		Terminale ta = new Terminale("a");
		assertFalse(ta.isAnnullabile());
	}
	
	@Test
	void lettera() {
		Terminale ta = new Terminale("a");
		assertEquals("a", ta.getLettera());
	}
	
	@Test
	void listaRegole() {
		Terminale ta = new Terminale("a");
		assertNull(ta.getRegole());
	}
	
	
	@Test
	void terminale() {
		Terminale ta = new Terminale("a");
		assertTrue(ta.isTerminale());
	}
}
