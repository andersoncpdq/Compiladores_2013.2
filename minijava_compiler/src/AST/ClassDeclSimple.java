package AST;
import java.util.Map;

import AST.Visitor.TypeVisitor;
import AST.Visitor.Visitor;
import AST.Visitor.Tables.Class;
import AST.Visitor.Tables.Method;

public class ClassDeclSimple extends ClassDecl {
  public Identifier i;
  public VarDeclList vl;  
  public MethodDeclList ml;
 
  public ClassDeclSimple(Identifier ai, VarDeclList avl, MethodDeclList aml, int ln) {
    super(ln);
    i=ai; vl=avl; ml=aml;
  }

  public void accept(Visitor v, Map prog, int lvl, Class cls,
			Method meth) {
    v.visit(this, prog, lvl,  cls, meth);
  }

public void typeaccept(TypeVisitor tv, Map prog, int lvl, Class cls, Method meth) {
	tv.visit(this, prog, lvl, cls, meth);
}
}
