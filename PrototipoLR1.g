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
  static LinkedList<NonTerminale> listaNT = new LinkedList<NonTerminale>();
  static LinkedList<RegolaDiProduzione> listaReg = new LinkedList<RegolaDiProduzione>();
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
   public void leftSide(String s){
   	boolean trovato = false;
 	NonTerminale ntSX = null;
 	for(NonTerminale nt: listaNT) {
		if(nt.getLettera().equals(s)) {
			trovato = true;
			ntSX = nt;
			break;
		}
	}
	if(!trovato) {
		System.out.println("Aggiungo carattere alla lista");
		ntSX = new NonTerminale(s);
		listaNT.add(ntSX);
		System.out.println("LISTA: " + listaNT);
	}
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
	;
	
pr	:	nxtChar=SZ EQ NT
	{
	 	leftSide($nxtChar.getText());
	}
	 	TER SC
	;

ar	:	nxtChar=NT
	{
	 	leftSide($nxtChar.getText());
	}
	 	EQ (NT|CT)* SC
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