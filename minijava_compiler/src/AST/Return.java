package AST;
import java.util.Map;

import AST.Visitor.TypeVisitor;
import AST.Visitor.Visitor;
import AST.Visitor.Tables.Class;
import AST.Visitor.Tables.Method;

public class Return extends Statement {
  public Exp e;

  public Return(Exp re, int ln) {
    super(ln);
    e=re; 
  }
  
  public void accept(Visitor v) {
    v.visit(this);
  }

public void typeaccept(TypeVisitor tv, Map prog, int lvl, Class cls, Method meth) {
}
}

