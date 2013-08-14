package AST;
import java.util.Map;

import AST.Visitor.TypeVisitor;
import AST.Visitor.Visitor;
import AST.Visitor.Tables.Class;
import AST.Visitor.Tables.Method;

public abstract class Exp extends ASTNode {
    public Exp(int ln) {
        super(ln);
    }
    public abstract void accept(Visitor v);
	public abstract String typeaccept(TypeVisitor typePrintVisitor, Map prog, int lvl, Class cls, Method meth);
}