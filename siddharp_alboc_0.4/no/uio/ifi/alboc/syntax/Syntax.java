package no.uio.ifi.alboc.syntax;
 
/*
 * module Syntax
 */
 
import no.uio.ifi.alboc.alboc.AlboC;
import no.uio.ifi.alboc.code.Code;
import no.uio.ifi.alboc.error.Error;
import no.uio.ifi.alboc.log.Log;
import no.uio.ifi.alboc.scanner.Scanner;
import no.uio.ifi.alboc.scanner.Token;
import static no.uio.ifi.alboc.scanner.Token.*;
import no.uio.ifi.alboc.types.*;
import java.util.*;
 
/*
 * Creates a syntax tree by parsing an AlboC program; 
 * prints the parse tree (if requested);
 * checks it;
 * generates executable code. 
 */
public class Syntax {
    static DeclList library;
    static Program program;
 
    public static void init() {
	//-- Must be changed in part 1+2:
	library = new GlobalDeclList();
	addFunction("putint" , 1 );
	addFunction("putchar" , 1 );
	addFunction("getint" , 0 );
	addFunction("getchar" , 0 );
	addFunction("exit" , 1 );
	
	Scanner.readNext();
       	Scanner.readNext();
    }

    public static void addFunction(String name,int arg){
	FuncDecl func = new FuncDecl(name);
	ParamDeclList params = new ParamDeclList();
	int i = 0;
	while( i < arg){
	    ParamDecl pd = new ParamDecl("p"+i);
	    pd.type = Types.intType;
	    params.addDecl(pd);
	    i++;
	}
	func.funcParams = params;
	func.type = Types.intType;
	library.addDecl(func);
    }
 
    public static void finish() {
	//-- Must be changed in part 1+2:
	library = null;
    }
 
    public static void checkProgram() {
	program.check(library);
    }
 
    public static void genCode() {
	program.genCode(null);
    }
 
    public static void parseProgram() {
	program = Program.parse();
    }
 
    public static void printProgram() {
	program.printTree();
    }
}
 
 
/*
 * Super class for all syntactic units.
 * (This class is not mentioned in the syntax diagrams.)
 */
abstract class SyntaxUnit {
    int lineNum;
 
    SyntaxUnit() {
	lineNum = Scanner.curLine;
    }
 
    abstract void check(DeclList curDecls);
    abstract void genCode(FuncDecl curFunc);
    
    abstract void printTree();
 
    void error(String message) {
	Error.error(lineNum, message);
    }
}
 
 
/*
 * A <program>
 */
class Program extends SyntaxUnit {
    DeclList progDecls;
     
    @Override
    void check(DeclList curDecls) {
	progDecls.check(curDecls);

	if (! AlboC.noLink) {
	    // Check that 'main' has been declared properly:
	    //-- Must be changed in part 2:
	    FuncDecl main = (FuncDecl)progDecls.findDecl("main",
							 this);
	    Log.noteBinding("main",-1,main.lineNum);
	    
	    if(main != null){
		if(main.funcParams != null){
		    error("main cannot have arguments");
		}
	    }
	}
	
    }
         
    @Override
    void genCode(FuncDecl curFunc) {
	progDecls.genCode(null);
    }
 
    static Program parse() {
	Log.enterParser("<program>");
	Program p = new Program();
	p.progDecls = GlobalDeclList.parse();
	if (Scanner.curToken != eofToken)
	    Error.expected("A declaration");
	Log.leaveParser("</program>");
	return p;
    }
 
    @Override
    void printTree() {
	progDecls.printTree();
    }
}
 
 
/*
 * A declaration list.
 * (This class is not mentioned in the syntax diagrams.)
 */
 
abstract class DeclList extends SyntaxUnit {
    Declaration firstDecl;
    DeclList outerScope;
    Declaration lastDecl;
    DeclList () {
	firstDecl = null;
	lastDecl = null;
    }
 
    @Override
    void check(DeclList curDecls) {
	outerScope = curDecls;
	Declaration dx = firstDecl;
	while (dx != null) {
	    dx.check(this);
	    dx = dx.nextDecl;
	}
    }
 
    @Override
    void printTree() {
	Declaration temp = firstDecl;
	while (temp != null) {
	    temp.printTree();
	    temp = temp.nextDecl;
	}        
    }
 
    void addDecl(Declaration d) {
	if (firstDecl == null) {
	    firstDecl = d;
	    lastDecl = d;
	} else {
	    lastDecl.nextDecl = d;
	    lastDecl = d; 
	}
    }
 
    int dataSize() {
	Declaration dx = firstDecl;
	int res = 0;
	while (dx != null) {
	    res += dx.declSize();  
	    dx = dx.nextDecl;
	}
	return res;
    }

    int size() {
	Declaration dx = firstDecl;
	int res = 0;
	while (dx != null) {
	    res ++;
	    dx = dx.nextDecl;
	}
	return res;
    }

    Declaration findDecl(String name, SyntaxUnit use) {
	//-- Must be changed in part 2:
	Declaration cur = firstDecl;
	while( cur != null){
	    if( cur.name.compareTo(name) == 0){
		return cur;
	    }
	    cur = cur.nextDecl;
	}
	if(outerScope == null){
	    error( name + " is not defined.");
	}
	return outerScope.findDecl(name , use);
    }

    
}
 
 
/*
 * A list of global declarations. 
 * (This class is not mentioned in the syntax diagrams.)
 */
class GlobalDeclList extends DeclList {

    @Override
    void genCode(FuncDecl curFunc) {
	//-- Must be changed in part 2:
	Declaration cur = firstDecl;
	if(! (firstDecl instanceof FuncDecl)){
	    Code.genInstr("",".data","","");
	}

	while( cur != null){
	    cur.genCode(curFunc);

	    if(cur.nextDecl == null){

	    }else{
		if((cur instanceof VarDecl) &&
		   (cur.nextDecl instanceof FuncDecl)){
		    Code.genInstr("",".text","","");
		}
		if((cur instanceof FuncDecl) &&
		   (cur.nextDecl instanceof VarDecl)){
		    Code.genInstr("", ".data","","");
		}
	    }
	    cur = cur.nextDecl;
	}	
    }
 
    static GlobalDeclList parse() {
	GlobalDeclList gdl = new GlobalDeclList();
	while (Scanner.curToken == intToken) {
	    DeclType ts = DeclType.parse();
	    gdl.addDecl(Declaration.parse(ts));
	}
	return gdl;
    }
}
 
 
/*
 * A list of local declarations. 
 * (This class is not mentioned in the syntax diagrams.)
 */
class LocalDeclList extends DeclList {
 
    @Override
    void genCode(FuncDecl curFunc) {
	//-- Must be changed in part 2:
	Declaration cur = firstDecl;
	int s= 0;
	while( cur != null){
	    s = s - cur.declSize();
	    cur.assemblerName = (s + "(%ebp)");
	    cur.genCode(curFunc);
	    cur = cur.nextDecl;
	}	
    }
 
    static LocalDeclList parse() {
	LocalDeclList ldl = new LocalDeclList();
	while (Scanner.curToken == intToken) {
	    DeclType ts = DeclType.parse();
	    ldl.addDecl(LocalVarDecl.parse(ts));
	}
	return ldl;
    }
}
 
 
/*
 * A list of parameter declarations. 
 * (This class is not mentioned in the syntax diagrams.)
 */
class ParamDeclList extends DeclList {
     
    @Override void genCode(FuncDecl curFunc) {
	//-- Must be changed in part 2:
	int ad = 8;
	Declaration cur = this.firstDecl;

	while( cur != null){
	    cur.assemblerName = (ad + "(%ebp)");
	    cur.genCode(curFunc);
	    ad = ad+4;
	    cur = cur.nextDecl;
	}
    }
 
