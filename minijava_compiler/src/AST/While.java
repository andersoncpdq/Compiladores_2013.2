package AST;

import java.util.Map;

import AST.Visitor.TypeVisitor;
import AST.Visitor.Visitor;
import AST.Visitor.Tables.Class;
import AST.Visitor.Tables.Method;

public class While extends Statement {
	public Exp e;
	public Statement s;

	public While(Exp ae, Statement as, int ln) {
		super(ln);
		e = ae;
		s = as;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}

	public void typeaccept(TypeVisitor tv, Map prog, int lvl, Class cls,
			Method meth) {
		tv.visit(this, prog, lvl, cls, meth);
	}
}
