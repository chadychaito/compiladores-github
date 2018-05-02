package AST;

import java.util.*;

public class FuncBody{

	public FuncBody(ArrayList<Variable> dcl, ArrayList<Statement> list_stmt){
		this.decl = dcl;
		this.list_stmt = list_stmt;
	}

	public void genC(){
		for(Variable e: decl){
			e.genC();
		}
		for(Statement stmt: list_stmt){
			stmt.genC();
		}
	}

	private ArrayList<Variable> decl;
	private ArrayList<Statement> list_stmt;
}