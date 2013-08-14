package AST;

import java.util.Map;

import AST.Visitor.TypeVisitor;
import AST.Visitor.Visitor;
import AST.Visitor.Tables.Class;
import AST.Visitor.Tables.Method;

public class Plus extends Exp {
	public Exp e1, e2;

	public Plus(Exp ae1, Exp ae2, int ln) {
		super(ln);
		e1 = ae1;
		e2 = ae2;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}

	public String typeaccept(TypeVisitor tv, Map prog, int lvl, Class cls,
			Method meth) {
		return tv.visit(this, prog, lvl, cls, meth);
	}
}
