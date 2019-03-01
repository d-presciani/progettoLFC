grammar PrototipoLR1;

options {
  k = 1;
  language = Java;
}

@header{
  package JavaPackage;
  import myPackage.*;
}

@lexer::header{
  package JavaPackage;
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
lr1	: {init();} pr ar+ EOF;
	
pr	:	SZ EQ NT TER SC;

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

