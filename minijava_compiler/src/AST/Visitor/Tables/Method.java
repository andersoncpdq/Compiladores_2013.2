package AST.Visitor.Tables;

import java.util.*;
import java.util.Map.Entry;

public class Method {

	public int level;
	public int line;
	public String type;
	public String id;
	public Map arg;
	public Map var;

	public Method() {
		level = 0;
		line = 0;
		type = new String();
		id = new String();
		arg = new LinkedHashMap();
		var = new LinkedHashMap();
	}

	public void indent() {
		for (int i = 0; i < level; i++) {
			System.out.print("\t");
		}
	}

	public void print() {
		System.out.print(" METHOD ");
		System.out.print(" type: " + type + " id: " + id);
		// argumentos
		if (!arg.isEmpty()) {
//			indent();
			System.out.print(" ARGUMENTS ");
			Set eset = arg.entrySet();
			Iterator it = eset.iterator();
			while (it.hasNext()) {
				Map.Entry in = (Entry) it.next();
				Variable table_var = (Variable) in.getValue();
				table_var.level++;
				table_var.print(false);
			}
		}
		// variaveis
		if (!var.isEmpty()) {
			Set eset = arg.entrySet();
			Iterator it = eset.iterator();
			while (it.hasNext()) {
				Map.Entry in = (Entry) it.next();
				Variable table_var = (Variable) in.getValue();
				table_var.level++;
				table_var.print(true);
			}
		}
	}

	public void verifyVar(Map classmap, String classname) {
		Class tclass = (Class) classmap.get(classname);
		Set eset = var.entrySet();
		Iterator it = eset.iterator();

		if (!var.isEmpty()) {
			while (it.hasNext()) {
				Map.Entry tmp_var = (Map.Entry) it.next();
				Variable tvar = (Variable) tmp_var.getValue();
				if (arg.containsKey(tvar.id)) {
					System.out.println("A variavel \"" + tvar.type + " "
							+ tvar.id + "\" foi redefinida na linha "
							+ (tvar.line + 1));
				}
			}

			if (tclass.var != null) {
				eset = var.entrySet();
				it = eset.iterator();
				while (it.hasNext()) {
					Map.Entry tmp_var = (Map.Entry) it.next();
					Variable tvar = (Variable) tmp_var.getValue();
					if (tclass.var.containsKey(tvar.id)) {
						System.out.println("A variavel \"" + tvar.type + " "
								+ tvar.id + "\" foi redefinida na linha "
								+ (tvar.line + 1));
					}
				}
			}

			if (!tclass.extended.isEmpty()) {
				String parent_name = tclass.extended;
				Class parent = null;

				while (!parent_name.isEmpty()) {
					eset = var.entrySet();
					it = eset.iterator();
					if (classmap.containsKey(parent_name)) {
						parent = (Class) classmap.get(parent_name);
						while (it.hasNext()) {
							Map.Entry tmp_var = (Map.Entry) it.next();
							Variable tvar = (Variable) tmp_var.getValue();
							if (parent.var.containsKey(tvar.id)) {
								System.out.println("O \"" + tvar.type + " "
										+ tvar.id + "\" da super-classe \""
										+ parent.id
										+ "\" foi redefinida na linha "
										+ (tvar.line + 1));
							}
						}
						parent_name = parent.extended;
					} else
						break;

				}

			}
		}

		// argumentos duplicados
		if (!arg.isEmpty()) {
			if (tclass.var != null) {
				eset = var.entrySet();
				it = eset.iterator();
				while (it.hasNext()) {
					Map.Entry tmp_var = (Map.Entry) it.next();
					Variable tvar = (Variable) tmp_var.getValue();
					if (arg.containsKey(tvar.id)) {
						System.out.println("O  argomento \"" + tvar.type + " "
								+ tvar.id + "\" foi redefinida na linha "
								+ (tvar.line + 1));
					}
				}
			}
		}
	}

	public void verifyType(Map m) {
		if (var != null) {
			Set eset = var.entrySet();
			Iterator it = eset.iterator();
			while (it.hasNext()) {
				Map.Entry tmp_var = (Map.Entry) it.next();
				Variable tvar = (Variable) tmp_var.getValue();
				tvar.typeVerification(m);
			}
		}
		if (!arg.isEmpty()) {
			Set eset = var.entrySet();
			Iterator it = eset.iterator();
			while (it.hasNext()) {
				Map.Entry tmp_var = (Map.Entry) it.next();
				Variable tvar = (Variable) tmp_var.getValue();
				tvar.typeVerification(m);
			}
		}
	}

}
