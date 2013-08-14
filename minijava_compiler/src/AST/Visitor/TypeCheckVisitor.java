package AST.Visitor;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import AST.*;
import AST.Visitor.Tables.Class;
import AST.Visitor.Tables.Method;
import AST.Visitor.Tables.Variable;

public class TypeCheckVisitor implements TypeVisitor {

	private final String inteiro = "int";
	private final String intarray = "int[]";
	private final String bool = "boolean";
	private final String nulo = "null";

	// Return added for the toy example language---they are subsumed in the
	// MiniJava AST by the MethodDecl nodes.
	// Exp e;
	public void visit(Return n) {
		n.e.typeaccept(this, null, 0, null, null);
	}

	public boolean isClassRepeated(ClassDeclList classlist, String classname) {
		int count = 0;
		Identifier id = null;
		for (int i = 0; i < classlist.size(); i++) {
			if (classlist.elementAt(i) instanceof ClassDeclSimple) {
				ClassDeclSimple cds = (ClassDeclSimple) classlist.elementAt(i);
				if (count == 0) {
					count = 1;
					id = new Identifier(cds.i.s, cds.line());
				} else {
					System.out.println("A Classe \"" + cds.i.s
							+ "\" foi definida nas linhas " + id.line()
							+ " e, " + cds.line());
					return true;
				}
			} else if (classlist.elementAt(i) instanceof ClassDeclExtends) {
				ClassDeclExtends cde = (ClassDeclExtends) classlist
						.elementAt(i);
				if (count == 0) {
					count = 1;
					id = new Identifier(cde.i.s, cde.line());
				} else {
					System.out.println("A Classe \"" + cde.i.s
							+ "\" foi definida nas linhas " + id.line()
							+ " e, " + cde.line());
					return true;
				}
			}
		}
		return false;
	}

	public boolean isVarRepeated(VarDeclList varlist, String varname) {
		int count = 0;
		Identifier id = null;
		for (int i = 0; i < varlist.size(); i++) {
			VarDecl var = varlist.elementAt(i);
			if (count == 0) {
				count = 1;
				id = new Identifier(var.i.s, var.line());
			} else {
				System.out.println("A variavel \"" + var.i.s
						+ "\" foi declarada nas linhas" + id.line() + " e, "
						+ var.line());
				return true;
			}
		}
		return false;
	}

	public boolean isMethRepeated(MethodDeclList methlist, String methname) {
		int count = 0;
		Identifier id = null;
		for (int i = 0; i < methlist.size(); i++) {
			MethodDecl meth = methlist.elementAt(i);
			if (count == 0) {
				count = 1;
				id = new Identifier(meth.i.s, meth.line());
			} else {
				System.out.println("O metodo \"" + meth.i.s
						+ "\" foi declarada nas linhas" + id.line() + " e, "
						+ meth.line());
				return true;
			}
		}

		return false;
	}

	// MainClass m;
	// ClassDeclList cl;
	public void visit(Program n, Map program) {
		n.m.typeaccept(this, program, 0, null, null);
		for (int i = 0; i < n.cl.size(); i++) {
			n.cl.elementAt(i).typeaccept(this, program, 0, null, null);
		}
	}

	// Identifier i1,i2;
	// Statement s;
	public void visit(MainClass n, Map prog, int lvl, Class classe,
			Method metodo) {
		Class mainclass = (Class) prog.get(n.i1.s); // nome da classe
		Method mainmeth = (Method) mainclass.method.get("main");

		n.i1.typeaccept(this, prog, lvl, mainclass, null);
		n.i2.typeaccept(this, prog, lvl, mainclass, mainmeth);
		n.s.typeaccept(this, prog, lvl, mainclass, mainmeth);
	}

	// Identifier i;
	// VarDeclList vl;
	// MethodDeclList ml;
	public void visit(ClassDeclSimple n, Map prog, int lvl, Class normclass,
			Method meth) {
		lvl++;

		Class simpleclass = (Class) prog.get(n.i.s);
		n.i.typeaccept(this, prog, lvl, simpleclass, null);

		for (int i = 0; i < n.vl.size(); i++) {
			// isVarRepeated(n.vl, n.vl.elementAt(i).i.s);
			n.vl.elementAt(i).typeaccept(this, prog, lvl, simpleclass, null);
		}
		for (int i = 0; i < n.ml.size(); i++) {
			// isMethRepeated(n.ml, n.ml.elementAt(i).i.s);
			n.ml.elementAt(i).typeaccept(this, prog, lvl, simpleclass, meth);
		}
	}

