package AST;

import java.util.Map;

import AST.Visitor.*;
import AST.Visitor.Tables.Class;
import AST.Visitor.Tables.Method;

public class VarDecl extends ASTNode {
	public Type t;
	public Identifier i;

	public VarDecl(Type at, Identifier ai, int ln) {
		super(ln);
		t = at;
		i = ai;
	}

	public void accept(Visitor v, Map var, int lvl, int line) {
		v.visit(this, var, lvl, line);
	}

	public void typeaccept(TypeVisitor tv, Map prog, int lvl, Class cls,
			Method meth) {
		tv.visit(this, prog, lvl, cls, meth);
	}
}
