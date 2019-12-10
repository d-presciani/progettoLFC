package solver;

public class Transizione {
	public int nodoOrigine;
	public int nodoTarghet;
	public String messaggio;
	
	public Transizione(int nodoOrigine, int nodoTarget, String messaggio) {
		this.nodoOrigine = nodoOrigine;
		this.nodoTarghet = nodoTarget;
		this.messaggio = messaggio;
	}
	
	@Override
	public String toString() {
		return "S"+nodoOrigine + "--"+messaggio+"-->S"+nodoTarghet;
	}
}