	// Identifier i;
	// Identifier j;
	// VarDeclList vl;
	// MethodDeclList ml;
	public void visit(ClassDeclExtends n, Map prog, int lvl, Class pclass,
			Method meth) {
		lvl++;
		Class sclass = (Class) prog.get(n.i.s);
		n.i.typeaccept(this, prog, lvl, pclass, meth);
		n.j.typeaccept(this, prog, lvl, pclass, meth);

		for (int i = 0; i < n.vl.size(); i++) {
			n.vl.elementAt(i).typeaccept(this, prog, lvl, sclass, null);
		}
		for (int i = 0; i < n.ml.size(); i++) {
			n.ml.elementAt(i).typeaccept(this, prog, lvl, sclass, meth);
		}
	}

	// Type t;
	// Identifier i;
	public void visit(VarDecl n, Map prog, int lvl, Class classe, Method meth) {
		lvl++;
		n.t.typeaccept(this, prog, lvl, classe, meth);
		n.i.typeaccept(this, prog, lvl, classe, meth);
	}

	// Type t;
	// Identifier i;
	// FormalList fl;
	// VarDeclList vl;
	// StatementList sl;
	// Exp e;
	public void visit(MethodDecl n, Map prog, int lvl, Class cls, Method meth) {
		lvl++;
		Method newmeth = (Method) cls.method.get(n.i.s);

		n.t.typeaccept(this, prog, lvl, cls, newmeth);
		n.i.typeaccept(this, prog, lvl, cls, newmeth);
		for (int i = 0; i < n.fl.size(); i++) {
			n.fl.elementAt(i).typeaccept(this, prog, lvl, cls, newmeth);
		}
		for (int i = 0; i < n.vl.size(); i++) {
			n.vl.elementAt(i).typeaccept(this, prog, lvl, cls, newmeth);
		}
		for (int i = 0; i < n.sl.size(); i++) {
			n.sl.elementAt(i).typeaccept(this, prog, lvl, cls, newmeth);
		}

		String returntype = n.e.typeaccept(this, prog, lvl, cls, newmeth);
		if (!n.t.type().equals(returntype)) {
			System.out
					.println("Erro na linha "
							+ n.line()
							+ ". Retorno imcompativel com o declarado. Retornando tipo \""
							+ returntype + "\" quando deveria retornar \""
							+ n.t.type() + "\"");
		}
	}

	// Type t;
	// Identifier i;
	public void visit(Formal n, Map prog, int lvl, Class cls, Method meth) {
		n.t.typeaccept(this, prog, lvl, cls, meth);
		n.i.typeaccept(this, prog, lvl, cls, meth);
	}

	// StatementList sl;
	public void visit(Block n, Map prog, int lvl, Class cls, Method meth) {
		for (int i = 0; i < n.sl.size(); i++) {
			n.sl.elementAt(i).typeaccept(this, prog, lvl, cls, meth);
		}
	}

	// Exp e;
	// Statement s1,s2;
	public void visit(If n, Map prog, int lvl, Class cls, Method meth) {
		lvl++;
		String type = n.e.typeaccept(this, prog, lvl, cls, meth);
		if (!type.equals(bool)) {
			System.out
					.println("Erro na linha "
							+ n.line()
							+ ". Condicao do Statement \"if\"' deve ser do tipo \"boolean\"");
		}
		n.s1.typeaccept(this, prog, lvl, cls, meth);
		n.s2.typeaccept(this, prog, lvl, cls, meth);
	}

	// Exp e;
	// Statement s;
	public void visit(While n, Map prog, int lvl, Class cls, Method meth) {
		lvl++;
		String type = n.e.typeaccept(this, prog, lvl, cls, meth);
		if (!type.equals(bool)) {
			System.out
					.println("Erro na linha "
							+ n.line()
							+ ". Condicao do Statement \"while\"' deve ser do tipo \"boolean\"");
		}
		n.s.typeaccept(this, prog, lvl, cls, meth);
	}

	// Exp e;
	public void visit(Print n, Map prog, int lvl, Class cls, Method meth) {
		String type = n.e.typeaccept(this, prog, lvl, cls, meth);
		if (!type.equals(inteiro)) {
			System.out.println("Erro na linha " + n.line()
					+ ". println deve ser do tipo\"int\"" + type);
		}
	}

