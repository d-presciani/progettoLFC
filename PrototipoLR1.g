grammar PrototipoLR1;

options {
  k = 1;
  language = Java;
}

@header{
  package lr1Package;
  import myPackage.*;
  import solver.*;
  import java.util.LinkedList;
  import java.util.Scanner;
}

@lexer::header{
  package lr1Package;
}

@members{
  Environment env;
  // Lista dei caratteri non terminali
  static LinkedList<NonTerminale> listaNT;
  // Lista delle regole di produzione
  static LinkedList<RegolaDiProduzione> listaReg;
  // Booleano per il controllo delle produzioni nulle
  boolean nullo;
  // Variabile per la conservazione del non terminale di sinistra
  NonTerminale ntSX;
  // Lista di store temporanea dei caratteri di destra della produzione
  List<Carattere> listaDX;
  // Classificatore della grammatica
  Solver classificatore;
  // Scanner per input utente
  Scanner capitano = new Scanner(System.in);
  void init () {
    env = new Environment();
    listaNT = new LinkedList<NonTerminale>();
    listaReg = new LinkedList<RegolaDiProduzione>();
    nullo = false;
    ntSX = null;
    listaDX = new LinkedList<Carattere>();
    classificatore = new Solver();
  }
  public String getTraslation () {
    return env.translation;
  }
  public ArrayList<String> getErrorList () {
    return env.errorList;
  }
  public Environment getEnv () {
    return env;
  }


    public void displayRecognitionError(String[] tokenNames,
                                        RecognitionException e) {
        String hdr = " * " + getErrorHeader(e);
        String msg = " - " + getErrorMessage(e, tokenNames);
         
       Token tk = input.LT(1);
       env.handleError(tokenNames,e,hdr,msg);
    }
    
   // Controllo del lato sinistro della produzione 
   public NonTerminale controlloNT(String s){
   	// Booleano per il controllo dell'esistenza del NT nella lista
   	boolean trovato = false;
   	// Variabile da tornare
   	NonTerminale ntNew = null;
 	for(NonTerminale nt: listaNT) {
		if(nt.getLettera().equals(s)) {
			// NT già esistente nella lista dei caratteri non terminali
			trovato = true;
			// Memorizzo il carattere nella variabile di store
			return nt;
		}
	}
	if(!trovato) {
		// NT non esistente nella lista dei caratteri non terminali
		//System.out.println("Aggiungo carattere alla lista");	// Stampa di debug (commentare in produzione)
		ntNew = new NonTerminale(s);
		// Aggiungo il terminale alla lista e faccio lo store nella variabile
		listaNT.add(ntNew);
		//System.out.println("LISTA DEI NON TERMINALI: " + listaNT);	// Stampa di debug (commentare in produzione)
	}
	return ntNew;
   }
   
   public void solve() {
		classificatore.solve(listaNT, listaReg);
	}
}



@lexer::members{
}

//Produzioni parser
lr1	: 
	{
		init();
	}
		pr (ar+)
	{
		// Controllo che la grammatica non presenti terminali senza produzioni associate
		try{
			for(NonTerminale nt : listaNT){
				nt.controlloProduzioni();
			}
		} catch (ErroreSemantico e){
			System.err.println("\nERRORE! " + e.getMessage() + "\n\nPremere INVIO per uscire dal programma.");
			capitano.nextLine();
			capitano.close();
			System.exit(0);
		}
	}
	;
	
pr	:	nxtChar=SZ EQ
	{	
		// Controllo se il non terminale è già noto o no
	 	ntSX = controlloNT($nxtChar.getText());
	}
	 	charDx=NT charTer=TER
	{	
		// Controllo se il non terminale è già noto o no
	 	NonTerminale ntDX = controlloNT($charDx.getText());
	 	listaDX.add(ntDX);
	 	listaDX.add(new Terminale($charTer.getText()));
	}
		 SC
	{	
		RegolaDiProduzione regola = new RegolaDiProduzione(ntSX, listaDX);
		regola.addSeguito("emptyset");
		listaReg.add(regola);
		ntSX.addRegola(regola);
		listaDX.clear();
		//System.out.println("LISTA DELLE PRODUZIONI:" + listaReg); // Stampa di debug (commentare in produzione)
	}
	;

ar	:	nxtChar=NT
	{
	 	ntSX = controlloNT($nxtChar.getText());
	}
	 	EQ (charDX=NT
	{	
		// Controllo se il non terminale è già noto o no e lo aggiungo alla regola di produzione
	 	NonTerminale ntDX = controlloNT($charDX.getText());
	 	listaDX.add(ntDX);
	}
	 	| charDXT=CT
	{
		// Aggiunta del terminale alla regola di produzione
		Terminale tDX = new Terminale($charDXT.getText());
		listaDX.add(tDX);
	}	
	 	)* SC
	{	
		RegolaDiProduzione regola;
		// Controllo se la produzione è nulla o meno
		if(listaDX.size() > 0){
			// Produzione non nulla
			regola = new RegolaDiProduzione(ntSX, listaDX);
		} else {
			// Setto il non terminale come annullabile
			ntSX.setAnnullabile();
			// Produzione nulla
			regola = new RegolaDiProduzione(ntSX, null);
		}
		boolean presente = false;
		for(RegolaDiProduzione r : listaReg){
			if(r.equals(regola)){
			presente = true;
			break;
			}
		}
		if(!presente){
			listaReg.add(regola);
		} else {
			System.out.println("ATTENZIONE! La produzione " + regola + " è stata inserita due volte!\nVerrà considerata una volta sola.\n");
		}
		// Associo la regola al non terminale
		ntSX.addRegola(regola);
		// Pulizia della lista temporanea per conservare il lato destro della produzione
		listaDX.clear();
		//System.out.println("LISTA DELLE PRODUZIONI:" + listaReg); // Stampa di debug (commentare in produzione)
	}
	;



// Lexer

SZ 	:	'S0';
EQ	:	'->' | ':=';
NT 	:	('A'..'Z');
CT	:	('a'..'z') | ('0'..'9') | '+' | '-' | '*' | '/' ;
TER	:	'/swa' | '/cjswa';
SC 	:	';';

COMMENT
    :   '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    |   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
    ;

WS  :   ( ' '
        | '\t'
        | '\r'
        | '\n'
        ) {$channel=HIDDEN;}
    ;