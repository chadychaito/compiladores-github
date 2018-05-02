package AST;

import java.util.*;

public class FuncBody{

	public FuncBody(ArrayList<Variable> dcl, ArrayList<Statement> list_stmt){
		this.decl = dcl;
		this.list_stmt = list_stmt;
	}

	public void genC(){
		if(!decl.isEmpty()){
			Iterator it = decl.iterator();
	        while(it.hasNext()){
	        	Variable e = (Variable) it.next();
	        	e.genC();
	        	if(it.hasNext()){
	        		System.out.println(",");
	        	}

	        }
		} 
		for(Statement stmt: list_stmt){
			stmt.genC();
		}
	}

	private ArrayList<Variable> decl;
	private ArrayList<Statement> list_stmt;
}