
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
        Scanner.readNext();
        Scanner.readNext();
    }
 
    public static void finish() {
    //-- Must be changed in part 1+2:
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
    }
    }
         
    @Override void genCode(FuncDecl curFunc) {
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
        //hvis lista er tom
        if (firstDecl == null) {
            firstDecl = d;
            lastDecl = d;
        } else {//lista er ikke tom
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
 
    Declaration findDecl(String name, SyntaxUnit use) {
    //-- Must be changed in part 2:
    return null;
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
    }
 
    static LocalDeclList parse() {
    LocalDeclList ldl = new LocalDeclList();
 
    while (Scanner.curToken == intToken) {
        DeclType ts = DeclType.parse();
        ldl.addDecl(Declaration.parse(ts));
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
    }
 
    static ParamDeclList parse() {
    ParamDeclList pdl = new ParamDeclList();
 
    while (Scanner.curToken == intToken) {
        DeclType ts = DeclType.parse();
        pdl.addDecl(Declaration.parse(ts));
    }
    return pdl;
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
     *                    (The method will give an error message if the
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
    Variable v;
    boolean isArray = false;
    int numElems = 0;
 
    VarDecl(String n) {
    super(n);
    }
 
    @Override
    void check(DeclList curDecls) {
    //-- Must be changed in part 2:
    }
 
    @Override
    void printTree() {
        typeSpec.printTree();
        Log.wTree(" " + name);
        if (isArray) {
            Log.wTree("[");
            v.index.printTree();
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
    }
 
    static GlobalVarDecl parse(DeclType dt) {
    Log.enterParser("<var decl>");
        GlobalVarDecl gvd = new GlobalVarDecl(Scanner.curName);
        gvd.typeSpec = dt;
       gvd.v = Variable.parse();
       if (gvd.v.index != null) { //sjekk om det er array
           gvd.isArray = true;
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
        lvd.v = Variable.parse();
       if (lvd.v.index != null) { //sjekk om det er array
           lvd.isArray = true;
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
        dt = DeclType.parse();
        ParamDecl pd = new ParamDecl(Scanner.curName);
        pd.typeSpec = dt;
        Scanner.readNext();
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
      
    FuncDecl(String n) {
    // Used for user functions:
    super(n);
    assemblerName = (AlboC.underscoredGlobals() ? "_" : "") + n;
        funcParams = null;
    }
 
    @Override
    int declSize() {
    return 0;
    }
 
    @Override
    void check(DeclList curDecls) {
    //-- Must be changed in part 2:
    }
 
    @Override
    void checkWhetherFunction(int nParamsUsed, SyntaxUnit use) {
    //-- Must be changed in part 2:
    }
     
    @Override
    void checkWhetherVariable(SyntaxUnit use) {
    //-- Must be changed in part 2:
    }
 
    @Override
    void genCode(FuncDecl curFunc) {
    //-- Must be changed in part 2:
    }
 
    static FuncDecl parse(DeclType ts) {
        Log.enterParser("<func decl>");
         
    FuncDecl fd = new FuncDecl(Scanner.curName);
        fd.typeSpec = ts;
        Scanner.skip(nameToken);
        Scanner.skip(leftParToken);
 
        if (Scanner.curToken == intToken) {
            fd.funcParams = new ParamDeclList();
            fd.funcParams.addDecl(ParamDecl.parse(ts));
 
            while (Scanner.curToken == commaToken) {
                Scanner.skip(commaToken);
                fd.funcParams.addDecl(ParamDecl.parse(ts));
            }
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
 
 
/*
 * A <statm list>.
 */
class StatmList extends SyntaxUnit {
 
    Statement firstStatm = null;
 
    @Override
    void check(DeclList curDecls) {
    //-- Must be changed in part 2:
    }
 
    @Override
    void genCode(FuncDecl curFunc) {
    //-- Must be changed in part 2:
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
    }
 
    @Override
    void genCode(FuncDecl curFunc) {
    //-- Must be changed in part 2:
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
 
    }
    @Override
    void genCode(FuncDecl curFunc) {
    //-- Must be changed in part 2:
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
 
class FuncBody extends SyntaxUnit {
    String funcname;
    DeclType dt = null;
    LocalDeclList ldl = null;
    StatmList sl = null;
 
    @Override
    void check(DeclList curDecls) {
 
    }
    @Override
    void genCode(FuncDecl curFunc) {
    //-- Must be changed in part 2:
    }
 
    static FuncBody parse() {
    Log.enterParser("<func-body>");
 
    FuncBody fb = new FuncBody();
        fb.ldl = new LocalDeclList();
        Scanner.skip(leftCurlToken);
        while (Scanner.curToken == intToken) {
            fb.dt = DeclType.parse();
            fb.ldl.addDecl(LocalVarDecl.parse(fb.dt));
        }
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
 
class Assignment extends SyntaxUnit {
    LhsVariable lhs = null;
    Expression e = null;
 
    @Override
    void check(DeclList curDecls) {
 
    }
    @Override
    void genCode(FuncDecl curFunc) {
    //-- Must be changed in part 2:
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
    }
   
    @Override
    void genCode(FuncDecl curFunc) {
    //-- Must be changed in part 2:
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
    }
   
    @Override
    void genCode(FuncDecl curFunc) {
    //-- Must be changed in part 2:
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
    }
   
    @Override
    void genCode(FuncDecl curFunc) {
    //-- Must be changed in part 2:
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
    }
   
    @Override
    void genCode(FuncDecl curFunc) {
    //-- Must be changed in part 2:
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
   
    @Override void check(DeclList curDecls) {
    //-- Must be changed in part 2:
    }
   
    @Override void genCode(FuncDecl curFunc) {
    //-- Must be changed in part 2:
    }
   
    static ExprList parse() {
    Expression lastExpr = null;
   
    Log.enterParser("<expr list>");
        ExprList el = new ExprList();
        if (Token.isOperand(Scanner.curToken) || Token.isPrefixOperator(Scanner.curToken))  {
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
    }
   
    @Override
    void genCode(FuncDecl curFunc) {
    //-- Must be changed in part 2:
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
   
    @Override void check(DeclList curDecls) {
    //-- Must be changed in part 2:
    }
   
    @Override void genCode(FuncDecl curFunc) {
    //-- Must be changed in part 2:
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
            factors.get(i).printTree();
            if (i != factors.size()-1) {
                opers.get(i).printTree();
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
   
    @Override void check(DeclList curDecls) {
    //-- Must be changed in part 2:
    }
   
    @Override void genCode(FuncDecl curFunc) {
    //-- Must be changed in part 2:
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
            prims.get(i).printTree();
            if (i != prims.size()-1) {
                opers.get(i).printTree();
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
   
    @Override
    void check(DeclList curDecls) {
    //-- Must be changed in part 2:
    }
   
    @Override
    void genCode(FuncDecl curFunc) {
    //-- Must be changed in part 2:
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
        // fordi vi bruker get(index i)fra Term og Parse
        size = 0;
    }
 
    public void add(Operator o) {
        if (firstOpr== null) { //tom liste
            firstOpr = lastOpr = o;
        } else { //ikke tom liste
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
            while (count != i) {
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
/*
 * Hei Natasja!
 * Her er et overblikk over det jeg har gjort:
 * lagd 3 klasser: FactorList, PrimaryList og OperatorList.
 * Disse er nesten identiske, bortsett fra Factor => Primary => Oper
 * Så kanskje vi skulle hatt èn generisk listeklasse?
 * Aner heller ikke om de burde extende noe annet enn SyntaxUnit
 * Jeg har fjernet ArrayList fra klassene Term og Factor.
 * Jeg har lagd metodene i denne klassen helt like (i teorien i hvert fall)
 * som de vi brukte til ArrayList, slik at jeg ikke forandret på mer enn
 * nødvendig.
 */
class FactorList extends SyntaxUnit {
    Factor first;
    Factor last;
    int size; //vi bruker size senere
    FactorList() {
        first = last = null;
        size = 0;
    }
 
    public void add(Factor f) {
        if (first== null) { //tom liste
            first = last = f;
        } else { //ikke tom liste
            last.nextFac = f;
            f = last;
        }
        size++;
    }
 
    public Factor get(int i) {
        //skal hente ut Factor på plass index
        int count = 0;
       Factor current = first;
        if (i > size) {
            throw new IndexOutOfBoundsException();
            //hvis i er større enn lista KASTES det unntak
            // se så fancy, ooo!
        } else {
            while (count != i) {
                // teller oppover til i, går èn bortover
                // for hver gang
                current = current.nextFac;
                count++;
            }
            return current;
            //og så returnerer den vi endte opp på :)
        }
    }
    public int size() { return size;}
    //brukes i for-løkka
 
    @Override
        void printTree() {
        // er bare lagt til fordi den ikke ville kompilere uten
 
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
  class PrimaryList extends SyntaxUnit {
    Primary first;
    Primary last;
    int size;
    PrimaryList() {
        first = last = null;
        size = 0;
    }
 
    public void add(Primary p) {
        if (first== null) { //tom liste
            first = last = p;
        } else { //ikke tom liste
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
        //endres i del 2
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
        //endres i del 2
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
        //endres i del 2
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
    }
   
    @Override
    void genCode(FuncDecl curFunc) {
    //-- Must be changed in part 2:
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
        elist.printTree();
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
    }
   
    @Override
    void genCode(FuncDecl curFunc) {
    //-- Must be changed in part 2:
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
