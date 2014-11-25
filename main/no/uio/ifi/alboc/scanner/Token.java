package no.uio.ifi.alboc.scanner;

/*
 * class Token
 */

/*
 * The different kinds of tokens read by Scanner.
 */
public enum Token { 
    addToken("+"), ampToken("&"), assignToken("="), 
    commaToken(","), 
    divideToken("/"),
    elseToken("else"), eofToken("eof"), equalToken("=="), 
    forToken("for"), 
    greaterEqualToken(">="), greaterToken(">"), 
    ifToken("if"), intToken("int"), 
    leftBracketToken("["), leftCurlToken("{"), leftParToken("("), lessEqualToken("<="), lessToken("<"), 
    nameToken("name"), notEqualToken("!="), numberToken("number"), 
    returnToken("return"), rightBracketToken("]"), rightCurlToken("}"), rightParToken(")"), 
    semicolonToken(";"), starToken("*"), subtractToken("-"), 
    whileToken("while");


    String tokenValue;
    private Token(String s){
	this.tokenValue = s;
    }

    public static boolean isFactorOperator(Token t) {
	//-- Must be changed in part 0:

	//Modified siddharp
	return (t.equals(divideToken) || t.equals(starToken)) ? true : false ;
	//return false;
	//end Modified
    }

    public static boolean isTermOperator(Token t) {
	//-- Must be changed in part 0:

	//Modified siddharp
	return (t.equals(addToken) || t.equals( subtractToken)) ? true : false ;
	
	//return false;
	//end Modified
    }

    public static boolean isPrefixOperator(Token t) {
	//-- Must be changed in part 0:
	
	//Modified siddharp
	return (t.equals(starToken) || t.equals(subtractToken)) ? true : false;
	//return false;
	//end Modified
    }

    public static boolean isRelOperator(Token t) {
	//-- Must be changed in part 0:
	//Modified siddharp
	return ( t.equals(equalToken) || t.equals(notEqualToken) ||
		 t.equals(lessToken)  || t.equals(lessEqualToken) ||
		 t.equals(greaterToken) || t.equals(greaterEqualToken) ) ? true : false;
	//return false;
	//end Modified
    }

    public static boolean isOperand(Token t) {
	//-- Must be changed in part 0:
	//Modified siddharp
	return (t.equals(nameToken) || t.equals(numberToken) ||
		t.equals(ampToken) || t.equals(leftParToken) ) ? true : false ;
	//return false;
	//end Modified
    }
}
