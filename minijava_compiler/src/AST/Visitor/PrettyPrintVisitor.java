package AST.Visitor;

import java.util.Map;

import AST.*;
import AST.Visitor.Tables.Method;
import AST.Visitor.Tables.Class;
import AST.Visitor.Tables.Variable;

public class PrettyPrintVisitor implements Visitor {
	// Return added for the toy example language---they are subsumed in the
	// MiniJava AST by the MethodDecl nodes.
	// Exp e;
	public void visit(Return n) {
		n.e.accept(this);
	}

	// MainClass m;
	// ClassDeclList cl;
	public void visit(Program n, Map prog) {
		n.m.accept(this, prog, 0, null, null);
		for (int i = 0; i < n.cl.size(); i++) {
			n.cl.elementAt(i).accept(this, prog, 0, null, null);
		}
	}

	// Identifier i1,i2;
	// Statement s;
	public void visit(MainClass n, Map prog, int lvl, Class cls, Method meth) {
		Class main = new Class(true);
		main.line = n.i1.line();
		if (prog.containsKey(n.i1.s)) {
			System.out.println("Erro na linha" + n.line() + ". A classe \""
					+ n.i1.s + "\" foi duplamente declarada.");
			while (prog.containsKey(main.id))
				main.id += "#";
		}
		main.id = n.i1.s;
		n.i1.accept(this);

		Variable var = new Variable();
		var.id = n.i2.s;
		var.type = "String[]";
		var.line = n.i2.line();
		n.i2.accept(this);

		Method method = new Method();
		method.id = "main";
		method.type = "void";
		method.arg.put(var.id, var);
		method.line = n.i2.line();
		n.s.accept(this);

		prog.put(main.id, main);
	}

	// Identifier i;
	// VarDeclList vl;
	// MethodDeclList ml;
	public void visit(ClassDeclSimple n, Map prog, int lvl, Class cls,
			Method meth) {
		lvl++;
		Class c = new Class(false);
		if (prog.containsKey(n.i.s)) {
			System.out.println("Erro na linha" + n.line() + ". A classe \""
					+ n.i.s + "\" foi duplamente declarada.");
			while (prog.containsKey(c.id)) {
				c.id += "#";
			}
		}
		c.id = n.i.s;
		n.i.accept(this);
		
		for (int i = 0; i < n.vl.size(); i++) {
			n.vl.elementAt(i).accept(this, c.var, c.line, lvl);
		}
		for (int i = 0; i < n.ml.size(); i++) {
			n.ml.elementAt(i).accept(this, c.method, c.line, lvl);
		}
		prog.put(c.id, c);
	}

	// Identifier i;
	// Identifier j;
	// VarDeclList vl;
	// MethodDeclList ml;
	public void visit(ClassDeclExtends n, Map prog, int lvl, Class cls,
			Method meth) {
		lvl++;
		Class c = new Class(false);
		if (prog.containsKey(n.i.s)) {
			System.out.println("Erro na linha" + n.line() + ". A classe \""
					+ n.i.s + "\" foi duplamente declarada.");
			while (prog.containsKey(c.id)) {
				c.id += "#";
			}
		}
		c.id = n.i.s;
		n.i.accept(this);

		c.extended = n.j.s;
		n.j.accept(this);
		for (int i = 0; i < n.vl.size(); i++) {
			n.vl.elementAt(i).accept(this, c.var, c.line, lvl);
		}
		for (int i = 0; i < n.ml.size(); i++) {
			n.ml.elementAt(i).accept(this, c.method, c.line, lvl);
		}
		prog.put(c.id, c);
	}

	// Type t;
	// Identifier i;
	public void visit(VarDecl n, Map varlist, int line, int lvl) {
		lvl++;
		Variable var = new Variable();

		var.id = n.i.s;
		n.i.accept(this);

		var.type = n.t.type();
		n.t.accept(this);

		if (varlist.containsKey(var.id)) {
			System.out.println("Erro na linha" + n.line() + ". A variavel \""
					+ n.i.s + "\" foi duplamente declarada.");
			while (varlist.containsKey(var.id)) {
				var.id += "#";
			}
		}
		varlist.put(var.id, var);
	}

