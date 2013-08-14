package AST;
import java.util.Map;

import AST.Visitor.TypeVisitor;
import AST.Visitor.Visitor;
import AST.Visitor.Tables.Class;
import AST.Visitor.Tables.Method;

public class If extends Statement {
  public Exp e;
  public Statement s1,s2;

  public If(Exp ae, Statement as1, Statement as2, int ln) {
    super(ln);
    e=ae; s1=as1; s2=as2;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
  
  public void typeaccept(TypeVisitor tv, Map prog, int lvl, Class cls,
			Method meth) {
		tv.visit(this, prog, lvl, cls, meth);
	}
}

