package no.uio.ifi.alboc.chargenerator;

/*
 * module CharGenerator
 */

import java.io.*;
import no.uio.ifi.alboc.alboc.AlboC;
import no.uio.ifi.alboc.error.Error;
import no.uio.ifi.alboc.log.Log;

/*
 * Module for reading single characters.
 */
public class CharGenerator {
    public static char curC, nextC;
	
    private static LineNumberReader sourceFile = null;
    private static String sourceLine , tempLine;
    private static int sourcePos, tempNum;
    private static int nextCVal;
    public static int notComment;
    
	
    public static void init() {
	try {
	    sourceFile = new LineNumberReader(new FileReader(AlboC.sourceName));
	} catch (FileNotFoundException e) {
	    Error.error("Cannot read " + AlboC.sourceName + "!");
	}
	tempLine = sourceLine = "";
	tempNum = sourcePos = 0;
	curC = nextC = ' ';
	notComment = 1;
	readNext();  readNext();
    }
	
    public static void finish() {
	if (sourceFile != null) {
	    try {
		sourceFile.close();
	    } catch (IOException e) {
		Error.error("Could not close source file!");
	    }
	}
    }
	
    public static boolean isMoreToRead() {
	//-- Must be changed in part 0:
	//Modified siddharp

	//Checks if thre is more to read.
	return nextCVal != -1;
    }
	
    public static int curLineNum() {
	return (sourceFile == null ? 0 : sourceFile.getLineNumber());
    }
	
    public static void readNext() {
	curC = nextC; 

	if(!isMoreToRead()){
	    return ;
	}

	if( curC == '\n' ){

	    Log.noteSourceLine(curLineNum(), sourceLine);
	    System.out.println(curLineNum()+":   "+sourceLine);
	 
	    sourceLine = "";
	    sourcePos = 0;

	}else{
	    sourceLine = sourceLine + curC;
	    sourcePos ++ ;
	}

	try{
	    nextCVal =  sourceFile.read();
	    nextC = (char) nextCVal ;

	    if( curC == '#' && (notComment == 1) ){
		sourceLine = sourceLine +nextC + sourceFile.readLine() ;
		if(!sourceLine.equals("")){
		    Log.noteSourceLine(curLineNum(), sourceLine);
		    System.out.println(curLineNum()+":   "+sourceLine);		    
		}

		sourceLine = "";
		sourcePos = 0;
		nextCVal =  sourceFile.read();
		nextC = (char) nextCVal ;
		readNext();  
	    }
	}catch (Exception e){
	    System.err.println(e.getMessage());
	}
    }
	
}




