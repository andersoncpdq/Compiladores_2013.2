package AST;
import java.util.Map;

import AST.Visitor.TypeVisitor;
import AST.Visitor.Visitor;
import AST.Visitor.Tables.Class;
import AST.Visitor.Tables.Method;

public class ClassDeclExtends extends ClassDecl {
  public Identifier i;
  public Identifier j;
  public VarDeclList vl;  
  public MethodDeclList ml;
 
  public ClassDeclExtends(Identifier ai, Identifier aj, 
                  VarDeclList avl, MethodDeclList aml, int ln) {
    super(ln);
    i=ai; j=aj; vl=avl; ml=aml;
  }

  public void accept(Visitor v, Map prog, int lvl, Class cls, Method meth) {
    v.visit(this, prog, lvl, cls, meth);
  }

public void typeaccept(TypeVisitor tv, Map prog, int lvl, Class cls,
		Method meth) {
	tv.visit(this, prog, lvl, cls, meth);
}



}
