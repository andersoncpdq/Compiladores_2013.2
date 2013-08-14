package AST;
import java.util.Map;

import AST.Visitor.TypeVisitor;
import AST.Visitor.Visitor;
import AST.Visitor.Tables.Class;
import AST.Visitor.Tables.Method;

public class Assign extends Statement {
  public Identifier i;
  public Exp e;

  public Assign(Identifier ai, Exp ae, int ln) {
    super(ln);
    i=ai; e=ae; 
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
  
  public void typeaccept(TypeVisitor tv, Map prog, int lvl, Class cls,
			Method meth) {
		tv.visit(this, prog, lvl, cls, meth);
	}
  
}