    static ParamDeclList parse() {
	ParamDeclList pdl = new ParamDeclList();
	while (Scanner.curToken == intToken) {
	    DeclType dt = DeclType.parse();
	    ParamDecl pd = ParamDecl.parse(dt);
	    pdl.addDecl(pd);
	}
	while (Scanner.curToken == commaToken) {
	    Scanner.skip(commaToken);
	    DeclType dt = DeclType.parse();
	    ParamDecl pd = ParamDecl.parse(dt);
	    pdl.addDecl(pd);
	}
	return pdl;
    }

    Type get(int i){
	int x = 1;
	Declaration cur = this.firstDecl;
	while( cur != null ){
	    if( i == x){
		return cur.type;
	    }
	    cur = cur.nextDecl;
	}
	return null;
    }
 
    @Override
    void printTree() {
	Declaration px = firstDecl;
	while (px != null) {
	    px.printTree();  
	    px = px.nextDecl;
	    if (px != null) Log.wTree(", ");
	}    
    }
}
 
 
/*
 * A <declaration type>
 */
class DeclType extends SyntaxUnit {
    int numStars = 0;
    Type type;
 
    @Override
    void check(DeclList curDecls) {
	type = Types.intType;
	for (int i = 1;  i <= numStars;  ++i)
	    type = new PointerType(type);
    }
 
    @Override
    void genCode(FuncDecl curFunc) {}
 
    static DeclType parse() {
	Log.enterParser("<type>");
	DeclType dt = new DeclType();
	Scanner.skip(intToken);
	while (Scanner.curToken == starToken) {
	    ++dt.numStars;
	    Scanner.skip(starToken);
	}
	Log.leaveParser("</type>");
	return dt;
    }
 
    @Override
    void printTree() {
	Log.wTree("int");
	for (int i = 1;  i <= numStars;  ++i) Log.wTree("*");
    }
}
 
 
/*
 * Any kind of declaration.
 */
abstract class Declaration extends SyntaxUnit {
    String name, assemblerName;
    DeclType typeSpec;
    Type type;
    boolean visible = false;
    Declaration nextDecl = null;
 
    Declaration(String n) {
	name = n;
    }
 
    abstract int declSize();
 
    static Declaration parse(DeclType dt) {
	Declaration d = null;	
	if (Scanner.curToken==nameToken && Scanner.nextToken==leftParToken) {
	    d = FuncDecl.parse(dt);
	} else if (Scanner.curToken == nameToken) {
	    d = GlobalVarDecl.parse(dt);
	} else {
	    Error.expected("A declaration name");
	}
	return d;
    }
 
 
    /**
     * checkWhetherVariable: Utility method to check whether this Declaration is
     * really a variable. The compiler must check that a name is used properly;
     * for instance, using a variable name a in "a()" is illegal.
     * This is handled in the following way:
     * <ul>
     * <li> When a name a is found in a setting which implies that should be a
     *      variable, the parser will first search for a's declaration d.
     * <li> The parser will call d.checkWhetherVariable(this).
     * <li> Every sub-class of Declaration will implement a checkWhetherVariable.
     *      If the declaration is indeed a variable, checkWhetherVariable will do
     *      nothing, but if it is not, the method will give an error message.
     * </ul>
     * Examples
     * <dl>
     *  <dt>GlobalVarDecl.checkWhetherVariable(...)</dt>
     *  <dd>will do nothing, as everything is all right.</dd>
     *  <dt>FuncDecl.checkWhetherVariable(...)</dt>
     *  <dd>will give an error message.</dd>
     * </dl>
     */
    abstract void checkWhetherVariable(SyntaxUnit use);
 
    /**
     * checkWhetherFunction: Utility method to check whether this Declaration
     * is really a function.
     * 
     * @param nParamsUsed Number of parameters used in the actual call.
     *                    (The method will give an error message if 
     *                    function was used with too many or too few parameters.)
     * @param use From where is the check performed?
     * @see   checkWhetherVariable
     */
    abstract void checkWhetherFunction(int nParamsUsed, SyntaxUnit use);
}
 
 
/*
 * A <var decl>
 */
abstract class VarDecl extends Declaration {

    boolean isArray = false;
    int numElems = 0;
 
    VarDecl(String n) {
	super(n);
    }
 
    @Override
    void check(DeclList curDecls) {
	//-- Must be changed in part 2:
	visible = true;
	typeSpec.check(curDecls);

	if(isArray == true ){
	    if(numElems < 0){
		error(name+ " cannot have negative elements!");
	    }
	    type = new ArrayType(typeSpec.type,numElems);
	}else{
	    type = typeSpec.type;
	}
	
    }
 
    @Override
    void printTree() {
	typeSpec.printTree();
	Log.wTree(" " + name);
	if (isArray) {
	    Log.wTree("[");
	    Log.wTree(""+Integer.toString(numElems));
	    Log.wTree("]");
	}
	Log.wTreeLn(";");
    }
 
    @Override
    int declSize() {
	return type.size();
    }
 
    @Override
    void checkWhetherFunction(int nParamsUsed, SyntaxUnit use) {
	use.error(name + " is a variable and no function!");
    }
     
    @Override
    void checkWhetherVariable(SyntaxUnit use) {
	// OK
    }
}
 
 
/*
 * A global <var decl>.
 */
class GlobalVarDecl extends VarDecl {
 
    GlobalVarDecl(String n) {
	super(n);
	assemblerName = (AlboC.underscoredGlobals() ? "_" : "") + n;
    }
 
    @Override
    void genCode(FuncDecl curFunc) {
	//-- Must be changed in part 2:
	Code.genInstr("",".globl", assemblerName,"");
	if(isArray){
	    Code.genInstr(assemblerName,".fill",numElems+",4,0","");
	}else{
	    Code.genInstr(assemblerName,".fill","1,4,0","");
	}
    }
 
    static GlobalVarDecl parse(DeclType dt) {
	Log.enterParser("<var decl>");
	GlobalVarDecl gvd = new GlobalVarDecl(Scanner.curName);
	gvd.typeSpec = dt;
	Scanner.skip(nameToken);
	if(Scanner.curToken == leftBracketToken){
	    Scanner.skip(leftBracketToken);
	    gvd.isArray = true;
	    gvd.numElems = Scanner.curNum;
	    Scanner.skip(numberToken);
	    Scanner.skip(rightBracketToken);
	}
	
	Scanner.skip(semicolonToken);
	Log.leaveParser("</var decl>");
	return gvd;
    }
}
 
 
/*
 * A local variable declaration
 */
class LocalVarDecl extends VarDecl {
    LocalVarDecl(String n) {
	super(n); 
    }
 
    @Override
    void genCode(FuncDecl curFunc) {
	//-- Must be changed in part 2:
    }
 
    static LocalVarDecl parse(DeclType dt) {
	Log.enterParser("<var decl>");
	LocalVarDecl lvd = new LocalVarDecl(Scanner.curName);
	lvd.typeSpec = dt;
	Scanner.skip(nameToken);
	if(Scanner.curToken == leftBracketToken){
	    Scanner.skip(leftBracketToken);
	    lvd.isArray = true;
	    lvd.numElems = Scanner.curNum;
	    Scanner.skip(numberToken);
	    Scanner.skip(rightBracketToken);
	}

	Scanner.skip(semicolonToken);
	Log.leaveParser("</var decl>");
	return lvd;
    }
}
 
 
/*
 * A <param decl>
 */
