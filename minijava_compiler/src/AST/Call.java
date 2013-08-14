package AST;

import java.util.Map;

import AST.Visitor.TypeVisitor;
import AST.Visitor.Visitor;
import AST.Visitor.Tables.*;
import AST.Visitor.Tables.Class;

public class Call extends Exp {
	public Exp e;
	public Identifier i;
	public ExpList el;

	public Call(Exp ae, Identifier ai, ExpList ael, int ln) {
		super(ln);
		e = ae;
		i = ai;
		el = ael;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}

	public String typeaccept(TypeVisitor v, Map prog, int lvl, Class cls,
			Method meth) {
		return v.visit(this, prog, lvl, cls, meth);
	}
}
