package AST.Visitor.Tables;

import java.util.*;

public class Class {
	public String id;
	public String extended;
	public Map var;
	public Map method;
	public int level;
	public int line;

	public Class(boolean main) {
		id = new String();
		method = new LinkedHashMap();
		extended = new String();
		level = 0;
		line = 0;

		if (main) {
			var = null;
		} else {
			var = new LinkedHashMap();
		}
	}

	public int sizeOfInstance() {
		return var.values().size() * 4;
	}

	public void indent() {
		for (int i = 0; i < level; i++) {
			System.out.println("\t");
		}
	}

	public void print() {
		System.out.println("CLASS " + id);
		if (!extended.isEmpty()) {
			System.out.print(" EXTENDS " + extended + " ");
		}

		if (var != null) {
			Set eset = var.entrySet();
			Iterator it = eset.iterator();
			while (it.hasNext()) {
				Map.Entry in_value = (Map.Entry) it.next();
				Variable table_var = (Variable) in_value.getValue();
				table_var.level = this.level + 1;
				table_var.print(true);
			}
		}

		if (method != null) {
			Set eset = method.entrySet();
			Iterator it = eset.iterator();
			while (it.hasNext()) {
				Map.Entry in_value = (Map.Entry) it.next();
				Method table_meth = (Method) in_value.getValue();
				table_meth.level = this.level + 1;
				table_meth.print();
				
			}
		}
		System.out.println("\n---------------------------\n");
	}

	public void check(Map classmap) {
		// checagem de repeticoes - atributos - metodos - superclasses
		if (!extended.isEmpty()) {

			String parent_name = extended;
			Class parent = null;
			do {
				Set eset = var.entrySet();
				Iterator it = eset.iterator();

				if (classmap.containsKey(parent_name)) {
					parent = (Class) classmap.get(parent_name);

					while (it.hasNext()) {
						Map.Entry in_value = (Map.Entry) it.next();
						Variable table_var = (Variable) in_value.getValue();
						if (parent.var != null
								&& parent.var.containsKey(table_var.id)) {
							System.out.println("A variavel " + table_var.type
									+ " " + table_var.id
									+ " foi redefinida na super classe"
									+ parent.id + ". na linha "
									+ table_var.line);
						}
					}

					while (it.hasNext()) {
						Map.Entry in_value = (Map.Entry) it.next();
						Method meth1 = (Method) in_value.getValue();

						if (parent.method.containsKey(meth1.id)) {
							boolean argerr = false;
							Method meth2 = (Method) parent.method.get(meth1.id);

							Set set1 = meth1.arg.entrySet();
							Set set2 = meth2.arg.entrySet();
							Iterator it1 = set1.iterator();
							Iterator it2 = set2.iterator();

							if (meth2.arg.size() != meth1.arg.size()) {
								argerr = true;
							} else {
								while (it2.hasNext()) {
									Map.Entry in_value1 = (Map.Entry) it.next();
									Variable table_var1 = (Variable) in_value
											.getValue();
									Map.Entry in_value2 = (Map.Entry) it.next();
									Variable table_var2 = (Variable) in_value
											.getValue();
									if (!isCompatible(classmap,
											table_var1.type, table_var2.type)) {
										argerr = true;
									}
								}
							}

							if (argerr) {
								System.out.println("Erro de Sobregarga: ");
								System.out.println("\t Metodo \"" + meth1.type
										+ " " + meth1.id
										+ "\" da super-classe " + parent.id
										+ "::" + meth2.type + " " + meth2.id
										+ "( ");

								eset = meth2.arg.entrySet();
								it = eset.iterator();
								while (it.hasNext()) {
									Map.Entry in_val = (Map.Entry) it.next();
									Variable tempvar = (Variable) in_val
											.getValue();
									System.out.print(tempvar.type);
									if (it.hasNext()) {
										System.out.print(", ");
									}
								}
								System.out.println(")");
								System.out.println("na sub-classe \"" + id
										+ "::" + meth1.type + " " + meth1.id
										+ "(");
								eset = meth2.arg.entrySet();
								it = eset.iterator();
								while (it.hasNext()) {
									Map.Entry in_val = (Map.Entry) it.next();
									Variable tempvar = (Variable) in_val
											.getValue();
									System.out.print(tempvar.type);
									if (it.hasNext()) {
										System.out.print(", ");
									}
								}
								System.out.println(")");
								System.out.println("na linha "
										+ (meth1.line + 1));

							}
						}

					}
					parent_name = parent.extended;
				} else {
					if (extended.equals(parent_name)) {
						System.out.println("Erro de heranca, super-classe \""
								+ parent_name + "\" nao encontrada na linha "
								+ (line + 1));
						break;
					}
				}
			} while (!parent_name.isEmpty());
		}
		// System.out.println(extended + id);
		if (!method.isEmpty()) {

			Set eset = method.entrySet();
			Iterator it = eset.iterator();

			while (it.hasNext()) {
				Map.Entry value = (Map.Entry) it.next();
				Method table_meth = (Method) value.getValue();

				table_meth.verifyVar(classmap, id);
			}
		}

	}

	public void checkType(Map m) {
		if (!var.isEmpty()) {
			Set eset = var.entrySet();
			Iterator it = eset.iterator();
			while (it.hasNext()) {
				Map.Entry in_value = (Map.Entry) it.next();
				Variable table_var = (Variable) in_value.getValue();
				table_var.typeVerification(m);
			}
		}

		if (!method.isEmpty()) {
			Set eset = method.entrySet();
			Iterator it = eset.iterator();
			while (it.hasNext()) {
				Map.Entry in_value = (Map.Entry) it.next();
				Method table_meth = (Method) in_value.getValue();
				table_meth.verifyType(m);
			}
		}
	}

	public boolean isCompatible(Map m, String classtype, String classext) {
		if ((classtype.equals("int") || classext.equals("int"))
				&& classtype.equals(classext))
			return true;
		else if ((classtype.equals("int[]") || classext.equals("int[]"))
				&& classtype.equals(classext))
			return true;
		else if ((classtype.equals("boolean") || classext.equals("boolean"))
				&& classtype.equals(classext))
			return true;
		else if ((classtype.equals(null) || classext.equals(null))
				&& classtype.equals(classext))
			return false;
		else {
			String parent_type = null;
			if (m.containsKey(classtype)) {
				Class tclass = (Class) m.get(classtype);
				if (!tclass.extended.isEmpty()) {
					String parent_name = tclass.extended;
					Class parent = null;
					do {
						if (m.containsKey(parent_name)) {
							parent = (Class) m.get(parent_name);
							if (parent.id.equals(classext)) {
								parent_type = classext;
								break;
							}
							parent_name = parent.extended;
						} else
							break;
					} while (!parent_name.isEmpty());
				}
			}
			if (classext.equals(parent_type)) {
				return true;
			}
		}
		return false;
	}

}
