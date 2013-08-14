package AST;
import java.util.Map;

import AST.Visitor.*;
import AST.Visitor.Tables.Class;
import AST.Visitor.Tables.Method;

public abstract class Statement extends ASTNode {
    public Statement(int ln) {
        super(ln);
    }
    public abstract void accept(Visitor v);
    public abstract void typeaccept(TypeVisitor tv, Map prog, int lvl, Class cls,
			Method meth);
}
