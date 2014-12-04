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
	nextToken = null;
	//end siddharp
    }

    public static void clean(){
	nextName = "";
	nextNum = -1;
    }
    
    public static void readNext() {
	curToken = nextToken;
	curName = nextName;
	curNum = nextNum;
	curLine = nextLine;

	nextName = "";
	nextNum = -1;
	nextToken = null;

	while (nextToken == null) {
	    nextLine = CharGenerator.curLineNum();
	    //Modified siddharp
	    if (! CharGenerator.isMoreToRead()) {

		ifTokenLog(checkToken() == 1);
		CharGenerator.readNext();

		ifTokenLog(checkToken()== 1);

		clean();
		nextToken = eofToken;
		Log.noteToken();
		return ;
	    }

	    int a = checkToken();

	    if(a == 1){
		Log.noteToken();
	    }else if(a == 2){
	    }else{
		Error.error(nextLine,
			    "Illegal symbol: '" + CharGenerator.curC + "'!");
	    }

	    CharGenerator.readNext();	    
	}

    }


    private static boolean checkName(char c){

	if( isLetterAZ(c) || Character.isDigit(c) || c == '_' ){
	    return true;
	}
	return false;
    }

    
    private static boolean checkNamedToken(String n){
	for(Token t : Token.values() ){
	    if(t.name().equals(n+"Token")){
		nextToken = t;
		return true;
	    }
	}
	return false;
    }

    //modified siddharp
    private static int checkToken(){


	if( isLetterAZ(CharGenerator.curC) ){
	    nextName = nextName + CharGenerator.curC;

	    if( checkNamedToken(nextName) ){
		return 1;
	    }

	    while ( checkName(CharGenerator.nextC) ) {
		CharGenerator.readNext();
		nextName += CharGenerator.curC;
		if( checkNamedToken(nextName) ){
		    return 1;
		}
	    }
	    curName = nextName ;
	    nextToken = nameToken;
	    return 1;
	}else if(CharGenerator.curC == '/' && CharGenerator.nextC == '*' ){

	    int commentStart = CharGenerator.curLineNum();
	    CharGenerator.readNext();
	    while(! (CharGenerator.curC == '*' && CharGenerator.nextC == '/' ) ){
		CharGenerator.readNext();

		if(CharGenerator.curC == '/' && CharGenerator.nextC == '*' ) {
		    Error.error(commentStart," comment inside a comment!");
		    Log.noteError("Comment inside a comment in line "+ commentStart+" !");
		}
		
		if(!CharGenerator.isMoreToRead()){
		    Error.error(commentStart," comment never ends!");
		    Log.noteError("Comment never ends in line "+ commentStart+" !");
		}

	    }
	    CharGenerator.readNext();
	    return 2;
	}

	else if (CharGenerator.curC == '\''){
	    CharGenerator.notComment = -1;
	    CharGenerator.readNext();
	    int val = (int) CharGenerator.curC;
	    nextToken = numberToken;
	    nextNum = val;
	    CharGenerator.notComment = -1;	    
	    CharGenerator.readNext();
	    return 1;
	}

	else if(CharGenerator.curC == ' ' || CharGenerator.curC == '\t'|| CharGenerator.curC == '\n' || CharGenerator.curC == '\r'){
	    return 2;
	}else if(Character.isDigit(CharGenerator.curC)){

	    nextNum = CharGenerator.curC-48;

	    while( Character.isDigit(CharGenerator.nextC) ){
		CharGenerator.readNext();
		String s = Integer.toString(nextNum);
		s = s + CharGenerator.curC;
		nextNum = Integer.parseInt(s);
	    }

	    curNum = nextNum ;
	    nextToken = numberToken;
	    return 1;
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



