package solver;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import solver.Carattere;
import solver.ErroreSemantico;
import solver.NonTerminale;
import solver.RegolaDiProduzione;
import solver.Terminale;

import java.util.LinkedList;
import java.util.List;

public class NonTerminaleTest {
	
	public NonTerminaleTest() {};
	
	@Test
	void testNoRegoleDefault() {
		// Creo il non terminale
		NonTerminale nts = new NonTerminale("S");
		//  Controllo che non vengano inserite regole di default
		assertEquals(0, nts.getRegole().size());
	}
	
	@Test
	void aggiuntaRegola() {
		// Creo il non terminale
		NonTerminale nts = new NonTerminale("S");
		// Genero e aggiungo una regola al non terminale
		NonTerminale nta = new NonTerminale("A");
		Terminale ta = new Terminale("a");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(nta);
		parteDx.add(ta);
		RegolaDiProduzione reg = new RegolaDiProduzione(nts, parteDx);
		nts.addRegola(reg);
		// Controllo avvenuta aggiunta
		assertEquals(1, nts.getRegole().size());
		// Deve stampare S->.Aa
		nts.stampaRegole();
	}
	
	@Test
	void settingAnnull() {
		// Creo il non terminale
		NonTerminale nts = new NonTerminale("S");
		// Controllo setter e getter dell'annullabilità
		nts.setAnnullabile();
		assertTrue(nts.isAnnullabile());
		nts.annullabile = false;
		assertFalse(nts.isAnnullabile());
	}
	
	@Test
	void calcoloAnnull() {
		// Creo il non terminale
		NonTerminale nts = new NonTerminale("S");
		// Genero e aggiungo una regola non annullabile al non terminale
		NonTerminale nta = new NonTerminale("A");
		Terminale ta = new Terminale("a");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(nta);
		parteDx.add(ta);
		RegolaDiProduzione reg = new RegolaDiProduzione(nts, parteDx);
		reg.calcolaAnnullabilita();
		nts.addRegola(reg);
		nts.calcolaAnnullabile();
		assertFalse(nts.isAnnullabile());
	}
	
	@Test
	void calcoloAnnullaRegNulla() {
		// Creo il non terminale
		NonTerminale nts = new NonTerminale("S");
		// Genero e aggiungo una regola annullabile
		RegolaDiProduzione reg = new RegolaDiProduzione(nts, null);
		// Controllo annullabilità regola
		reg.calcolaAnnullabilita();
		assertTrue(reg.annullabile);
		nts.addRegola(reg);
		nts.calcolaAnnullabile();
		assertTrue(nts.isAnnullabile());		
	}
	
	@Test
	void controlloStampa(){
		// Creo il non terminale
		NonTerminale nts = new NonTerminale("S");
		assertEquals(nts.toString(), nts.getLettera());
	}
	
	@Test
	void controlloPresenzaProduzioni() {
		boolean presenzaProd = true;
		// Creo il non terminale
		NonTerminale nts = new NonTerminale("S");
		try {
			nts.controlloProduzioni();
		} catch (ErroreSemantico e) {
			// Il nonTerminale S non ha regole di produzioni associate e quindi abbiamo un errore semantico
			presenzaProd = false;
		}
		assertFalse(presenzaProd);
		// Genero e aggiungo una regola non annullabile al non terminale
		NonTerminale nta = new NonTerminale("A");
		Terminale ta = new Terminale("a");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(nta);
		parteDx.add(ta);
		RegolaDiProduzione reg = new RegolaDiProduzione(nts, parteDx);
		reg.calcolaAnnullabilita();
		nts.addRegola(reg);
		try {
			nts.controlloProduzioni();
			presenzaProd = true;
		} catch (ErroreSemantico e) {}
		assertTrue(presenzaProd);
	}
	
	@Test
	void controlloTerminale() {
		// Creo il non terminale
		NonTerminale nts = new NonTerminale("S");
		assertFalse(nts.isTerminale());
	}
	
