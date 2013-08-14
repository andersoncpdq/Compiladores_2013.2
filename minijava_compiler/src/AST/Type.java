package AST;
import java.util.Map;

import AST.Visitor.TypeVisitor;
import AST.Visitor.Visitor;
import AST.Visitor.Tables.Class;
import AST.Visitor.Tables.Method;

public abstract class Type extends ASTNode {
    public Type(int ln) {
        super(ln);
    }
    public abstract void accept(Visitor v);
    public abstract String typeaccept(TypeVisitor tv, Map prog, int lvl, Class cls,
			Method meth);
    public abstract String type();
}
