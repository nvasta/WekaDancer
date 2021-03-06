/* Generated By:JavaCC: Do not edit this line. J48Parser.java */
package wekadancer.parsers;

import org.dancer.PandaLib.*;

public class J48Parser implements J48ParserConstants {

	public Group group;
	public String device;
	public int PIDCount = 1;

	public J48Parser(String device, java.io.InputStream stream) {
		this.device = device;
		group = new Group("Disc_"+this.device, "0");
		jj_input_stream = new SimpleCharStream(stream, 1, 1);
		token_source = new J48ParserTokenManager(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < jj_la1.length; i++)
			jj_la1[i] = -1;
	}

	public J48Parser(String device, java.io.Reader stream) {
		this.device = device;
		group = new Group("Disc_"+this.device, "0");
		jj_input_stream = new SimpleCharStream(stream, 1, 1);
		token_source = new J48ParserTokenManager(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < jj_la1.length; i++)
			jj_la1[i] = -1;
	}

	final public void RuleSet() throws ParseException {
		Condition ci = null;
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case THEN:
			jj_consume_token(THEN);
			Function();
			break;
		case 0:
		case ID:
			label_1:
				while (true) {
					switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
					case ID:
						;
						break;
					default:
						jj_la1[0] = jj_gen;
						break label_1;
					}
					Rule(ci);
				}
		jj_consume_token(0);
		break;
		default:
			jj_la1[1] = jj_gen;
			jj_consume_token(-1);
			throw new ParseException();
		}
	}

	final public void Rule(ConditionItem ci) throws ParseException {
		Policy policy = null;
		Condition condition =  null;
		PolicyAction a = null;
		condition = Condition();
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case THEN:
			jj_consume_token(THEN);
			a = Function();
			policy = new Policy();
			policy.setPID(String.format("PID%04d", PIDCount++));
			//Copy the previous conditions
			if(ci instanceof ConditionGroup) {
				policy.setConditions(new ConditionGroup((ConditionGroup)ci));
			}
			else {
				if(ci!=null) {
					ConditionGroup cg = new ConditionGroup();
					cg.addCondition(ci);
					policy.setConditions(cg);
				}
			}
			//Add the new condition
			if(policy.getConditions()!=null)
				policy.getConditions().addCondition(condition);
			else
				policy.setConditions(condition);
			//Add the action in the policy
			policy.setAction(a);
			//Add the policy in the group
			group.addPolicy(policy);
			break;
		case CHILD:
			label_2:
				while (true) {
					label_3:
						while (true) {
							jj_consume_token(CHILD);
							switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
							case CHILD:
								;
								break;
							default:
								jj_la1[2] = jj_gen;
								break label_3;
							}
						}
		ConditionItem new_ci;
		if(ci instanceof ConditionGroup) {
			new_ci = new ConditionGroup((ConditionGroup)ci);
			((ConditionGroup)new_ci).addCondition(condition);
			((ConditionGroup)new_ci).setConjunction("AND");
		}
		else {
			if(ci==null) {
				new_ci = condition;
			}
			else {
				new_ci = new ConditionGroup();
				((ConditionGroup)new_ci).addCondition((Condition)ci);
				((ConditionGroup)new_ci).addCondition(condition);
				((ConditionGroup)new_ci).setConjunction("AND");
			}
		}
		Rule(new_ci);
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case CHILD:
			;
			break;
		default:
			jj_la1[3] = jj_gen;
			break label_2;
		}
				}
		break;
		default:
			jj_la1[4] = jj_gen;
			jj_consume_token(-1);
			throw new ParseException();
		}
	}

	final public Condition Condition() throws ParseException {
		Condition c;
		Token id;
		Token opp;
		Token v;
		id = jj_consume_token(ID);
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case GT:
			opp = jj_consume_token(GT);
			break;
		case LT:
			opp = jj_consume_token(LT);
			break;
		case EQ:
			opp = jj_consume_token(EQ);
			break;
		case LE:
			opp = jj_consume_token(LE);
			break;
		case GE:
			opp = jj_consume_token(GE);
			break;
		case NE:
			opp = jj_consume_token(NE);
			break;
		default:
			jj_la1[5] = jj_gen;
			jj_consume_token(-1);
			throw new ParseException();
		}
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case FLOAT:
			v = jj_consume_token(FLOAT);
			break;
		case INT:
			v = jj_consume_token(INT);
			break;
		case ID:
			v = jj_consume_token(ID);
			break;
		default:
			jj_la1[6] = jj_gen;
			jj_consume_token(-1);
			throw new ParseException();
		}
		c = new Condition(id.image,opp.image,v.image);
		{if (true) return c;}
		throw new Error("Missing return statement in function");
	}

	final public PolicyAction Function() throws ParseException {
		Token f;
		f = jj_consume_token(FUNCTION);
		Success();
		//Create a new action
		PolicyAction action = new PolicyAction(this.device,"Unknown",f.image,"");
		{if (true) return action;}
		throw new Error("Missing return statement in function");
	}

	final public void Success() throws ParseException {
		Token t;
		Token p;
		jj_consume_token(OPR);
		t = jj_consume_token(FLOAT);
		label_4:
			while (true) {
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case DIV:
					;
					break;
				default:
					jj_la1[7] = jj_gen;
					break label_4;
				}
				jj_consume_token(DIV);
				p = jj_consume_token(FLOAT);
			}
		jj_consume_token(CPR);
	}

	/** Generated Token Manager. */
	public J48ParserTokenManager token_source;
	SimpleCharStream jj_input_stream;
	/** Current token. */
	public Token token;
	/** Next token. */
	public Token jj_nt;
	private int jj_ntk;
	private int jj_gen;
	final private int[] jj_la1 = new int[8];
	static private int[] jj_la1_0;
	static {
		jj_la1_init_0();
	}
	private static void jj_la1_init_0() {
		jj_la1_0 = new int[] {0x200000,0x200081,0x40,0x40,0xc0,0x7e000,0xa80000,0x1000,};
	}

	/** Constructor with InputStream. */
	public J48Parser(java.io.InputStream stream) {
		this(stream, null);
	}
	/** Constructor with InputStream and supplied encoding */
	public J48Parser(java.io.InputStream stream, String encoding) {
		try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
		token_source = new J48ParserTokenManager(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 8; i++) jj_la1[i] = -1;
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
		for (int i = 0; i < 8; i++) jj_la1[i] = -1;
	}

	/** Constructor. */
	public J48Parser(java.io.Reader stream) {
		jj_input_stream = new SimpleCharStream(stream, 1, 1);
		token_source = new J48ParserTokenManager(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 8; i++) jj_la1[i] = -1;
	}

	/** Reinitialise. */
	public void ReInit(java.io.Reader stream) {
		jj_input_stream.ReInit(stream, 1, 1);
		token_source.ReInit(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 8; i++) jj_la1[i] = -1;
	}

	/** Constructor with generated Token Manager. */
	public J48Parser(J48ParserTokenManager tm) {
		token_source = tm;
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 8; i++) jj_la1[i] = -1;
	}

	/** Reinitialise. */
	public void ReInit(J48ParserTokenManager tm) {
		token_source = tm;
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 8; i++) jj_la1[i] = -1;
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
		boolean[] la1tokens = new boolean[26];
		if (jj_kind >= 0) {
			la1tokens[jj_kind] = true;
			jj_kind = -1;
		}
		for (int i = 0; i < 8; i++) {
			if (jj_la1[i] == jj_gen) {
				for (int j = 0; j < 32; j++) {
					if ((jj_la1_0[i] & (1<<j)) != 0) {
						la1tokens[j] = true;
					}
				}
			}
		}
		for (int i = 0; i < 26; i++) {
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
