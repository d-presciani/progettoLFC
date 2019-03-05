grammar PrototipoLR1;

options {
  k = 1;
  language = Java;
}

@header{
  package lr1Package;
  import myPackage.*;
  import solver.*;
}

@lexer::header{
  package lr1Package;
}

@members{
  Environment env;
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
}



@lexer::members{
}

//Produzioni parser
lr1	: 
	{
		init();
		static LinkedList<NonTerminale> listaNT = new LinkedList<NonTerminale>();
		static LinkedList<RegolaDiProduzione> listaReg = new LinkedList<RegolaDiProduzione>();
	} 
		pr ar+ EOF
	;
	
pr	:	start=SZ EQ NT
	{
	  	System.out.println("Test");
	  	NonTerminale prova = new NonTerminale($start.getText());
	  	System.out.println("Questo è il non terminale inserito: " + prova);	  	
	}
	 	TER
	 {
	 	boolean trovato = false;
	 	for(NonTerminale nt: listaNT) {
			if(nt.lettera.equals(charTemp)) {
				trovato = true;
				ntSX = nt;
				break;
			}
		}
		if(!trovato) {
			System.out.println("Aggiungo carattere alla lista");
			ntSX = new NonTerminale(charTemp);
			listaNT.add(ntSX);
			System.out.println("LISTA: " + listaNT);
		}
	 }	
	 	SC
	;

ar	:	NT EQ (NT|CT)* SC;



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