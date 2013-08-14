package AST;
import java.util.Map;

import AST.Visitor.TypeVisitor;
import AST.Visitor.Visitor;
import AST.Visitor.Tables.Class;
import AST.Visitor.Tables.Method;

public class ArrayAssign extends Statement {
  public Identifier i;
  public Exp e1,e2;

  public ArrayAssign(Identifier ai, Exp ae1, Exp ae2, int ln) {
    super(ln);
    i=ai; e1=ae1; e2=ae2;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
  
  public void typeaccept(TypeVisitor tv, Map prog, int lvl, Class cls,
			Method meth) {
		tv.visit(this, prog, lvl, cls, meth);
	}
}