class ParamDecl extends VarDecl {
 
    ParamDecl(String n) {
	super(n);
    }
 
    @Override
    void genCode(FuncDecl curFunc) {
	//-- Must be changed in part 2:
    }
 
    static ParamDecl parse(DeclType dt) {
	Log.enterParser("<param decl>");
	ParamDecl pd = new ParamDecl(Scanner.curName);
	pd.typeSpec = dt;
	Scanner.skip(nameToken);
	Log.leaveParser("</param decl>");
	return pd;
    }
 
    @Override
    void printTree() {
	typeSpec.printTree(); 
	Log.wTree(" "+name);
    }
}
 
 
/*
 * A <func decl>
 */
class FuncDecl extends Declaration {
    ParamDeclList funcParams;
    String exitLabel;
    FuncBody fb = null;
    StatmList body ;
    
    FuncDecl(String n) {
	// Used for user functions:
	super(n);
	assemblerName = (AlboC.underscoredGlobals() ? "_" : "") + n;
	exitLabel = (".exit$"+n) ;
	funcParams = null;
    }
 
    @Override
    int declSize() {
	return 0;
    }
    
    @Override
    void check(DeclList curDecls) {
	//-- Must be changed in part 2:
	visible = true;
	typeSpec.check(curDecls);
	type = typeSpec.type;
	if(funcParams != null){
	    funcParams.check(curDecls);
	    fb.check(funcParams);
	}else{
	    fb.check(curDecls);
	}
    }

    @Override
    void checkWhetherFunction(int nParamsUsed, SyntaxUnit use) {
	//-- Must be changed in part 2:
	int size = funcParams.size();
	if( size != nParamsUsed){
	    error("illegal number of arguments (Expected "+ size+ " but found "+ nParamsUsed+" )");
	}
    }
     
    @Override
    void checkWhetherVariable(SyntaxUnit use) {
	//-- Must be changed in part 2:
	error(" is not a variable");
    }
 
    @Override
    void genCode(FuncDecl curFunc) {
	//-- Must be changed in part 2:

	Code.genInstr("",".globl",assemblerName,"");

	Code.genInstr(assemblerName,"enter","$"+fb.ldl.dataSize()+",$0",
		      "Start function "+name+" ;");

	
	if(funcParams != null){
	    funcParams.genCode(this);
	}
	fb.genCode(this);
	Code.genInstr(exitLabel,"","","");
	Code.genInstr("","leave","","");
	Code.genInstr("", "ret","","end "+assemblerName);
    }
 
    static FuncDecl parse(DeclType ts) {
	Log.enterParser("<func decl>");         
	FuncDecl fd = new FuncDecl(Scanner.curName);
	fd.typeSpec = ts;
	Scanner.skip(nameToken);
	Scanner.skip(leftParToken);
	if (Scanner.curToken == intToken) {
	    fd.funcParams = new ParamDeclList();
	    ParamDeclList pdl = ParamDeclList.parse();
	    fd.funcParams = pdl ;
	}
	Scanner.skip(rightParToken);
	fd.fb = FuncBody.parse();
	Log.leaveParser("</func decl>");
	return fd;
    }
 
    @Override
    void printTree() {
	typeSpec.printTree();
	Log.wTree(" ");
	Log.wTree(name + "(");
	if (funcParams != null) {
	    funcParams.printTree();
	}
	Log.wTree(") ");
	fb.printTree();
    }
}

class FuncBody extends SyntaxUnit {
    String funcname;
    DeclType dt = null;
    LocalDeclList ldl = null;
    StatmList sl = null;
 
    @Override
    void check(DeclList curDecls) {
	ldl.check(curDecls);
	sl.check(ldl);
    }
    
    @Override
    void genCode(FuncDecl curFunc) {
    	ldl.genCode(curFunc);
	sl.genCode(curFunc);
    }
 
    static FuncBody parse() {
	Log.enterParser("<func-body>");
	FuncBody fb = new FuncBody();
	Scanner.skip(leftCurlToken);
	fb.ldl = LocalDeclList.parse();
	fb.sl = StatmList.parse();
	Scanner.skip(rightCurlToken);
	Log.leaveParser("</func-body>");
	return fb;
    }
 
    @Override
    void printTree() {
	Log.wTreeLn("{");
	Log.indentTree();
	if (ldl != null) {
	    ldl.printTree();
	}
	sl.printTree();
	Log.outdentTree();
	Log.wTreeLn("}");
    }
}

 
 
/*
 * A <statm list>.
 */
class StatmList extends SyntaxUnit {
 
    Statement firstStatm = null;
    @Override
    void check(DeclList curDecls) {
	//-- Must be changed in part 2:
	Statement cur = firstStatm;
	while(cur != null){
	    cur.check(curDecls);
	    cur = cur.nextStatm ;
	}
    }
 
    @Override
    void genCode(FuncDecl curFunc) {
	//-- Must be changed in part 2:
	Statement cur = firstStatm;
	while(cur != null){
	    cur.genCode(curFunc);
	    cur = cur.nextStatm ;
	}
    }
 
    static StatmList parse() {
	Log.enterParser("<statm list>");
	StatmList sl = new StatmList();
	Statement lastStatm = null;

	while (Scanner.curToken != rightCurlToken) {
	    Statement s = Statement.parse();

	    if (lastStatm == null) {
		sl.firstStatm = lastStatm = s; 
	    } else {
		lastStatm.nextStatm = lastStatm = s;
	    }
	    
	}
	Log.leaveParser("</statm list>");
	return sl;
    }
 
    @Override
    void printTree() {
	Statement temp = firstStatm;
	while (temp != null) {
	    temp.printTree();
	    temp = temp.nextStatm;
	}
    }
}
 
 
/*
 * A <statement>.
 */
abstract class Statement extends SyntaxUnit {
    Statement nextStatm = null;
 
    static Statement parse() {
	Log.enterParser("<statement>");
	Statement s = null;
	if (Scanner.curToken==nameToken && 
	    Scanner.nextToken==leftParToken) {
	    s = CallStatm.parse();
	} else if (Scanner.curToken==nameToken || Scanner.curToken==starToken) {
	    s = AssignStatm.parse();
	} else if (Scanner.curToken == forToken) {
	    s = ForStatm.parse();
	} else if (Scanner.curToken == ifToken) {
	    s = IfStatm.parse();
	} else if (Scanner.curToken == returnToken) {
	    s = ReturnStatm.parse();
	} else if (Scanner.curToken == whileToken) {
	    s = WhileStatm.parse();
	} else if (Scanner.curToken == semicolonToken) {
	    s = EmptyStatm.parse();
	} else {
	    Error.expected("A statement");
	}
	Log.leaveParser("</statement>");
	return s;
    }
}
 
 
 
 
/*
 * An <empty statm>.
 */
class EmptyStatm extends Statement {
    //-- Must be changed in part 1+2:
 
    @Override
    void check(DeclList curDecls) {
	//-- Must be changed in part 2:
    }
 
    @Override
    void genCode(FuncDecl curFunc) {
	//-- Must be changed in part 2:
    }
 
    static EmptyStatm parse() {
	Log.enterParser("<empty statm>");
	Scanner.skip(semicolonToken);
	Log.leaveParser("</empty statm>");
	return new EmptyStatm();
    }
 
    @Override
    void printTree() {
	Log.wTreeLn(";");
    }
}
     
 
/*
 * A <for-statm>.
 * 
 */
class ForStatm extends Statement {
    //-- Must be changed in part 1+2:
    ForControl test;
    StatmList body;
 
