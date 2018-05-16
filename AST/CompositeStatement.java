package AST;
import java.util.*;

public class CompositeStatement extends Statement{

	public CompositeStatement(String ident, Expr expr){
		this.ident = ident;
		this.expr = expr;
	}

	public void genC(PW pw){
		pw.print(this.ident + " = ");
		String aux = expr.getExpr();
		
		//Se call_expr
		/*if(aux.indexOf("(") != -1){ //Se for -1 não existe
			pw.print("(");
			expr.genC(pw);
			pw.print(");");
		}*/
		{	
			
			int currentIdentAux = pw.get(); //Guardando o valor da Identação atual
			pw.set(0); //Setando pra 0

			expr.genC(pw);
			pw.print(";");
			pw.set(currentIdentAux); //Voltando ao valor da Identação atual
		}

		pw.println("");
	}

	//Duvida como saber quanto precisa colocar := ou ()

	private String ident;
	private Expr expr;
}