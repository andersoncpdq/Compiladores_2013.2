package AST.Visitor.Tables;

import java.util.Map;

public class Variable {

	public String type;
	public String id;
	public int level;
	public int line;
	
	public Variable(){
		type = new String();
		id = new String();
		level = 0;
		line = 0;
	}
	
	public Variable(String type , String id, int lvl, int line){
		this.type = type;
		this.id = id;
		this.level = lvl;
		this.line = line;
	}
	
	void indent(){
		for(int i = 0; i < level; i++){
			System.out.print("\t");
		}
	}
	
	void print(boolean ok){
		if(ok){
//			indent();
			System.out.print(" VARIABLE ");
//			indent();
		}
		System.out.print(" " + type + " " + id);
		
	}
	public void typeVerification(Map cl){
		if (!type.equals("int") && type.equals("int []") && type.equals("boolean") && cl.containsKey(type) && type.equals("String []")){
			System.out.println("Tipo (" + type + "nao deinido na linha - " + (line +1) );
		}
	}

}
