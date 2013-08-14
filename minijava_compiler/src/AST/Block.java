package AST;
import java.util.Map;

import AST.Visitor.TypeVisitor;
import AST.Visitor.Visitor;
import AST.Visitor.Tables.Class;
import AST.Visitor.Tables.Method;

public class Block extends Statement {
  public StatementList sl;

  public Block(StatementList asl, int ln) {
    super(ln);
    sl=asl;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
  
  public void typeaccept(TypeVisitor tv, Map prog, int lvl, Class cls,
			Method meth) {
		tv.visit(this, prog, lvl, cls, meth);
	}
  
}

