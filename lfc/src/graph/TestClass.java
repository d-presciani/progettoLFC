package graph;


public class TestClass{
	
    public static void main(String[] args)
    {
    	String[] nodi = new String[2];
    	String[] messaggi = new String[1];
    	nodi[0]="S0->.S\\term";
    	nodi[1]="S->.a\nS->.\nS->.A\n-----\nA->.";
    	messaggi[0]="S";
    	JGraphXDrawer drawer = new JGraphXDrawer();
    	//drawer.draw(nodi, messaggi);
    }

  
}