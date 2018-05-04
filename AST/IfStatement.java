package AST;

import java.util.*;

public class IfStatement extends Statement{

	public IfStatement(Cond cond, ArrayList<Statement> if_part, ArrayList<Statement> else_part){
		this.cond = cond;
		this.if_part = if_part;
		this.else_part = else_part;
	}

	public void genC(PW pw){
		System.out.println("if (");
		cond.genC(pw);
		System.out.println("){\n");
		
		for(Statement stmt : if_part){
			stmt.genC(pw);
		}
		System.out.println("}\n");

		if(!else_part.isEmpty()){
			System.out.println("else {\n");
			for(Statement stmt : else_part){
				stmt.genC(pw);
			}
			System.out.println("}");
		}
	}

	private ArrayList<Statement> if_part;
	private ArrayList<Statement> else_part;
	private Cond cond;

}