/* Generated By:JavaCC: Do not edit this line. J48ParserTokenManager.java */
package wekadancer.parsers;
import PandaLib.*;

/** Token Manager. */
public class J48ParserTokenManager implements J48ParserConstants
{

	/** Debug output. */
	public  java.io.PrintStream debugStream = System.out;
	/** Set debug output. */
	public  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
	private final int jjStopStringLiteralDfa_0(int pos, long active0)
	{
		switch (pos)
		{
		default :
			return -1;
		}
	}
	private final int jjStartNfa_0(int pos, long active0)
	{
		return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
	}
	private int jjStopAtPos(int pos, int kind)
	{
		jjmatchedKind = kind;
		jjmatchedPos = pos;
		return pos + 1;
	}
	private int jjMoveStringLiteralDfa0_0()
	{
		switch(curChar)
		{
		case 33:
			return jjMoveStringLiteralDfa1_0(0x40000L);
		case 40:
			return jjStopAtPos(0, 10);
		case 41:
			return jjStopAtPos(0, 11);
		case 47:
			return jjStopAtPos(0, 12);
		case 60:
			jjmatchedKind = 14;
			return jjMoveStringLiteralDfa1_0(0x10000L);
		case 62:
			jjmatchedKind = 13;
			return jjMoveStringLiteralDfa1_0(0x20000L);
		case 124:
			return jjStopAtPos(0, 6);
		default :
			return jjMoveNfa_0(0, 0);
		}
	}
	private int jjMoveStringLiteralDfa1_0(long active0)
	{
		try { curChar = input_stream.readChar(); }
		catch(java.io.IOException e) {
			jjStopStringLiteralDfa_0(0, active0);
			return 1;
		}
		switch(curChar)
		{
		case 61:
			if ((active0 & 0x10000L) != 0L)
				return jjStopAtPos(1, 16);
			else if ((active0 & 0x20000L) != 0L)
				return jjStopAtPos(1, 17);
			else if ((active0 & 0x40000L) != 0L)
				return jjStopAtPos(1, 18);
			break;
		default :
			break;
		}
		return jjStartNfa_0(0, active0);
	}
	private int jjMoveNfa_0(int startState, int curPos)
	{
		int startsAt = 0;
		jjnewStateCnt = 83;
		int i = 1;
		jjstateSet[0] = startState;
		int kind = 0x7fffffff;
		for (;;)
		{
			if (++jjround == 0x7fffffff)
				ReInitRounds();
			if (curChar < 64)
			{
				long l = 1L << curChar;
				do
				{
					switch(jjstateSet[--i])
					{
					case 0:
						if ((0x3ff000000000000L & l) != 0L)
							jjCheckNAddStates(0, 3);
						else if (curChar == 45)
							jjCheckNAddTwoStates(13, 31);
						else if (curChar == 61)
							jjAddStates(4, 5);
						else if (curChar == 34)
							jjCheckNAddTwoStates(39, 40);
						else if (curChar == 58)
						{
							if (kind > 7)
								kind = 7;
						}
						if ((0x3ff000000000000L & l) != 0L)
						{
							if (kind > 20)
								kind = 20;
							jjCheckNAddStates(6, 9);
						}
						else if (curChar == 61)
						{
							if (kind > 15)
								kind = 15;
						}
						if ((0x3ff000000000000L & l) != 0L)
							jjCheckNAddTwoStates(31, 32);
						if ((0x3fe000000000000L & l) != 0L)
						{
							if (kind > 19)
								kind = 19;
							jjCheckNAdd(14);
						}
						else if (curChar == 48)
						{
							if (kind > 19)
								kind = 19;
						}
						break;
					case 11:
					case 43:
						if (curChar == 61 && kind > 15)
							kind = 15;
						break;
					case 12:
						if (curChar == 48 && kind > 19)
							kind = 19;
						break;
					case 13:
						if ((0x3fe000000000000L & l) == 0L)
							break;
						if (kind > 19)
							kind = 19;
						jjCheckNAdd(14);
						break;
					case 14:
						if ((0x3ff000000000000L & l) == 0L)
							break;
						if (kind > 19)
							kind = 19;
						jjCheckNAdd(14);
						break;
					case 31:
						if ((0x3ff000000000000L & l) != 0L)
							jjCheckNAddTwoStates(31, 32);
						break;
					case 32:
						if (curChar == 46)
							jjCheckNAdd(33);
						break;
					case 33:
						if ((0x3ff000000000000L & l) == 0L)
							break;
						if (kind > 23)
							kind = 23;
						jjCheckNAddStates(10, 12);
						break;
					case 35:
						if (curChar == 45)
							jjCheckNAdd(36);
						break;
					case 36:
						if ((0x3ff000000000000L & l) == 0L)
							break;
						if (kind > 23)
							kind = 23;
						jjCheckNAddTwoStates(36, 37);
						break;
					case 38:
						if (curChar == 34)
							jjCheckNAddTwoStates(39, 40);
						break;
					case 39:
						if ((0x7ff00b000000000L & l) != 0L)
							jjCheckNAddTwoStates(39, 40);
						break;
					case 40:
						if (curChar == 34 && kind > 24)
							kind = 24;
						break;
					case 41:
						if (curChar == 61)
							jjAddStates(4, 5);
						break;
					case 42:
						if (curChar == 62)
							kind = 7;
						break;
					case 44:
						if (curChar == 45)
							jjCheckNAddTwoStates(13, 31);
						break;
					case 45:
						if ((0x3ff000000000000L & l) == 0L)
							break;
						if (kind > 20)
							kind = 20;
						jjCheckNAddStates(6, 9);
						break;
					case 46:
						if ((0x3ff000000000000L & l) == 0L)
							break;
						if (kind > 20)
							kind = 20;
						jjCheckNAdd(46);
						break;
					case 47:
						if ((0x3ff000000000000L & l) != 0L)
							jjCheckNAddStates(13, 15);
						break;
					case 48:
						if (curChar == 46)
							jjCheckNAddTwoStates(49, 50);
						break;
					case 49:
						if ((0x3ff000000000000L & l) != 0L)
							jjCheckNAddTwoStates(49, 50);
						break;
					case 51:
						if ((0x3ff000000000000L & l) != 0L)
							jjCheckNAddTwoStates(51, 52);
						break;
					case 52:
						if (curChar == 40)
							jjCheckNAddStates(16, 23);
						break;
					case 53:
						if ((0x3ff000000000000L & l) != 0L)
							jjCheckNAddTwoStates(53, 54);
						break;
					case 55:
						if ((0x3ff000000000000L & l) != 0L)
							jjCheckNAddStates(24, 26);
						break;
					case 56:
						if (curChar == 44)
							jjCheckNAddStates(27, 33);
						break;
					case 57:
						if ((0x3ff000000000000L & l) != 0L)
							jjCheckNAddTwoStates(57, 58);
						break;
					case 59:
						if ((0x3ff000000000000L & l) != 0L)
							jjCheckNAddStates(34, 36);
						break;
					case 60:
						if (curChar == 41)
							kind = 25;
						break;
					case 61:
						if (curChar == 34)
							jjCheckNAddTwoStates(62, 63);
						break;
					case 62:
						if ((0x7ff00b000000000L & l) != 0L)
							jjCheckNAddTwoStates(62, 63);
						break;
					case 63:
						if (curChar == 34)
							jjCheckNAddTwoStates(56, 60);
						break;
					case 64:
						if ((0x3ff000000000000L & l) != 0L)
							jjCheckNAddStates(37, 39);
						break;
					case 65:
						if (curChar == 45)
							jjCheckNAdd(66);
						break;
					case 66:
						if ((0x3fe000000000000L & l) != 0L)
							jjCheckNAddStates(40, 42);
						break;
					case 67:
						if ((0x3ff000000000000L & l) != 0L)
							jjCheckNAddStates(40, 42);
						break;
					case 68:
						if (curChar == 48)
							jjCheckNAddTwoStates(56, 60);
						break;
					case 69:
						if (curChar == 34)
							jjCheckNAddTwoStates(70, 63);
						break;
					case 70:
						if ((0x7ff00b000000000L & l) != 0L)
							jjCheckNAddTwoStates(70, 63);
						break;
					case 71:
						if ((0x3ff000000000000L & l) != 0L)
							jjCheckNAddStates(43, 45);
						break;
					case 72:
						if (curChar == 45)
							jjCheckNAdd(73);
						break;
					case 73:
						if ((0x3fe000000000000L & l) != 0L)
							jjCheckNAddStates(46, 48);
						break;
					case 74:
						if ((0x3ff000000000000L & l) != 0L)
							jjCheckNAddStates(46, 48);
						break;
					case 75:
						if ((0x3ff000000000000L & l) != 0L)
							jjCheckNAddStates(0, 3);
						break;
					case 76:
						if ((0x3ff000000000000L & l) != 0L)
							jjCheckNAddTwoStates(76, 77);
						break;
					case 78:
						if ((0x3ff000000000000L & l) == 0L)
							break;
						if (kind > 21)
							kind = 21;
						jjstateSet[jjnewStateCnt++] = 78;
						break;
					case 79:
						if ((0x3ff000000000000L & l) != 0L)
							jjCheckNAddTwoStates(79, 80);
						break;
					case 81:
						if ((0x3ff000000000000L & l) != 0L)
							jjCheckNAddStates(49, 51);
						break;
					default : break;
					}
				} while(i != startsAt);
			}
			else if (curChar < 128)
			{
				long l = 1L << (curChar & 077);
				do
				{
					switch(jjstateSet[--i])
					{
					case 0:
						if ((0x7fffffe87fffffeL & l) != 0L)
						{
							if (kind > 21)
								kind = 21;
							jjCheckNAddStates(52, 55);
						}
						if ((0x7fffffe87fffffeL & l) != 0L)
							jjCheckNAddStates(0, 3);
						if ((0x7eL & l) != 0L)
						{
							if (kind > 20)
								kind = 20;
							jjCheckNAddStates(6, 9);
						}
						else if (curChar == 84)
							jjstateSet[jjnewStateCnt++] = 25;
						else if (curChar == 102)
							jjstateSet[jjnewStateCnt++] = 21;
						else if (curChar == 116)
							jjstateSet[jjnewStateCnt++] = 17;
						else if (curChar == 111)
							jjstateSet[jjnewStateCnt++] = 9;
						else if (curChar == 79)
							jjstateSet[jjnewStateCnt++] = 7;
						else if (curChar == 97)
							jjstateSet[jjnewStateCnt++] = 5;
						if (curChar == 70)
							jjstateSet[jjnewStateCnt++] = 29;
						else if (curChar == 65)
							jjstateSet[jjnewStateCnt++] = 2;
						break;
					case 1:
						if (curChar == 68 && kind > 8)
							kind = 8;
						break;
					case 2:
						if (curChar == 78)
							jjstateSet[jjnewStateCnt++] = 1;
						break;
					case 3:
						if (curChar == 65)
							jjstateSet[jjnewStateCnt++] = 2;
						break;
					case 4:
						if (curChar == 100 && kind > 8)
							kind = 8;
						break;
					case 5:
						if (curChar == 110)
							jjstateSet[jjnewStateCnt++] = 4;
						break;
					case 6:
						if (curChar == 97)
							jjstateSet[jjnewStateCnt++] = 5;
						break;
					case 7:
						if (curChar == 82 && kind > 9)
							kind = 9;
						break;
					case 8:
						if (curChar == 79)
							jjstateSet[jjnewStateCnt++] = 7;
						break;
					case 9:
						if (curChar == 114 && kind > 9)
							kind = 9;
						break;
					case 10:
						if (curChar == 111)
							jjstateSet[jjnewStateCnt++] = 9;
						break;
					case 15:
						if (curChar == 101 && kind > 22)
							kind = 22;
						break;
					case 16:
						if (curChar == 117)
							jjCheckNAdd(15);
						break;
					case 17:
						if (curChar == 114)
							jjstateSet[jjnewStateCnt++] = 16;
						break;
					case 18:
						if (curChar == 116)
							jjstateSet[jjnewStateCnt++] = 17;
						break;
					case 19:
						if (curChar == 115)
							jjCheckNAdd(15);
						break;
					case 20:
						if (curChar == 108)
							jjstateSet[jjnewStateCnt++] = 19;
						break;
					case 21:
						if (curChar == 97)
							jjstateSet[jjnewStateCnt++] = 20;
						break;
					case 22:
						if (curChar == 102)
							jjstateSet[jjnewStateCnt++] = 21;
						break;
					case 23:
						if (curChar == 69 && kind > 22)
							kind = 22;
						break;
					case 24:
						if (curChar == 85)
							jjCheckNAdd(23);
						break;
					case 25:
						if (curChar == 82)
							jjstateSet[jjnewStateCnt++] = 24;
						break;
					case 26:
						if (curChar == 84)
							jjstateSet[jjnewStateCnt++] = 25;
						break;
					case 27:
						if (curChar == 83)
							jjCheckNAdd(23);
						break;
					case 28:
						if (curChar == 76)
							jjstateSet[jjnewStateCnt++] = 27;
						break;
					case 29:
						if (curChar == 65)
							jjstateSet[jjnewStateCnt++] = 28;
						break;
					case 30:
						if (curChar == 70)
							jjstateSet[jjnewStateCnt++] = 29;
						break;
					case 34:
						if (curChar == 69)
							jjAddStates(56, 57);
						break;
					case 37:
						if (curChar == 68 && kind > 23)
							kind = 23;
						break;
					case 39:
						if ((0x7fffffe87fffffeL & l) != 0L)
							jjAddStates(58, 59);
						break;
					case 45:
						if ((0x7eL & l) == 0L)
							break;
						if (kind > 20)
							kind = 20;
						jjCheckNAddStates(6, 9);
						break;
					case 46:
						if ((0x7eL & l) == 0L)
							break;
						if (kind > 20)
							kind = 20;
						jjCheckNAdd(46);
						break;
					case 47:
						if ((0x7eL & l) != 0L)
							jjCheckNAddStates(13, 15);
						break;
					case 49:
						if ((0x7fffffe87fffffeL & l) != 0L)
							jjAddStates(60, 61);
						break;
					case 50:
					case 51:
						if ((0x7fffffe87fffffeL & l) != 0L)
							jjCheckNAddTwoStates(51, 52);
						break;
					case 53:
						if ((0x7fffffe87fffffeL & l) != 0L)
							jjAddStates(62, 63);
						break;
					case 54:
					case 55:
						if ((0x7fffffe87fffffeL & l) != 0L)
							jjCheckNAddStates(24, 26);
						break;
					case 57:
						if ((0x7fffffe87fffffeL & l) != 0L)
							jjAddStates(64, 65);
						break;
					case 58:
					case 59:
						if ((0x7fffffe87fffffeL & l) != 0L)
							jjCheckNAddStates(34, 36);
						break;
					case 62:
						if ((0x7fffffe87fffffeL & l) != 0L)
							jjCheckNAddTwoStates(62, 63);
						break;
					case 64:
						if ((0x7eL & l) != 0L)
							jjCheckNAddStates(37, 39);
						break;
					case 70:
						if ((0x7fffffe87fffffeL & l) != 0L)
							jjCheckNAddTwoStates(70, 63);
						break;
					case 71:
						if ((0x7eL & l) != 0L)
							jjCheckNAddStates(43, 45);
						break;
					case 75:
						if ((0x7fffffe87fffffeL & l) != 0L)
							jjCheckNAddStates(0, 3);
						break;
					case 76:
						if ((0x7fffffe87fffffeL & l) != 0L)
							jjCheckNAddTwoStates(76, 77);
						break;
					case 77:
					case 78:
						if ((0x7fffffe87fffffeL & l) == 0L)
							break;
						if (kind > 21)
							kind = 21;
						jjCheckNAdd(78);
						break;
					case 79:
						if ((0x7fffffe87fffffeL & l) != 0L)
							jjCheckNAddTwoStates(79, 80);
						break;
					case 80:
					case 81:
						if ((0x7fffffe87fffffeL & l) != 0L)
							jjCheckNAddStates(49, 51);
						break;
					case 82:
						if ((0x7fffffe87fffffeL & l) == 0L)
							break;
						if (kind > 21)
							kind = 21;
						jjCheckNAddStates(52, 55);
						break;
					default : break;
					}
				} while(i != startsAt);
			}
			else
			{
				int i2 = (curChar & 0xff) >> 6;
				long l2 = 1L << (curChar & 077);
				do
				{
					switch(jjstateSet[--i])
					{
					default : break;
					}
				} while(i != startsAt);
			}
			if (kind != 0x7fffffff)
			{
				jjmatchedKind = kind;
				jjmatchedPos = curPos;
				kind = 0x7fffffff;
			}
			++curPos;
			if ((i = jjnewStateCnt) == (startsAt = 83 - (jjnewStateCnt = startsAt)))
				return curPos;
			try { curChar = input_stream.readChar(); }
			catch(java.io.IOException e) { return curPos; }
		}
	}
	static final int[] jjnextStates = {
		76, 77, 79, 80, 42, 43, 46, 47, 48, 52, 33, 34, 37, 47, 48, 52, 
		53, 54, 69, 71, 72, 73, 68, 60, 55, 56, 60, 57, 58, 61, 64, 65, 
		66, 68, 56, 59, 60, 56, 64, 60, 56, 67, 60, 71, 56, 60, 74, 56, 
		60, 81, 48, 52, 78, 81, 48, 52, 35, 36, 39, 40, 49, 50, 53, 54, 
		57, 58, 
	};

