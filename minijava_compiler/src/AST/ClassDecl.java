package AST;

import java.util.Map;

import AST.Visitor.TypeVisitor;
import AST.Visitor.Visitor;

import AST.Visitor.Tables.Class;
import AST.Visitor.Tables.Method;

public abstract class ClassDecl extends ASTNode {
	public ClassDecl(int ln) {
		super(ln);
	}

	public abstract void accept(Visitor v, Map prog, int i, Class object,
			Method object2);

	public abstract void typeaccept(TypeVisitor tv, Map prog, int lvl,
			Class cls, Method meth);
}
