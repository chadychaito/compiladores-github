package AST;

import java.util.*;

public class FuncBody{

	public FuncBody(ArrayList<Variable> dcl, ArrayList<Statement> list_stmt){
		this.decl = dcl;
		this.list_stmt = list_stmt;
	}

	public void genC(PW pw){
		if(!decl.isEmpty()){

			Iterator it = decl.iterator();
	        while(it.hasNext()){
	        	Variable e = (Variable) it.next();
	        	e.genC(pw);
	        	if(it.hasNext()){
	        		pw.print(",");
	        	}

	        }
	        pw.println(";");
	        pw.println("");
	        pw.println("");
		} 
		for(Statement stmt: list_stmt){
			stmt.genC(pw);
		}
	}

	private ArrayList<Variable> decl;
	private ArrayList<Statement> list_stmt;
}