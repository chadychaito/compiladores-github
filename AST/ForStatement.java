
package AST;

import java.util.*;

public class ForStatement extends Statement{

	public void genC(PW pw){
		pw.print("for(");
		if(this.id != ""){
			int currentIdentAux = pw.get(); //Guardando o valor da Identação atual
			pw.set(0); //Setando pra 0
			pw.print(this.id + " = ");

			exprL.genC(pw);
			
			pw.print(" ; ");
			pw.set(currentIdentAux); //Voltando ao valor da Identação atual 

		}
		if(this.cond.getCompop() != ""){
			int currentIdentAux = pw.get(); //Guardando o valor da Identação atual
			pw.set(0); //Setando pra 0
			
			cond.genC(pw);
			
			pw.print(" ; ");
			pw.set(currentIdentAux); //Voltando ao valor da Identação atual
		}
		if(this.id2 != ""){
			int currentIdentAux = pw.get(); //Guardando o valor da Identação atual
			pw.set(0); //Setando pra 0
			pw.print(this.id2 + " = ");
			
			exprR.genC(pw);

			pw.println("){");
			pw.set(currentIdentAux); //Voltando ao valor da Identação atual 
		}
		pw.add();
		for(Statement e: stmt){
			e.genC(pw);
		}
		pw.sub();
		pw.println("}");

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