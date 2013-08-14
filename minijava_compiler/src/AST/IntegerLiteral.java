package AST;

import java.util.Map;

import AST.Visitor.TypeVisitor;
import AST.Visitor.Visitor;
import AST.Visitor.Tables.Class;
import AST.Visitor.Tables.Method;

public class IntegerLiteral extends Exp {
	public int i;

	public IntegerLiteral(int ai, int ln) {
		super(ln);
		i = ai;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}

	public String typeaccept(TypeVisitor v, Map prog, int lvl, Class cls,
			Method meth) {
		return v.visit(this, prog, lvl, cls, meth);
	}
}