	/** Token literal values. */
	public static final String[] jjstrLiteralImages = {
		"", null, null, null, null, null, "\174", null, null, null, "\50", "\51", 
		"\57", "\76", "\74", null, "\74\75", "\76\75", "\41\75", null, null, null, null, null, 
		null, null, };

	/** Lexer state names. */
	public static final String[] lexStateNames = {
		"DEFAULT",
	};
	static final long[] jjtoToken = {
		0x3ffffc1L, 
	};
	static final long[] jjtoSkip = {
		0x3eL, 
	};
	protected SimpleCharStream input_stream;
	private final int[] jjrounds = new int[83];
	private final int[] jjstateSet = new int[166];
	protected char curChar;
	/** Constructor. */
	public J48ParserTokenManager(SimpleCharStream stream){
		if (SimpleCharStream.staticFlag)
			throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");
		input_stream = stream;
	}

	/** Constructor. */
	public J48ParserTokenManager(SimpleCharStream stream, int lexState){
		this(stream);
		SwitchTo(lexState);
	}

	/** Reinitialise parser. */
	public void ReInit(SimpleCharStream stream)
	{
		jjmatchedPos = jjnewStateCnt = 0;
		curLexState = defaultLexState;
		input_stream = stream;
		ReInitRounds();
	}
	private void ReInitRounds()
	{
		int i;
		jjround = 0x80000001;
		for (i = 83; i-- > 0;)
			jjrounds[i] = 0x80000000;
	}