    @Override
    void check(DeclList curDecls) {
	//-- Must be changed in part 2:
	test.check(curDecls);
	body.check(curDecls);
	Log.noteTypeCheck("for(...,t,...) ...",
			  test.e.type,"t", lineNum);
	if(test.e.type instanceof ValueType){
	    //OK
	}else{
	    error("For-test must be a value.");
	}
    }
 
    @Override
    void genCode(FuncDecl curFunc) {
	//-- Must be changed in part 2:
	String testLabel = Code.getLocalLabel();
	String endLabel = Code.getLocalLabel();
	test.genCode(curFunc);
	Code.genInstr(testLabel,"","","Start For-statement");
	test.e.genCode(curFunc);
	Code.genInstr("", "cmpl","$0,%eax","");
	Code.genInstr("","je",endLabel,"");
	body.genCode(curFunc);
	test.a2.genCode(curFunc);
	Code.genInstr("","jmp",testLabel,"");
	Code.genInstr(endLabel,"","","End For-statement");
    }
 
    static ForStatm parse() {

	Log.enterParser("<for-statm>");
	ForStatm fs = new ForStatm();
	Scanner.skip(forToken);
	Scanner.skip(leftParToken);
	fs.test = ForControl.parse();
	Scanner.skip(rightParToken);
	Scanner.skip(leftCurlToken);
	fs.body = StatmList.parse();
	Scanner.skip(rightCurlToken);
 
	Log.leaveParser("</for-statm>");
	return fs;
    }
 
    @Override
    void printTree() {
	Log.wTree("for (");
	test.printTree();
	Log.wTreeLn(") {");
	Log.indentTree();
	body.printTree();
	Log.outdentTree();
	Log.wTreeLn("}");
    }
}
 
 
/**
 * 
 */
 
class ForControl extends SyntaxUnit {
    Assignment a1;
    Assignment a2;
    Expression e;
 
    @Override
    void check(DeclList curDecls) {
	a1.check(curDecls);
	e.check(curDecls);
	a2.check(curDecls);
    }
    
    @Override
    void genCode(FuncDecl curFunc) {
	//-- Must be changed in part 2:
	a1.genCode(curFunc);	
    }
 
    static ForControl parse() {

	Log.enterParser("<for-control>");
 
	ForControl fc = new ForControl();
	fc.a1 = Assignment.parse();
	Scanner.skip(semicolonToken);
	fc.e = Expression.parse();
	Scanner.skip(semicolonToken);
	fc.a2 = Assignment.parse();
 
	Log.leaveParser("</for-control>");
	return fc;
    }
 
    @Override
    void printTree() {
	a1.printTree();
	Log.wTree("; ");
	e.printTree();
	Log.wTree("; ");
	a2.printTree();
    }
}
 
 
class Assignment extends SyntaxUnit {
    LhsVariable lhs = null;
    Expression e = null;
 
    @Override
    void check(DeclList curDecls) {
	lhs.check(curDecls);
	e.check(curDecls);
	Log.noteTypeCheck(" v = e",lhs.type,"v",e.type,"e",
			  lineNum);
	
	if((lhs.type instanceof ValueType)){
		if(lhs.type.isSameType(e.type)){
		    return;
		}
		if(e.type == Types.intType){
		    return;
		}

	    }
	    error("Assignment must be of int or of the same type");
    }
	    
    
    @Override
    void genCode(FuncDecl curFunc) {
	//-- Must be changed in part 2:                
	/*  PROBLEM POSSIBLY HERE  -- FIXED think (double check)
	    if(lhs.var.index != null){
	    lhs.var.index.genCode(curFunc);
	    Code.genInstr("","pushl","%eax","");
	    }  
	*/  
	lhs.genCode(curFunc);
	Code.genInstr("","pushl","%eax","");
	e.genCode(curFunc);
	Code.genInstr("","popl","%edx","");
	Code.genInstr("","movl","%eax,(%edx)"," = ");
    }
 
    static Assignment parse() {
	Log.enterParser("<assignment>");
	Assignment a = new Assignment();
	a.lhs = LhsVariable.parse();
	Scanner.skip(assignToken);
	a.e = Expression.parse();
	Log.leaveParser("</assignment>");
	return a;
    }
 
    @Override
    void printTree() {
	lhs.printTree();
	Log.wTree(" = ");
	e.printTree();
    }
}
 
/*
 * An <if-statm>.
 */
class IfStatm extends Statement {
    //-- Must be changed in part 1+2:
    Expression test;
    StatmList body;
    ElseStatm elsepart;
   
    @Override
    void check(DeclList curDecls) {
	//-- Must be changed in part 2:
	test.check(curDecls);
	body.check(curDecls);
	if( elsepart != null){
	    elsepart.check(curDecls);
	}
	Log.noteTypeCheck("if (t) ...",test.type,"t", lineNum);
	if (test.type instanceof ValueType) {
	    // OK
	} else {
	    error("If-test must be a value.");
	}
    }
   
    @Override
    void genCode(FuncDecl curFunc) {
	//-- Must be changed in part 2:
	String testLabel = Code.getLocalLabel();
	String endLabel = Code.getLocalLabel();
	Code.genInstr("","","","Start If-statement");
	test.genCode(curFunc);
	Code.genInstr("", "cmpl","$0,%eax","");


	if(elsepart == null){
	    Code.genInstr("","je",testLabel,"");
	    body.genCode(curFunc);
	    Code.genInstr(testLabel,"","","");
	}else{
	    Code.genInstr("","je",endLabel,"");
	    body.genCode(curFunc);
	    Code.genInstr("","jmp",testLabel,"");
	    Code.genInstr(endLabel,"","","Else-Part");
	    elsepart.genCode(curFunc);
	    Code.genInstr(testLabel,"","","End If-Statement");
	}
	
    }
   
    static IfStatm parse() {
	Log.enterParser("<if-statm>");
	IfStatm is = new IfStatm();
	Scanner.skip(ifToken);
	Scanner.skip(leftParToken);
	is.test = Expression.parse();
	Scanner.skip(rightParToken);
	Scanner.skip(leftCurlToken);
	is.body = StatmList.parse();
	Scanner.skip(rightCurlToken);
	if (Scanner.curToken == elseToken) {
	    is.elsepart = ElseStatm.parse();
	}
	Log.leaveParser("</if-statm>");
	return is;
    }
   
    @Override
    void printTree() {
	Log.wTree("if (");
	test.printTree();
	Log.wTreeLn(") {");
	Log.indentTree();
	body.printTree();
	Log.outdentTree();
	if (elsepart != null) {
	    Log.wTree("}");
	    elsepart.printTree();
	} else {
	    Log.wTreeLn("}");
	}
    }
}
   
   
   
/*
 * An <else-part>.
 *
 */
class ElseStatm extends Statement {
   
    StatmList body;
   
    @Override
    void check(DeclList curDecls) {
	//-- Must be changed in part 2:
	body.check(curDecls);
    }
   
    @Override
    void genCode(FuncDecl curFunc) {
	//-- Must be changed in part 2:
	body.genCode(curFunc);
    }
   
    static ElseStatm parse() {
	Log.enterParser("<else-part>");
	ElseStatm es = new ElseStatm();
	Scanner.skip(elseToken);
	Scanner.skip(leftCurlToken);
	es.body = StatmList.parse();
	Scanner.skip(rightCurlToken);
	Log.leaveParser("</else-part>");
	return es;
    }
   
    @Override
    void printTree() {
	Log.wTreeLn(" else {");
	Log.indentTree();
	body.printTree();
	Log.outdentTree();
	Log.wTreeLn("}");
    }
}
   
