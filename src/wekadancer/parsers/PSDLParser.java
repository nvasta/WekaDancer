/* Generated By:JavaCC: Do not edit this line. PSDLParser.java */
        package wekadancer.parsers;

        import org.dancer.PandaLib.*;


        public class PSDLParser implements PSDLParserConstants {

        public PervasiveService curPS;

        PSDLParser(PervasiveService parent, java.io.InputStream stream) {
                curPS = parent;
                jj_input_stream = new SimpleCharStream(stream, 1, 1);
                token_source = new PSDLParserTokenManager(jj_input_stream);
                token = new Token();
                jj_ntk = -1;
                jj_gen = 0;
                for (int i = 0; i < jj_la1.length; i++)
                        jj_la1[i] = -1;
        }

        public PSDLParser(PervasiveService parent, java.io.Reader stream) {
                curPS = parent ;
                jj_input_stream = new SimpleCharStream(stream, 1, 1);
                token_source = new PSDLParserTokenManager(jj_input_stream);
                token = new Token();
                jj_ntk = -1;
                jj_gen = 0;
                for (int i = 0; i < jj_la1.length; i++)
                        jj_la1[i] = -1;
        }

//TOKEN : { < FLOATING_POINT_LITERAL : "." > }
  final public void PolicyBlock() throws ParseException {
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PS:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      jj_consume_token(PS);
      ProgramName();
      ProgramBody();
      jj_consume_token(ENDPS);
    }
    jj_consume_token(0);
  }

  final public void ProgramName() throws ParseException {
        Token t;
    t = jj_consume_token(ID);
          curPS.setName(t.image);
  }

  final public void ProgramBody() throws ParseException {
    ControlInfo();
    ReasoningInfo();
  }

  final public void ControlInfo() throws ParseException {
        Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ID:
      t = jj_consume_token(ID);
      break;
    case INT:
      t = jj_consume_token(INT);
      break;
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void ReasoningInfo() throws ParseException {
    GroupSet();
  }

  final public void GroupSet() throws ParseException {
    label_2:
    while (true) {
      Group();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case GROUP:
        ;
        break;
      default:
        jj_la1[2] = jj_gen;
        break label_2;
      }
    }
  }

  final public void Group() throws ParseException {
    jj_consume_token(GROUP);
    GroupName();
    GroupBody();
  }

  final public void GroupName() throws ParseException {
        Token t;
        Token p;
    t = jj_consume_token(ID);
    p = GroupPriority();
                Group newGroup = new Group(t.image, p.image);
                curPS.addGroup(newGroup);
                curPS.setCurGroup(curPS.getCurGroup()+1);
  }

  final public Token GroupPriority() throws ParseException {
        Token t;
    t = jj_consume_token(INT);
          {if (true) return t;}
    throw new Error("Missing return statement in function");
  }

  final public void GroupBody() throws ParseException {
    jj_consume_token(BEGIN);
    policyGroup();
    jj_consume_token(END);
  }

  final public void policyGroup() throws ParseException {
    label_3:
    while (true) {
      Policy();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ID:
        ;
        break;
      default:
        jj_la1[3] = jj_gen;
        break label_3;
      }
    }
  }

  final public void Policy() throws ParseException {
        Policy p;
    p = PolicyID();
    jj_consume_token(30);
    PolicyBody(p);
    jj_consume_token(31);
  }

  final public Policy PolicyID() throws ParseException {
        Token t;
        Policy newPolicy;
    t = jj_consume_token(ID);
                // -------- Add the new policy in the pervasive service -------- //
    newPolicy = new Policy();
    newPolicy.setPID(t.image);
    curPS.setMaxPID(t.image);
    {if (true) return newPolicy;}
    throw new Error("Missing return statement in function");
  }

  final public void PolicyBody(Policy p) throws ParseException {
    Expression(p);
  }

  final public void Expression(Policy p) throws ParseException {
    IFStatement(p);
  }

  final public void IFStatement(Policy p) throws ParseException {
    jj_consume_token(IF);
    PolicyCondition(p);
    jj_consume_token(THEN);
    PolicyAction(p);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INT:
      Priority(p);
      break;
    default:
      jj_la1[4] = jj_gen;
      ;
    }
                curPS.getGroup(curPS.getCurGroup()).addPolicy(p);
                curPS.setCurPolicy(curPS.getCurPolicy()+1);
  }

  final public void PolicyCondition(Policy p) throws ParseException {
                // Create new Root Condition Group
                ConditionItem curItem = new ConditionGroup();
    curItem.setID(0);
    ConditionGroup(curItem);
                // Add it to the policy description
                p.setConditions(curItem);
  }

  final public void ConditionGroup(ConditionItem topItem) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case OBR:
      jj_consume_token(OBR);
                        // Create new Condition Group
                        ConditionItem cg = new ConditionGroup();
      ConditionGroup(cg);
                        // Add it to the policy description
                        topItem.addCondition(cg);
      jj_consume_token(CBR);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case AND:
      case OR:
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case AND:
          jj_consume_token(AND);
                 ((ConditionGroup)topItem).setConjunction("AND");
          ConditionGroup(topItem);
          break;
        case OR:
          jj_consume_token(OR);
                   ((ConditionGroup)topItem).setConjunction("OR");
          ConditionGroup(topItem);
          break;
        default:
          jj_la1[5] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        break;
      default:
        jj_la1[6] = jj_gen;
        ;
      }
      break;
    case INT:
    case HEX:
    case ID:
    case BOOL:
    case STRING:
      Condition(topItem);
      break;
    default:
      jj_la1[7] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void Condition(ConditionItem topItem) throws ParseException {
                // Create new
        ConditionItem curItem = new Condition();
    CondLiteral(curItem,0);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case GT:
      jj_consume_token(GT);
        ((Condition)curItem).setOperator(">");
      break;
    case LT:
      jj_consume_token(LT);
        ((Condition)curItem).setOperator("<");
      break;
    case EQ:
      jj_consume_token(EQ);
        ((Condition)curItem).setOperator("==");
      break;
    case LE:
      jj_consume_token(LE);
        ((Condition)curItem).setOperator("<=");
      break;
    case GE:
      jj_consume_token(GE);
        ((Condition)curItem).setOperator(">=");
      break;
    case NE:
      jj_consume_token(NE);
           ((Condition)curItem).setOperator("!=");
      break;
    default:
      jj_la1[8] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    CondLiteral(curItem,1);
        // Add it in the condition Group
        topItem.addCondition(curItem);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case AND:
    case OR:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case AND:
        jj_consume_token(AND);
         ((ConditionGroup)topItem).setConjunction("AND");
        ConditionGroup(topItem);
        break;
      case OR:
        jj_consume_token(OR);
         ((ConditionGroup)topItem).setConjunction("OR");
        ConditionGroup(topItem);
        break;
      default:
        jj_la1[9] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    default:
      jj_la1[10] = jj_gen;
      ;
    }
  }

  final public void CondLiteral(ConditionItem curItem,int type) throws ParseException {
        Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INT:
    case HEX:
    case BOOL:
    case STRING:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case INT:
        t = jj_consume_token(INT);
        break;
      case BOOL:
        t = jj_consume_token(BOOL);
        break;
      case STRING:
        t = jj_consume_token(STRING);
        break;
      case HEX:
        t = jj_consume_token(HEX);
        break;
      default:
        jj_la1[11] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                if (type == 0)
                        ((Condition)curItem).setVarName(t.image);
                else
                        ((Condition)curItem).setValue(t.image);
      break;
    case ID:
      t = jj_consume_token(ID);
                if (type == 0)
                        ((Condition)curItem).setVarName(t.image);
                else
                        ((Condition)curItem).setValue(t.image);
      break;
    default:
      jj_la1[12] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void PolicyAction(Policy p) throws ParseException {
        Token t;
    t = jj_consume_token(COMPONENT_FUNCTION);
         p.setAction(t.image);
  }

  final public void Priority(Policy p) throws ParseException {
        Token t;
    t = jj_consume_token(INT);
         p.setPriority(t.image);
  }

  /** Generated Token Manager. */
  public PSDLParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[13];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x40,0x2800000,0x100,0x2000000,0x800000,0x6000,0x6000,0x17808000,0x7e0000,0x6000,0x6000,0x15800000,0x17800000,};
   }

  /** Constructor with InputStream. */
  public PSDLParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public PSDLParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new PSDLParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public PSDLParser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new PSDLParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public PSDLParser(PSDLParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(PSDLParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[32];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 13; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 32; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

        }