	/** Reinitialise parser. */
	public void ReInit(SimpleCharStream stream, int lexState)
	{
		ReInit(stream);
		SwitchTo(lexState);
	}

	/** Switch to specified lex state. */
	public void SwitchTo(int lexState)
	{
		if (lexState >= 1 || lexState < 0)
			throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
		else
			curLexState = lexState;
	}

	protected Token jjFillToken()
	{
		final Token t;
		final String curTokenImage;
		final int beginLine;
		final int endLine;
		final int beginColumn;
		final int endColumn;
		String im = jjstrLiteralImages[jjmatchedKind];
		curTokenImage = (im == null) ? input_stream.GetImage() : im;
		beginLine = input_stream.getBeginLine();
		beginColumn = input_stream.getBeginColumn();
		endLine = input_stream.getEndLine();
		endColumn = input_stream.getEndColumn();
		t = Token.newToken(jjmatchedKind, curTokenImage);

		t.beginLine = beginLine;
		t.endLine = endLine;
		t.beginColumn = beginColumn;
		t.endColumn = endColumn;

		return t;
	}

	int curLexState = 0;
	int defaultLexState = 0;
	int jjnewStateCnt;
	int jjround;
	int jjmatchedPos;
	int jjmatchedKind;

	/** Get the next Token. */
	public Token getNextToken() 
	{
		Token matchedToken;
		int curPos = 0;

		EOFLoop :
			for (;;)
			{
				try
				{
					curChar = input_stream.BeginToken();
				}
				catch(java.io.IOException e)
				{
					jjmatchedKind = 0;
					matchedToken = jjFillToken();
					return matchedToken;
				}

				try { input_stream.backup(0);
				while (curChar <= 32 && (0x100003600L & (1L << curChar)) != 0L)
					curChar = input_stream.BeginToken();
				}
				catch (java.io.IOException e1) { continue EOFLoop; }
				jjmatchedKind = 0x7fffffff;
				jjmatchedPos = 0;
				curPos = jjMoveStringLiteralDfa0_0();
				if (jjmatchedKind != 0x7fffffff)
				{
					if (jjmatchedPos + 1 < curPos)
						input_stream.backup(curPos - jjmatchedPos - 1);
					if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
					{
						matchedToken = jjFillToken();
						return matchedToken;
					}
					else
					{
						continue EOFLoop;
					}
				}
				int error_line = input_stream.getEndLine();
				int error_column = input_stream.getEndColumn();
				String error_after = null;
				boolean EOFSeen = false;
				try { input_stream.readChar(); input_stream.backup(1); }
				catch (java.io.IOException e1) {
					EOFSeen = true;
					error_after = curPos <= 1 ? "" : input_stream.GetImage();
					if (curChar == '\n' || curChar == '\r') {
						error_line++;
						error_column = 0;
					}
					else
						error_column++;
				}
				if (!EOFSeen) {
					input_stream.backup(1);
					error_after = curPos <= 1 ? "" : input_stream.GetImage();
				}
				throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
			}
	}

	private void jjCheckNAdd(int state)
	{
		if (jjrounds[state] != jjround)
		{
			jjstateSet[jjnewStateCnt++] = state;
			jjrounds[state] = jjround;
		}
	}
	private void jjAddStates(int start, int end)
	{
		do {
			jjstateSet[jjnewStateCnt++] = jjnextStates[start];
		} while (start++ != end);
	}
	private void jjCheckNAddTwoStates(int state1, int state2)
	{
		jjCheckNAdd(state1);
		jjCheckNAdd(state2);
	}

	private void jjCheckNAddStates(int start, int end)
	{
		do {
			jjCheckNAdd(jjnextStates[start]);
		} while (start++ != end);
	}

}