/*
 * An <assign-statm>.
 */
class AssignStatm extends Statement {
    //-- Must be changed in part 1+2:
    Assignment a;
   
    @Override
    void check(DeclList curDecls) {
	//-- Must be changed in part 2:
	a.check(curDecls);
    }
   
    @Override
    void genCode(FuncDecl curFunc) {
	//-- Must be changed in part 2:
	a.genCode(curFunc);
    }
   
    static AssignStatm parse() {
	Log.enterParser("<assign-statm>");
	AssignStatm as = new AssignStatm();
	as.a = Assignment.parse();
	Scanner.skip(semicolonToken);
	Log.leaveParser("</assign-statm>");
	return as;
    }
   
    @Override
    void printTree() {
	a.printTree();
	Log.wTreeLn(";");
    }
}
   
/*
 * A <return-statm>.
 *
 */
class ReturnStatm extends Statement {
    //-- Must be changed in part 1+2:
    Expression e = null;
   
    @Override
    void check(DeclList curDecls) {
	//-- Must be changed in part 2:
	
	e.check(curDecls);
	Log.noteTypeCheck("return e; in int f(...)",
			  e.type,"e",lineNum );

	if(e.type == Types.intType){
	    
	}else{
	    error("Error in return type.");
	}
    }
   
    @Override
    void genCode(FuncDecl curFunc) {
	//-- Must be changed in part 2:
	if(e != null){
	    e.genCode(curFunc);
	    Code.genInstr("", "jmp", ".exit$" + curFunc.assemblerName, "Return-Statement");
	}
    }
   
    static ReturnStatm parse() {
	Log.enterParser("<return-statm>");
	ReturnStatm rs = new ReturnStatm();
	Scanner.skip(returnToken);
	rs.e = Expression.parse();
	Scanner.skip(semicolonToken);
	Log.leaveParser("</return-statm>");
	return rs;
    }
   
    @Override
    void printTree() {
	Log.wTree("return ");
	e.printTree();
	Log.wTreeLn(";");
    }
}
   
   
/*
 * A <while-statm>.
 */
class WhileStatm extends Statement {
    Expression test;
    StatmList body;
   
    @Override
    void check(DeclList curDecls) {
	test.check(curDecls);
	body.check(curDecls);
	Log.noteTypeCheck("while (t) ...", test.type, "t", lineNum);
	if (test.type instanceof ValueType) {
	    // OK
	} else {
	    error("While-test must be a value.");
	}
    }
   
    @Override
    void genCode(FuncDecl curFunc) {
	String testLabel = Code.getLocalLabel(),
	    endLabel  = Code.getLocalLabel();
   
	Code.genInstr(testLabel, "", "", "Start while-statement");
	test.genCode(curFunc);
	Code.genInstr("", "cmpl", "$0,%eax", "");
	Code.genInstr("", "je", endLabel, "");
	body.genCode(curFunc);
	Code.genInstr("", "jmp", testLabel, "");
	Code.genInstr(endLabel, "", "", "End while-statement");
    }
   
    static WhileStatm parse() {

	Log.enterParser("<while-statm>");
	WhileStatm ws = new WhileStatm();
	Scanner.skip(whileToken);
	Scanner.skip(leftParToken);
	ws.test = Expression.parse();
	Scanner.skip(rightParToken);
	Scanner.skip(leftCurlToken);
	ws.body = StatmList.parse();
	Scanner.skip(rightCurlToken);
   
	Log.leaveParser("</while-statm>");
	return ws;
    }
   
    @Override
    void printTree() {
	Log.wTree("while (");
	test.printTree();
	Log.wTreeLn(") {");
	Log.indentTree();
	body.printTree();
	Log.outdentTree();
	Log.wTreeLn("}");
    }
}
   
   
/*
 * An <Lhs-variable>
 */
   
class LhsVariable extends SyntaxUnit {
    int numStars = 0;
    Variable var;
    Type type;
   
    @Override
    void check(DeclList curDecls) {
	var.check(curDecls);
	type = var.type;
	for (int i = 1;  i <= numStars;  ++i) {
	    Type e = type.getElemType();
	    if (e == null)
		error("Type error in left-hand side variable!");
	    type = e;
	}
    }
   
    @Override
    void genCode(FuncDecl curFunc) {
	var.genAddressCode(curFunc);
	for (int i = 1;  i <= numStars;  ++i)
	    Code.genInstr("", "movl", "(%eax),%eax", "  *");
    }
   
    static LhsVariable parse() {
	Log.enterParser("<lhs-variable>");
	LhsVariable lhs = new LhsVariable();
	while (Scanner.curToken == starToken) {
	    ++lhs.numStars;
	    Scanner.skip(starToken);
	}
	Scanner.check(nameToken);
	lhs.var = Variable.parse();
	Log.leaveParser("</lhs-variable>");
	return lhs;
    }
   
    @Override
    void printTree() {
	for (int i = 1;  i <= numStars;  ++i) Log.wTree("*");
	var.printTree();
    }
}
   
   
/*
 * An <expression list>.
 */
   
class ExprList extends SyntaxUnit {
    Expression firstExpr = null;
   
    @Override
    void check(DeclList curDecls) {
	//-- Must be changed in part 2:
	Expression cur = firstExpr;
	while(cur != null){
	    cur.check(curDecls);
	    cur = cur.nextExpr ;
	}
    }
   
    @Override
    void genCode(FuncDecl curFunc) {
	//-- Must be changed in part 2:
    }

    int dataSize(){
	Expression cur = firstExpr;
	int size = 0;
	while(cur != null){
	    size++;
	    cur = cur.nextExpr ;
	}
	return size;
    }
    
    static ExprList parse() {

	Expression lastExpr = null;

	Log.enterParser("<expr list>");
	if(Scanner.curToken == rightParToken ) {
	    Log.leaveParser("</expr list>");
	    return null ;
	}
	
	ExprList el = new ExprList();
	while (true) {
	    Expression e = Expression.parse();
	    if (lastExpr == null) {
		el.firstExpr = lastExpr = e;
	    } else {
		lastExpr.nextExpr = lastExpr = e;
	    }
	    
	    if (Scanner.curToken != commaToken) {
		break;
	    }
	    Scanner.skip(commaToken);
	}
	Log.leaveParser("</expr list>");
	return el;
    }
   
    @Override
    void printTree() {
	Expression temp = firstExpr;
	while (temp != null) {
	    temp.printTree();
	    temp = temp.nextExpr;
	    if (temp != null)
		Log.wTree(", ");
	}
    }
    //-- Must be changed in part 1:
}
   
   
/*
 * An <expression>
 */
class Expression extends SyntaxUnit {
    Expression nextExpr = null;
    Term firstTerm, secondTerm = null;
    Operator relOpr = null;
    Type type = null;

    @Override
    void check(DeclList curDecls) {
	//-- Must be changed in part 2:
	firstTerm.check(curDecls);
	if( relOpr != null){
	    //   relOpr.check(curDecls);
	    secondTerm.check(curDecls);
	    Log.noteTypeCheck("x "+ relOpr.oprToken+" y",
			      firstTerm.type, "x",
			      secondTerm.type, "y",lineNum);
	    type = Types.intType;
	}else{
	    type = firstTerm.type;
	}

    }
   
    @Override
    void genCode(FuncDecl curFunc) {
	//-- Must be changed in part 2:
	firstTerm.genCode(curFunc);
	if(relOpr != null){
	    Code.genInstr("","pushl","%eax","");
	    secondTerm.genCode(curFunc);	    
	    relOpr.genCode(curFunc);
	}
    }
   
