package AST;

import java.util.Map;

import AST.Visitor.TypeVisitor;
import AST.Visitor.Visitor;
import AST.Visitor.Tables.Class;
import AST.Visitor.Tables.Method;

public class True extends Exp {
	public True(int ln) {
		super(ln);
	}

	public void accept(Visitor v) {
		v.visit(this);
	}

	public String typeaccept(TypeVisitor tv, Map prog, int lvl, Class cls,
			Method meth) {
		return tv.visit(this, prog, lvl, cls, meth);
	}
}
