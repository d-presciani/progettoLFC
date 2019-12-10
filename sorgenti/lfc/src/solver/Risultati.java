package solver;

import java.util.LinkedList;

public class Risultati {
	LinkedList<String> nodi;
	LinkedList<Transizione> listaTransizioni;
	String messaggi = "";
	
	public Risultati(LinkedList<String> nodi, LinkedList<Transizione> transizioni, String mesaggi) {
		this.nodi = nodi;
		this.listaTransizioni= transizioni;
		this.messaggi = mesaggi;
	}

	public LinkedList<String> getNodi() {
		return nodi;
	}

	public LinkedList<Transizione> getListaTransizioni() {
		return listaTransizioni;
	}

	public String getMessaggi() {
		return messaggi;
	}
	
	
}