    static Expression parse() {
	Log.enterParser("<expression>");
	Expression e = new Expression();
	e.firstTerm = Term.parse();
	if (Token.isRelOperator(Scanner.curToken)) {
	    e.relOpr = RelOpr.parse();
	    e.secondTerm = Term.parse();
	}
	Log.leaveParser("</expression>");
	return e;
    }
   
    @Override
    void printTree() {
	firstTerm.printTree();
	if (secondTerm != null) {
	    relOpr.printTree();
	    secondTerm.printTree();
	}
    }
}
   
   
/*
 * A <term>
 */
class Term extends SyntaxUnit {
    //-- Must be changed in part 1+2:
    FactorList factors = new FactorList();
    OperatorList opers = new OperatorList();
    Type type = null;
    
    @Override void check(DeclList curDecls) {
	//-- Must be changed in part 2:
	for(int j = 0; j < factors.size ; j++){
	    Factor cur = factors.get(j);
	    if(cur!= null){
		cur.check(curDecls);
		type = cur.type ;
	    }
	}	
    }
   
    @Override void genCode(FuncDecl curFunc) {
	//-- Must be changed in part 2:
	for (int i = 0; i < factors.size(); i++) {
	    Factor f = factors.get(i);

	    if(f != null){
		f.genCode(curFunc);
	    }
	    if (i != factors.size()-1) {
		f = factors.get(i+1);
		if(f != null){
		    Code.genInstr("","pushl","%eax","");
		    f.genCode(curFunc);
		}
		Operator op = opers.get(i);
		if(op!=null){
		    op.genCode(curFunc);
		}
		i++;
	    }
	}
    }
   
    static Term parse() {
	Log.enterParser("<term>");
	Term t = new Term();
	t.factors.add(Factor.parse());
	while (Token.isTermOperator(Scanner.curToken)) {
	    t.opers.add(TermOpr.parse());
	    t.factors.add(Factor.parse());
	}
	Log.leaveParser("</term>");
	return t;
    }
   
    @Override
    void printTree() {
	for (int i = 0; i < factors.size(); i++) {
	    Factor f = factors.get(i);
	    if(f != null){
		f.printTree();
	    }
	    if (i != factors.size()-1) {
		Operator op = opers.get(i);
		if(op!=null){
		    op.printTree();
		}		
	    }
	}
    }
}
   
/*
 * A <factor>
 */
class Factor extends SyntaxUnit {
    Factor nextFac = null;
    //-- Must be changed in part 1+2:
    PrimaryList prims = new PrimaryList();
    OperatorList opers = new OperatorList();
    Type type = null;
   
    @Override void check(DeclList curDecls) {
	//-- Must be changed in part 2:
	for (int i = 0; i < prims.size(); i++) {
	    Primary p = prims.get(i);
	    if(p != null){
		p.check(curDecls);
		type = p.type;
	    }
	    if (i != prims.size()-1) {
		Operator po = opers.get(i);
		po.check(curDecls);
	    }
	}
    }
   
    @Override void genCode(FuncDecl curFunc) {
	//-- Must be changed in part 2:
	for (int i = 0; i < prims.size(); i++) {
	    Primary p = prims.get(i);
	    if(p!= null){
		p.genCode(curFunc);
	    }
	    if (i != prims.size()-1) {
		p = prims.get(i+1);
		if( p != null){
		    Code.genInstr("","pushl","%eax","");
		    p.genCode(curFunc);
		}
		Operator op = opers.get(i);
		if(op!=null){
		    op.genCode(curFunc);
		}
		i++;
	    }
	}	
    }
   
    static Factor parse() {
	Log.enterParser("<factor>");
	Factor f = new Factor();
	f.prims.add(Primary.parse());
	while (Token.isFactorOperator(Scanner.curToken)) {
	    f.opers.add(FactorOpr.parse());
	    f.prims.add(Primary.parse());
	}
	Log.leaveParser("</factor>");
	return f;
    }
   
    @Override void printTree() {
	for (int i = 0; i < prims.size(); i++) {
	    Primary p = prims.get(i);
	    if(p!= null){
		p.printTree();
	    }
	    if (i != prims.size()-1) {
		Operator po = opers.get(i);
		po.printTree();
	    }
	}
    }
}
   
/*
 * A <primary>
 */
class Primary extends SyntaxUnit {
    //-- Must be changed in part 1+2:
    Primary nextPrim = null;
    PrefixOpr po = null;
    Operand o = null;
    Type type = null;
   
    @Override
    void check(DeclList curDecls) {
	//-- Must be changed in part 2:
	o.check(curDecls);
	if( po!= null){
	    if( po.oprToken == starToken){
		type = new PointerType(o.type);
	    }else{
		type = Types.intType;
	    }
	}else{
	    type = o.type;
	}
    }
   
    @Override
    void genCode(FuncDecl curFunc) {
	//-- Must be changed in part 2:
	o.genCode(curFunc);
	if(po != null){
	    po.genCode(curFunc);
	}
    }
   
    static Primary parse() {

	Log.enterParser("<primary>");
	Primary p = new Primary();
	if (Token.isPrefixOperator(Scanner.curToken)) {
	    p.po = PrefixOpr.parse();
	}
	p.o = Operand.parse();
	Log.leaveParser("</primary>");
	return p;
    }
   
    @Override
    void printTree() {
	if (po != null) {
	    po.printTree();
	}
	o.printTree();
    }
}
 
   
/*
 * An <operator>
 */
abstract class Operator extends SyntaxUnit {
    Operator nextOpr = null;
    Token oprToken;
    int size;
    @Override
    void check(DeclList curDecls) {}  // Never needed.
}

/*
 * A list for operators.
 */
class OperatorList extends SyntaxUnit {
    Operator firstOpr;
    Operator lastOpr;
    int size;
    OperatorList() {
	firstOpr = lastOpr= null;
	size = 0;
    }
 
    public void add(Operator o) {
	if (firstOpr== null) { 
	    firstOpr = lastOpr = o;
	} else { 
	    lastOpr.nextOpr = o;
	    o = lastOpr;
	}
	size++;
    }
 
    public Operator get(int i) {
	int count = 0;
	Operator current = firstOpr;
	if (i > size) {
	    throw new IndexOutOfBoundsException();
	} else {
	    while (count != i && current != null) {
		current = current.nextOpr;
		count++;
	    }
	    return current;
	}
    }
    public int size() { return size;}
 
    @Override
    void printTree() {
    }
    
    @Override
    void genCode(FuncDecl curFunc) {
	//probably needs to be changed in part 2
    }
    @Override
    void check(DeclList curDecls) {
	//probably needs to be changed in part 2
    } 
}

class FactorList extends SyntaxUnit {
    Factor first;
    Factor last;
    int size; 
    FactorList() {
	first = last = null;
	size = 0;
    }
 
    public void add(Factor f) {
	if (first== null) { 
	    first = last = f;
	} else { 
	    last.nextFac = f;
	    f = last;
	}
	size++;
    }
 
    public Factor get(int i) {
	int count = 0;
	Factor current = first;

	if (i > size) {
	    throw new IndexOutOfBoundsException();
	} else {
	    while (count != i && current != null) {
		current = current.nextFac;
		count++;
	    }
	    return current;
	}
    }
    
    public int size() { return size;}

 
    @Override
    void printTree() {
	Factor cur = first;
	while(cur != null){
	    cur.printTree();
	    cur = cur.nextFac;
	}	
    }
    
    @Override
    void genCode(FuncDecl curFunc) {
	//probably needs to be changed in part 2
    }
    