	// Identifier i;
	// Exp e;
	public void visit(Assign n, Map prog, int lvl, Class cls, Method meth) {
		n.i.typeaccept(this, prog, lvl, cls, meth);
		String exptype = n.e.typeaccept(this, prog, lvl, cls, meth);
		String type = getVarType(n.i.s, cls, prog);
		if (type == nulo) {
			type = getVarType(n.i.s, meth);
		}
		if (type == nulo) {
			System.out.println("Erro na lina " + n.line()
					+ ". A hua variavel \"" + n.i.s + "\"não foi declarada");
		} else if (!exptype.equals(nulo) && type != exptype) {
			if (!isCompatible(type, exptype, prog)) {
				System.out.println("Erro na linha " + n.line()
						+ ". atribuição incompativel, o tipo \"" + type
						+ " nao pode receber o tipo \"" + exptype);
			}

		}
	}

	// Identifier i;
	// Exp e1,e2;
	public void visit(ArrayAssign n, Map prog, int lvl, Class cls, Method meth) {

		n.i.typeaccept(this, prog, lvl, cls, meth);
		n.e1.typeaccept(this, prog, lvl, cls, meth);
		n.e2.typeaccept(this, prog, lvl, cls, meth);
	}

	// Exp e1,e2;
	public String visit(And n, Map prog, int lvl, Class cls, Method meth) {
		lvl++;
		String type1 = n.e1.typeaccept(this, prog, lvl, cls, meth);
		String type2 = n.e2.typeaccept(this, prog, lvl, cls, meth);
		if (!type1.equals(nulo) && !type2.equals(nulo)) {
			if (!type1.equals(bool) && !type2.equals(bool)) {
				System.out
						.println("Erro na lina "
								+ n.line()
								+ ". Operador \"&&\" suporta apenas o tipo \"boolean\".");
			}
		}
		return bool;
	}

	// Exp e1,e2;
	public String visit(LessThan n, Map prog, int lvl, Class cls, Method meth) {
		lvl++;
		String type1 = n.e1.typeaccept(this, prog, lvl, cls, meth);
		String type2 = n.e2.typeaccept(this, prog, lvl, cls, meth);
		if (!type1.equals(nulo) && !type2.equals(nulo)) {
			if (!type1.equals(type2)) {
				System.out.println("Erro na lina " + n.line() + ". expressao ("
						+ type1 + " < " + type2 + ") mal formada.");
			} else if (type1 != inteiro) {
				System.out.println("Erro na lina " + n.line() + ". expressao ("
						+ type1 + " < int) mal formada.");
			} else if (type2 != inteiro) {
				System.out.println("Erro na lina " + n.line()
						+ ". expressao ( int < " + type2 + ") mal formada.");
			}
		}
		return bool;
	}

	// Exp e1,e2;
	public String visit(Plus n, Map prog, int lvl, Class cls, Method meth) {
		lvl++;
		String type1 = n.e1.typeaccept(this, prog, lvl, cls, meth);
		String type2 = n.e2.typeaccept(this, prog, lvl, cls, meth);
		if (!type1.equals(nulo) && !type2.equals(nulo)) {
			if (!type1.equals(inteiro) && !type2.equals(inteiro)) {
				System.out.println("Erro na lina " + n.line()
						+ ". Operador \"+\" suporta apenas o tipo \"int\".");
			}
		}
		return inteiro;
	}

	// Exp e1,e2;
	public String visit(Minus n, Map prog, int lvl, Class cls, Method meth) {
		lvl++;
		String type1 = n.e1.typeaccept(this, prog, lvl, cls, meth);
		String type2 = n.e2.typeaccept(this, prog, lvl, cls, meth);
		if (!type1.equals(nulo) && !type2.equals(nulo)) {
			if (!type1.equals(inteiro) && !type2.equals(inteiro)) {
				System.out.println("Erro na lina " + n.line()
						+ ". Operador \"-\" suporta apenas o tipo \"int\".");
			}
		}
		return inteiro;
	}

	// Exp e1,e2;
	public String visit(Times n, Map prog, int lvl, Class cls, Method meth) {
		lvl++;
		String type1 = n.e1.typeaccept(this, prog, lvl, cls, meth);
		String type2 = n.e2.typeaccept(this, prog, lvl, cls, meth);
		if (!type1.equals(nulo) && !type2.equals(nulo)) {
			if (!type1.equals(inteiro) && !type2.equals(inteiro)) {
				System.out.println("Erro na lina " + n.line()
						+ ". Operador \"*\" suporta apenas o tipo \"int\".");
			}
		}
		return inteiro;
	}

