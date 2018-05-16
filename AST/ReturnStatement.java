package AST;
import java.util.*;

public class ReturnStatement extends Statement{

	public ReturnStatement(String tipo, Expr expr){
		this.tipo = tipo;
		this.expr = expr;
	}


	public void genC(PW pw){
		pw.print("return ");
		int currentIdentAux = pw.get(); //Guardando o valor da Identação atual
		pw.set(0); //Setando pra 0
		
		expr.genC(pw);

		pw.println(";");
		pw.set(currentIdentAux); //Setando para o valor da Identação atual
		
	}
	
	private String tipo;
	private Expr expr;
}