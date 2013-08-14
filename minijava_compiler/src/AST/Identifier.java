package AST;

import java.util.Map;

import AST.Visitor.*;
import AST.Visitor.Tables.Class;
import AST.Visitor.Tables.Method;

public class Identifier extends ASTNode {
	public String s;

	public Identifier(String as, int ln) {
		super(ln);
		s = as;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}

	public String toString() {
		return s;
	}

	public void typeaccept(TypeVisitor tv, Map prog, int lvl, Class cls,
			Method meth) {
		tv.visit(this, prog, lvl, cls, meth);
	}
}