    @Override
    void check(DeclList curDecls) {
	//probably needs to be changed in part 2
	Factor cur = first;
	while(cur != null){
	    cur.check(curDecls);
	    cur = cur.nextFac;
	}	
    } 
}

class PrimaryList extends SyntaxUnit {
    Primary first;
    Primary last;
    int size;
    PrimaryList() {
	first = last = null;
	size = 0;
    }
 
    public void add(Primary p) {
	if (first== null) { 
	    first = last = p;
	} else { 
	    last.nextPrim = p;
	    p = last;
	}
	size++;
    }
 
    public Primary get(int i) {
	int count = 0;
	Primary current = first;
	if (i > size) {
	    throw new IndexOutOfBoundsException();
	} else {
	    while (count != i) {
		current = current.nextPrim;
		count++;
	    }
	    return current;
	}
    }
    public int size() { return size;}
 
    @Override
    void printTree() {
    }
    
    @Override
    void genCode(FuncDecl curFunc) {
	//probably needs to be changed in part 2
    }
    @Override
    void check(DeclList curDecls) {
	//probably needs to be changed in part 2
    } 
}
 
/*
 * A <rel opr> (==, !=, <, <=, > or >=).
 */
   
class RelOpr extends Operator {
    @Override
    void genCode(FuncDecl curFunc) {
	Code.genInstr("", "popl", "%ecx", "");
	Code.genInstr("", "cmpl", "%eax,%ecx", "");
	Code.genInstr("", "movl", "$0,%eax", "");
	switch (oprToken) {
	case equalToken:      
	    Code.genInstr("", "sete", "%al", "Test ==");  break;
	case notEqualToken:
	    Code.genInstr("", "setne", "%al", "Test !=");  break;
	case lessToken:
	    Code.genInstr("", "setl", "%al", "Test <");  break;
	case lessEqualToken:
	    Code.genInstr("", "setle", "%al", "Test <=");  break;
	case greaterToken:
	    Code.genInstr("", "setg", "%al", "Test >");  break;
	case greaterEqualToken:
	    Code.genInstr("", "setge", "%al", "Test >=");  break;
	}
    }
   
    static RelOpr parse() {
	Log.enterParser("<rel opr>");
	RelOpr ro = new RelOpr();
	ro.oprToken = Scanner.curToken;
	Scanner.readNext();
	Log.leaveParser("</rel opr>");
	return ro;
    }
   
    @Override void printTree() {
	String op = "?";
	switch (oprToken) {
	case equalToken:        op = "==";  break;
	case notEqualToken:     op = "!=";  break;
	case lessToken:         op = "<";   break;
	case lessEqualToken:    op = "<=";  break;
	case greaterToken:      op = ">";   break;
	case greaterEqualToken: op = ">=";  break;
	}
	Log.wTree(" " + op + " ");
    }
}
   
/*
 * A <term opr> (+ or -).
 */
   
class TermOpr extends Operator {
    @Override
    void genCode(FuncDecl curFunc) {
	Code.genInstr("","movl","%eax,%ecx","");
	Code.genInstr("","popl","%eax","");
	switch (oprToken) {
	case subtractToken:
	    Code.genInstr("","subl","%ecx,%eax","Compute -");
	    break;
	case addToken:
	    Code.genInstr("","addl","%ecx,%eax","Compute +");
	    break;
	}
    }
   
    static TermOpr parse() {
	Log.enterParser("<term opr>");
	TermOpr to = new TermOpr();
	to.oprToken = Scanner.curToken;
	Scanner.readNext();
	Log.leaveParser("</term opr>");
	return to;
    }
   
    @Override
    void printTree() {
	String op = "?";
	switch (oprToken) {
	case addToken:        op = "+";  break;
	case subtractToken:   op = "-";  break;
	}
	Log.wTree(" " + op + " ");
    }
}
   
/*
 * A <factor opr> (* or /).
 */
   
class FactorOpr extends Operator {
    @Override
    void genCode(FuncDecl curFunc) {
	Code.genInstr("","movl","%eax,%ecx","");
	Code.genInstr("","popl","%eax","");
	switch (oprToken) {
	case starToken:
	    Code.genInstr("","imull","%ecx,%eax","Multiply");
	    break;
	case divideToken:
	    Code.genInstr("","cdq","","");
	    Code.genInstr("","idivl","%ecx","Divide");
	    break;
	}
    }
   
    static FactorOpr parse() {
	Log.enterParser("<factor opr>");
	FactorOpr fo = new FactorOpr();
	fo.oprToken = Scanner.curToken;
	Scanner.readNext();
	Log.leaveParser("</factor opr>");
	return fo;
    }
   
    @Override
    void printTree() {
	String op = "?";
	switch (oprToken) {
	case starToken:     op = "*";  break;
	case divideToken:   op = "/";  break;
	}
	Log.wTree(" " + op + " ");
    }
}
   
/*
 * A <prefix opr> (- or *).
 */
   
class PrefixOpr extends Operator {
    @Override
    void genCode(FuncDecl curFunc) {
	switch(oprToken){
	case starToken:
	    Code.genInstr("","movl","(%eax),%eax","");
	    break;
	case subtractToken:
	    Code.genInstr("","negl","%eax","");
	    break;
	}	    
    }
   
    static PrefixOpr parse() {
	Log.enterParser("<prefix opr>");
   
	PrefixOpr po = new PrefixOpr();
	po.oprToken = Scanner.curToken;
	Scanner.readNext();
   
	Log.leaveParser("</prefix opr>");
	return po;
    }
   
    @Override
    void printTree() {
	String op = "?";
	switch (oprToken) {
	case subtractToken: op = "-";  break;
	case starToken:     op = "*";  break;
	}
	Log.wTree(" " + op);
    }
}
  
/*
 * An <operand>
 */
abstract class Operand extends SyntaxUnit {
    Operand nextOperand = null;
    Type type;
   
    static Operand parse() {
	Log.enterParser("<operand>");
	Operand o = null;
	if (Scanner.curToken == numberToken) {
	    o = Number.parse();
	} else if (Scanner.curToken==nameToken && Scanner.nextToken==leftParToken) {
	    o = FunctionCall.parse();
	} else if (Scanner.curToken == nameToken) {
	    o = Variable.parse();
	} else if (Scanner.curToken == ampToken) {
	    o = Address.parse();
	} else if (Scanner.curToken == leftParToken) {
	    o = InnerExpr.parse();
	} else {
	    Error.expected("An operand");
	}
	Log.leaveParser("</operand>");
	return o;
    }
}
   
   
/*
 * A <function call>.
 */
class FunctionCall extends Operand {
    //-- Must be changed in part 1+2:
    String funcName;
    ExprList elist = null;
   
    @Override
    void check(DeclList curDecls) {
	//-- Must be changed in part 2:

	FuncDecl func = (FuncDecl) curDecls.findDecl(funcName, this);
	type = func.type;

	Log.noteBinding(funcName,lineNum,func.lineNum);
	
	if(elist == null){ return ;}
	func.checkWhetherFunction(elist.dataSize(), this);
	//elist.check(curDecls);
	Expression cur = elist.firstExpr;
	Declaration d = func.funcParams.firstDecl;

	int i = 1;
	while( cur!= null){
	    cur.check(curDecls);
	    Log.noteTypeCheck("Parameter #"+i+" in call on " +
			      funcName, cur.type,"actual",
			      type,"formal", lineNum);

	    Type t = func.funcParams.get(i);


	    if (cur.type != Types.intType){
	    }else if(	(cur.type.isSameType(d.type))){
	    }else{
		error("function-call type mismatch error");
	    }
	    cur = cur.nextExpr;
	    d = d.nextDecl;
	    i++;
	}

	if(nextOperand != null){
	    nextOperand.check(curDecls);
	}
    }
   