	// Type t;
	// Identifier i;
	// FormalList fl;
	// VarDeclList vl;
	// StatementList sl;
	// Exp e;
	public void visit(MethodDecl n, Map methodlist, int line, int lvl) {
		lvl++;
		Method meth = new Method();
		meth.line = n.line();
		
		meth.id = n.i.s;
		n.i.accept(this);
		meth.type = n.t.type();
		n.t.accept(this);
		
		for (int i = 0; i < n.fl.size(); i++) {
			n.fl.elementAt(i).accept(this, meth.arg, meth.line, lvl);
		}
		for (int i = 0; i < n.vl.size(); i++) {
			n.vl.elementAt(i).accept(this, meth.var, meth.line, lvl);
		}
		for (int i = 0; i < n.sl.size(); i++) {
			n.sl.elementAt(i).accept(this);
		}
		methodlist.put(meth.id, meth);
		n.e.accept(this);
	}

	// Type t;
	// Identifier i;
	public void visit(Formal n, Map arglist, int line, int lvl) {
		Variable var = new Variable();
		var.id = n.i.s;
		var.type = n.t.type();
		var.line = n.line();
		if(arglist.containsKey(var.id)){
			System.out.println("Erro na linha" + n.line() + ". O argumento \""
					+ n.i.s + "\" foi duplamente declarado.");
			while (arglist.containsKey(var.id)) {
				var.id += "#";
			}
		}
		arglist.put(var.id, var);
		n.t.accept(this);
		n.i.accept(this);
	}

	public void visit(IntArrayType n) {
	}

	public void visit(BooleanType n) {
	}

	public void visit(IntegerType n) {
	}

	// String s;
	public void visit(IdentifierType n) {
	}

	// StatementList sl;
	public void visit(Block n) {
		for (int i = 0; i < n.sl.size(); i++) {
			n.sl.elementAt(i).accept(this);
		}
	}

	// Exp e;
	// Statement s1,s2;
	public void visit(If n) {
		n.e.accept(this);
		n.s1.accept(this);
		n.s2.accept(this);
	}

	// Exp e;
	// Statement s;
	public void visit(While n) {
		n.e.accept(this);
		n.s.accept(this);
	}

	// Exp e;
	public void visit(Print n) {
		n.e.accept(this);
	}

	// Identifier i;
	// Exp e;
	public void visit(Assign n) {
		n.i.accept(this);
		n.e.accept(this);
	}

	// Identifier i;
	// Exp e1,e2;
	public void visit(ArrayAssign n) {
		n.i.accept(this);
		n.e1.accept(this);
		n.e2.accept(this);
	}

	// Exp e1,e2;
	public void visit(And n) {
		n.e1.accept(this);
		n.e2.accept(this);
	}

	// Exp e1,e2;
	public void visit(LessThan n) {
		n.e1.accept(this);
		n.e2.accept(this);
	}

	// Exp e1,e2;
	public void visit(Plus n) {
		n.e1.accept(this);
		n.e2.accept(this);
	}

	// Exp e1,e2;
	public void visit(Minus n) {
		n.e1.accept(this);
		n.e2.accept(this);
	}

	// Exp e1,e2;
	public void visit(Times n) {
		n.e1.accept(this);
		n.e2.accept(this);
	}

	// Exp e1,e2;
	public void visit(ArrayLookup n) {
		n.e1.accept(this);
		n.e2.accept(this);
	}

	// Exp e;
	public void visit(ArrayLength n) {
		n.e.accept(this);
	}

	// Exp e;
	// Identifier i;
	// ExpList el;
	public void visit(Call n) {
		n.e.accept(this);
		n.i.accept(this);
		for (int i = 0; i < n.el.size(); i++) {
			n.el.elementAt(i).accept(this);
		}
	}

	// int i;
	public void visit(IntegerLiteral n) {
	}

	public void visit(True n) {
	}

	public void visit(False n) {
	}

	// String s;
	public void visit(IdentifierExp n) {
	}

	public void visit(This n) {
	}

	// Exp e;
	public void visit(NewArray n) {
		n.e.accept(this);
	}

	// Identifier i;
	public void visit(NewObject n) {
	}

	// Exp e;
	public void visit(Not n) {
		n.e.accept(this);
	}

	// String s;
	public void visit(Identifier n) {
	}

	

}