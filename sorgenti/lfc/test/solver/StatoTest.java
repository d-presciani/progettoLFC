package solver;

import static org.junit.jupiter.api.Assertions.*;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;


//TODO: FIXARE (aggiornare i test con la nuova struttra della listaTransizioni!!!!



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
		Stato s = new Stato();
		NonTerminale nts = new NonTerminale("S");
		Terminale ta = new Terminale("a");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(ta);
		RegolaDiProduzione reg = new RegolaDiProduzione(nts, parteDx);
		int nRegole = s.regoleCore.size();
		s.aggiungiCore(reg);
		assertEquals(nRegole + 1, s.regoleCore.size());
	}
	
	@Test
	void aggiuntaRegolaCoreNulla() {
		// rdp.parteDX.size()>0 == FALSE
		Stato s = new Stato();
		NonTerminale nts = new NonTerminale("S");
		RegolaDiProduzione reg = new RegolaDiProduzione(nts, null);
		int nRegole = s.regoleCore.size();
		s.aggiungiCore(reg);
		assertEquals(nRegole + 1, s.regoleCore.size());
	}
	
	@Test
	void aggiuntaRegolaCoreIndice() {
		// rdp.parteDX.size()>rdp.indice == FALSE
		Stato s = new Stato();
		NonTerminale nts = new NonTerminale("S");
		Terminale ta = new Terminale("a");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(ta);
		RegolaDiProduzione reg = new RegolaDiProduzione(nts, parteDx);
		while(reg.indice < reg.parteDX.size()) {
			reg.avanzaPuntino();
		}
		int nRegole = s.regoleCore.size();
		s.aggiungiCore(reg);
		assertEquals(nRegole + 1, s.regoleCore.size());
	}
	
	@Test
	void aggiuntaRegolaCoreNT() {
		// Condizioni tutte rispettate
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
		int nRegole = s.regoleCore.size();
		s.aggiungiCore(reg);
		assertEquals(nRegole + 1, s.regoleCore.size());
	}
	
	@Test
	void aggiuntaCoreCarattereSingolo() {
		// Condizioni tutte rispettate
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
		int nRegole = s.regoleCore.size();
		s.aggiungiCore(reg);
		assertEquals(nRegole + 1, s.regoleCore.size());
	}
	
	@Test
	void aggiuntaCoreAnnullabileSeguitiPadre() {
		// Condizioni tutte rispettate
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
		int nRegole = s.regoleCore.size();
		s.aggiungiCore(reg);
		assertEquals(nRegole + 1, s.regoleCore.size());
	}
	
	@Test
	void aggiuntaCoreNoInizi() {
		// Condizioni tutte rispettate
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
		int nRegole = s.regoleCore.size();
		s.aggiungiCore(reg);
		assertEquals(nRegole + 1, s.regoleCore.size());
	}
	
	@Test
	void aggiuntaCoreNullaSeguitiPadre() {
		// Condizioni tutte rispettate
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
		int nRegole = s.regoleCore.size();
		s.aggiungiCore(reg3);
		assertEquals(nRegole + 1, s.regoleCore.size());
	}
	
	@Test
	void aggiuntaConCompPres() {
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
		int nRegole = s.regoleCore.size();
		s.aggiungiCore(reg);
		assertEquals(nRegole + 1, s.regoleCore.size());
	}
	
	@Test
	void espansioneDaComp() {
		// Condizioni tutte rispettatey
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
		int nRegole = s.regoleCore.size();
		s.aggiungiCore(reg);
		assertEquals(nRegole + 1, s.regoleCore.size());
	}
	
	@Test
	void ricalcoloInizi() {
		// Condizioni tutte rispettate
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
		int nRegole = s.regoleCore.size();
		s.aggiungiCore(reg);
		assertEquals(nRegole + 1, s.regoleCore.size());
	}
	
	@Test
	void aggiuntaCompletamenti() {
		// Condizioni tutte rispettate
		Stato s = new Stato();
		NonTerminale nts = new NonTerminale("S");
		NonTerminale nta = new NonTerminale("A");
		NonTerminale ntb = new NonTerminale("B");
		NonTerminale ntc = new NonTerminale("C");
		NonTerminale ntd = new NonTerminale("D");
		NonTerminale nte = new NonTerminale("E");
		Terminale ta = new Terminale("a");
		Terminale tb = new Terminale("b");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		RegolaDiProduzione reg7 = new RegolaDiProduzione(nte, parteDx);
		reg7.calcolaAnnullabilita();
		nte.addRegola(reg7);
		nte.calcolaAnnullabile();
		RegolaDiProduzione reg4 = new RegolaDiProduzione(ntd, parteDx);
		reg4.calcolaAnnullabilita();
		reg4.addSeguito("b");
		ntd.addRegola(reg4);
		ntd.calcolaAnnullabile();
		parteDx.add(ntd);
		parteDx.add(nte);
		RegolaDiProduzione reg5 = new RegolaDiProduzione(ntc, parteDx);
		reg5.calcolaAnnullabilita();
		reg5.addSeguito("c");
		ntc.addRegola(reg5);
		parteDx.add(tb);
		RegolaDiProduzione reg = new RegolaDiProduzione(ntc, parteDx);
		reg.calcolaAnnullabilita();
		reg.addSeguito("b");
		ntc.addRegola(reg);
		ntc.calcolaAnnullabile();
		parteDx.clear();
		parteDx.add(ntc);
		parteDx.add(ntd);
		parteDx.add(tb);
		parteDx.add(ta);
		RegolaDiProduzione reg1 = new RegolaDiProduzione(ntb, parteDx);
		reg1.calcolaAnnullabilita();
		reg1.addSeguito("f");
		ntb.addRegola(reg1);
		ntb.calcolaAnnullabile();
		parteDx.clear();
		parteDx.add(ntb);
		RegolaDiProduzione reg6 = new RegolaDiProduzione(nta, parteDx);
		reg6.calcolaAnnullabilita();
		nta.addRegola(reg6);
		parteDx.clear();
		parteDx.add(ntb);
		parteDx.add(ta);
		RegolaDiProduzione reg2 = new RegolaDiProduzione(nta, parteDx);
		reg2.calcolaAnnullabilita();
		nta.addRegola(reg2);
		parteDx.clear();
		parteDx.add(ntb);
		parteDx.add(ntd);
		parteDx.add(nte);
		RegolaDiProduzione reg8 = new RegolaDiProduzione(nta, parteDx);
		reg8.calcolaAnnullabilita();
		nta.addRegola(reg8);
		nta.calcolaAnnullabile();
		parteDx.clear();
		parteDx.add(nta);
		RegolaDiProduzione reg3 = new RegolaDiProduzione(nts, parteDx);
		reg3.calcolaAnnullabilita();
		reg3.addSeguito("d");
		reg3.addSeguito("f");
		nts.addRegola(reg3);
		nts.calcolaAnnullabile();
		int nRegole = s.regoleCore.size();
		s.aggiungiCore(reg);
		assertEquals(nRegole + 1, s.regoleCore.size());
	}
	
	@Test
	void espansioneStatoCore() {
		// Condizioni tutte rispettate
		Stato s = new Stato();
		NonTerminale nts = new NonTerminale("S");
		NonTerminale nta = new NonTerminale("A");
		NonTerminale ntb = new NonTerminale("B");
		NonTerminale ntc = new NonTerminale("C");
		Terminale ta = new Terminale("a");
		Terminale tb = new Terminale("b");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(tb);
		RegolaDiProduzione reg = new RegolaDiProduzione(ntc, parteDx);
		reg.calcolaAnnullabilita();
		reg.addSeguito("b");
		ntc.addRegola(reg);
		ntc.calcolaAnnullabile();
		parteDx.clear();
		parteDx.add(ntc);
		parteDx.add(tb);
		parteDx.add(ta);
		RegolaDiProduzione reg1 = new RegolaDiProduzione(ntb, parteDx);
		reg1.calcolaAnnullabilita();
		ntb.addRegola(reg1);
		ntb.calcolaAnnullabile();
		parteDx.clear();
		parteDx.add(ntb);
		parteDx.add(ta);
		RegolaDiProduzione reg2 = new RegolaDiProduzione(nta, parteDx);
		reg2.calcolaAnnullabilita();
		nta.addRegola(reg2);
		nta.calcolaAnnullabile();
		parteDx.clear();
		parteDx.add(nta);
		RegolaDiProduzione reg3 = new RegolaDiProduzione(nts, parteDx);
		reg3.calcolaAnnullabilita();
		nts.addRegola(reg3);
		nts.calcolaAnnullabile();
		parteDx.clear();
		parteDx.add(nta);
		parteDx.add(ta);
		RegolaDiProduzione reg4 = new RegolaDiProduzione(ntc, parteDx);
		reg4.calcolaAnnullabilita();
		ntc.addRegola(reg4);
		parteDx.clear();
		parteDx.add(ta);
		RegolaDiProduzione reg5 = new RegolaDiProduzione(ntc, parteDx);
		reg5.calcolaAnnullabilita();
		ntc.addRegola(reg5);
		ntc.calcolaAnnullabile();
		parteDx.clear();
		RegolaDiProduzione reg6 = new RegolaDiProduzione(ntc, parteDx);
		reg6.calcolaAnnullabilita();
		ntc.addRegola(reg6);
		ntc.calcolaAnnullabile();
		s.aggiungiCore(reg3);
		s.aggiungiCore(reg);
		s.aggiungiCore(reg4);
		s.aggiungiCore(reg5);
		s.regoleCore.get(1).avanzaPuntino();
		LinkedList<Stato> listaStati = new LinkedList<Stato>();
		LinkedList<Transizione> listaTransizioni = new LinkedList<Transizione>();
		int nStati = listaStati.size();
		s.espandiStato(listaStati, listaTransizioni);
		assertEquals(nStati + 5, listaStati.size());
	}
	
	@Test
	void espansioneStatoCoreDuplicati() {
		// Condizioni tutte rispettate
		Stato s = new Stato();
		NonTerminale nts = new NonTerminale("S");
		NonTerminale nta = new NonTerminale("A");
		NonTerminale ntb = new NonTerminale("B");
		NonTerminale ntc = new NonTerminale("C");
		Terminale ta = new Terminale("a");
		Terminale tb = new Terminale("b");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(tb);
		RegolaDiProduzione reg = new RegolaDiProduzione(ntc, parteDx);
		reg.calcolaAnnullabilita();
		reg.addSeguito("b");
		ntc.addRegola(reg);
		ntc.calcolaAnnullabile();
		parteDx.clear();
		parteDx.add(ntc);
		parteDx.add(tb);
		parteDx.add(ta);
		RegolaDiProduzione reg1 = new RegolaDiProduzione(ntb, parteDx);
		reg1.calcolaAnnullabilita();
		ntb.addRegola(reg1);
		ntb.calcolaAnnullabile();
		parteDx.clear();
		parteDx.add(ntb);
		parteDx.add(ta);
		RegolaDiProduzione reg2 = new RegolaDiProduzione(nta, parteDx);
		reg2.calcolaAnnullabilita();
		nta.addRegola(reg2);
		nta.calcolaAnnullabile();
		parteDx.clear();
		parteDx.add(nta);
		RegolaDiProduzione reg3 = new RegolaDiProduzione(nts, parteDx);
		reg3.calcolaAnnullabilita();
		nts.addRegola(reg3);
		parteDx.clear();
		parteDx.add(nta);
		parteDx.add(tb);
		RegolaDiProduzione reg4 = new RegolaDiProduzione(nts, parteDx);
		reg4.calcolaAnnullabilita();
		nts.addRegola(reg4);
		parteDx.clear();
		parteDx.add(tb);
		RegolaDiProduzione reg5 = new RegolaDiProduzione(nts, parteDx);
		reg5.calcolaAnnullabilita();
		nts.addRegola(reg5);
		nts.calcolaAnnullabile();
		// CREAZIONE STATI DUPLICATI
		Stato s1 = new Stato();
		Stato s2 = new Stato();
		Stato s3 = new Stato();
			s.aggiungiCore(reg3);
			s.aggiungiCore(reg4);
			s.aggiungiCore(reg5);
			reg3.avanzaPuntino();
			reg4.avanzaPuntino();
			s1.aggiungiCore(reg3);
			s1.aggiungiCore(reg4);
			s2.aggiungiCore(reg1);
			s3.aggiungiCore(reg3);
			s3.aggiungiCore(reg1);
			LinkedList<Stato> listaStati = new LinkedList<Stato>();
			LinkedList<Transizione> listaTransizioni = new LinkedList<Transizione>();
			listaStati.add(s1);
			listaStati.add(s2);
			listaStati.add(s3);
			int nStati = listaStati.size();
			s.espandiStato(listaStati, listaTransizioni);
			assertEquals(nStati + 3, listaStati.size());
	}
	
	@Test
	void espansioneStatoCompletamento() {
		// Condizioni tutte rispettate
		Stato s = new Stato();
		NonTerminale nts = new NonTerminale("S");
		NonTerminale nta = new NonTerminale("A");
		NonTerminale ntb = new NonTerminale("B");
		NonTerminale ntc = new NonTerminale("C");
		Terminale ta = new Terminale("a");
		Terminale tb = new Terminale("b");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(tb);
		RegolaDiProduzione reg = new RegolaDiProduzione(ntc, parteDx);
		reg.calcolaAnnullabilita();
		reg.addSeguito("b");
		ntc.addRegola(reg);
		parteDx.clear();
		parteDx.add(ntc);
		parteDx.add(tb);
		parteDx.add(ta);
		RegolaDiProduzione reg1 = new RegolaDiProduzione(ntb, parteDx);
		reg1.calcolaAnnullabilita();
		ntb.addRegola(reg1);
		ntb.calcolaAnnullabile();
		parteDx.clear();
		parteDx.add(ntb);
		parteDx.add(ta);
		RegolaDiProduzione reg2 = new RegolaDiProduzione(nta, parteDx);
		reg2.calcolaAnnullabilita();
		nta.addRegola(reg2);
		nta.calcolaAnnullabile();
		parteDx.clear();
		parteDx.add(nta);
		RegolaDiProduzione reg3 = new RegolaDiProduzione(nts, parteDx);
		reg3.calcolaAnnullabilita();
		nts.addRegola(reg3);
		nts.calcolaAnnullabile();
		parteDx.clear();
		RegolaDiProduzione reg4 = new RegolaDiProduzione(ntc, parteDx);
		reg4.calcolaAnnullabilita();
		ntc.addRegola(reg4);
		parteDx.add(tb);
		parteDx.add(ta);
		RegolaDiProduzione reg5 = new RegolaDiProduzione(ntc, parteDx);
		reg5.calcolaAnnullabilita();
		ntc.addRegola(reg5);
		ntc.calcolaAnnullabile();
		s.aggiungiCore(reg3);
		LinkedList<Stato> listaStati = new LinkedList<Stato>();
		LinkedList<Transizione> listaTransizioni = new LinkedList<Transizione>();
		int nStati = listaStati.size();
		s.espandiStato(listaStati, listaTransizioni);
		assertEquals(nStati + 4, listaStati.size());
	}
	
	@Test
	void espansioneStatoCompletamentiDuplicati() {
		// Condizioni tutte rispettate
		Stato s = new Stato();
		NonTerminale nts = new NonTerminale("S");
		NonTerminale nta = new NonTerminale("A");
		NonTerminale ntb = new NonTerminale("B");
		NonTerminale ntc = new NonTerminale("C");
		Terminale ta = new Terminale("a");
		Terminale tb = new Terminale("b");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(tb);
		RegolaDiProduzione reg = new RegolaDiProduzione(ntc, parteDx);
		reg.calcolaAnnullabilita();
		reg.addSeguito("b");
		ntc.addRegola(reg);
		ntc.calcolaAnnullabile();
		parteDx.clear();
		parteDx.add(ntc);
		parteDx.add(tb);
		parteDx.add(ta);
		RegolaDiProduzione reg1 = new RegolaDiProduzione(ntb, parteDx);
		reg1.calcolaAnnullabilita();
		ntb.addRegola(reg1);
		ntb.calcolaAnnullabile();
		parteDx.clear();
		parteDx.add(ntb);
		parteDx.add(ta);
		RegolaDiProduzione reg2 = new RegolaDiProduzione(nta, parteDx);
		reg2.calcolaAnnullabilita();
		nta.addRegola(reg2);
		nta.calcolaAnnullabile();
		parteDx.clear();
		parteDx.add(nta);
		RegolaDiProduzione reg3 = new RegolaDiProduzione(nts, parteDx);
		reg3.calcolaAnnullabilita();
		nts.addRegola(reg3);
		parteDx.clear();
		parteDx.add(nta);
		parteDx.add(tb);
		RegolaDiProduzione reg4 = new RegolaDiProduzione(nts, parteDx);
		reg4.calcolaAnnullabilita();
		nts.addRegola(reg4);
		parteDx.clear();
		parteDx.add(tb);
		RegolaDiProduzione reg5 = new RegolaDiProduzione(nts, parteDx);
		reg5.calcolaAnnullabilita();
		nts.addRegola(reg5);
		nts.calcolaAnnullabile();
		// CREAZIONE STATI DUPLICATI
		Stato s1 = new Stato();
		Stato s2 = new Stato();
		Stato s3 = new Stato();
		s.aggiungiCore(reg3);
		s.aggiungiCore(reg4);
		s.aggiungiCore(reg5);
		reg3.avanzaPuntino();
		reg4.avanzaPuntino();
		s1.aggiungiCore(reg3);
		s1.aggiungiCore(reg4);
		s2.aggiungiCore(reg1);
		reg2.avanzaPuntino();
		reg2.addSeguito("b");
		s3.aggiungiCore(reg2);
		LinkedList<Stato> listaStati = new LinkedList<Stato>();
		LinkedList<Transizione> listaTransizioni = new LinkedList<Transizione>();
		listaStati.add(s1);
		listaStati.add(s2);
		listaStati.add(s3);
		int nStati = listaStati.size();
		s.espandiStato(listaStati, listaTransizioni);
		assertEquals(nStati + 2, listaStati.size());
	}
	
	@Test
	void controlloLR1RiduzioneCore() {
		Stato s = new Stato();
		NonTerminale nts = new NonTerminale("S");
		NonTerminale nta = new NonTerminale("A");
		Terminale tb = new Terminale("b");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(tb);
		RegolaDiProduzione reg = new RegolaDiProduzione(nts, parteDx);
		reg.calcolaAnnullabilita();
		reg.addSeguito("b");
		nts.addRegola(reg);
		parteDx.clear();
		parteDx.add(nta);
		RegolaDiProduzione reg1 = new RegolaDiProduzione(nts, parteDx);
		reg1.calcolaAnnullabilita();
		nts.addRegola(reg1);
		nts.calcolaAnnullabile();
		parteDx.clear();
		RegolaDiProduzione reg2 = new RegolaDiProduzione(nts, parteDx);
		reg2.calcolaAnnullabilita();
		reg2.addSeguito("b");
		nts.addRegola(reg2);
		nts.calcolaAnnullabile();
		s.aggiungiCore(reg1);
		s.aggiungiCore(reg);
		s.aggiungiCore(reg2);
		s.regoleCore.get(1).avanzaPuntino();
		LinkedList<Stato> listaStati = new LinkedList<Stato>();
		LinkedList<Transizione> listaTransizioni = new LinkedList<Transizione>();
		s.espandiStato(listaStati, listaTransizioni);
		assertTrue(s.erroreLR1);
	}
	
	@Test
	void controlloLR1RiduzioneCompletamenti() {
		Stato s = new Stato();
		NonTerminale nts = new NonTerminale("S");
		NonTerminale nta = new NonTerminale("A");
		NonTerminale ntb = new NonTerminale("B");
		Terminale ta = new Terminale("a");
		Terminale tb = new Terminale("b");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(ta);
		RegolaDiProduzione reg = new RegolaDiProduzione(nts, parteDx);
		reg.calcolaAnnullabilita();
		reg.addSeguito("b");
		nts.addRegola(reg);
		nts.calcolaAnnullabile();
		parteDx.clear();
		RegolaDiProduzione reg1 = new RegolaDiProduzione(ntb, parteDx);
		reg1.calcolaAnnullabilita();
		ntb.addRegola(reg1);
		ntb.calcolaAnnullabile();
		parteDx.clear();
		parteDx.add(ntb);
		parteDx.add(tb);
		RegolaDiProduzione reg2 = new RegolaDiProduzione(nta, parteDx);
		reg2.calcolaAnnullabilita();
		nta.addRegola(reg2);
		nta.calcolaAnnullabile();
		parteDx.clear();
		parteDx.add(nta);
		RegolaDiProduzione reg3 = new RegolaDiProduzione(nts, parteDx);
		reg3.calcolaAnnullabilita();
		reg3.addSeguito("b");
		nts.addRegola(reg3);
		nts.calcolaAnnullabile();
		parteDx.clear();
		RegolaDiProduzione reg4 = new RegolaDiProduzione(nta, parteDx);
		reg4.calcolaAnnullabilita();
		nta.addRegola(reg4);
		nta.calcolaAnnullabile();
		s.aggiungiCore(reg3);
		s.aggiungiCore(reg);
		LinkedList<Stato> listaStati = new LinkedList<Stato>();
		LinkedList<Transizione> listaTransizioni = new LinkedList<Transizione>();
		s.espandiStato(listaStati, listaTransizioni);
		assertTrue(s.erroreLR1);
	}
	
	@Test
	void controlloLR1Spostamento() {
		// Condizioni tutte rispettate
		Stato s = new Stato();
		NonTerminale nts = new NonTerminale("S");
		Terminale ta = new Terminale("a");
		Terminale tb = new Terminale("b");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(ta);
		RegolaDiProduzione reg = new RegolaDiProduzione(nts, parteDx);
		reg.calcolaAnnullabilita();
		reg.addSeguito("b");
		nts.addRegola(reg);
		parteDx.clear();
		parteDx.add(tb);
		RegolaDiProduzione reg3 = new RegolaDiProduzione(nts, parteDx);
		reg3.calcolaAnnullabilita();
		reg3.addSeguito("a");
		nts.addRegola(reg3);
		nts.calcolaAnnullabile();
		reg3.avanzaPuntino();
		s.aggiungiCore(reg3);
		s.aggiungiCore(reg);
		LinkedList<Stato> listaStati = new LinkedList<Stato>();
		LinkedList<Transizione> listaTransizioni = new LinkedList<Transizione>();
		s.espandiStato(listaStati, listaTransizioni);
		assertTrue(s.erroreLR1);
	}
	
	@Test
	void testToString() {
		Stato s = new Stato();
		NonTerminale nts = new NonTerminale("S");
		NonTerminale nta = new NonTerminale("A");
		Terminale ta = new Terminale("a");
		Terminale tb = new Terminale("b");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(nta);
		RegolaDiProduzione reg = new RegolaDiProduzione(nts, parteDx);
		reg.calcolaAnnullabilita();
		reg.addSeguito("b");
		nts.addRegola(reg);
		nts.calcolaAnnullabile();
		parteDx.clear();
		parteDx.add(ta);
		parteDx.add(tb);
		RegolaDiProduzione reg1 = new RegolaDiProduzione(nta, parteDx);
		reg1.calcolaAnnullabilita();
		nta.addRegola(reg1);
		nta.calcolaAnnullabile();
		s.aggiungiCore(reg);
		LinkedList<Stato> listaStati = new LinkedList<Stato>();
		LinkedList<Transizione> listaTransizioni = new LinkedList<Transizione>();
		int counterStato = Stato.counter - 1;
		s.espandiStato(listaStati, listaTransizioni);
		String check = "";
		check += "\nStato: S" + counterStato +"\nRegole Core:\n";
		check += "S->.A {[b]} \n";
		check += "-----\nRegole completamento:\n";
		check += "A->.ab {[b]} \n";
		assertEquals(check, s.toString());
	}
	
	@Test
	void testToStringNoComplementi() {
		Stato s = new Stato();
		NonTerminale nts = new NonTerminale("S");
		Terminale ta = new Terminale("a");
		Terminale tb = new Terminale("b");
		List<Carattere> parteDx = new LinkedList<Carattere>();
		parteDx.add(ta);
		RegolaDiProduzione reg = new RegolaDiProduzione(nts, parteDx);
		reg.calcolaAnnullabilita();
		reg.addSeguito("b");
		nts.addRegola(reg);
		parteDx.clear();
		parteDx.add(tb);
		RegolaDiProduzione reg1 = new RegolaDiProduzione(nts, parteDx);
		reg1.calcolaAnnullabilita();
		reg1.addSeguito("b");
		nts.addRegola(reg1);
		nts.calcolaAnnullabile();
		parteDx.clear();
		parteDx.add(ta);
		parteDx.add(tb);
		s.aggiungiCore(reg1);
		LinkedList<Stato> listaStati = new LinkedList<Stato>();
		LinkedList<Transizione> listaTransizioni = new LinkedList<Transizione>();
		int counterStato = Stato.counter - 1;
		s.espandiStato(listaStati, listaTransizioni);
		String check = "";
		check += "\nStato: S" + counterStato + "\nRegole Core:\n";
		check += "S->.b {[b]} \n";
		assertEquals(check, s.toString());
	}
}