	// Exp e1,e2;
	public String visit(ArrayLookup n, Map prog, int lvl, Class cls, Method meth) {
		lvl++;
		String type1 = n.e1.typeaccept(this, prog, lvl, cls, meth);
		String type2 = n.e2.typeaccept(this, prog, lvl, cls, meth);

		if (!type1.equals(nulo) && !type1.equals(intarray)) {
			System.out.println("Erro na lina " + n.line()
					+ "Expressao deveria ser do tipo \"int[]\".");
		}
		if (!type2.equals(nulo) && !type2.equals(inteiro)) {
			System.out.println("Erro na lina " + n.line()
					+ "Expressao deveria ser do tipo \"int\".");
		}
		return inteiro;
	}

	// Exp e;
	public String visit(ArrayLength n, Map prog, int lvl, Class cls, Method meth) {
		String type = n.e.typeaccept(this, prog, lvl, cls, meth);
		if (!type.equals(null) && !type.equals(intarray)) {
			System.out.println("Erro na lina " + n.line()
					+ "Expressao deveria ser do tipo \"int[]\".");
		}
		return inteiro;
	}

	// Exp e;
	// Identifier i;
	// ExpList el;
	public String visit(Call n, Map prog, int lvl, Class cls, Method meth) {
		lvl++;
		String type = n.e.typeaccept(this, prog, lvl, cls, meth);
		Class aux = null;
		if (cls.id != type) {
			aux = getClass(type, prog);
		} else {
			aux = cls;
		}
		Method m = null;
		if (aux != null) {
			m = getMethod(n.i.s, aux, prog);
			Map args1 = new LinkedHashMap();
			for (int i = 0; i < n.el.size(); i++) {
				String argtype = n.el.elementAt(i).typeaccept(this, prog, lvl,
						cls, meth);
				String key = "#";
				while (args1.containsKey(key)) {
					key += "#";
				}
				args1.put(key, argtype);
			}
			if (m != null) {
				Map args2 = m.arg;
				boolean argerr = false;

				Set argset1 = args1.entrySet();
				Set argset2 = args2.entrySet();
				Iterator it1 = argset1.iterator();
				Iterator it2 = argset2.iterator();

				if (args1.size() != args2.size()) {
					argerr = true;
				} else {
					while (it2.hasNext()) {
						Map.Entry entry1 = (Map.Entry) it1.next();
						Map.Entry entry2 = (Map.Entry) it2.next();
						String type1 = (String) entry1.getValue();
						Variable var2 = (Variable) entry2.getValue();
						if (!isCompatible(type1, var2.type, prog)) {
							argerr = true;
						}
					}
				}
				if (argerr) {
					System.out.print("Erro na linha " + n.line()
							+ ". Argumentos incorretor no metodo " + n.i.s
							+ "(");
					Set set = args1.entrySet();
					Iterator it = set.iterator();
					while (it.hasNext()) {
						Map.Entry entry = (Map.Entry) it.next();
						String var = (String) entry.getValue();
						System.out.print(var);
						if (it.hasNext())
							System.out.print(", ");
					}
					System.out.println(")");
					set = args2.entrySet();
					it = set.iterator();
					System.out.print("Deveria ser: " + n.i.s + "(");
					while (it.hasNext()) {
						Map.Entry entry = (Map.Entry) it.next();
						Variable var = (Variable) entry.getValue();
						System.out.print(var.type);
						if (it.hasNext())
							System.out.print(", ");
					}
					System.out.println(")");
				}
				return m.type;
			}
		}
		System.out.println("Erro na linha " + n.line() + ", metodo \"" + n.i.s
				+ "\" nao encontrado");
		return nulo;
	}

	// int i;
	public String visit(IntegerLiteral n, Map prog, int lvl, Class cls,
			Method meth) {
		return inteiro;
	}

	public String visit(True n, Map prog, int lvl, Class cls, Method meth) {
		return bool;
	}

	public String visit(False n, Map prog, int lvl, Class cls, Method meth) {
		return bool;
	}

