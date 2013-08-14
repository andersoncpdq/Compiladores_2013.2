package AST;

import java.util.Map;

import AST.Visitor.TypeCheckVisitor;
import AST.Visitor.TypeVisitor;
import AST.Visitor.Visitor;

public class Program extends ASTNode {
	public MainClass m;
	public ClassDeclList cl;

	public Program(MainClass am, ClassDeclList acl, int ln) {
		super(ln);
		m = am;
		cl = acl;
	}

	public void accept(Visitor v, Map prog) {
		v.visit(this, prog);
	}

	public void typeaccept(TypeVisitor v, Map prog) {
		v.visit(this, prog);
	}
}
