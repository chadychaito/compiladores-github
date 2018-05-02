package AST;

import java.util.*;

public class ForStatement extends Statement{

	public void genC(){
		System.out.println("for(");
		if(this.id != ""){
			System.out.println(this.id + " = " + this.exprL + ";");
		}
		if(this.cond.getCompop() != ""){
			cond.genC();
		}
		if(this.id2 != ""){
			System.out.println(this.id2 + " = " + this.exprR + ";");
		}
		System.out.println("){\n");
		for(Statement e: stmt){
			e.genC();
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