	// String s;
	public String visit(IdentifierExp n, Map prog, int lvl, Class cls,
			Method meth) {
		String type = getVarType(n.s, cls, prog);
		
		if (type == nulo) {
			type = getVarType(n.s, meth);
		}
		if (type == nulo) {
			System.out.println("Erro na linha " + n.line() + ". !!!A  variavel \""
					+ n.s + "\" nao foi declarada");
		}
		return type;
	}

	public String visit(This n, Map prog, int lvl, Class cls, Method meth) {
		return cls.id;
	}

	// Exp e;
	public String visit(NewArray n, Map prog, int lvl, Class cls, Method meth) {
		String type = n.e.typeaccept(this, prog, lvl, cls, meth);
		if (type != inteiro) {
			System.out.println("Erro na linha " + n.line() + ", tipo \"["
					+ type + "]\" incompativel");
		}
		return intarray;
	}

	// Identifier i;
	public String visit(NewObject n, Map prog, int lvl, Class cls, Method meth) {
		lvl++;
		n.i.typeaccept(this, prog, lvl, cls, meth);
		if (!prog.containsKey(n.i.s)) {
			System.out.println("Erro na linha " + n.line() + ", tipo \""
					+ n.i.s + "\" nao definido");
		}
		return n.i.s;
	}

	// Exp e;
	public String visit(Not n, Map prog, int lvl, Class cls, Method meth) {
		String type = n.e.typeaccept(this, prog, lvl, cls, meth);
		if (type != bool) {
			System.out
					.println("Erro na linha "
							+ n.line()
							+ " operador \"!\" eh compativel apenas com tipos booleanos");
		}
		return bool;
	}

	private boolean isCompatible(String type1, String type2, Map prog) {

		if (type1.equals(type2) && type2.equals(inteiro))
			return true;
		else if (type1.equals(type2) && type2.equals(intarray))
			return true;
		else if (type1.equals(type2) && type2.equals(bool))
			return true;
		else if (type1.equals(nulo) || type2.equals(nulo))
			return true;

		else if (type1.equals(type2)) {
			return true;
		} else {
			Class c1 = getClass(type1, prog);
			if (c1 != null && !c1.extended.isEmpty()) {
				Class parent = getClass(c1.extended, prog);
				while (parent != null) {
					if (parent.id.equals(type2)) {
						return true;
					}
					parent = getClass(parent.extended, prog);
				}
			}
		}
		return false;
	}

	private Class getClass(String classname, Map prog) {
		Class aux = null;
		if (prog.containsKey(classname)) {
			aux = (Class) prog.get(classname);
		}
		return aux;
	}

	private Method getMethod(String methname, Class cls, Map prog) {
		Method meth = null;
		if (cls != null) {
			if (cls.method.containsKey(methname)) {
				meth = (Method) cls.method.get(methname);
			} else {
				if (!cls.extended.isEmpty()) {
					Class parent = getClass(cls.extended, prog);
					while (parent != null) {
						if (parent.method.containsKey(methname)) {
							meth = (Method) parent.method.get(methname);
							break;
						}
						parent = getClass(cls.extended, prog);
					}
				}
			}
		}
		return meth;
	}

	private String getVarType(String id, Class cls, Map prog) {
		if (cls != null) {
			if (cls.var.containsKey(id)) {
				Variable var = (Variable) cls.var.get(id);
				return var.type;
			} else if (!cls.extended.isEmpty()) {
				String parentname = cls.extended;
				if (prog.containsKey(parentname)) {
					Class parent = (Class) prog.get(parentname);
					return getVarType(id, parent, prog);
				}
			}
		}
		return nulo;
	}

	private String getVarType(String id, Method meth) {
		Variable var;
		if (meth != null) {
			if (meth.arg.containsKey(id)) {
				var = (Variable) meth.arg.get(id);
				return var.type;
			} else if (meth.var.containsKey(id)) {
				var = (Variable) meth.var.get(id);

				return var.type;
			}
		}
		return nulo;
	}

	public String visit(IntArrayType n, Map prog, int lvl, Class cls,
			Method meth) {
		return intarray;
	}

	public String visit(BooleanType n, Map prog, int lvl, Class cls, Method meth) {
		return bool;
	}

	public String visit(IntegerType n, Map prog, int lvl, Class cls, Method meth) {
		return inteiro;
	}

	public String visit(IdentifierType n, Map prog, int lvl, Class cls,
			Method meth) {
		return n.s;
	}

	@Override
	public String visit(Identifier n, Map prog, int lvl, Class cls, Method meth) {
		return null;
	}

}
