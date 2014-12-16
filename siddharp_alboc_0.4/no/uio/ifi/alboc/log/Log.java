package no.uio.ifi.alboc.log;

/*
 * module Log
 */

import java.io.*;
import no.uio.ifi.alboc.alboc.AlboC;
import no.uio.ifi.alboc.error.Error;
import no.uio.ifi.alboc.scanner.Scanner;
import static no.uio.ifi.alboc.scanner.Token.*;
import no.uio.ifi.alboc.types.*;

/*
 * Produce logging information.
 */
public class Log {
    public static boolean doLogBinding = false, doLogTypeCheck = false,
	doLogParser = false, doLogScanner = false, doLogTree = false;
	
    private static String logName, curTreeLine = "", curParseLine = "";
    private static int nLogLines = 0, parseLevel = 0, treeLevel = 0;
	
    public static void init() {
	logName = AlboC.sourceBaseName + ".log";
    }
	
    public static void finish() {
	//-- Must be changed in part 0:
    }

    private static void writeLogLine(String data) {
	try {
	    PrintWriter log = (nLogLines==0 ? new PrintWriter(logName) :
		new PrintWriter(new FileOutputStream(logName,true)));
	    log.println(data);  ++nLogLines;
	    log.close();
	} catch (FileNotFoundException e) {
	    nLogLines = 0;  // To avoid infinite recursion
	                    // Error.error -> Log.noteError -> Log.writeLogLine -> ...
	    Error.error("Cannot open log file " + logName + "!");
	}
    }

    /*
     * Make a note in the log file that an error has occured.
     * (If the log file is not in use, request is ignored.)
     *
     * @param message  The error message
     */
    public static void noteError(String message) {
	if (nLogLines > 0) 
	    writeLogLine(message);
    }


    public static void enterParser(String symbol) {
	if (! doLogParser) return;
	//-- Must be changed in part 1:
	parseLevel++;
	printParser(symbol);
    }

    public static void leaveParser(String symbol) {
	if (! doLogParser) return;
	printParser(symbol);
	if(parseLevel  > 0)
	    parseLevel --;
    }

    public static void printParser(String symbol){
	if(curParseLine.length() == 0){
	    for (int i = 1; i <= parseLevel ; ++i){
		curParseLine += "  ";
	    }
	}
	curParseLine += symbol;
	writeLogLine("Parser:     "+curParseLine);
	curParseLine = "";
    }

    /**
     * Make a note in the log file that another source line has been read.
     * This note is only made if the user has requested it.
     *
     * @param lineNum  The line number
     * @param line     The actual line
     */
    public static void noteSourceLine(int lineNum, String line) {
	if (! doLogParser && ! doLogScanner) return;

	//-- Must be changed in part 0:
	//Modified siddharp
	writeLogLine(" "+lineNum+":  "+line);
	//end Modified
    }
	
    /**
     * Make a note in the log file that another token has been read 
     * by the Scanner module into Scanner.nextToken.
     * This note will only be made if the user has requested it.
     */
    public static void noteToken() {
	if (! doLogScanner) return;

	//-- Must be changed in part 0:

	//Modified siddharp
	if(Scanner.nextToken == nameToken ){
	    System.out.println("Scanner:\t"+Scanner.nextToken+" "+Scanner.nextName);
	    writeLogLine("Scanner:\t"+Scanner.nextToken+" "+Scanner.nextName);
	}else if (Scanner.nextToken == numberToken ){
	    System.out.println("Scanner:\t"+Scanner.nextToken+" "+Scanner.nextNum);
	    writeLogLine("Scanner:\t"+Scanner.nextToken+" "+Scanner.nextNum);
	}else{
	    System.out.println("Scanner:\t"+Scanner.nextToken);
	    writeLogLine("Scanner:\t"+Scanner.nextToken);
	}
	//end Modified
    }

    public static void noteBinding(String name, int lineNum, int useLineNum) {
	if (! doLogBinding) return;
	//-- Must be changed in part 2:
    }

    public static void noteTypeCheck(String what, Type t, String s, int lineNum) {
	if (! doLogTypeCheck) return;

	writeLogLine("Checking types: " +
		     (lineNum>0 ? "Line "+lineNum+": " : "") + what +
		     ",\n                where Type(" + s + ") is " + t);
    }

    public static void noteTypeCheck(String what, Type t1, String s1, 
				     Type t2, String s2, int lineNum) {
	if (! doLogTypeCheck) return;
	writeLogLine("Checking types: " +
		     (lineNum>0 ? "Line "+lineNum+": " : "") + what +
		     ",\n                where Type(" + s1 + ") is " + t1 +
		     " and Type(" + s2 + ") is " + t2);
    }

    public static void wTree(String s) {
	if (curTreeLine.length() == 0) {
	    for (int i = 1;  i <= treeLevel;  ++i) curTreeLine += "  ";
	}
	curTreeLine += s;
    }

    public static String stringRepeater(String s, int n){
	String str = "";
	for(int i = 0; i < n ; i++){
	    str += s;
	}
	return s;
    }

    public static void wTreeLn() {
	writeLogLine("Tree:     " + curTreeLine);
	curTreeLine = "";
    }

    public static void wTreeLn(String s) {
	wTree(s);  wTreeLn();
    }

    public static void indentTree() {
	//-- Must be changed in part 1:
	treeLevel++;
    }

    public static void outdentTree() {
	//-- Must be changed in part 1:
	treeLevel--;
    }
}

