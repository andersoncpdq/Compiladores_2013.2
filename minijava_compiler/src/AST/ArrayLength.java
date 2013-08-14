package AST;

import java.util.Map;

import AST.Visitor.TypeVisitor;
import AST.Visitor.Visitor;
import AST.Visitor.Tables.Class;
import AST.Visitor.Tables.Method;

public class ArrayLength extends Exp {
	public Exp e;

	public ArrayLength(Exp ae, int ln) {
		super(ln);
		e = ae;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}

	@Override
	public String typeaccept(TypeVisitor v, Map prog, int lvl, Class cls,
			Method meth) {
		return v.visit(this, prog, lvl, cls, meth);
	}

}
