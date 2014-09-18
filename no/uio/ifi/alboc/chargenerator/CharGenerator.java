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
    
	
    public static void init() {
	try {
	    sourceFile = new LineNumberReader(new FileReader(AlboC.sourceName));
	} catch (FileNotFoundException e) {
	    Error.error("Cannot read " + AlboC.sourceName + "!");
	}
	tempLine = sourceLine = "";  tempNum = sourcePos = 0;  curC = nextC = ' ';
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
	
	//Check sourceLine has more to read? and that sourceLine is not null; Return true if so
	if((sourceLine != null) && (sourceLine.length() > sourcePos)) return true;

	else{

	    try{

		sourcePos = 0;		
		sourceLine = sourceFile.readLine();                      

		//Skip through the comment line and empty lines while printing it
		//in screen and logging it.
		while(sourceLine.equals("") || sourceLine.charAt(0) == '#'){

		    System.out.println(sourceFile.getLineNumber()+":    "+sourceLine);
		    Log.noteSourceLine(sourceFile.getLineNumber(),sourceLine);
		    sourceLine = sourceFile.readLine();
		}
		return true;
	    }catch(Exception e) {
		return false;
	    }

	}
	//end modified
    }
	
    public static int curLineNum() {
	return (sourceFile == null ? 0 : sourceFile.getLineNumber());
    }
	
    public static void readNext() {
	curC = nextC;

	//-- Must be changed in part 0:
	//Modified siddharp

	if( tempLine != null){
	    System.out.println(tempNum+":    "+tempLine);
	    Log.noteSourceLine(tempNum,tempLine);
	    tempLine = null;
	}
	
	if (! isMoreToRead()) return;

	
	if(sourceLine.length() <= 1){
	    tempLine = sourceLine;
	    tempNum = sourceFile.getLineNumber();
	}

	if(sourcePos == 1){
	    Log.noteSourceLine(sourceFile.getLineNumber(),sourceLine);
	    System.out.println(sourceFile.getLineNumber()+":    "+sourceLine);
	}

	nextC = sourceLine.charAt(sourcePos++);
	if(curC == '/' && nextC == '*'){
	    while (! (curC == '*' && nextC == '/')){
		readNext();
	    }
	    readNext(); readNext();
	}
	//end modified
    }
}
