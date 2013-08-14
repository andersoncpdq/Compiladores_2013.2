import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import Scanner.*;

import java_cup.runtime.Symbol;
import AST.And;
import AST.ArrayAssign;
import AST.ArrayLength;
import AST.ArrayLookup;
import AST.Assign;
import AST.Block;
import AST.BooleanType;
import AST.Call;
import AST.ClassDeclExtends;
import AST.ClassDeclSimple;
import AST.False;
import AST.Formal;
import AST.Identifier;
import AST.IdentifierExp;
import AST.IdentifierType;
import AST.If;
import AST.IntArrayType;
import AST.IntegerLiteral;
import AST.IntegerType;
import AST.LessThan;
import AST.MainClass;
import AST.MethodDecl;
import AST.Minus;
import AST.NewArray;
import AST.NewObject;
import AST.Not;
import AST.Plus;
import AST.Print;
import AST.Program;
import AST.Statement;
import AST.This;
import AST.Times;
import AST.True;
import AST.VarDecl;
import AST.While;
import AST.Visitor.TypeCheckVisitor;
import AST.Visitor.PrettyPrintVisitor;
import AST.Visitor.TypeVisitor;
import Parser.parser;
import Scanner.scanner;
import Throwables.CompilerException;
import AST.Visitor.Tables.*;
import AST.Visitor.Tables.Class;

public class Main {
	public static void main(String[] args) {
		try {
			// create a scanner on the input file
			Map m = new HashMap<>();
			Map prog = new LinkedHashMap(m);

			System.out
					.println("Trabalho de compiladores 2013.1\n " +
							"Francisco Robson Oliveira de Lima\n " +
							"Igor Brasil Nogueira\n\n" +
							"Digite o nome do arquivo a ser compilado.");

			Scanner leitor = new Scanner(System.in);
			String filename = leitor.next();
			FileReader file = new FileReader(filename);

			System.out.println("Erros lexicos e sintaticos:");
			scanner s = new scanner(file);
			parser p = new parser(s);
			Symbol root;
			root = p.parse();
			
			System.out.println("Erros Semanticos:");
			
			Program program = (Program) root.value;
			program.accept(new PrettyPrintVisitor(), prog);

			Set set = prog.entrySet();
			Iterator it = set.iterator();
			while (it.hasNext()) {
				Map.Entry in = (Map.Entry) it.next();
				Class c = (Class) in.getValue();
				c.check(prog);
			}

			file = new FileReader(filename);
			scanner s2 = new scanner(file);
			parser p2 = new parser(s2);
			Symbol root2;
			root2 = p2.parse();
			Program program2 = (Program) root2.value;
			program2.typeaccept(new TypeCheckVisitor(), prog);
			
			System.out.println("Tabelas Intermediarias");
			
			set = prog.entrySet();
			it = set.iterator();
			while (it.hasNext()) {
				Map.Entry in = (Map.Entry) it.next();
				Class c = (Class) in.getValue();
				c.print();
			}
			
			System.out.print("Geracao de codigo pendente...");
		} catch (CompilerException e) {
			// an error in the user's arguments or input, or some
			// other kind of error that we've already handled in the
			// appropriate way (not a bug that the error got here)
			System.err.println(e.getMessage());
		} catch (Exception e) {
			// yuck: some kind of error in the compiler implementation
			// that we're not expecting (a bug!)
			System.err.println("Unexpected internal compiler error: "
					+ e.toString());
			// print out a stack dump
			e.printStackTrace();
		}
	}
}