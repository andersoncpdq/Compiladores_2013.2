package AST;

import java.util.Map;

import AST.Visitor.*;
import AST.Visitor.Tables.Class;
import AST.Visitor.Tables.Method;

public class MainClass extends ASTNode {
	public Identifier i1, i2;
	public Statement s;

	public MainClass(Identifier ai1, Identifier ai2, Statement as, int ln) {
		super(ln);
		i1 = ai1;
		i2 = ai2;
		s = as;
	}

	public void accept(Visitor v, Map prog, int lvl, Class cls, Method meth) {
		v.visit(this, prog, lvl, cls, meth);
	}

	public void typeaccept(TypeVisitor tv, Map prog, int lvl, Class cls,
			Method meth) {
		tv.visit(this, prog, lvl, cls, meth);

	}
}
