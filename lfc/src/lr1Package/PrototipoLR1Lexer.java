// $ANTLR 3.5.1 C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g 2019-03-17 12:22:36

  package lr1Package;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class PrototipoLR1Lexer extends Lexer {
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
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public PrototipoLR1Lexer() {} 
	public PrototipoLR1Lexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public PrototipoLR1Lexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g"; }

	// $ANTLR start "SZ"
	public final void mSZ() throws RecognitionException {
		try {
			int _type = SZ;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:178:5: ( 'S0' )
			// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:178:7: 'S0'
			{
			match("S0"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "SZ"

	// $ANTLR start "EQ"
	public final void mEQ() throws RecognitionException {
		try {
			int _type = EQ;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:179:4: ( '->' | ':=' )
			int alt1=2;
			int LA1_0 = input.LA(1);
			if ( (LA1_0=='-') ) {
				alt1=1;
			}
			else if ( (LA1_0==':') ) {
				alt1=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 1, 0, input);
				throw nvae;
			}

			switch (alt1) {
				case 1 :
					// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:179:6: '->'
					{
					match("->"); 

					}
					break;
				case 2 :
					// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:179:13: ':='
					{
					match(":="); 

					}
					break;

			}
			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "EQ"

	// $ANTLR start "NT"
	public final void mNT() throws RecognitionException {
		try {
			int _type = NT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:180:5: ( ( 'A' .. 'Z' ) )
			// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:
			{
			if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "NT"

	// $ANTLR start "CT"
	public final void mCT() throws RecognitionException {
		try {
			int _type = CT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:181:4: ( ( 'a' .. 'z' ) | ( '0' .. '9' ) | '+' | '-' | '*' | '/' )
			// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:
			{
			if ( (input.LA(1) >= '*' && input.LA(1) <= '+')||input.LA(1)=='-'||(input.LA(1) >= '/' && input.LA(1) <= '9')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "CT"

	// $ANTLR start "TER"
	public final void mTER() throws RecognitionException {
		try {
			int _type = TER;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:182:5: ( '/swa' | '/cjswa' )
			int alt2=2;
			int LA2_0 = input.LA(1);
			if ( (LA2_0=='/') ) {
				int LA2_1 = input.LA(2);
				if ( (LA2_1=='s') ) {
					alt2=1;
				}
				else if ( (LA2_1=='c') ) {
					alt2=2;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 2, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 2, 0, input);
				throw nvae;
			}

			switch (alt2) {
				case 1 :
					// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:182:7: '/swa'
					{
					match("/swa"); 

					}
					break;
				case 2 :
					// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:182:16: '/cjswa'
					{
					match("/cjswa"); 

					}
					break;

			}
			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "TER"

	// $ANTLR start "SC"
	public final void mSC() throws RecognitionException {
		try {
			int _type = SC;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:183:5: ( ';' )
			// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:183:7: ';'
			{
			match(';'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "SC"

	// $ANTLR start "COMMENT"
	public final void mCOMMENT() throws RecognitionException {
		try {
			int _type = COMMENT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:186:5: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' | '/*' ( options {greedy=false; } : . )* '*/' )
			int alt6=2;
			int LA6_0 = input.LA(1);
			if ( (LA6_0=='/') ) {
				int LA6_1 = input.LA(2);
				if ( (LA6_1=='/') ) {
					alt6=1;
				}
				else if ( (LA6_1=='*') ) {
					alt6=2;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 6, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 6, 0, input);
				throw nvae;
			}

			switch (alt6) {
				case 1 :
					// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:186:9: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
					{
					match("//"); 

					// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:186:14: (~ ( '\\n' | '\\r' ) )*
					loop3:
					while (true) {
						int alt3=2;
						int LA3_0 = input.LA(1);
						if ( ((LA3_0 >= '\u0000' && LA3_0 <= '\t')||(LA3_0 >= '\u000B' && LA3_0 <= '\f')||(LA3_0 >= '\u000E' && LA3_0 <= '\uFFFF')) ) {
							alt3=1;
						}

						switch (alt3) {
						case 1 :
							// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:
							{
							if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '\uFFFF') ) {
								input.consume();
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								recover(mse);
								throw mse;
							}
							}
							break;

						default :
							break loop3;
						}
					}

					// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:186:28: ( '\\r' )?
					int alt4=2;
					int LA4_0 = input.LA(1);
					if ( (LA4_0=='\r') ) {
						alt4=1;
					}
					switch (alt4) {
						case 1 :
							// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:186:28: '\\r'
							{
							match('\r'); 
							}
							break;

					}

					match('\n'); 
					_channel=HIDDEN;
					}
					break;
				case 2 :
					// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:187:9: '/*' ( options {greedy=false; } : . )* '*/'
					{
					match("/*"); 

					// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:187:14: ( options {greedy=false; } : . )*
					loop5:
					while (true) {
						int alt5=2;
						int LA5_0 = input.LA(1);
						if ( (LA5_0=='*') ) {
							int LA5_1 = input.LA(2);
							if ( (LA5_1=='/') ) {
								alt5=2;
							}
							else if ( ((LA5_1 >= '\u0000' && LA5_1 <= '.')||(LA5_1 >= '0' && LA5_1 <= '\uFFFF')) ) {
								alt5=1;
							}

						}
						else if ( ((LA5_0 >= '\u0000' && LA5_0 <= ')')||(LA5_0 >= '+' && LA5_0 <= '\uFFFF')) ) {
							alt5=1;
						}

						switch (alt5) {
						case 1 :
							// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:187:42: .
							{
							matchAny(); 
							}
							break;

						default :
							break loop5;
						}
					}

					match("*/"); 

					_channel=HIDDEN;
					}
					break;

			}
			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "COMMENT"

	// $ANTLR start "WS"
	public final void mWS() throws RecognitionException {
		try {
			int _type = WS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:190:5: ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
			// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:190:9: ( ' ' | '\\t' | '\\r' | '\\n' )
			{
			if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			_channel=HIDDEN;
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "WS"

	@Override
	public void mTokens() throws RecognitionException {
		// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:1:8: ( SZ | EQ | NT | CT | TER | SC | COMMENT | WS )
		int alt7=8;
		switch ( input.LA(1) ) {
		case 'S':
			{
			int LA7_1 = input.LA(2);
			if ( (LA7_1=='0') ) {
				alt7=1;
			}

			else {
				alt7=3;
			}

			}
			break;
		case '-':
			{
			int LA7_2 = input.LA(2);
			if ( (LA7_2=='>') ) {
				alt7=2;
			}

			else {
				alt7=4;
			}

			}
			break;
		case ':':
			{
			alt7=2;
			}
			break;
		case 'A':
		case 'B':
		case 'C':
		case 'D':
		case 'E':
		case 'F':
		case 'G':
		case 'H':
		case 'I':
		case 'J':
		case 'K':
		case 'L':
		case 'M':
		case 'N':
		case 'O':
		case 'P':
		case 'Q':
		case 'R':
		case 'T':
		case 'U':
		case 'V':
		case 'W':
		case 'X':
		case 'Y':
		case 'Z':
			{
			alt7=3;
			}
			break;
		case '/':
			{
			switch ( input.LA(2) ) {
			case 'c':
			case 's':
				{
				alt7=5;
				}
				break;
			case '*':
			case '/':
				{
				alt7=7;
				}
				break;
			default:
				alt7=4;
			}
			}
			break;
		case '*':
		case '+':
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
		case 'a':
		case 'b':
		case 'c':
		case 'd':
		case 'e':
		case 'f':
		case 'g':
		case 'h':
		case 'i':
		case 'j':
		case 'k':
		case 'l':
		case 'm':
		case 'n':
		case 'o':
		case 'p':
		case 'q':
		case 'r':
		case 's':
		case 't':
		case 'u':
		case 'v':
		case 'w':
		case 'x':
		case 'y':
		case 'z':
			{
			alt7=4;
			}
			break;
		case ';':
			{
			alt7=6;
			}
			break;
		case '\t':
		case '\n':
		case '\r':
		case ' ':
			{
			alt7=8;
			}
			break;
		default:
			NoViableAltException nvae =
				new NoViableAltException("", 7, 0, input);
			throw nvae;
		}
		switch (alt7) {
			case 1 :
				// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:1:10: SZ
				{
				mSZ(); 

				}
				break;
			case 2 :
				// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:1:13: EQ
				{
				mEQ(); 

				}
				break;
			case 3 :
				// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:1:16: NT
				{
				mNT(); 

				}
				break;
			case 4 :
				// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:1:19: CT
				{
				mCT(); 

				}
				break;
			case 5 :
				// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:1:22: TER
				{
				mTER(); 

				}
				break;
			case 6 :
				// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:1:26: SC
				{
				mSC(); 

				}
				break;
			case 7 :
				// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:1:29: COMMENT
				{
				mCOMMENT(); 

				}
				break;
			case 8 :
				// C:\\Users\\Luka8\\Desktop\\progettoLFC\\PrototipoLR1.g:1:37: WS
				{
				mWS(); 

				}
				break;

		}
	}



}
