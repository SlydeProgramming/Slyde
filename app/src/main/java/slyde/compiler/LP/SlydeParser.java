package slyde.compiler.LP;

import java.util.List;

import org.antlr.v4.runtime.FailedPredicateException;
import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
// Generated from app/src/main/java/slyde/compiler/LP/Slyde.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

@SuppressWarnings({ "all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape" })
public class SlydeParser extends Parser {
  static {
    RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION);
  }

  protected static final DFA[] _decisionToDFA;
  protected static final PredictionContextCache _sharedContextCache = new PredictionContextCache();
  public static final int T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, T__4 = 5, T__5 = 6, T__6 = 7, T__7 = 8, T__8 = 9,
      T__9 = 10, T__10 = 11, T__11 = 12, T__12 = 13, T__13 = 14, T__14 = 15, T__15 = 16, T__16 = 17,
      T__17 = 18, T__18 = 19, T__19 = 20, T__20 = 21, T__21 = 22, T__22 = 23, T__23 = 24,
      T__24 = 25, T__25 = 26, T__26 = 27, T__27 = 28, T__28 = 29, T__29 = 30, SLC = 31, MLC = 32,
      RAW_BLOCK = 33, RAW_BLOCKWS = 34, CLASS = 35, EXTENDS = 36, IF = 37, ELSE = 38, WHILE = 39,
      FOR = 40, RETURN = 41, CONSTRUCT = 42, MAIN = 43, IDENTIFIER = 44, NUMBER = 45, DOUBLE = 46,
      FLOAT = 47, STRING = 48, BOOLEAN = 49, WS = 50;
  public static final int RULE_prog = 0, RULE_classDeclaration = 1, RULE_classBody = 2, RULE_methodDeclaration = 3,
      RULE_paramList = 4, RULE_block = 5, RULE_constructor = 6, RULE_statement = 7,
      RULE_varDecl = 8, RULE_assignment = 9, RULE_methodCall = 10, RULE_argList = 11,
      RULE_binOp = 12, RULE_compareOp = 13, RULE_expr = 14, RULE_ifStmt = 15,
      RULE_whileStmt = 16, RULE_forStmt = 17, RULE_returnStmt = 18, RULE_type = 19,
      RULE_arrayDeclaration = 20, RULE_arrayLiteral = 21, RULE_newInstance = 22,
      RULE_raw = 23;

  private static String[] makeRuleNames() {
    return new String[] {
        "prog", "classDeclaration", "classBody", "methodDeclaration", "paramList",
        "block", "constructor", "statement", "varDecl", "assignment", "methodCall",
        "argList", "binOp", "compareOp", "expr", "ifStmt", "whileStmt", "forStmt",
        "returnStmt", "type", "arrayDeclaration", "arrayLiteral", "newInstance",
        "raw"
    };
  }

  public static final String[] ruleNames = makeRuleNames();

  private static String[] makeLiteralNames() {
    return new String[] {
        null, "'('", "')'", "'{'", "'}'", "','", "';'", "'='", "'['", "']'",
        "'.'", "'+'", "'-'", "'*'", "'/'", "'=='", "'!='", "'<'", "'>'", "'<='",
        "'>='", "'&&'", "'||'", "'!'", "'int'", "'double'", "'float'", "'String'",
        "'boolean'", "'void'", "'new'", null, null, null, null, "'class'", "'extends'",
        "'if'", "'else'", "'while'", "'for'", "'return'", "'constructor'", "'main'"
    };
  }

  private static final String[] _LITERAL_NAMES = makeLiteralNames();

  private static String[] makeSymbolicNames() {
    return new String[] {
        null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, "SLC", "MLC", "RAW_BLOCK",
        "RAW_BLOCKWS", "CLASS", "EXTENDS", "IF", "ELSE", "WHILE", "FOR", "RETURN",
        "CONSTRUCT", "MAIN", "IDENTIFIER", "NUMBER", "DOUBLE", "FLOAT", "STRING",
        "BOOLEAN", "WS"
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

  @Override
  public String getGrammarFileName() {
    return "Slyde.g4";
  }

  @Override
  public String[] getRuleNames() {
    return ruleNames;
  }

  @Override
  public String getSerializedATN() {
    return _serializedATN;
  }

  @Override
  public ATN getATN() {
    return _ATN;
  }

  public SlydeParser(TokenStream input) {
    super(input);
    _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ProgContext extends ParserRuleContext {
    public TerminalNode EOF() {
      return getToken(SlydeParser.EOF, 0);
    }

    public List<ClassDeclarationContext> classDeclaration() {
      return getRuleContexts(ClassDeclarationContext.class);
    }

    public ClassDeclarationContext classDeclaration(int i) {
      return getRuleContext(ClassDeclarationContext.class, i);
    }

    public List<TerminalNode> MAIN() {
      return getTokens(SlydeParser.MAIN);
    }

    public TerminalNode MAIN(int i) {
      return getToken(SlydeParser.MAIN, i);
    }

    public List<BlockContext> block() {
      return getRuleContexts(BlockContext.class);
    }

    public BlockContext block(int i) {
      return getRuleContext(BlockContext.class, i);
    }

    public List<ParamListContext> paramList() {
      return getRuleContexts(ParamListContext.class);
    }

    public ParamListContext paramList(int i) {
      return getRuleContext(ParamListContext.class, i);
    }

    public ProgContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_prog;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).enterProg(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).exitProg(this);
    }
  }

  public final ProgContext prog() throws RecognitionException {
    ProgContext _localctx = new ProgContext(_ctx, getState());
    enterRule(_localctx, 0, RULE_prog);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(58);
        _errHandler.sync(this);
        _la = _input.LA(1);
        while (_la == CLASS || _la == MAIN) {
          {
            setState(56);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
              case CLASS: {
                setState(48);
                classDeclaration();
              }
                break;
              case MAIN: {
                setState(49);
                match(MAIN);
                setState(50);
                match(T__0);
                setState(52);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 17593243009024L) != 0)) {
                  {
                    setState(51);
                    paramList();
                  }
                }

                setState(54);
                match(T__1);
                setState(55);
                block();
              }
                break;
              default:
                throw new NoViableAltException(this);
            }
          }
          setState(60);
          _errHandler.sync(this);
          _la = _input.LA(1);
        }
        setState(61);
        match(EOF);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ClassDeclarationContext extends ParserRuleContext {
    public TerminalNode CLASS() {
      return getToken(SlydeParser.CLASS, 0);
    }

    public List<TerminalNode> IDENTIFIER() {
      return getTokens(SlydeParser.IDENTIFIER);
    }

    public TerminalNode IDENTIFIER(int i) {
      return getToken(SlydeParser.IDENTIFIER, i);
    }

    public ClassBodyContext classBody() {
      return getRuleContext(ClassBodyContext.class, 0);
    }

    public TerminalNode EXTENDS() {
      return getToken(SlydeParser.EXTENDS, 0);
    }

    public ClassDeclarationContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_classDeclaration;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).enterClassDeclaration(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).exitClassDeclaration(this);
    }
  }

  public final ClassDeclarationContext classDeclaration() throws RecognitionException {
    ClassDeclarationContext _localctx = new ClassDeclarationContext(_ctx, getState());
    enterRule(_localctx, 2, RULE_classDeclaration);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(63);
        match(CLASS);
        setState(64);
        match(IDENTIFIER);
        setState(67);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == EXTENDS) {
          {
            setState(65);
            match(EXTENDS);
            setState(66);
            match(IDENTIFIER);
          }
        }

        setState(69);
        match(T__2);
        setState(70);
        classBody();
        setState(71);
        match(T__3);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ClassBodyContext extends ParserRuleContext {
    public List<VarDeclContext> varDecl() {
      return getRuleContexts(VarDeclContext.class);
    }

    public VarDeclContext varDecl(int i) {
      return getRuleContext(VarDeclContext.class, i);
    }

    public List<MethodDeclarationContext> methodDeclaration() {
      return getRuleContexts(MethodDeclarationContext.class);
    }

    public MethodDeclarationContext methodDeclaration(int i) {
      return getRuleContext(MethodDeclarationContext.class, i);
    }

    public List<ConstructorContext> constructor() {
      return getRuleContexts(ConstructorContext.class);
    }

    public ConstructorContext constructor(int i) {
      return getRuleContext(ConstructorContext.class, i);
    }

    public ClassBodyContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_classBody;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).enterClassBody(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).exitClassBody(this);
    }
  }

  public final ClassBodyContext classBody() throws RecognitionException {
    ClassBodyContext _localctx = new ClassBodyContext(_ctx, getState());
    enterRule(_localctx, 4, RULE_classBody);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(78);
        _errHandler.sync(this);
        _la = _input.LA(1);
        while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 21991289520128L) != 0)) {
          {
            setState(76);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 4, _ctx)) {
              case 1: {
                setState(73);
                varDecl();
              }
                break;
              case 2: {
                setState(74);
                methodDeclaration();
              }
                break;
              case 3: {
                setState(75);
                constructor();
              }
                break;
            }
          }
          setState(80);
          _errHandler.sync(this);
          _la = _input.LA(1);
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class MethodDeclarationContext extends ParserRuleContext {
    public TypeContext type() {
      return getRuleContext(TypeContext.class, 0);
    }

    public TerminalNode IDENTIFIER() {
      return getToken(SlydeParser.IDENTIFIER, 0);
    }

    public BlockContext block() {
      return getRuleContext(BlockContext.class, 0);
    }

    public ParamListContext paramList() {
      return getRuleContext(ParamListContext.class, 0);
    }

    public MethodDeclarationContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_methodDeclaration;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).enterMethodDeclaration(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).exitMethodDeclaration(this);
    }
  }

  public final MethodDeclarationContext methodDeclaration() throws RecognitionException {
    MethodDeclarationContext _localctx = new MethodDeclarationContext(_ctx, getState());
    enterRule(_localctx, 6, RULE_methodDeclaration);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(81);
        type();
        setState(82);
        match(IDENTIFIER);
        setState(83);
        match(T__0);
        setState(85);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 17593243009024L) != 0)) {
          {
            setState(84);
            paramList();
          }
        }

        setState(87);
        match(T__1);
        setState(88);
        block();
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ParamListContext extends ParserRuleContext {
    public List<TypeContext> type() {
      return getRuleContexts(TypeContext.class);
    }

    public TypeContext type(int i) {
      return getRuleContext(TypeContext.class, i);
    }

    public List<TerminalNode> IDENTIFIER() {
      return getTokens(SlydeParser.IDENTIFIER);
    }

    public TerminalNode IDENTIFIER(int i) {
      return getToken(SlydeParser.IDENTIFIER, i);
    }

    public ParamListContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_paramList;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).enterParamList(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).exitParamList(this);
    }
  }

  public final ParamListContext paramList() throws RecognitionException {
    ParamListContext _localctx = new ParamListContext(_ctx, getState());
    enterRule(_localctx, 8, RULE_paramList);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(90);
        type();
        setState(91);
        match(IDENTIFIER);
        setState(98);
        _errHandler.sync(this);
        _la = _input.LA(1);
        while (_la == T__4) {
          {
            {
              setState(92);
              match(T__4);
              setState(93);
              type();
              setState(94);
              match(IDENTIFIER);
            }
          }
          setState(100);
          _errHandler.sync(this);
          _la = _input.LA(1);
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class BlockContext extends ParserRuleContext {
    public List<StatementContext> statement() {
      return getRuleContexts(StatementContext.class);
    }

    public StatementContext statement(int i) {
      return getRuleContext(StatementContext.class, i);
    }

    public BlockContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_block;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).enterBlock(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).exitBlock(this);
    }
  }

  public final BlockContext block() throws RecognitionException {
    BlockContext _localctx = new BlockContext(_ctx, getState());
    enterRule(_localctx, 10, RULE_block);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(101);
        match(T__2);
        setState(105);
        _errHandler.sync(this);
        _la = _input.LA(1);
        while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 901215126814730L) != 0)) {
          {
            {
              setState(102);
              statement();
            }
          }
          setState(107);
          _errHandler.sync(this);
          _la = _input.LA(1);
        }
        setState(108);
        match(T__3);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ConstructorContext extends ParserRuleContext {
    public TerminalNode CONSTRUCT() {
      return getToken(SlydeParser.CONSTRUCT, 0);
    }

    public BlockContext block() {
      return getRuleContext(BlockContext.class, 0);
    }

    public ParamListContext paramList() {
      return getRuleContext(ParamListContext.class, 0);
    }

    public ConstructorContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_constructor;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).enterConstructor(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).exitConstructor(this);
    }
  }

  public final ConstructorContext constructor() throws RecognitionException {
    ConstructorContext _localctx = new ConstructorContext(_ctx, getState());
    enterRule(_localctx, 12, RULE_constructor);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(110);
        match(CONSTRUCT);
        setState(111);
        match(T__0);
        setState(113);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 17593243009024L) != 0)) {
          {
            setState(112);
            paramList();
          }
        }

        setState(115);
        match(T__1);
        setState(116);
        block();
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class StatementContext extends ParserRuleContext {
    public VarDeclContext varDecl() {
      return getRuleContext(VarDeclContext.class, 0);
    }

    public AssignmentContext assignment() {
      return getRuleContext(AssignmentContext.class, 0);
    }

    public MethodCallContext methodCall() {
      return getRuleContext(MethodCallContext.class, 0);
    }

    public ReturnStmtContext returnStmt() {
      return getRuleContext(ReturnStmtContext.class, 0);
    }

    public BlockContext block() {
      return getRuleContext(BlockContext.class, 0);
    }

    public IfStmtContext ifStmt() {
      return getRuleContext(IfStmtContext.class, 0);
    }

    public WhileStmtContext whileStmt() {
      return getRuleContext(WhileStmtContext.class, 0);
    }

    public ForStmtContext forStmt() {
      return getRuleContext(ForStmtContext.class, 0);
    }

    public RawContext raw() {
      return getRuleContext(RawContext.class, 0);
    }

    public ExprContext expr() {
      return getRuleContext(ExprContext.class, 0);
    }

    public StatementContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_statement;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).enterStatement(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).exitStatement(this);
    }
  }

  public final StatementContext statement() throws RecognitionException {
    StatementContext _localctx = new StatementContext(_ctx, getState());
    enterRule(_localctx, 14, RULE_statement);
    try {
      setState(138);
      _errHandler.sync(this);
      switch (getInterpreter().adaptivePredict(_input, 10, _ctx)) {
        case 1:
          enterOuterAlt(_localctx, 1); {
          setState(118);
          varDecl();
          setState(119);
          match(T__5);
        }
          break;
        case 2:
          enterOuterAlt(_localctx, 2); {
          setState(121);
          assignment();
          setState(122);
          match(T__5);
        }
          break;
        case 3:
          enterOuterAlt(_localctx, 3); {
          setState(124);
          methodCall();
          setState(125);
          match(T__5);
        }
          break;
        case 4:
          enterOuterAlt(_localctx, 4); {
          setState(127);
          returnStmt();
          setState(128);
          match(T__5);
        }
          break;
        case 5:
          enterOuterAlt(_localctx, 5); {
          setState(130);
          block();
        }
          break;
        case 6:
          enterOuterAlt(_localctx, 6); {
          setState(131);
          ifStmt();
        }
          break;
        case 7:
          enterOuterAlt(_localctx, 7); {
          setState(132);
          whileStmt();
        }
          break;
        case 8:
          enterOuterAlt(_localctx, 8); {
          setState(133);
          forStmt();
        }
          break;
        case 9:
          enterOuterAlt(_localctx, 9); {
          setState(134);
          raw();
        }
          break;
        case 10:
          enterOuterAlt(_localctx, 10); {
          setState(135);
          expr(0);
          setState(136);
          match(T__5);
        }
          break;
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class VarDeclContext extends ParserRuleContext {
    public TypeContext type() {
      return getRuleContext(TypeContext.class, 0);
    }

    public TerminalNode IDENTIFIER() {
      return getToken(SlydeParser.IDENTIFIER, 0);
    }

    public ExprContext expr() {
      return getRuleContext(ExprContext.class, 0);
    }

    public VarDeclContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_varDecl;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).enterVarDecl(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).exitVarDecl(this);
    }
  }

  public final VarDeclContext varDecl() throws RecognitionException {
    VarDeclContext _localctx = new VarDeclContext(_ctx, getState());
    enterRule(_localctx, 16, RULE_varDecl);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(140);
        type();
        setState(141);
        match(IDENTIFIER);
        setState(144);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == T__6) {
          {
            setState(142);
            match(T__6);
            setState(143);
            expr(0);
          }
        }

      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class AssignmentContext extends ParserRuleContext {
    public TerminalNode IDENTIFIER() {
      return getToken(SlydeParser.IDENTIFIER, 0);
    }

    public List<ExprContext> expr() {
      return getRuleContexts(ExprContext.class);
    }

    public ExprContext expr(int i) {
      return getRuleContext(ExprContext.class, i);
    }

    public AssignmentContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_assignment;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).enterAssignment(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).exitAssignment(this);
    }
  }

  public final AssignmentContext assignment() throws RecognitionException {
    AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
    enterRule(_localctx, 18, RULE_assignment);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(146);
        match(IDENTIFIER);
        setState(151);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == T__7) {
          {
            setState(147);
            match(T__7);
            setState(148);
            expr(0);
            setState(149);
            match(T__8);
          }
        }

        setState(153);
        match(T__6);
        setState(154);
        expr(0);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class MethodCallContext extends ParserRuleContext {
    public List<TerminalNode> IDENTIFIER() {
      return getTokens(SlydeParser.IDENTIFIER);
    }

    public TerminalNode IDENTIFIER(int i) {
      return getToken(SlydeParser.IDENTIFIER, i);
    }

    public ArgListContext argList() {
      return getRuleContext(ArgListContext.class, 0);
    }

    public MethodCallContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_methodCall;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).enterMethodCall(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).exitMethodCall(this);
    }
  }

  public final MethodCallContext methodCall() throws RecognitionException {
    MethodCallContext _localctx = new MethodCallContext(_ctx, getState());
    enterRule(_localctx, 20, RULE_methodCall);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(156);
        match(IDENTIFIER);
        setState(159);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == T__9) {
          {
            setState(157);
            match(T__9);
            setState(158);
            match(IDENTIFIER);
          }
        }

        setState(161);
        match(T__0);
        setState(163);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 897202570395650L) != 0)) {
          {
            setState(162);
            argList();
          }
        }

        setState(165);
        match(T__1);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ArgListContext extends ParserRuleContext {
    public List<ExprContext> expr() {
      return getRuleContexts(ExprContext.class);
    }

    public ExprContext expr(int i) {
      return getRuleContext(ExprContext.class, i);
    }

    public ArgListContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_argList;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).enterArgList(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).exitArgList(this);
    }
  }

  public final ArgListContext argList() throws RecognitionException {
    ArgListContext _localctx = new ArgListContext(_ctx, getState());
    enterRule(_localctx, 22, RULE_argList);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(167);
        expr(0);
        setState(172);
        _errHandler.sync(this);
        _la = _input.LA(1);
        while (_la == T__4) {
          {
            {
              setState(168);
              match(T__4);
              setState(169);
              expr(0);
            }
          }
          setState(174);
          _errHandler.sync(this);
          _la = _input.LA(1);
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class BinOpContext extends ParserRuleContext {
    public BinOpContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_binOp;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).enterBinOp(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).exitBinOp(this);
    }
  }

  public final BinOpContext binOp() throws RecognitionException {
    BinOpContext _localctx = new BinOpContext(_ctx, getState());
    enterRule(_localctx, 24, RULE_binOp);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(175);
        _la = _input.LA(1);
        if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 30720L) != 0))) {
          _errHandler.recoverInline(this);
        } else {
          if (_input.LA(1) == Token.EOF)
            matchedEOF = true;
          _errHandler.reportMatch(this);
          consume();
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class CompareOpContext extends ParserRuleContext {
    public CompareOpContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_compareOp;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).enterCompareOp(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).exitCompareOp(this);
    }
  }

  public final CompareOpContext compareOp() throws RecognitionException {
    CompareOpContext _localctx = new CompareOpContext(_ctx, getState());
    enterRule(_localctx, 26, RULE_compareOp);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(177);
        _la = _input.LA(1);
        if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 8355840L) != 0))) {
          _errHandler.recoverInline(this);
        } else {
          if (_input.LA(1) == Token.EOF)
            matchedEOF = true;
          _errHandler.reportMatch(this);
          consume();
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ExprContext extends ParserRuleContext {
    public List<ExprContext> expr() {
      return getRuleContexts(ExprContext.class);
    }

    public ExprContext expr(int i) {
      return getRuleContext(ExprContext.class, i);
    }

    public TerminalNode IDENTIFIER() {
      return getToken(SlydeParser.IDENTIFIER, 0);
    }

    public TerminalNode NUMBER() {
      return getToken(SlydeParser.NUMBER, 0);
    }

    public TerminalNode STRING() {
      return getToken(SlydeParser.STRING, 0);
    }

    public TerminalNode BOOLEAN() {
      return getToken(SlydeParser.BOOLEAN, 0);
    }

    public MethodCallContext methodCall() {
      return getRuleContext(MethodCallContext.class, 0);
    }

    public NewInstanceContext newInstance() {
      return getRuleContext(NewInstanceContext.class, 0);
    }

    public BinOpContext binOp() {
      return getRuleContext(BinOpContext.class, 0);
    }

    public CompareOpContext compareOp() {
      return getRuleContext(CompareOpContext.class, 0);
    }

    public ExprContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_expr;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).enterExpr(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).exitExpr(this);
    }
  }

  public final ExprContext expr() throws RecognitionException {
    return expr(0);
  }

  private ExprContext expr(int _p) throws RecognitionException {
    ParserRuleContext _parentctx = _ctx;
    int _parentState = getState();
    ExprContext _localctx = new ExprContext(_ctx, _parentState);
    ExprContext _prevctx = _localctx;
    int _startState = 28;
    enterRecursionRule(_localctx, 28, RULE_expr, _p);
    try {
      int _alt;
      enterOuterAlt(_localctx, 1);
      {
        setState(199);
        _errHandler.sync(this);
        switch (getInterpreter().adaptivePredict(_input, 17, _ctx)) {
          case 1: {
            setState(180);
            match(T__22);
            setState(181);
            expr(9);
          }
            break;
          case 2: {
            setState(182);
            match(IDENTIFIER);
            setState(187);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 16, _ctx)) {
              case 1: {
                setState(183);
                match(T__7);
                setState(184);
                expr(0);
                setState(185);
                match(T__8);
              }
                break;
            }
          }
            break;
          case 3: {
            setState(189);
            match(NUMBER);
          }
            break;
          case 4: {
            setState(190);
            match(STRING);
          }
            break;
          case 5: {
            setState(191);
            match(BOOLEAN);
          }
            break;
          case 6: {
            setState(192);
            methodCall();
          }
            break;
          case 7: {
            setState(193);
            match(T__0);
            setState(194);
            expr(0);
            setState(195);
            match(T__1);
          }
            break;
          case 8: {
            setState(197);
            match(IDENTIFIER);
          }
            break;
          case 9: {
            setState(198);
            newInstance();
          }
            break;
        }
        _ctx.stop = _input.LT(-1);
        setState(211);
        _errHandler.sync(this);
        _alt = getInterpreter().adaptivePredict(_input, 19, _ctx);
        while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
          if (_alt == 1) {
            if (_parseListeners != null)
              triggerExitRuleEvent();
            _prevctx = _localctx;
            {
              setState(209);
              _errHandler.sync(this);
              switch (getInterpreter().adaptivePredict(_input, 18, _ctx)) {
                case 1: {
                  _localctx = new ExprContext(_parentctx, _parentState);
                  pushNewRecursionContext(_localctx, _startState, RULE_expr);
                  setState(201);
                  if (!(precpred(_ctx, 11)))
                    throw new FailedPredicateException(this, "precpred(_ctx, 11)");
                  setState(202);
                  binOp();
                  setState(203);
                  expr(12);
                }
                  break;
                case 2: {
                  _localctx = new ExprContext(_parentctx, _parentState);
                  pushNewRecursionContext(_localctx, _startState, RULE_expr);
                  setState(205);
                  if (!(precpred(_ctx, 10)))
                    throw new FailedPredicateException(this, "precpred(_ctx, 10)");
                  setState(206);
                  compareOp();
                  setState(207);
                  expr(11);
                }
                  break;
              }
            }
          }
          setState(213);
          _errHandler.sync(this);
          _alt = getInterpreter().adaptivePredict(_input, 19, _ctx);
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      unrollRecursionContexts(_parentctx);
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class IfStmtContext extends ParserRuleContext {
    public TerminalNode IF() {
      return getToken(SlydeParser.IF, 0);
    }

    public ExprContext expr() {
      return getRuleContext(ExprContext.class, 0);
    }

    public List<BlockContext> block() {
      return getRuleContexts(BlockContext.class);
    }

    public BlockContext block(int i) {
      return getRuleContext(BlockContext.class, i);
    }

    public TerminalNode ELSE() {
      return getToken(SlydeParser.ELSE, 0);
    }

    public IfStmtContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_ifStmt;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).enterIfStmt(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).exitIfStmt(this);
    }
  }

  public final IfStmtContext ifStmt() throws RecognitionException {
    IfStmtContext _localctx = new IfStmtContext(_ctx, getState());
    enterRule(_localctx, 30, RULE_ifStmt);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(214);
        match(IF);
        setState(215);
        match(T__0);
        setState(216);
        expr(0);
        setState(217);
        match(T__1);
        setState(218);
        block();
        setState(221);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == ELSE) {
          {
            setState(219);
            match(ELSE);
            setState(220);
            block();
          }
        }

      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class WhileStmtContext extends ParserRuleContext {
    public TerminalNode WHILE() {
      return getToken(SlydeParser.WHILE, 0);
    }

    public ExprContext expr() {
      return getRuleContext(ExprContext.class, 0);
    }

    public BlockContext block() {
      return getRuleContext(BlockContext.class, 0);
    }

    public WhileStmtContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_whileStmt;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).enterWhileStmt(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).exitWhileStmt(this);
    }
  }

  public final WhileStmtContext whileStmt() throws RecognitionException {
    WhileStmtContext _localctx = new WhileStmtContext(_ctx, getState());
    enterRule(_localctx, 32, RULE_whileStmt);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(223);
        match(WHILE);
        setState(224);
        match(T__0);
        setState(225);
        expr(0);
        setState(226);
        match(T__1);
        setState(227);
        block();
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ForStmtContext extends ParserRuleContext {
    public TerminalNode FOR() {
      return getToken(SlydeParser.FOR, 0);
    }

    public BlockContext block() {
      return getRuleContext(BlockContext.class, 0);
    }

    public VarDeclContext varDecl() {
      return getRuleContext(VarDeclContext.class, 0);
    }

    public ExprContext expr() {
      return getRuleContext(ExprContext.class, 0);
    }

    public AssignmentContext assignment() {
      return getRuleContext(AssignmentContext.class, 0);
    }

    public ForStmtContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_forStmt;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).enterForStmt(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).exitForStmt(this);
    }
  }

  public final ForStmtContext forStmt() throws RecognitionException {
    ForStmtContext _localctx = new ForStmtContext(_ctx, getState());
    enterRule(_localctx, 34, RULE_forStmt);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(229);
        match(FOR);
        setState(230);
        match(T__0);
        setState(232);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 17593243009024L) != 0)) {
          {
            setState(231);
            varDecl();
          }
        }

        setState(234);
        match(T__5);
        setState(236);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 897202570395650L) != 0)) {
          {
            setState(235);
            expr(0);
          }
        }

        setState(238);
        match(T__5);
        setState(240);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == IDENTIFIER) {
          {
            setState(239);
            assignment();
          }
        }

        setState(242);
        match(T__1);
        setState(243);
        block();
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ReturnStmtContext extends ParserRuleContext {
    public TerminalNode RETURN() {
      return getToken(SlydeParser.RETURN, 0);
    }

    public ExprContext expr() {
      return getRuleContext(ExprContext.class, 0);
    }

    public ReturnStmtContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_returnStmt;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).enterReturnStmt(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).exitReturnStmt(this);
    }
  }

  public final ReturnStmtContext returnStmt() throws RecognitionException {
    ReturnStmtContext _localctx = new ReturnStmtContext(_ctx, getState());
    enterRule(_localctx, 36, RULE_returnStmt);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(245);
        match(RETURN);
        setState(246);
        expr(0);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class TypeContext extends ParserRuleContext {
    public TerminalNode IDENTIFIER() {
      return getToken(SlydeParser.IDENTIFIER, 0);
    }

    public TypeContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_type;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).enterType(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).exitType(this);
    }
  }

  public final TypeContext type() throws RecognitionException {
    TypeContext _localctx = new TypeContext(_ctx, getState());
    enterRule(_localctx, 38, RULE_type);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(248);
        _la = _input.LA(1);
        if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 17593243009024L) != 0))) {
          _errHandler.recoverInline(this);
        } else {
          if (_input.LA(1) == Token.EOF)
            matchedEOF = true;
          _errHandler.reportMatch(this);
          consume();
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ArrayDeclarationContext extends ParserRuleContext {
    public TypeContext type() {
      return getRuleContext(TypeContext.class, 0);
    }

    public TerminalNode IDENTIFIER() {
      return getToken(SlydeParser.IDENTIFIER, 0);
    }

    public ArrayLiteralContext arrayLiteral() {
      return getRuleContext(ArrayLiteralContext.class, 0);
    }

    public ArrayDeclarationContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_arrayDeclaration;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).enterArrayDeclaration(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).exitArrayDeclaration(this);
    }
  }

  public final ArrayDeclarationContext arrayDeclaration() throws RecognitionException {
    ArrayDeclarationContext _localctx = new ArrayDeclarationContext(_ctx, getState());
    enterRule(_localctx, 40, RULE_arrayDeclaration);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(250);
        type();
        setState(251);
        match(T__7);
        setState(252);
        match(T__8);
        setState(253);
        match(IDENTIFIER);
        setState(256);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == T__6) {
          {
            setState(254);
            match(T__6);
            setState(255);
            arrayLiteral();
          }
        }

        setState(258);
        match(T__5);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ArrayLiteralContext extends ParserRuleContext {
    public List<ExprContext> expr() {
      return getRuleContexts(ExprContext.class);
    }

    public ExprContext expr(int i) {
      return getRuleContext(ExprContext.class, i);
    }

    public ArrayLiteralContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_arrayLiteral;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).enterArrayLiteral(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).exitArrayLiteral(this);
    }
  }

  public final ArrayLiteralContext arrayLiteral() throws RecognitionException {
    ArrayLiteralContext _localctx = new ArrayLiteralContext(_ctx, getState());
    enterRule(_localctx, 42, RULE_arrayLiteral);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(260);
        match(T__7);
        setState(269);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 897202570395650L) != 0)) {
          {
            setState(261);
            expr(0);
            setState(266);
            _errHandler.sync(this);
            _la = _input.LA(1);
            while (_la == T__4) {
              {
                {
                  setState(262);
                  match(T__4);
                  setState(263);
                  expr(0);
                }
              }
              setState(268);
              _errHandler.sync(this);
              _la = _input.LA(1);
            }
          }
        }

        setState(271);
        match(T__8);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class NewInstanceContext extends ParserRuleContext {
    public TerminalNode IDENTIFIER() {
      return getToken(SlydeParser.IDENTIFIER, 0);
    }

    public ArgListContext argList() {
      return getRuleContext(ArgListContext.class, 0);
    }

    public NewInstanceContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_newInstance;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).enterNewInstance(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).exitNewInstance(this);
    }
  }

  public final NewInstanceContext newInstance() throws RecognitionException {
    NewInstanceContext _localctx = new NewInstanceContext(_ctx, getState());
    enterRule(_localctx, 44, RULE_newInstance);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(273);
        match(T__29);
        setState(274);
        match(IDENTIFIER);
        setState(275);
        match(T__0);
        setState(277);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 897202570395650L) != 0)) {
          {
            setState(276);
            argList();
          }
        }

        setState(279);
        match(T__1);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class RawContext extends ParserRuleContext {
    public TerminalNode RAW_BLOCK() {
      return getToken(SlydeParser.RAW_BLOCK, 0);
    }

    public TerminalNode RAW_BLOCKWS() {
      return getToken(SlydeParser.RAW_BLOCKWS, 0);
    }

    public RawContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_raw;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).enterRaw(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof SlydeListener)
        ((SlydeListener) listener).exitRaw(this);
    }
  }

  public final RawContext raw() throws RecognitionException {
    RawContext _localctx = new RawContext(_ctx, getState());
    enterRule(_localctx, 46, RULE_raw);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(281);
        _la = _input.LA(1);
        if (!(_la == RAW_BLOCK || _la == RAW_BLOCKWS)) {
          _errHandler.recoverInline(this);
        } else {
          if (_input.LA(1) == Token.EOF)
            matchedEOF = true;
          _errHandler.reportMatch(this);
          consume();
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
    switch (ruleIndex) {
      case 14:
        return expr_sempred((ExprContext) _localctx, predIndex);
    }
    return true;
  }

  private boolean expr_sempred(ExprContext _localctx, int predIndex) {
    switch (predIndex) {
      case 0:
        return precpred(_ctx, 11);
      case 1:
        return precpred(_ctx, 10);
    }
    return true;
  }

  public static final String _serializedATN = "\u0004\u00012\u011c\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"
      +
      "\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002" +
      "\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002" +
      "\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002" +
      "\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f" +
      "\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012" +
      "\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015" +
      "\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0001\u0000\u0001\u0000" +
      "\u0001\u0000\u0001\u0000\u0003\u00005\b\u0000\u0001\u0000\u0001\u0000" +
      "\u0005\u00009\b\u0000\n\u0000\f\u0000<\t\u0000\u0001\u0000\u0001\u0000" +
      "\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001D\b\u0001" +
      "\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002" +
      "\u0001\u0002\u0005\u0002M\b\u0002\n\u0002\f\u0002P\t\u0002\u0001\u0003" +
      "\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003V\b\u0003\u0001\u0003" +
      "\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004" +
      "\u0001\u0004\u0001\u0004\u0005\u0004a\b\u0004\n\u0004\f\u0004d\t\u0004" +
      "\u0001\u0005\u0001\u0005\u0005\u0005h\b\u0005\n\u0005\f\u0005k\t\u0005" +
      "\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0003\u0006" +
      "r\b\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007" +
      "\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007" +
      "\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007" +
      "\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007" +
      "\u0003\u0007\u008b\b\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0003\b\u0091" +
      "\b\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0003\t\u0098\b\t\u0001\t" +
      "\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0003\n\u00a0\b\n\u0001\n\u0001" +
      "\n\u0003\n\u00a4\b\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b" +
      "\u0005\u000b\u00ab\b\u000b\n\u000b\f\u000b\u00ae\t\u000b\u0001\f\u0001" +
      "\f\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001" +
      "\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0003\u000e\u00bc\b\u000e\u0001" +
      "\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001" +
      "\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0003\u000e\u00c8\b\u000e\u0001" +
      "\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001" +
      "\u000e\u0001\u000e\u0005\u000e\u00d2\b\u000e\n\u000e\f\u000e\u00d5\t\u000e" +
      "\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f" +
      "\u0001\u000f\u0003\u000f\u00de\b\u000f\u0001\u0010\u0001\u0010\u0001\u0010" +
      "\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0011" +
      "\u0003\u0011\u00e9\b\u0011\u0001\u0011\u0001\u0011\u0003\u0011\u00ed\b" +
      "\u0011\u0001\u0011\u0001\u0011\u0003\u0011\u00f1\b\u0011\u0001\u0011\u0001" +
      "\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0013\u0001" +
      "\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001" +
      "\u0014\u0003\u0014\u0101\b\u0014\u0001\u0014\u0001\u0014\u0001\u0015\u0001" +
      "\u0015\u0001\u0015\u0001\u0015\u0005\u0015\u0109\b\u0015\n\u0015\f\u0015" +
      "\u010c\t\u0015\u0003\u0015\u010e\b\u0015\u0001\u0015\u0001\u0015\u0001" +
      "\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0003\u0016\u0116\b\u0016\u0001" +
      "\u0016\u0001\u0016\u0001\u0017\u0001\u0017\u0001\u0017\u0000\u0001\u001c" +
      "\u0018\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018" +
      "\u001a\u001c\u001e \"$&(*,.\u0000\u0004\u0001\u0000\u000b\u000e\u0001" +
      "\u0000\u000f\u0016\u0002\u0000\u0018\u001d,,\u0001\u0000!\"\u012f\u0000" +
      ":\u0001\u0000\u0000\u0000\u0002?\u0001\u0000\u0000\u0000\u0004N\u0001" +
      "\u0000\u0000\u0000\u0006Q\u0001\u0000\u0000\u0000\bZ\u0001\u0000\u0000" +
      "\u0000\ne\u0001\u0000\u0000\u0000\fn\u0001\u0000\u0000\u0000\u000e\u008a" +
      "\u0001\u0000\u0000\u0000\u0010\u008c\u0001\u0000\u0000\u0000\u0012\u0092" +
      "\u0001\u0000\u0000\u0000\u0014\u009c\u0001\u0000\u0000\u0000\u0016\u00a7" +
      "\u0001\u0000\u0000\u0000\u0018\u00af\u0001\u0000\u0000\u0000\u001a\u00b1" +
      "\u0001\u0000\u0000\u0000\u001c\u00c7\u0001\u0000\u0000\u0000\u001e\u00d6" +
      "\u0001\u0000\u0000\u0000 \u00df\u0001\u0000\u0000\u0000\"\u00e5\u0001" +
      "\u0000\u0000\u0000$\u00f5\u0001\u0000\u0000\u0000&\u00f8\u0001\u0000\u0000" +
      "\u0000(\u00fa\u0001\u0000\u0000\u0000*\u0104\u0001\u0000\u0000\u0000," +
      "\u0111\u0001\u0000\u0000\u0000.\u0119\u0001\u0000\u0000\u000009\u0003" +
      "\u0002\u0001\u000012\u0005+\u0000\u000024\u0005\u0001\u0000\u000035\u0003" +
      "\b\u0004\u000043\u0001\u0000\u0000\u000045\u0001\u0000\u0000\u000056\u0001" +
      "\u0000\u0000\u000067\u0005\u0002\u0000\u000079\u0003\n\u0005\u000080\u0001" +
      "\u0000\u0000\u000081\u0001\u0000\u0000\u00009<\u0001\u0000\u0000\u0000" +
      ":8\u0001\u0000\u0000\u0000:;\u0001\u0000\u0000\u0000;=\u0001\u0000\u0000" +
      "\u0000<:\u0001\u0000\u0000\u0000=>\u0005\u0000\u0000\u0001>\u0001\u0001" +
      "\u0000\u0000\u0000?@\u0005#\u0000\u0000@C\u0005,\u0000\u0000AB\u0005$" +
      "\u0000\u0000BD\u0005,\u0000\u0000CA\u0001\u0000\u0000\u0000CD\u0001\u0000" +
      "\u0000\u0000DE\u0001\u0000\u0000\u0000EF\u0005\u0003\u0000\u0000FG\u0003" +
      "\u0004\u0002\u0000GH\u0005\u0004\u0000\u0000H\u0003\u0001\u0000\u0000" +
      "\u0000IM\u0003\u0010\b\u0000JM\u0003\u0006\u0003\u0000KM\u0003\f\u0006" +
      "\u0000LI\u0001\u0000\u0000\u0000LJ\u0001\u0000\u0000\u0000LK\u0001\u0000" +
      "\u0000\u0000MP\u0001\u0000\u0000\u0000NL\u0001\u0000\u0000\u0000NO\u0001" +
      "\u0000\u0000\u0000O\u0005\u0001\u0000\u0000\u0000PN\u0001\u0000\u0000" +
      "\u0000QR\u0003&\u0013\u0000RS\u0005,\u0000\u0000SU\u0005\u0001\u0000\u0000" +
      "TV\u0003\b\u0004\u0000UT\u0001\u0000\u0000\u0000UV\u0001\u0000\u0000\u0000" +
      "VW\u0001\u0000\u0000\u0000WX\u0005\u0002\u0000\u0000XY\u0003\n\u0005\u0000" +
      "Y\u0007\u0001\u0000\u0000\u0000Z[\u0003&\u0013\u0000[b\u0005,\u0000\u0000" +
      "\\]\u0005\u0005\u0000\u0000]^\u0003&\u0013\u0000^_\u0005,\u0000\u0000" +
      "_a\u0001\u0000\u0000\u0000`\\\u0001\u0000\u0000\u0000ad\u0001\u0000\u0000" +
      "\u0000b`\u0001\u0000\u0000\u0000bc\u0001\u0000\u0000\u0000c\t\u0001\u0000" +
      "\u0000\u0000db\u0001\u0000\u0000\u0000ei\u0005\u0003\u0000\u0000fh\u0003" +
      "\u000e\u0007\u0000gf\u0001\u0000\u0000\u0000hk\u0001\u0000\u0000\u0000" +
      "ig\u0001\u0000\u0000\u0000ij\u0001\u0000\u0000\u0000jl\u0001\u0000\u0000" +
      "\u0000ki\u0001\u0000\u0000\u0000lm\u0005\u0004\u0000\u0000m\u000b\u0001" +
      "\u0000\u0000\u0000no\u0005*\u0000\u0000oq\u0005\u0001\u0000\u0000pr\u0003" +
      "\b\u0004\u0000qp\u0001\u0000\u0000\u0000qr\u0001\u0000\u0000\u0000rs\u0001" +
      "\u0000\u0000\u0000st\u0005\u0002\u0000\u0000tu\u0003\n\u0005\u0000u\r" +
      "\u0001\u0000\u0000\u0000vw\u0003\u0010\b\u0000wx\u0005\u0006\u0000\u0000" +
      "x\u008b\u0001\u0000\u0000\u0000yz\u0003\u0012\t\u0000z{\u0005\u0006\u0000" +
      "\u0000{\u008b\u0001\u0000\u0000\u0000|}\u0003\u0014\n\u0000}~\u0005\u0006" +
      "\u0000\u0000~\u008b\u0001\u0000\u0000\u0000\u007f\u0080\u0003$\u0012\u0000" +
      "\u0080\u0081\u0005\u0006\u0000\u0000\u0081\u008b\u0001\u0000\u0000\u0000" +
      "\u0082\u008b\u0003\n\u0005\u0000\u0083\u008b\u0003\u001e\u000f\u0000\u0084" +
      "\u008b\u0003 \u0010\u0000\u0085\u008b\u0003\"\u0011\u0000\u0086\u008b" +
      "\u0003.\u0017\u0000\u0087\u0088\u0003\u001c\u000e\u0000\u0088\u0089\u0005" +
      "\u0006\u0000\u0000\u0089\u008b\u0001\u0000\u0000\u0000\u008av\u0001\u0000" +
      "\u0000\u0000\u008ay\u0001\u0000\u0000\u0000\u008a|\u0001\u0000\u0000\u0000" +
      "\u008a\u007f\u0001\u0000\u0000\u0000\u008a\u0082\u0001\u0000\u0000\u0000" +
      "\u008a\u0083\u0001\u0000\u0000\u0000\u008a\u0084\u0001\u0000\u0000\u0000" +
      "\u008a\u0085\u0001\u0000\u0000\u0000\u008a\u0086\u0001\u0000\u0000\u0000" +
      "\u008a\u0087\u0001\u0000\u0000\u0000\u008b\u000f\u0001\u0000\u0000\u0000" +
      "\u008c\u008d\u0003&\u0013\u0000\u008d\u0090\u0005,\u0000\u0000\u008e\u008f" +
      "\u0005\u0007\u0000\u0000\u008f\u0091\u0003\u001c\u000e\u0000\u0090\u008e" +
      "\u0001\u0000\u0000\u0000\u0090\u0091\u0001\u0000\u0000\u0000\u0091\u0011" +
      "\u0001\u0000\u0000\u0000\u0092\u0097\u0005,\u0000\u0000\u0093\u0094\u0005" +
      "\b\u0000\u0000\u0094\u0095\u0003\u001c\u000e\u0000\u0095\u0096\u0005\t" +
      "\u0000\u0000\u0096\u0098\u0001\u0000\u0000\u0000\u0097\u0093\u0001\u0000" +
      "\u0000\u0000\u0097\u0098\u0001\u0000\u0000\u0000\u0098\u0099\u0001\u0000" +
      "\u0000\u0000\u0099\u009a\u0005\u0007\u0000\u0000\u009a\u009b\u0003\u001c" +
      "\u000e\u0000\u009b\u0013\u0001\u0000\u0000\u0000\u009c\u009f\u0005,\u0000" +
      "\u0000\u009d\u009e\u0005\n\u0000\u0000\u009e\u00a0\u0005,\u0000\u0000" +
      "\u009f\u009d\u0001\u0000\u0000\u0000\u009f\u00a0\u0001\u0000\u0000\u0000" +
      "\u00a0\u00a1\u0001\u0000\u0000\u0000\u00a1\u00a3\u0005\u0001\u0000\u0000" +
      "\u00a2\u00a4\u0003\u0016\u000b\u0000\u00a3\u00a2\u0001\u0000\u0000\u0000" +
      "\u00a3\u00a4\u0001\u0000\u0000\u0000\u00a4\u00a5\u0001\u0000\u0000\u0000" +
      "\u00a5\u00a6\u0005\u0002\u0000\u0000\u00a6\u0015\u0001\u0000\u0000\u0000" +
      "\u00a7\u00ac\u0003\u001c\u000e\u0000\u00a8\u00a9\u0005\u0005\u0000\u0000" +
      "\u00a9\u00ab\u0003\u001c\u000e\u0000\u00aa\u00a8\u0001\u0000\u0000\u0000" +
      "\u00ab\u00ae\u0001\u0000\u0000\u0000\u00ac\u00aa\u0001\u0000\u0000\u0000" +
      "\u00ac\u00ad\u0001\u0000\u0000\u0000\u00ad\u0017\u0001\u0000\u0000\u0000" +
      "\u00ae\u00ac\u0001\u0000\u0000\u0000\u00af\u00b0\u0007\u0000\u0000\u0000" +
      "\u00b0\u0019\u0001\u0000\u0000\u0000\u00b1\u00b2\u0007\u0001\u0000\u0000" +
      "\u00b2\u001b\u0001\u0000\u0000\u0000\u00b3\u00b4\u0006\u000e\uffff\uffff" +
      "\u0000\u00b4\u00b5\u0005\u0017\u0000\u0000\u00b5\u00c8\u0003\u001c\u000e" +
      "\t\u00b6\u00bb\u0005,\u0000\u0000\u00b7\u00b8\u0005\b\u0000\u0000\u00b8" +
      "\u00b9\u0003\u001c\u000e\u0000\u00b9\u00ba\u0005\t\u0000\u0000\u00ba\u00bc" +
      "\u0001\u0000\u0000\u0000\u00bb\u00b7\u0001\u0000\u0000\u0000\u00bb\u00bc" +
      "\u0001\u0000\u0000\u0000\u00bc\u00c8\u0001\u0000\u0000\u0000\u00bd\u00c8" +
      "\u0005-\u0000\u0000\u00be\u00c8\u00050\u0000\u0000\u00bf\u00c8\u00051" +
      "\u0000\u0000\u00c0\u00c8\u0003\u0014\n\u0000\u00c1\u00c2\u0005\u0001\u0000" +
      "\u0000\u00c2\u00c3\u0003\u001c\u000e\u0000\u00c3\u00c4\u0005\u0002\u0000" +
      "\u0000\u00c4\u00c8\u0001\u0000\u0000\u0000\u00c5\u00c8\u0005,\u0000\u0000" +
      "\u00c6\u00c8\u0003,\u0016\u0000\u00c7\u00b3\u0001\u0000\u0000\u0000\u00c7" +
      "\u00b6\u0001\u0000\u0000\u0000\u00c7\u00bd\u0001\u0000\u0000\u0000\u00c7" +
      "\u00be\u0001\u0000\u0000\u0000\u00c7\u00bf\u0001\u0000\u0000\u0000\u00c7" +
      "\u00c0\u0001\u0000\u0000\u0000\u00c7\u00c1\u0001\u0000\u0000\u0000\u00c7" +
      "\u00c5\u0001\u0000\u0000\u0000\u00c7\u00c6\u0001\u0000\u0000\u0000\u00c8" +
      "\u00d3\u0001\u0000\u0000\u0000\u00c9\u00ca\n\u000b\u0000\u0000\u00ca\u00cb" +
      "\u0003\u0018\f\u0000\u00cb\u00cc\u0003\u001c\u000e\f\u00cc\u00d2\u0001" +
      "\u0000\u0000\u0000\u00cd\u00ce\n\n\u0000\u0000\u00ce\u00cf\u0003\u001a" +
      "\r\u0000\u00cf\u00d0\u0003\u001c\u000e\u000b\u00d0\u00d2\u0001\u0000\u0000" +
      "\u0000\u00d1\u00c9\u0001\u0000\u0000\u0000\u00d1\u00cd\u0001\u0000\u0000" +
      "\u0000\u00d2\u00d5\u0001\u0000\u0000\u0000\u00d3\u00d1\u0001\u0000\u0000" +
      "\u0000\u00d3\u00d4\u0001\u0000\u0000\u0000\u00d4\u001d\u0001\u0000\u0000" +
      "\u0000\u00d5\u00d3\u0001\u0000\u0000\u0000\u00d6\u00d7\u0005%\u0000\u0000" +
      "\u00d7\u00d8\u0005\u0001\u0000\u0000\u00d8\u00d9\u0003\u001c\u000e\u0000" +
      "\u00d9\u00da\u0005\u0002\u0000\u0000\u00da\u00dd\u0003\n\u0005\u0000\u00db" +
      "\u00dc\u0005&\u0000\u0000\u00dc\u00de\u0003\n\u0005\u0000\u00dd\u00db" +
      "\u0001\u0000\u0000\u0000\u00dd\u00de\u0001\u0000\u0000\u0000\u00de\u001f" +
      "\u0001\u0000\u0000\u0000\u00df\u00e0\u0005\'\u0000\u0000\u00e0\u00e1\u0005" +
      "\u0001\u0000\u0000\u00e1\u00e2\u0003\u001c\u000e\u0000\u00e2\u00e3\u0005" +
      "\u0002\u0000\u0000\u00e3\u00e4\u0003\n\u0005\u0000\u00e4!\u0001\u0000" +
      "\u0000\u0000\u00e5\u00e6\u0005(\u0000\u0000\u00e6\u00e8\u0005\u0001\u0000" +
      "\u0000\u00e7\u00e9\u0003\u0010\b\u0000\u00e8\u00e7\u0001\u0000\u0000\u0000" +
      "\u00e8\u00e9\u0001\u0000\u0000\u0000\u00e9\u00ea\u0001\u0000\u0000\u0000" +
      "\u00ea\u00ec\u0005\u0006\u0000\u0000\u00eb\u00ed\u0003\u001c\u000e\u0000" +
      "\u00ec\u00eb\u0001\u0000\u0000\u0000\u00ec\u00ed\u0001\u0000\u0000\u0000" +
      "\u00ed\u00ee\u0001\u0000\u0000\u0000\u00ee\u00f0\u0005\u0006\u0000\u0000" +
      "\u00ef\u00f1\u0003\u0012\t\u0000\u00f0\u00ef\u0001\u0000\u0000\u0000\u00f0" +
      "\u00f1\u0001\u0000\u0000\u0000\u00f1\u00f2\u0001\u0000\u0000\u0000\u00f2" +
      "\u00f3\u0005\u0002\u0000\u0000\u00f3\u00f4\u0003\n\u0005\u0000\u00f4#" +
      "\u0001\u0000\u0000\u0000\u00f5\u00f6\u0005)\u0000\u0000\u00f6\u00f7\u0003" +
      "\u001c\u000e\u0000\u00f7%\u0001\u0000\u0000\u0000\u00f8\u00f9\u0007\u0002" +
      "\u0000\u0000\u00f9\'\u0001\u0000\u0000\u0000\u00fa\u00fb\u0003&\u0013" +
      "\u0000\u00fb\u00fc\u0005\b\u0000\u0000\u00fc\u00fd\u0005\t\u0000\u0000" +
      "\u00fd\u0100\u0005,\u0000\u0000\u00fe\u00ff\u0005\u0007\u0000\u0000\u00ff" +
      "\u0101\u0003*\u0015\u0000\u0100\u00fe\u0001\u0000\u0000\u0000\u0100\u0101" +
      "\u0001\u0000\u0000\u0000\u0101\u0102\u0001\u0000\u0000\u0000\u0102\u0103" +
      "\u0005\u0006\u0000\u0000\u0103)\u0001\u0000\u0000\u0000\u0104\u010d\u0005" +
      "\b\u0000\u0000\u0105\u010a\u0003\u001c\u000e\u0000\u0106\u0107\u0005\u0005" +
      "\u0000\u0000\u0107\u0109\u0003\u001c\u000e\u0000\u0108\u0106\u0001\u0000" +
      "\u0000\u0000\u0109\u010c\u0001\u0000\u0000\u0000\u010a\u0108\u0001\u0000" +
      "\u0000\u0000\u010a\u010b\u0001\u0000\u0000\u0000\u010b\u010e\u0001\u0000" +
      "\u0000\u0000\u010c\u010a\u0001\u0000\u0000\u0000\u010d\u0105\u0001\u0000" +
      "\u0000\u0000\u010d\u010e\u0001\u0000\u0000\u0000\u010e\u010f\u0001\u0000" +
      "\u0000\u0000\u010f\u0110\u0005\t\u0000\u0000\u0110+\u0001\u0000\u0000" +
      "\u0000\u0111\u0112\u0005\u001e\u0000\u0000\u0112\u0113\u0005,\u0000\u0000" +
      "\u0113\u0115\u0005\u0001\u0000\u0000\u0114\u0116\u0003\u0016\u000b\u0000" +
      "\u0115\u0114\u0001\u0000\u0000\u0000\u0115\u0116\u0001\u0000\u0000\u0000" +
      "\u0116\u0117\u0001\u0000\u0000\u0000\u0117\u0118\u0005\u0002\u0000\u0000" +
      "\u0118-\u0001\u0000\u0000\u0000\u0119\u011a\u0007\u0003\u0000\u0000\u011a" +
      "/\u0001\u0000\u0000\u0000\u001c48:CLNUbiq\u008a\u0090\u0097\u009f\u00a3" +
      "\u00ac\u00bb\u00c7\u00d1\u00d3\u00dd\u00e8\u00ec\u00f0\u0100\u010a\u010d" +
      "\u0115";
  public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());
  static {
    _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
    for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
      _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
    }
  }
}