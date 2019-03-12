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
}

@lexer::header{
  package lr1Package;
}

@members{
  Environment env;
  // Lista dei caratteri non terminali
  static LinkedList<NonTerminale> listaNT = new LinkedList<NonTerminale>();
  // Lista delle regole di produzione
  static LinkedList<RegolaDiProduzione> listaReg = new LinkedList<RegolaDiProduzione>();
  // Booleano per il controllo delle produzioni nulle
  boolean nullo = false;
  // Variabile per la conservazione del non terminale di sinistra
  NonTerminale ntSX = null;
  // Lista di store temporanea dei caratteri di destra della produzione
  List<Carattere> listaDX = new LinkedList<Carattere>();
  // Classificatore della grammatica
  Solver classificatore = new Solver();
  void init () {
    env = new Environment();
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
		System.out.println("Aggiungo carattere alla lista");	// Stampa di debug (commentare in produzione)
		ntNew = new NonTerminale(s);
		// Aggiungo il terminale alla lista e faccio lo store nella variabile
		listaNT.add(ntNew);
		System.out.println("LISTA DEI NON TERMINALI: " + listaNT);	// Stampa di debug (commentare in produzione)
	}
	return ntNew;
   }
}



@lexer::members{
}

//Produzioni parser
lr1	: 
	{
		init();
	} 
		pr ar+ EOF
	{
		classificatore.solve(listaNT, listaReg);
	}
	;
	
pr	:	nxtChar=SZ EQ
	{	
		// Controllo se il non terminale è già noto o no
	 	ntSX = controlloNT($nxtChar.getText());
	}
	 	charDx=NT TER
	{	
		// Controllo se il non terminale è già noto o no
	 	NonTerminale ntDX = controlloNT($charDx.getText());
	 	listaDX.add(ntDX);
	}
		 SC
	{
		listaReg.add(new RegolaDiProduzione(ntSX, listaDX));
		listaDX.clear();
		System.out.println("LISTA DELLE PRODUZIONI:" + listaReg);
	}
	;

ar	:	nxtChar=NT
	{
	 	ntSX = controlloNT($nxtChar.getText());
	}
	 	EQ (charDX=NT
	{	
		// Controllo se il non terminale è già noto o no
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
		if(listaDX.size() > 0){
			listaReg.add(new RegolaDiProduzione(ntSX, listaDX));
		} else {
			listaReg.add(new RegolaDiProduzione(ntSX, null));
		}
		listaDX.clear();
		System.out.println("LISTA DELLE PRODUZIONI:" + listaReg);
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