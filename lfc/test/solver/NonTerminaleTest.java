package solver;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

public class NonTerminaleTest {
	
	public NonTerminaleTest() {};
	
	@Test
	void testNoRegoleDefault() {
		// Creo il non terminale
		NonTerminale nt = new NonTerminale("S");
		//  Controllo che non vengano inserite regole di default
		assertEquals(0, nt.getRegole().size());
	}
	
	@Test
	void aggiuntaRegola() {
		// Creo il non terminale
		NonTerminale nt = new NonTerminale("S");
		// Genero e aggiungo una regola al non terminale
		NonTerminale dx1 = new NonTerminale("A");
		Terminale dx2 = new Terminale("a");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(dx1);
		parteDx.add(dx2);
		RegolaDiProduzione reg = new RegolaDiProduzione(nt, parteDx);
		nt.addRegola(reg);
		// Controllo avvenuta aggiunta
		assertEquals(1, nt.getRegole().size());
		// Deve stampare S->.Aa
		nt.stampaRegole();
	}
	
	@Test
	void settingAnnull() {
		// Creo il non terminale
		NonTerminale nt = new NonTerminale("S");
		// Controllo setter e getter dell'annullabilità
		nt.setAnnullabile();
		assertTrue(nt.isAnnullabile());
		nt.annullabile = false;
	}
	
	@Test
	void calcoloAnnull() {
		// Creo il non terminale
		NonTerminale nt = new NonTerminale("S");
		// Genero e aggiungo una regola non annullabile al non terminale
		NonTerminale dx1 = new NonTerminale("A");
		Terminale dx2 = new Terminale("a");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(dx1);
		parteDx.add(dx2);
		RegolaDiProduzione reg = new RegolaDiProduzione(nt, parteDx);
		reg.calcolaAnnullabilita();
		nt.addRegola(reg);
		nt.calcolaAnnullabile();
		assertFalse(nt.isAnnullabile());
	}
	
	@Test
	void calcoloAnnullaRegNulla() {
		// Creo il non terminale
		NonTerminale nt = new NonTerminale("S");
		// Genero e aggiungo una regola annullabile
		RegolaDiProduzione reg = new RegolaDiProduzione(nt, null);
		// Controllo annullabilità regola
		reg.calcolaAnnullabilita();
		assertTrue(reg.annullabile);
		nt.addRegola(reg);
		nt.calcolaAnnullabile();
		assertTrue(nt.isAnnullabile());		
	}
	
	@Test
	void controlloStampa(){
		// Creo il non terminale
		NonTerminale nt = new NonTerminale("S");
		assertEquals(nt.toString(), nt.getLettera());
	}
	
	@Test
	void controlloPresenzaProduzioni() {
		boolean presenzaProd = true;
		// Creo il non terminale
		NonTerminale nt = new NonTerminale("S");
		try {
			nt.controlloProduzioni();
		} catch (ErroreSemantico e) {
			presenzaProd = false;
		}
		assertFalse(presenzaProd);
		// Genero e aggiungo una regola non annullabile al non terminale
		NonTerminale dx1 = new NonTerminale("A");
		Terminale dx2 = new Terminale("a");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(dx1);
		parteDx.add(dx2);
		RegolaDiProduzione reg = new RegolaDiProduzione(nt, parteDx);
		reg.calcolaAnnullabilita();
		nt.addRegola(reg);
		try {
			nt.controlloProduzioni();
			presenzaProd = true;
		} catch (ErroreSemantico e) {}
		assertTrue(presenzaProd);
	}
	
	@Test
	void controlloTerminale() {
		// Creo il non terminale
		NonTerminale nt = new NonTerminale("S");
		assertFalse(nt.isTerminale());
	}
	
	@Test
	void iniziConTerminale() {
		// Creo i non terminali
		NonTerminale nt = new NonTerminale("S");
		// Genero e aggiungo una regola al non terminale S
		Terminale dx1 = new Terminale("a");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(dx1);
		RegolaDiProduzione reg = new RegolaDiProduzione(nt, parteDx);
		reg.calcolaAnnullabilita();
		nt.addRegola(reg);
		List<String> inizi = new LinkedList<String>();
		inizi = nt.calcolaInizi(new LinkedList<RegolaDiProduzione>());
		List<String> iniziAttesi = new LinkedList<String>();
		iniziAttesi.add("a");
		assertEquals(iniziAttesi, inizi);
	}
	
