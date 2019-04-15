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
		// rdp.parteDX.get(rdp.indice).getRegole()!=null == FALSE
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
		// rdp.parteDX.size()>0 == FALSE
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
		// rdp.parteDX.size()>rdp.indice == FALSE
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
		// Condizioni tutte rispettate
		boolean aggiunta = false;
		Stato s = new Stato();
		NonTerminale nts = new NonTerminale("S");
		Terminale ta = new Terminale("a");
		NonTerminale nta = new NonTerminale("A");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(ta);
		RegolaDiProduzione reg1 = new RegolaDiProduzione(nta,parteDx);
		nta.addRegola(reg1);
		parteDx.clear();
		parteDx.add(nta);
		RegolaDiProduzione reg = new RegolaDiProduzione(nts, parteDx);
		nts.addRegola(reg);
		try {
			s.aggiungiCore(reg);
			aggiunta = true;
		} catch (ErroreSemantico e) {}
		assertTrue(aggiunta);
	}
	
	@Test
	void aggiuntaCoreCarattereSingolo() {
		// Condizioni tutte rispettate
		boolean aggiunta = false;
		Stato s = new Stato();
		// Creazione dei caratteri
		NonTerminale nts = new NonTerminale("S");
		NonTerminale nta = new NonTerminale("A");
		NonTerminale ntb = new NonTerminale("B");
		NonTerminale ntc = new NonTerminale("C");
		Terminale ta = new Terminale("a");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		RegolaDiProduzione reg = new RegolaDiProduzione(nta, null);
		// Aggiunta delle regole di produzinoe ad A
		reg.calcolaAnnullabilita();
		nta.addRegola(reg);
		nta.calcolaAnnullabile();
		// Aggiunta delle regole di produzione a B
		parteDx.add(ta);
		RegolaDiProduzione reg1 = new RegolaDiProduzione(ntb, parteDx);
		reg1.calcolaAnnullabilita();
		ntb.addRegola(reg1);
		RegolaDiProduzione reg2 = new RegolaDiProduzione(ntb, null);
		reg2.calcolaAnnullabilita();
		ntb.addRegola(reg2);
		ntb.calcolaAnnullabile();
		// Aggiunta delle regole di produzione a C
		RegolaDiProduzione reg3 = new RegolaDiProduzione(ntc, parteDx);
		reg3.calcolaAnnullabilita();
		ntc.addRegola(reg3);
		ntc.calcolaAnnullabile();
		// Aggiunta delle regole di produzione a S
		parteDx.clear();
		parteDx.add(nta);
		parteDx.add(ntb);
		parteDx.add(ntc);
		RegolaDiProduzione reg4 = new RegolaDiProduzione(nts, parteDx);
		reg4.calcolaAnnullabilita();
		nts.addRegola(reg4);
		nts.calcolaAnnullabile();
		// Entro in questo IF: if(rdp.indice+1<rdp.parteDX.size())
		try {
			s.aggiungiCore(reg4);
			aggiunta = true;
		} catch (ErroreSemantico e) {}
		assertTrue(aggiunta);
	}
	
	@Test
	void aggiuntaCoreAnnullabileSeguitiPadre() {
		// Condizioni tutte rispettate
		boolean aggiunta = false;
		Stato s = new Stato();
		// Creazione dei caratteri
		NonTerminale nts = new NonTerminale("S");
		NonTerminale nta = new NonTerminale("A");
		NonTerminale ntb = new NonTerminale("B");
		Terminale ta = new Terminale("a");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		RegolaDiProduzione reg = new RegolaDiProduzione(nta, null);
		// Aggiunta delle regole di produzinoe ad A
		reg.calcolaAnnullabilita();
		nta.addRegola(reg);
		nta.calcolaAnnullabile();
		// Aggiunta delle regole di produzione a B
		parteDx.add(ta);
		RegolaDiProduzione reg1 = new RegolaDiProduzione(ntb, parteDx);
		reg1.calcolaAnnullabilita();
		ntb.addRegola(reg1);
		RegolaDiProduzione reg2 = new RegolaDiProduzione(ntb, null);
		reg2.calcolaAnnullabilita();
		ntb.addRegola(reg2);
		ntb.calcolaAnnullabile();
		// Aggiunta delle regole di produzione a S
		parteDx.clear();
		parteDx.add(nta);
		parteDx.add(ntb);
		RegolaDiProduzione reg4 = new RegolaDiProduzione(nts, parteDx);
		reg4.calcolaAnnullabilita();
		reg4.addSeguito("a");
		reg4.addSeguito("b");
		reg4.addSeguito("/cjswa");
		nts.addRegola(reg4);
		nts.calcolaAnnullabile();
		// Entro in questo IF: if(rdp.indice+1<rdp.parteDX.size())
		try {
			s.aggiungiCore(reg4);
			aggiunta = true;
		} catch (ErroreSemantico e) {}
		assertTrue(aggiunta);
	}
	
	@Test
	void aggiuntaCoreNoInizi() {
		// Condizioni tutte rispettate
		boolean aggiunta = false;
		Stato s = new Stato();
		// Creazione dei caratteri
		NonTerminale nts = new NonTerminale("S");
		NonTerminale nta = new NonTerminale("A");
		NonTerminale ntb = new NonTerminale("B");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		RegolaDiProduzione reg = new RegolaDiProduzione(nta, null);
		// Aggiunta delle regole di produzinoe ad A
		nta.addRegola(reg);
		// Aggiunta delle regole di produzione a B
		parteDx.add(nta);
		RegolaDiProduzione reg1 = new RegolaDiProduzione(ntb, parteDx);
		ntb.addRegola(reg1);
		// Aggiunta delle regole di produzione a S
		parteDx.clear();
		parteDx.add(nta);
		parteDx.add(ntb);
		RegolaDiProduzione reg2 = new RegolaDiProduzione(nts, parteDx);
		nts.addRegola(reg2);
		// Entro in questo IF: if(rdp.indice+1<rdp.parteDX.size())
		try {
			s.aggiungiCore(reg2);
			aggiunta = true;
		} catch (ErroreSemantico e) {}
		assertTrue(aggiunta);
	}
	
	@Test
	void aggiuntaCoreNullaSeguitiPadre() {
		// Condizioni tutte rispettate
		boolean aggiunta = false;
		Stato s = new Stato();
		NonTerminale nts = new NonTerminale("S");
		NonTerminale nta = new NonTerminale("A");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		// Aggiunta delle regole ad A
		RegolaDiProduzione reg1 = new RegolaDiProduzione(nta,null);
		reg1.calcolaAnnullabilita();
		nta.addRegola(reg1);
		nta.calcolaAnnullabile();
		// Aggiunta delle regole a S
		parteDx.clear();
		parteDx.add(nta);
		RegolaDiProduzione reg3 = new RegolaDiProduzione(nts, parteDx);
		reg3.addSeguito("a");
		reg3.addSeguito("b");
		nts.addRegola(reg3);
		try {
			s.aggiungiCore(reg3);
			aggiunta = true;
		} catch (ErroreSemantico e) {}
		assertTrue(aggiunta);
	}
	
	@Test
	void aggiuntaConCompPres() {
		// Condizioni tutte rispettate
		boolean aggiunta = false;
		Stato s = new Stato();
		NonTerminale nts = new NonTerminale("S");
		NonTerminale nta = new NonTerminale("A");
		NonTerminale ntb = new NonTerminale("B");
		Terminale ta = new Terminale("a");
		Terminale tb = new Terminale("b");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(ta);
		RegolaDiProduzione reg1 = new RegolaDiProduzione(nta,parteDx);
		nta.addRegola(reg1);
		parteDx.clear();
		parteDx.add(nta);
		RegolaDiProduzione reg = new RegolaDiProduzione(nts, parteDx);
		reg.addSeguito("a");
		reg.addSeguito("b");
		reg.addSeguito("c");
		nts.addRegola(reg);
		// AGGIUNTA DI REGOLE DI COMPLETAMENTO
		parteDx.clear();
		parteDx.add(ta);
		s.regoleCompletamenti.add(new RegolaDiProduzione(ntb, parteDx));
		RegolaDiProduzione reg2 = new RegolaDiProduzione(nta, parteDx);
		reg2.addSeguito("a");
		s.regoleCompletamenti.add(reg2);
		parteDx.clear();
		parteDx.add(tb);
		s.regoleCompletamenti.add(new RegolaDiProduzione(nta, parteDx));
		try {
			s.aggiungiCore(reg);
			aggiunta = true;
		} catch (ErroreSemantico e) {}
		assertTrue(aggiunta);
	}
	
	@Test
	void espansioneDaComp() {
		// Condizioni tutte rispettate
		boolean aggiunta = false;
		Stato s = new Stato();
		NonTerminale nts = new NonTerminale("S");
		NonTerminale nta = new NonTerminale("A");
		NonTerminale ntb = new NonTerminale("B");
		Terminale ta = new Terminale("a");
		Terminale tb = new Terminale("b");
		Terminale tc = new Terminale("c");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(tb);
		ntb.addRegola(new RegolaDiProduzione(ntb, parteDx));
		parteDx.clear();
		parteDx.add(tc);
		ntb.addRegola(new RegolaDiProduzione(ntb, parteDx));
		parteDx.clear();
		parteDx.add(ntb);
		parteDx.add(ta);
		nta.addRegola(new RegolaDiProduzione(nta,parteDx));
		parteDx.clear();
		parteDx.add(nta);
		RegolaDiProduzione reg = new RegolaDiProduzione(nts, parteDx);
		reg.addSeguito("a");
		reg.addSeguito("b");
		reg.addSeguito("c");
		nts.addRegola(reg);
		// AGGIUNTA DI REGOLE DI COMPLETAMENTO
		parteDx.clear();
		parteDx.add(ta);
		s.regoleCompletamenti.add(new RegolaDiProduzione(nta, parteDx));
		s.regoleCompletamenti.add(new RegolaDiProduzione(ntb, parteDx));
		parteDx.clear();
		parteDx.add(tb);
		RegolaDiProduzione reg1 = new RegolaDiProduzione(ntb, parteDx);
		reg1.addSeguito("a");
		s.regoleCompletamenti.add(reg1);
		try {
			s.aggiungiCore(reg);
			aggiunta = true;
		} catch (ErroreSemantico e) {}
		assertTrue(aggiunta);
	}
	
	@Test
	void ricalcoloInizi() {
		// Condizioni tutte rispettate
		boolean aggiunta = false;
		Stato s = new Stato();
		NonTerminale nts = new NonTerminale("S");
		NonTerminale nta = new NonTerminale("A");
		NonTerminale ntb = new NonTerminale("B");
		Terminale ta = new Terminale("a");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(ntb);
		parteDx.add(ta);
		nta.addRegola(new RegolaDiProduzione(nta, parteDx));
		parteDx.clear();
		nta.addRegola(new RegolaDiProduzione(nta, parteDx));
		parteDx.add(ta);
		ntb.addRegola(new RegolaDiProduzione(ntb, parteDx));
		parteDx.clear();
		parteDx.add(nta);
		RegolaDiProduzione reg1 = new RegolaDiProduzione(nts, parteDx);
		reg1.addSeguito("b");
		reg1.addSeguito("c");
		nts.addRegola(reg1);
		parteDx.clear();
		parteDx.add(nta);
		RegolaDiProduzione reg = new RegolaDiProduzione(nta,parteDx);
		reg.addSeguito("a");
		reg.addSeguito("b");
		s.regoleCompletamenti.add(reg);
		try {
			s.aggiungiCore(reg1);
			aggiunta = true;
		} catch (ErroreSemantico e) {}
		assertTrue(aggiunta);
	}
	
	@Test
	void aggiuntaComp() {
		// Condizioni tutte rispettate
		boolean aggiunta = false;
		Stato s = new Stato();
		NonTerminale nts = new NonTerminale("S");
		NonTerminale nta = new NonTerminale("A");
		Terminale ta = new Terminale("a");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(ta);
		nta.addRegola(new RegolaDiProduzione(nta, parteDx));
		parteDx.clear();
		parteDx.add(nta);
		RegolaDiProduzione reg1 = new RegolaDiProduzione(nts, parteDx);
		nts.addRegola(reg1);
		try {
			s.aggiungiCore(reg1);
			aggiunta = true;
		} catch (ErroreSemantico e) {}
		assertTrue(aggiunta);
		System.out.println(s.toString());
		nts.stampaRegole();
		nta.stampaRegole();
		System.out.println("A annullabile: " + nta.isAnnullabile());
		System.out.println("S annullabile: " + nts.isAnnullabile());
	}

}
