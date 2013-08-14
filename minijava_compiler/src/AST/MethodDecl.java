package AST;

import java.util.Map;

import AST.Visitor.TypeVisitor;
import AST.Visitor.Visitor;
import AST.Visitor.Tables.Class;
import AST.Visitor.Tables.Method;

public class MethodDecl extends ASTNode {
	public Type t;
	public Identifier i;
	public FormalList fl;
	public VarDeclList vl;
	public StatementList sl;
	public Exp e;

	public MethodDecl(Type at, Identifier ai, FormalList afl, VarDeclList avl,
			StatementList asl, Exp ae, int ln) {
		super(ln);
		t = at;
		i = ai;
		fl = afl;
		vl = avl;
		sl = asl;
		e = ae;
	}

	public void accept(Visitor v, Map method, int line, int lvl) {
		v.visit(this, method, line, lvl);
	}

	public void typeaccept(TypeVisitor tv, Map prog, int lvl, Class cls,
			Method meth) {
		tv.visit(this, prog, lvl, cls, meth);
	}

}
