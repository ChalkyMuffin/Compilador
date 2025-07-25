// Generated from grammar/Expr.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class ExprLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, INT_NUM=15, FLOAT_NUM=16, 
		ID=17, WS=18, COMMENT=19, SUMA=20, RESTA=21, MULT=22, DIVIDE=23, MAYOR_QUE=24, 
		MENOR_QUE=25, IGUALDAD_ABS=26, DESIGUALDAD=27, COMMA=28, LPAREN=29, RPAREN=30, 
		LBRACE=31, RBRACE=32, SQUARE_BRACKET_LEFT=33, SQUARE_BRACKET_RIGHT=34;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "T__11", "T__12", "T__13", "INT_NUM", "FLOAT_NUM", "ID", 
			"WS", "COMMENT", "SUMA", "RESTA", "MULT", "DIVIDE", "MAYOR_QUE", "MENOR_QUE", 
			"IGUALDAD_ABS", "DESIGUALDAD", "COMMA", "LPAREN", "RPAREN", "LBRACE", 
			"RBRACE", "SQUARE_BRACKET_LEFT", "SQUARE_BRACKET_RIGHT"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'program'", "';'", "'end'", "'main'", "'var'", "':'", "'void'", 
			"'='", "'if'", "'else'", "'while'", "'print'", "'int'", "'float'", null, 
			null, null, null, null, "'+'", "'-'", "'*'", "'/'", "'>'", "'<'", "'=='", 
			"'!='", "','", "'('", "')'", "'{'", "'}'", "'['", "']'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, "INT_NUM", "FLOAT_NUM", "ID", "WS", "COMMENT", "SUMA", 
			"RESTA", "MULT", "DIVIDE", "MAYOR_QUE", "MENOR_QUE", "IGUALDAD_ABS", 
			"DESIGUALDAD", "COMMA", "LPAREN", "RPAREN", "LBRACE", "RBRACE", "SQUARE_BRACKET_LEFT", 
			"SQUARE_BRACKET_RIGHT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public ExprLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Expr.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\"\u00ce\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002"+
		"\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002"+
		"\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002"+
		"\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002"+
		"\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002"+
		"\u001e\u0007\u001e\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007"+
		"!\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005"+
		"\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\u000e\u0004\u000e\u0085\b\u000e\u000b\u000e\f\u000e\u0086\u0001"+
		"\u000f\u0004\u000f\u008a\b\u000f\u000b\u000f\f\u000f\u008b\u0001\u000f"+
		"\u0001\u000f\u0004\u000f\u0090\b\u000f\u000b\u000f\f\u000f\u0091\u0001"+
		"\u0010\u0001\u0010\u0005\u0010\u0096\b\u0010\n\u0010\f\u0010\u0099\t\u0010"+
		"\u0001\u0011\u0004\u0011\u009c\b\u0011\u000b\u0011\f\u0011\u009d\u0001"+
		"\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0005"+
		"\u0012\u00a6\b\u0012\n\u0012\f\u0012\u00a9\t\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0014\u0001\u0014"+
		"\u0001\u0015\u0001\u0015\u0001\u0016\u0001\u0016\u0001\u0017\u0001\u0017"+
		"\u0001\u0018\u0001\u0018\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u001a"+
		"\u0001\u001a\u0001\u001a\u0001\u001b\u0001\u001b\u0001\u001c\u0001\u001c"+
		"\u0001\u001d\u0001\u001d\u0001\u001e\u0001\u001e\u0001\u001f\u0001\u001f"+
		"\u0001 \u0001 \u0001!\u0001!\u0001\u00a7\u0000\"\u0001\u0001\u0003\u0002"+
		"\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013"+
		"\n\u0015\u000b\u0017\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011"+
		"#\u0012%\u0013\'\u0014)\u0015+\u0016-\u0017/\u00181\u00193\u001a5\u001b"+
		"7\u001c9\u001d;\u001e=\u001f? A!C\"\u0001\u0000\u0004\u0001\u000009\u0002"+
		"\u0000AZaz\u0003\u000009AZaz\u0003\u0000\t\n\r\r  \u00d3\u0000\u0001\u0001"+
		"\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001"+
		"\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000"+
		"\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000"+
		"\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000"+
		"\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000"+
		"\u0000\u0000\u0017\u0001\u0000\u0000\u0000\u0000\u0019\u0001\u0000\u0000"+
		"\u0000\u0000\u001b\u0001\u0000\u0000\u0000\u0000\u001d\u0001\u0000\u0000"+
		"\u0000\u0000\u001f\u0001\u0000\u0000\u0000\u0000!\u0001\u0000\u0000\u0000"+
		"\u0000#\u0001\u0000\u0000\u0000\u0000%\u0001\u0000\u0000\u0000\u0000\'"+
		"\u0001\u0000\u0000\u0000\u0000)\u0001\u0000\u0000\u0000\u0000+\u0001\u0000"+
		"\u0000\u0000\u0000-\u0001\u0000\u0000\u0000\u0000/\u0001\u0000\u0000\u0000"+
		"\u00001\u0001\u0000\u0000\u0000\u00003\u0001\u0000\u0000\u0000\u00005"+
		"\u0001\u0000\u0000\u0000\u00007\u0001\u0000\u0000\u0000\u00009\u0001\u0000"+
		"\u0000\u0000\u0000;\u0001\u0000\u0000\u0000\u0000=\u0001\u0000\u0000\u0000"+
		"\u0000?\u0001\u0000\u0000\u0000\u0000A\u0001\u0000\u0000\u0000\u0000C"+
		"\u0001\u0000\u0000\u0000\u0001E\u0001\u0000\u0000\u0000\u0003M\u0001\u0000"+
		"\u0000\u0000\u0005O\u0001\u0000\u0000\u0000\u0007S\u0001\u0000\u0000\u0000"+
		"\tX\u0001\u0000\u0000\u0000\u000b\\\u0001\u0000\u0000\u0000\r^\u0001\u0000"+
		"\u0000\u0000\u000fc\u0001\u0000\u0000\u0000\u0011e\u0001\u0000\u0000\u0000"+
		"\u0013h\u0001\u0000\u0000\u0000\u0015m\u0001\u0000\u0000\u0000\u0017s"+
		"\u0001\u0000\u0000\u0000\u0019y\u0001\u0000\u0000\u0000\u001b}\u0001\u0000"+
		"\u0000\u0000\u001d\u0084\u0001\u0000\u0000\u0000\u001f\u0089\u0001\u0000"+
		"\u0000\u0000!\u0093\u0001\u0000\u0000\u0000#\u009b\u0001\u0000\u0000\u0000"+
		"%\u00a1\u0001\u0000\u0000\u0000\'\u00ae\u0001\u0000\u0000\u0000)\u00b0"+
		"\u0001\u0000\u0000\u0000+\u00b2\u0001\u0000\u0000\u0000-\u00b4\u0001\u0000"+
		"\u0000\u0000/\u00b6\u0001\u0000\u0000\u00001\u00b8\u0001\u0000\u0000\u0000"+
		"3\u00ba\u0001\u0000\u0000\u00005\u00bd\u0001\u0000\u0000\u00007\u00c0"+
		"\u0001\u0000\u0000\u00009\u00c2\u0001\u0000\u0000\u0000;\u00c4\u0001\u0000"+
		"\u0000\u0000=\u00c6\u0001\u0000\u0000\u0000?\u00c8\u0001\u0000\u0000\u0000"+
		"A\u00ca\u0001\u0000\u0000\u0000C\u00cc\u0001\u0000\u0000\u0000EF\u0005"+
		"p\u0000\u0000FG\u0005r\u0000\u0000GH\u0005o\u0000\u0000HI\u0005g\u0000"+
		"\u0000IJ\u0005r\u0000\u0000JK\u0005a\u0000\u0000KL\u0005m\u0000\u0000"+
		"L\u0002\u0001\u0000\u0000\u0000MN\u0005;\u0000\u0000N\u0004\u0001\u0000"+
		"\u0000\u0000OP\u0005e\u0000\u0000PQ\u0005n\u0000\u0000QR\u0005d\u0000"+
		"\u0000R\u0006\u0001\u0000\u0000\u0000ST\u0005m\u0000\u0000TU\u0005a\u0000"+
		"\u0000UV\u0005i\u0000\u0000VW\u0005n\u0000\u0000W\b\u0001\u0000\u0000"+
		"\u0000XY\u0005v\u0000\u0000YZ\u0005a\u0000\u0000Z[\u0005r\u0000\u0000"+
		"[\n\u0001\u0000\u0000\u0000\\]\u0005:\u0000\u0000]\f\u0001\u0000\u0000"+
		"\u0000^_\u0005v\u0000\u0000_`\u0005o\u0000\u0000`a\u0005i\u0000\u0000"+
		"ab\u0005d\u0000\u0000b\u000e\u0001\u0000\u0000\u0000cd\u0005=\u0000\u0000"+
		"d\u0010\u0001\u0000\u0000\u0000ef\u0005i\u0000\u0000fg\u0005f\u0000\u0000"+
		"g\u0012\u0001\u0000\u0000\u0000hi\u0005e\u0000\u0000ij\u0005l\u0000\u0000"+
		"jk\u0005s\u0000\u0000kl\u0005e\u0000\u0000l\u0014\u0001\u0000\u0000\u0000"+
		"mn\u0005w\u0000\u0000no\u0005h\u0000\u0000op\u0005i\u0000\u0000pq\u0005"+
		"l\u0000\u0000qr\u0005e\u0000\u0000r\u0016\u0001\u0000\u0000\u0000st\u0005"+
		"p\u0000\u0000tu\u0005r\u0000\u0000uv\u0005i\u0000\u0000vw\u0005n\u0000"+
		"\u0000wx\u0005t\u0000\u0000x\u0018\u0001\u0000\u0000\u0000yz\u0005i\u0000"+
		"\u0000z{\u0005n\u0000\u0000{|\u0005t\u0000\u0000|\u001a\u0001\u0000\u0000"+
		"\u0000}~\u0005f\u0000\u0000~\u007f\u0005l\u0000\u0000\u007f\u0080\u0005"+
		"o\u0000\u0000\u0080\u0081\u0005a\u0000\u0000\u0081\u0082\u0005t\u0000"+
		"\u0000\u0082\u001c\u0001\u0000\u0000\u0000\u0083\u0085\u0007\u0000\u0000"+
		"\u0000\u0084\u0083\u0001\u0000\u0000\u0000\u0085\u0086\u0001\u0000\u0000"+
		"\u0000\u0086\u0084\u0001\u0000\u0000\u0000\u0086\u0087\u0001\u0000\u0000"+
		"\u0000\u0087\u001e\u0001\u0000\u0000\u0000\u0088\u008a\u0007\u0000\u0000"+
		"\u0000\u0089\u0088\u0001\u0000\u0000\u0000\u008a\u008b\u0001\u0000\u0000"+
		"\u0000\u008b\u0089\u0001\u0000\u0000\u0000\u008b\u008c\u0001\u0000\u0000"+
		"\u0000\u008c\u008d\u0001\u0000\u0000\u0000\u008d\u008f\u0005.\u0000\u0000"+
		"\u008e\u0090\u0007\u0000\u0000\u0000\u008f\u008e\u0001\u0000\u0000\u0000"+
		"\u0090\u0091\u0001\u0000\u0000\u0000\u0091\u008f\u0001\u0000\u0000\u0000"+
		"\u0091\u0092\u0001\u0000\u0000\u0000\u0092 \u0001\u0000\u0000\u0000\u0093"+
		"\u0097\u0007\u0001\u0000\u0000\u0094\u0096\u0007\u0002\u0000\u0000\u0095"+
		"\u0094\u0001\u0000\u0000\u0000\u0096\u0099\u0001\u0000\u0000\u0000\u0097"+
		"\u0095\u0001\u0000\u0000\u0000\u0097\u0098\u0001\u0000\u0000\u0000\u0098"+
		"\"\u0001\u0000\u0000\u0000\u0099\u0097\u0001\u0000\u0000\u0000\u009a\u009c"+
		"\u0007\u0003\u0000\u0000\u009b\u009a\u0001\u0000\u0000\u0000\u009c\u009d"+
		"\u0001\u0000\u0000\u0000\u009d\u009b\u0001\u0000\u0000\u0000\u009d\u009e"+
		"\u0001\u0000\u0000\u0000\u009e\u009f\u0001\u0000\u0000\u0000\u009f\u00a0"+
		"\u0006\u0011\u0000\u0000\u00a0$\u0001\u0000\u0000\u0000\u00a1\u00a2\u0005"+
		"/\u0000\u0000\u00a2\u00a3\u0005/\u0000\u0000\u00a3\u00a7\u0001\u0000\u0000"+
		"\u0000\u00a4\u00a6\t\u0000\u0000\u0000\u00a5\u00a4\u0001\u0000\u0000\u0000"+
		"\u00a6\u00a9\u0001\u0000\u0000\u0000\u00a7\u00a8\u0001\u0000\u0000\u0000"+
		"\u00a7\u00a5\u0001\u0000\u0000\u0000\u00a8\u00aa\u0001\u0000\u0000\u0000"+
		"\u00a9\u00a7\u0001\u0000\u0000\u0000\u00aa\u00ab\u0005\n\u0000\u0000\u00ab"+
		"\u00ac\u0001\u0000\u0000\u0000\u00ac\u00ad\u0006\u0012\u0000\u0000\u00ad"+
		"&\u0001\u0000\u0000\u0000\u00ae\u00af\u0005+\u0000\u0000\u00af(\u0001"+
		"\u0000\u0000\u0000\u00b0\u00b1\u0005-\u0000\u0000\u00b1*\u0001\u0000\u0000"+
		"\u0000\u00b2\u00b3\u0005*\u0000\u0000\u00b3,\u0001\u0000\u0000\u0000\u00b4"+
		"\u00b5\u0005/\u0000\u0000\u00b5.\u0001\u0000\u0000\u0000\u00b6\u00b7\u0005"+
		">\u0000\u0000\u00b70\u0001\u0000\u0000\u0000\u00b8\u00b9\u0005<\u0000"+
		"\u0000\u00b92\u0001\u0000\u0000\u0000\u00ba\u00bb\u0005=\u0000\u0000\u00bb"+
		"\u00bc\u0005=\u0000\u0000\u00bc4\u0001\u0000\u0000\u0000\u00bd\u00be\u0005"+
		"!\u0000\u0000\u00be\u00bf\u0005=\u0000\u0000\u00bf6\u0001\u0000\u0000"+
		"\u0000\u00c0\u00c1\u0005,\u0000\u0000\u00c18\u0001\u0000\u0000\u0000\u00c2"+
		"\u00c3\u0005(\u0000\u0000\u00c3:\u0001\u0000\u0000\u0000\u00c4\u00c5\u0005"+
		")\u0000\u0000\u00c5<\u0001\u0000\u0000\u0000\u00c6\u00c7\u0005{\u0000"+
		"\u0000\u00c7>\u0001\u0000\u0000\u0000\u00c8\u00c9\u0005}\u0000\u0000\u00c9"+
		"@\u0001\u0000\u0000\u0000\u00ca\u00cb\u0005[\u0000\u0000\u00cbB\u0001"+
		"\u0000\u0000\u0000\u00cc\u00cd\u0005]\u0000\u0000\u00cdD\u0001\u0000\u0000"+
		"\u0000\u0007\u0000\u0086\u008b\u0091\u0097\u009d\u00a7\u0001\u0006\u0000"+
		"\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}