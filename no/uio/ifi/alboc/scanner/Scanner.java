package no.uio.ifi.alboc.scanner;

/*
 * module Scanner
 */

import no.uio.ifi.alboc.chargenerator.CharGenerator;
import no.uio.ifi.alboc.error.Error;
import no.uio.ifi.alboc.log.Log;
import static no.uio.ifi.alboc.scanner.Token.*;

/*
 * Module for forming characters into tokens.
 */
public class Scanner {
    public static Token curToken, nextToken;
    public static String curName, nextName;
    public static int curNum, nextNum;
    public static int curLine, nextLine;
	
    public static void init() {
	//-- Must be changed in part 0:

	//Modified siddharp
	curToken  = nextToken = null;
	curName = nextName = "";
	curNum = nextNum = -1;
	curLine = nextLine = -1;
	//end Modified	
    }
	
    public static void finish() {
	//-- Must be changed in part 0:

	//Modified siddharp
	//end siddharp
    }

    public static void clean(){
	nextName = "";
	nextNum = -1;
    }
    
    public static void readNext() {
	curToken = nextToken;  curName = nextName;  curNum = nextNum;
	curLine = nextLine;

	nextName = "";
	nextNum = -1;
	nextToken = null;
	while (nextToken == null) {
	    nextLine = CharGenerator.curLineNum();
	    //Modified siddharp
	    if (! CharGenerator.isMoreToRead()) {
		ifTokenLog(nextIsToken()); clean();
		ifTokenLog(checkToken()==1);
		CharGenerator.readNext();
		ifTokenLog(checkToken()==1);
		ifTokenLog(nextIsToken());clean();
		nextToken = eofToken;
		ifTokenLog(true);
	    } else {
		int a = checkToken();
		if ( a == 1){
		    Token tmp = nextToken ;
		    nextToken = null;
		    ifTokenLog(nextIsToken());
		    nextToken = tmp;
		    ifTokenLog(true);
		}else if ( a == 2){
		    ifTokenLog(nextIsToken());
		}else if (a==0){
		}else{
		    Error.error(nextLine,
			    "Illegal symbol: '" + CharGenerator.curC + "'!");
		}

	    }
	    	CharGenerator.readNext();
	}

    }
	

    //modified siddharp
    // returns 0 is lettersAZ, number
    // return 1 on tokens
    // return -1 on error
    // return 2 on comment, space tab
    private static int checkToken(){

	if(isLetterAZ(CharGenerator.curC)){
	    nextName = nextName + CharGenerator.curC;
	    return 0;
	}else if (CharGenerator.curC == '\''){
	    nextNum = CharGenerator.nextC;
	    CharGenerator.readNext();
	    if(CharGenerator.nextC == '\''){
		CharGenerator.readNext();
	    }else{
		return -1;
	    }
	    return 2;
	}else if(CharGenerator.curC == ' ' || CharGenerator.curC == '\t'){
	    return 2;
	}else if(Character.isDigit(CharGenerator.curC)){
	    if(!nextName.equals("")){
		nextName = nextName + CharGenerator.curC;
	    }else{
		if(nextNum != -1){
		    String s = Integer.toString(nextNum);
		    s = s + CharGenerator.curC;
		    nextNum = Integer.parseInt(s);
		}else{
		    nextNum = CharGenerator.curC-48;
		}
	    }
	    return 0;
	}else{
	    for (Token t: Token.values()){
		if (t.tokenValue.equals(""+CharGenerator.curC+CharGenerator.nextC)){
		    nextToken = t;
		    CharGenerator.readNext();
		    return 1;
		}else if(t.tokenValue.equals(CharGenerator.curC+"")){
		    nextToken = t;
		    return 1;
		}
	    }
	}
	return -1;
    }
    //end modified

    //Modified siddharp
    private static void ifTokenLog(boolean token){
	if(token)
	    Log.noteToken();
    }
    //end Modified


    //Modified siddharp
    //Check if nextToken is actually a token
    private static boolean nextIsToken(){
	for(Token t : Token.values() ){
	    if(t.name().equals(nextName+"Token")){
		nextToken = t;
		return true;
	    }
	}
	if(! nextName.equals("") ){

	    curName = nextName;
	    nextToken = nameToken ;
	    return true;
	}
	if( nextNum != -1 ){
	    curNum = nextNum ;
	    nextToken = numberToken ;
	    return true;
	}
	return false;
    }
    
	
    private static boolean isLetterAZ(char c) {
	//-- Must be changed in part 0:

	//Modified siddharp

	int a = (int) c;
	return ( (a >= 65 && a <= 90) || ( a >= 97 && a <= 122)) ? true : false ;
	
	//end Modified

    }
	
    public static void check(Token t) {
	if (curToken != t)
	    Error.expected("A " + t);
    }
	
    public static void check(Token t1, Token t2) {
	if (curToken != t1 && curToken != t2)
	    Error.expected("A " + t1 + " or a " + t2);
    }
	
    public static void skip(Token t) {
	check(t);  readNext();
    }
	
    public static void skip(Token t1, Token t2) {
	check(t1,t2);  readNext();
    }
}