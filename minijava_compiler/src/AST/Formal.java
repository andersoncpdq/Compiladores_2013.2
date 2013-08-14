package AST;

import java.util.Map;

import AST.Visitor.TypeVisitor;
import AST.Visitor.Visitor;
import AST.Visitor.Tables.Class;
import AST.Visitor.Tables.Method;

public class Formal extends ASTNode {
	public Type t;
	public Identifier i;

	public Formal(Type at, Identifier ai, int ln) {
		super(ln);
		t = at;
		i = ai;
	}

	public void accept(Visitor v, Map arglist, int line, int lvl) {
		v.visit(this, arglist, line, lvl);
	}

	public void typeaccept(TypeVisitor tv, Map prog, int lvl, Class cls,
			Method meth) {
		tv.visit(this, prog, lvl, cls, meth);
	}
}
