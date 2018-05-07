
package AST;

import java.util.*;

public class ForStatement extends Statement{

	public void genC(PW pw){
		pw.print("for(");
		if(this.id != ""){
			pw.print(this.id + " = ");
			exprL.genC(pw);
			pw.print(" ; ");
		}
		if(this.cond.getCompop() != ""){
			cond.genC(pw);
			pw.print(" ; ");
		}
		if(this.id2 != ""){
			pw.print(this.id2 + " = ");
			exprR.genC(pw);
			pw.print(";");
		}
		pw.println("){");
		for(Statement e: stmt){
			e.genC(pw);
		}

	}

	public ForStatement(String id, Expr exprL, Cond cond, String id2, Expr exprR, ArrayList<Statement> stmt){
		this.id = id;
		this.exprL = exprL;
		this.cond = cond;
		this.id2 = id;
		this.exprR = exprR;
		this.stmt = stmt;
	}


	//private String tipo;
	private ArrayList<Statement> stmt;
	private Expr exprL;
	private Expr exprR;
	private Cond cond;
	private String id;
	private String id2;

}