package AST.Visitor;

import java.util.Map;

import AST.*;
import AST.Visitor.Tables.Class;
import AST.Visitor.Tables.Method;

public interface Visitor {
  // Return added for the toy example language---they are subsumed in the MiniJava AST by the MethodDecl nodes.
  public void visit(Return n);
  public void visit(Program n, Map prog);
  public void visit(MainClass n, Map prog, int lvl, Class cls, Method meth);
  public void visit(ClassDeclSimple n, Map prog, int lvl, Class cls, Method meth);
  public void visit(ClassDeclExtends n, Map prog, int lvl, Class cls, Method meth);
  public void visit(VarDecl n, Map varlist, int line, int lvl);
  public void visit(MethodDecl n, Map methodlist, int line, int lvl);
  public void visit(Formal n, Map methodlist, int line, int lvl);
  public void visit(IntArrayType n);
  public void visit(BooleanType n);
  public void visit(IntegerType n);
  public void visit(IdentifierType n);
  public void visit(Block n);
  public void visit(If n);
  public void visit(While n);
  public void visit(Print n);
  public void visit(Assign n);
  public void visit(ArrayAssign n);
  public void visit(And n);
  public void visit(LessThan n);
  public void visit(Plus n);
  public void visit(Minus n);
  public void visit(Times n);
  public void visit(ArrayLookup n);
  public void visit(ArrayLength n);
  public void visit(Call n);
  public void visit(IntegerLiteral n);
  public void visit(True n);
  public void visit(False n);
  public void visit(IdentifierExp n);
  public void visit(This n);
  public void visit(NewArray n);
  public void visit(NewObject n);
  public void visit(Not n);
  public void visit(Identifier id);
}
