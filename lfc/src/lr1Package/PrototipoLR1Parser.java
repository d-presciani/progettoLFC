// $ANTLR 3.5.1 C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g 2019-03-05 16:10:39

  package lr1Package;
  import myPackage.*;
  import solver.*;


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



	// $ANTLR start "lr1"
	// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:50:1: lr1 : pr ( ar )+ EOF ;
	public final void lr1() throws RecognitionException {
		try {
			// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:50:5: ( pr ( ar )+ EOF )
			// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:51:2: pr ( ar )+ EOF
			{

					init();
					static LinkedList<NonTerminale> listaNT = new LinkedList<NonTerminale>();
					static LinkedList<RegolaDiProduzione> listaReg = new LinkedList<RegolaDiProduzione>();
				
			pushFollow(FOLLOW_pr_in_lr169);
			pr();
			state._fsp--;

			// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:56:6: ( ar )+
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
					// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:56:6: ar
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

			match(input,EOF,FOLLOW_EOF_in_lr174); 
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
	// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:59:1: pr : start= SZ EQ NT TER SC ;
	public final void pr() throws RecognitionException {
		Token start=null;

		try {
			// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:59:4: (start= SZ EQ NT TER SC )
			// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:59:6: start= SZ EQ NT TER SC
			{
			start=(Token)match(input,SZ,FOLLOW_SZ_in_pr87); 
			match(input,EQ,FOLLOW_EQ_in_pr89); 
			match(input,NT,FOLLOW_NT_in_pr91); 

				  	System.out.println("Test");
				  	NonTerminale prova = new NonTerminale(start.getText());
				  	System.out.println("Questo è il non terminale inserito: " + prova);	  	
				
			match(input,TER,FOLLOW_TER_in_pr99); 

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
				 
			match(input,SC,FOLLOW_SC_in_pr109); 
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
	// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:85:1: ar : NT EQ ( NT | CT )* SC ;
	public final void ar() throws RecognitionException {
		try {
			// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:85:4: ( NT EQ ( NT | CT )* SC )
			// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:85:6: NT EQ ( NT | CT )* SC
			{
			match(input,NT,FOLLOW_NT_in_ar119); 
			match(input,EQ,FOLLOW_EQ_in_ar121); 
			// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:85:12: ( NT | CT )*
			loop2:
			while (true) {
				int alt2=2;
				int LA2_0 = input.LA(1);
				if ( (LA2_0==CT||LA2_0==NT) ) {
					alt2=1;
				}

				switch (alt2) {
				case 1 :
					// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:
					{
					if ( input.LA(1)==CT||input.LA(1)==NT ) {
						input.consume();
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					}
					break;

				default :
					break loop2;
				}
			}

			match(input,SC,FOLLOW_SC_in_ar130); 
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



	public static final BitSet FOLLOW_pr_in_lr169 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_ar_in_lr171 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_EOF_in_lr174 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_SZ_in_pr87 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_EQ_in_pr89 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_NT_in_pr91 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_TER_in_pr99 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_SC_in_pr109 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NT_in_ar119 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_EQ_in_ar121 = new BitSet(new long[]{0x00000000000001A0L});
	public static final BitSet FOLLOW_SC_in_ar130 = new BitSet(new long[]{0x0000000000000002L});
}