    @Override
    void genCode(FuncDecl curFunc) {
	//-- Must be changed in part 2:
	if(elist == null){
	    Code.genInstr("","call", funcName,"call "+funcName);
	    return ;
	}
	Expression[] e = new Expression[elist.dataSize()];
	Expression cur = elist.firstExpr;
	int i = 0;
	while( cur!= null){
	    e[i++] =  cur;
	    cur = cur.nextExpr;
	}
	int p_bit = (i * 4);    
	for(int k = i-1; k >= 0 ; k --){
	    e[k].genCode(curFunc);
	    Code.genInstr("","pushl","%eax","Push Parameter #"+
			  (k+1));
	}
	Code.genInstr("","call", funcName,"call "+funcName);
	Code.genInstr("","addl", "$"+p_bit+",%esp","Remove parameter");
	
    }
   
    static FunctionCall parse() {
	Log.enterParser("<function call>");
	//-- Must be changed in part 1:
	FunctionCall fc = new FunctionCall();
	fc.funcName = Scanner.curName;
	Scanner.skip(nameToken);
	Scanner.skip(leftParToken);
	fc.elist = ExprList.parse();
	Scanner.skip(rightParToken);
	Log.leaveParser("</function call>");
	return fc;
   
    }
   
    @Override
    void printTree() {
	Log.wTree(funcName + "(");
	if(elist != null){
	    elist.printTree();
	}
	Log.wTree(")");
    }
    //-- Must be changed in part 1+2:
}
  
  
/*
 * A <call-statm>.
 */
   
class CallStatm extends Statement {
    //-- Must be changed in part 1+2:
    FunctionCall fc = null;
   
    @Override
    void check(DeclList curDecls) {
	//-- Must be changed in part 2:
	fc.check(curDecls);
    }
   
    @Override
    void genCode(FuncDecl curFunc) {
	//-- Must be changed in part 2:
	fc.genCode(curFunc);
    }
   
    static CallStatm parse() {
	Log.enterParser("<call-statm>");
	CallStatm cs = new CallStatm();
	cs.fc = FunctionCall.parse();
	Scanner.skip(semicolonToken);
	Log.leaveParser("</call-statm>");
	return cs;
    }
   
    @Override
    void printTree() {
	fc.printTree();
	Log.wTreeLn(";");
    }
    //-- Must be changed in part 1+2:
}
   
/*
 * A <number>.
 */
class Number extends Operand {
    int numVal;
   
    @Override
    void check(DeclList curDecls) {
	//-- Must be changed in part 2:
	type = Types.intType;
	if( nextOperand!= null){
	    nextOperand.check(curDecls);
	}
    }
       
    @Override
    void genCode(FuncDecl curFunc) {
	Code.genInstr("", "movl", "$"+numVal+",%eax", ""+numVal);
    }
   
    static Number parse() {
	Log.enterParser("<number>");
	Number n = new Number();
	n.numVal = Scanner.curNum;
	Scanner.skip(numberToken);
	Log.leaveParser("</number>");
	return n;
    }
   
    @Override
    void printTree() {
	Log.wTree("" + numVal);
    }
}
   
/*
 * A <variable>.
 */
   
class Variable extends Operand {
    String varName;
    VarDecl declRef = null;
    Expression index = null;
   
    @Override
    void check(DeclList curDecls) {

	Declaration d = curDecls.findDecl(varName,this);
	d.checkWhetherVariable(this);
	declRef = (VarDecl)d;

	Log.noteBinding(varName,lineNum,d.lineNum);

	if (index == null) {
	    type = d.type;
	} else {
	    index.check(curDecls);
	    Log.noteTypeCheck("a[e]", d.type, "a", index.type, "e", lineNum);
   
	    if (index.type == Types.intType) {
		// OK
	    } else {
		error("Only integers may be used as index.");
	    }
	    
	    if (d.type.mayBeIndexed()) {
		// OK
	    } else {
		error("Only arrays and pointers may be indexed.");
	    }
	    type = d.type.getElemType();
	}
    }
   
    @Override
    void genCode(FuncDecl curFunc) {
	//-- Must be changed in part 2:

	if(index != null){
	    index.genCode(curFunc);
	    if(declRef.type instanceof ArrayType){
		Code.genInstr("","leal",declRef.assemblerName+",%edx"
			      , varName+"[...]");
	    }else{
		Code.genInstr("","movl",declRef.assemblerName+",%edx"
			  ,varName+"[...]");
	    }
	    Code.genInstr("","movl","(%edx,%eax,4),%eax","");
	}else{
	    if(declRef.type instanceof ArrayType){
		Code.genInstr("","leal",declRef.assemblerName+",%eax"
			      , varName);
	    }else{
		Code.genInstr("","movl",declRef.assemblerName+",%eax"
			  ,varName);
	    }
	}
    }
    
   
    void genAddressCode(FuncDecl curFunc) {
	// Generate code to load the _address_ of the variable
	// rather than its value.
	if (index == null) {
	    Code.genInstr("", "leal", declRef.assemblerName+",%eax", varName);
	} else {
	    index.genCode(curFunc);
	    if (declRef.type instanceof ArrayType) {
		Code.genInstr("", "leal", declRef.assemblerName+",%edx",
			      varName+"[...]");
	    } else {
		Code.genInstr("", "movl", declRef.assemblerName+",%edx",
			      varName+"[...]");
	    }
	    Code.genInstr("", "leal", "(%edx,%eax,4),%eax", "");
	}
    }
   
    static Variable parse() {
           
	Log.enterParser("<variable>");
	Variable v = new Variable();
	v.varName = Scanner.curName;
	Scanner.skip(nameToken);
	if (Scanner.curToken == leftBracketToken) {
	    Scanner.skip(leftBracketToken);
	    v.index = Expression.parse();
	    Scanner.skip(rightBracketToken);
	}
	Log.leaveParser("</variable>");
	return v;
    }
   
    @Override
    void printTree() {
	Log.wTree(varName);
	if (index != null) {
	    Log.wTree("[");
	    index.printTree();
	    Log.wTree("]");
	}
    }
}
   
   
/*
 * An <address>.
 */
class Address extends Operand {
    Variable var;
   
    @Override
    void check(DeclList curDecls) {
	
	var.check(curDecls);
	type = new PointerType(var.type);
    }
   
    @Override
    void genCode(FuncDecl curFunc) {
	var.genAddressCode(curFunc);
    }
   
    static Address parse() {
	Log.enterParser("<address>");
	Address a = new Address();
	Scanner.skip(ampToken);
	a.var = Variable.parse();
	Log.leaveParser("</address>");
	return a;
    }
   
    @Override
    void printTree() {
	Log.wTree("&");
	var.printTree();
    }
}
   
   
/*
 * An <inner expr>.
 */
class InnerExpr extends Operand {
    Expression expr;
   
    @Override
    void check(DeclList curDecls) {
	expr.check(curDecls);
	type = expr.type;
    }
   
    @Override
    void genCode(FuncDecl curFunc) {
	expr.genCode(curFunc);
    }
   
    static InnerExpr parse() {
	Log.enterParser("<inner expr>");
	InnerExpr ie = new InnerExpr();
	Scanner.skip(leftParToken);
	ie.expr = Expression.parse();
	Scanner.skip(rightParToken);
   
	Log.leaveParser("</inner expr>");
	return ie;
    }
   
    @Override
    void printTree() {
	Log.wTree("(");
	expr.printTree();
	Log.wTree(")");
    }
}