	@Test
	void iniziDxVuota() {
		// Creo i non terminali
		NonTerminale nt = new NonTerminale("S");
		// Genero e aggiungo una regola al non terminale S
		RegolaDiProduzione reg = new RegolaDiProduzione(nt, null);
		reg.calcolaAnnullabilita();
		nt.addRegola(reg);
		nt.stampaRegole();
		List<String> inizi = new LinkedList<String>();
		inizi = nt.calcolaInizi(new LinkedList<RegolaDiProduzione>());
		List<String> iniziAttesi = new LinkedList<String>();
		assertEquals(iniziAttesi, inizi);
	}
	
	@Test
	void iniziConRicorsione() {
		// Creo i non terminali
		NonTerminale nt = new NonTerminale("S");
		// Genero e aggiungo una regola al non terminale S
		Terminale dx2 = new Terminale("a");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(nt);
		parteDx.add(dx2);
		RegolaDiProduzione reg = new RegolaDiProduzione(nt, parteDx);
		reg.calcolaAnnullabilita();
		nt.addRegola(reg);
		List<String> inizi = new LinkedList<String>();
		inizi = nt.calcolaInizi(new LinkedList<RegolaDiProduzione>());
		List<String> iniziAttesi = new LinkedList<String>();
		assertEquals(iniziAttesi, inizi);
	}
	
	@Test
	void iniziConNtAnnullabile() {
		// Creo i non terminali
		NonTerminale nt = new NonTerminale("S");
		NonTerminale nt1 = new NonTerminale("A");
		// Aggiungo una regola annullabile ad A
		RegolaDiProduzione regA = new RegolaDiProduzione(nt1, null);
		regA.calcolaAnnullabilita();
		nt1.addRegola(regA);
		nt1.calcolaAnnullabile();
		// Genero e aggiungo una regola al non terminale S
		Terminale dx2 = new Terminale("a");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(nt1);
		parteDx.add(dx2);
		RegolaDiProduzione reg = new RegolaDiProduzione(nt, parteDx);
		reg.calcolaAnnullabilita();
		nt.addRegola(reg);
		nt.calcolaAnnullabile();
		List<String> inizi = new LinkedList<String>();
		inizi = nt.calcolaInizi(new LinkedList<RegolaDiProduzione>());
		List<String> iniziAttesi = new LinkedList<String>();
		iniziAttesi.add("a");
		assertEquals(iniziAttesi, inizi);
	}
	
	@Test
	void iniziConRipetizione() {
		// Creo i non terminali
		NonTerminale nt = new NonTerminale("S");
		NonTerminale nt1 = new NonTerminale("A");
		// Aggiungo una regola annullabile ad A
		RegolaDiProduzione regA = new RegolaDiProduzione(nt1, null);
		regA.calcolaAnnullabilita();
		nt1.addRegola(regA);
		nt1.calcolaAnnullabile();
		// Genero e aggiungo una regola al non terminale S
		Terminale dx2 = new Terminale("a");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(nt1);
		parteDx.add(dx2);
		RegolaDiProduzione reg = new RegolaDiProduzione(nt, parteDx);
		reg.calcolaAnnullabilita();
		nt.addRegola(reg);
		// Genero una seconda regola per S
		parteDx.clear();
		parteDx.add(dx2);
		reg = new RegolaDiProduzione(nt, parteDx);
		reg.calcolaAnnullabilita();
		nt.addRegola(reg);
		List<String> inizi = new LinkedList<String>();
		inizi = nt.calcolaInizi(new LinkedList<RegolaDiProduzione>());
		List<String> iniziAttesi = new LinkedList<String>();
		iniziAttesi.add("a");
		assertEquals(iniziAttesi, inizi);
	}
	
}
