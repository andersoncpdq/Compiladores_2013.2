package AST.Visitor;

import java.util.Map;

import AST.*;
import AST.Visitor.Tables.Class;
import AST.Visitor.Tables.Method;

public interface TypeVisitor {
	// Return added for the toy example language---they are subsumed in the
	// MiniJava AST by the MethodDecl nodes.
	public void visit(Program n, Map program);

	public void visit(MainClass n, Map prog, int lvl, Class cls, Method meth);

	public void visit(ClassDeclSimple n, Map prog, int lvl, Class cls,
			Method meth);

	public void visit(ClassDeclExtends n, Map prog, int lvl, Class cls,
			Method meth);

	public void visit(VarDecl n, Map prog, int lvl, Class cls, Method meth);

	public void visit(MethodDecl n, Map prog, int lvl, Class cls, Method meth);

	public void visit(Formal n, Map prog, int lvl, Class cls, Method meth);

	public String visit(IntArrayType n, Map prog, int lvl, Class cls, Method meth);

	public String visit(BooleanType n, Map prog, int lvl, Class cls, Method meth);

	public String visit(IntegerType n, Map prog, int lvl, Class cls, Method meth);

	public String visit(IdentifierType n, Map prog, int lvl, Class cls,
			Method meth);

	public void visit(Block n, Map prog, int lvl, Class cls, Method meth);

	public void visit(If n, Map prog, int lvl, Class cls, Method meth);

	public void visit(While n, Map prog, int lvl, Class cls, Method meth);

	public void visit(Print n, Map prog, int lvl, Class cls, Method meth);

	public void visit(Assign n, Map prog, int lvl, Class cls, Method meth);

	public void visit(ArrayAssign n, Map prog, int lvl, Class cls, Method meth);

	public String visit(And n, Map prog, int lvl, Class cls, Method meth);

	public String visit(LessThan n, Map prog, int lvl, Class cls, Method meth);

	public String visit(Plus n, Map prog, int lvl, Class cls, Method meth);

	public String visit(Minus n, Map prog, int lvl, Class cls, Method meth);

	public String visit(Times n, Map prog, int lvl, Class cls, Method meth);

	public String visit(ArrayLookup n, Map prog, int lvl, Class cls, Method meth);

	public String visit(ArrayLength n, Map prog, int lvl, Class cls, Method meth);

	public String visit(Call n, Map prog, int lvl, Class cls, Method meth);

	public String visit(IntegerLiteral n, Map prog, int lvl, Class cls,
			Method meth);

	public String visit(True n, Map prog, int lvl, Class cls, Method meth);

	public String visit(False n, Map prog, int lvl, Class cls, Method meth);

	public String visit(IdentifierExp n, Map prog, int lvl, Class cls,
			Method meth);

	public String visit(This n, Map prog, int lvl, Class cls, Method meth);

	public String visit(NewArray n, Map prog, int lvl, Class cls, Method meth);

	public String visit(NewObject n, Map prog, int lvl, Class cls, Method meth);

	public String visit(Not n, Map prog, int lvl, Class cls, Method meth);

	public String visit(Identifier n, Map prog, int lvl, Class cls, Method meth);
}