	@Test
	void iniziConTerminale() {
		// Creo i non terminali
		NonTerminale nts = new NonTerminale("S");
		// Genero e aggiungo una regola al non terminale S
		Terminale ta = new Terminale("a");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(ta);
		RegolaDiProduzione reg = new RegolaDiProduzione(nts, parteDx);
		reg.calcolaAnnullabilita();
		nts.addRegola(reg);
		List<String> inizi = new LinkedList<String>();
		inizi = nts.calcolaInizi(new LinkedList<RegolaDiProduzione>());
		List<String> iniziAttesi = new LinkedList<String>();
		iniziAttesi.add("a");
		assertEquals(iniziAttesi, inizi);
	}
	
	@Test
	void iniziDxVuota() {
		// Creo i non terminali
		NonTerminale nts = new NonTerminale("S");
		// Genero e aggiungo una regola nulla al non terminale S
		RegolaDiProduzione reg = new RegolaDiProduzione(nts, null);
		reg.calcolaAnnullabilita();
		nts.addRegola(reg);
		//Deve stampare S->.
		nts.stampaRegole();
		List<String> inizi = new LinkedList<String>();
		inizi = nts.calcolaInizi(new LinkedList<RegolaDiProduzione>());
		List<String> iniziAttesi = new LinkedList<String>();
		assertEquals(iniziAttesi, inizi);
	}
	
	@Test
	void iniziConRicorsione() {
		// Creo i non terminali
		NonTerminale nts = new NonTerminale("S");
		// Genero e aggiungo una regola al non terminale S
		Terminale ta = new Terminale("a");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(nts);
		parteDx.add(ta);
		RegolaDiProduzione reg = new RegolaDiProduzione(nts, parteDx);
		reg.calcolaAnnullabilita();
		nts.addRegola(reg);
		List<String> inizi = new LinkedList<String>();
		inizi = nts.calcolaInizi(new LinkedList<RegolaDiProduzione>());
		List<String> iniziAttesi = new LinkedList<String>();
		assertEquals(iniziAttesi, inizi);
	}
	
	@Test
	void iniziConNtAnnullabile() {
		// Creo i non terminali
		NonTerminale nts = new NonTerminale("S");
		NonTerminale nta = new NonTerminale("A");
		// Aggiungo una regola annullabile ad A
		RegolaDiProduzione regA = new RegolaDiProduzione(nta, null);
		regA.calcolaAnnullabilita();
		nta.addRegola(regA);
		nta.calcolaAnnullabile();
		// Genero e aggiungo una regola al non terminale S
		Terminale ta = new Terminale("a");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(nta);
		parteDx.add(ta);
		RegolaDiProduzione reg = new RegolaDiProduzione(nts, parteDx);
		reg.calcolaAnnullabilita();
		nts.addRegola(reg);
		nts.calcolaAnnullabile();
		List<String> inizi = new LinkedList<String>();
		inizi = nts.calcolaInizi(new LinkedList<RegolaDiProduzione>());
		List<String> iniziAttesi = new LinkedList<String>();
		iniziAttesi.add("a");
		assertEquals(iniziAttesi, inizi);
	}
	
	@Test
	void iniziConRipetizione() {
		// Creo i non terminali
		NonTerminale nts = new NonTerminale("S");
		NonTerminale nta = new NonTerminale("A");
		// Aggiungo una regola annullabile ad A
		RegolaDiProduzione regA = new RegolaDiProduzione(nta, null);
		regA.calcolaAnnullabilita();
		nta.addRegola(regA);
		nta.calcolaAnnullabile();
		// Genero e aggiungo una regola al non terminale S
		Terminale ta = new Terminale("a");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(nta);
		parteDx.add(ta);
		RegolaDiProduzione reg = new RegolaDiProduzione(nts, parteDx);
		reg.calcolaAnnullabilita();
		nts.addRegola(reg);
		// Genero una seconda regola per S
		parteDx.clear();
		parteDx.add(ta);
		reg = new RegolaDiProduzione(nts, parteDx);
		reg.calcolaAnnullabilita();
		nts.addRegola(reg);
		List<String> inizi = new LinkedList<String>();
		inizi = nts.calcolaInizi(new LinkedList<RegolaDiProduzione>());
		List<String> iniziAttesi = new LinkedList<String>();
		iniziAttesi.add("a");
		assertEquals(iniziAttesi, inizi);
	}
	
}
