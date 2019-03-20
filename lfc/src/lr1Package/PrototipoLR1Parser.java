// $ANTLR 3.5.1 C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g 2019-03-20 16:54:28

  package lr1Package;
  import myPackage.*;
  import solver.*;
  import java.util.LinkedList;
  import java.util.Scanner;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class PrototipoLR1Parser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "COMMENT", "CT", "EQ", "NT", "SC", 
		"SZ", "TER", "WS"
	};
	public static final int EOF=-1;
	public static final int COMMENT=4;
	public static final int CT=5;
	public static final int EQ=6;
	public static final int NT=7;
	public static final int SC=8;
	public static final int SZ=9;
	public static final int TER=10;
	public static final int WS=11;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public PrototipoLR1Parser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public PrototipoLR1Parser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	@Override public String[] getTokenNames() { return PrototipoLR1Parser.tokenNames; }
	@Override public String getGrammarFileName() { return "C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g"; }


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



	// $ANTLR start "lr1"
	// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:101:1: lr1 : pr ( ( ar )+ ) ;
	public final void lr1() throws RecognitionException {
		try {
			// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:101:5: ( pr ( ( ar )+ ) )
			// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:102:2: pr ( ( ar )+ )
			{

					init();
				
			pushFollow(FOLLOW_pr_in_lr168);
			pr();
			state._fsp--;

			// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:105:6: ( ( ar )+ )
			// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:105:7: ( ar )+
			{
			// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:105:7: ( ar )+
			int cnt1=0;
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( (LA1_0==NT) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:105:7: ar
					{
					pushFollow(FOLLOW_ar_in_lr171);
					ar();
					state._fsp--;

					}
					break;

				default :
					if ( cnt1 >= 1 ) break loop1;
					EarlyExitException eee = new EarlyExitException(1, input);
					throw eee;
				}
				cnt1++;
			}

			}


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

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "lr1"



	// $ANTLR start "pr"
	// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:121:1: pr : nxtChar= SZ EQ charDx= NT charTer= TER SC ;
	public final void pr() throws RecognitionException {
		Token nxtChar=null;
		Token charDx=null;
		Token charTer=null;

		try {
			// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:121:4: (nxtChar= SZ EQ charDx= NT charTer= TER SC )
			// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:121:6: nxtChar= SZ EQ charDx= NT charTer= TER SC
			{
			nxtChar=(Token)match(input,SZ,FOLLOW_SZ_in_pr89); 
			match(input,EQ,FOLLOW_EQ_in_pr91); 
				
					// Controllo se il non terminale è già noto o no
				 	ntSX = controlloNT(nxtChar.getText());
				
			charDx=(Token)match(input,NT,FOLLOW_NT_in_pr101); 
			charTer=(Token)match(input,TER,FOLLOW_TER_in_pr105); 
				
					// Controllo se il non terminale è già noto o no
				 	NonTerminale ntDX = controlloNT(charDx.getText());
				 	listaDX.add(ntDX);
				 	listaDX.add(new Terminale(charTer.getText()));
				
			match(input,SC,FOLLOW_SC_in_pr113); 
				
					RegolaDiProduzione regola = new RegolaDiProduzione(ntSX, listaDX);
					regola.addSeguito("emptyset");
					listaReg.add(regola);
					ntSX.addRegola(regola);
					listaDX.clear();
					//System.out.println("LISTA DELLE PRODUZIONI:" + listaReg); // Stampa di debug (commentare in produzione)
				
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "pr"



	// $ANTLR start "ar"
	// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:144:1: ar : nxtChar= NT EQ (charDX= NT |charDXT= CT )* SC ;
	public final void ar() throws RecognitionException {
		Token nxtChar=null;
		Token charDX=null;
		Token charDXT=null;

		try {
			// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:144:4: (nxtChar= NT EQ (charDX= NT |charDXT= CT )* SC )
			// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:144:6: nxtChar= NT EQ (charDX= NT |charDXT= CT )* SC
			{
			nxtChar=(Token)match(input,NT,FOLLOW_NT_in_ar128); 

				 	ntSX = controlloNT(nxtChar.getText());
				
			match(input,EQ,FOLLOW_EQ_in_ar136); 
			// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:148:7: (charDX= NT |charDXT= CT )*
			loop2:
			while (true) {
				int alt2=3;
				int LA2_0 = input.LA(1);
				if ( (LA2_0==NT) ) {
					alt2=1;
				}
				else if ( (LA2_0==CT) ) {
					alt2=2;
				}

				switch (alt2) {
				case 1 :
					// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:148:8: charDX= NT
					{
					charDX=(Token)match(input,NT,FOLLOW_NT_in_ar141); 
						
							// Controllo se il non terminale è già noto o no e lo aggiungo alla regola di produzione
						 	NonTerminale ntDX = controlloNT(charDX.getText());
						 	listaDX.add(ntDX);
						
					}
					break;
				case 2 :
					// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:154:6: charDXT= CT
					{
					charDXT=(Token)match(input,CT,FOLLOW_CT_in_ar153); 

							// Aggiunta del terminale alla regola di produzione
							Terminale tDX = new Terminale(charDXT.getText());
							listaDX.add(tDX);
						
					}
					break;

				default :
					break loop2;
				}
			}

			match(input,SC,FOLLOW_SC_in_ar165); 
				
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

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ar"

	// Delegated rules



	public static final BitSet FOLLOW_pr_in_lr168 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_ar_in_lr171 = new BitSet(new long[]{0x0000000000000082L});
	public static final BitSet FOLLOW_SZ_in_pr89 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_EQ_in_pr91 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_NT_in_pr101 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_TER_in_pr105 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_SC_in_pr113 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NT_in_ar128 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_EQ_in_ar136 = new BitSet(new long[]{0x00000000000001A0L});
	public static final BitSet FOLLOW_NT_in_ar141 = new BitSet(new long[]{0x00000000000001A0L});
	public static final BitSet FOLLOW_CT_in_ar153 = new BitSet(new long[]{0x00000000000001A0L});
	public static final BitSet FOLLOW_SC_in_ar165 = new BitSet(new long[]{0x0000000000000002L});
}
