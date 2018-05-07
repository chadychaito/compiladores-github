package AST;

import java.util.*;

public class IfStatement extends Statement{

	public IfStatement(Cond cond, ArrayList<Statement> if_part, ArrayList<Statement> else_part){
		this.cond = cond;
		this.if_part = if_part;
		this.else_part = else_part;
	}

	public void genC(PW pw){
		pw.print("if (");
		cond.genC(pw);
		pw.println("){");
		
		for(Statement stmt : if_part){
			stmt.genC(pw);
		}
		pw.println("}");

		if(!else_part.isEmpty()){
			pw.println("else {");
			for(Statement stmt : else_part){
				stmt.genC(pw);
			}
			pw.println("}");
		}
	}

	private ArrayList<Statement> if_part;
	private ArrayList<Statement> else_part;
	private Cond cond;

}