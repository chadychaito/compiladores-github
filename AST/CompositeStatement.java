package AST;
import java.util.*;

public class CompositeStatement extends Statement{

	public CompositeStatement(String ident, Expr expr){
		this.ident = ident;
		this.expr = expr;
	}

	public void genC(PW pw){
		pw.print(this.ident);
		String aux = expr.getExpr();
		
		//Se call_expr
		if(aux.charAt(0) == '('){
			pw.print(" (");
			expr.genC(pw);
			pw.print(");");
		}
		else{
			pw.print(" = ");
			expr.genC(pw);
			pw.print(";");
		}

		pw.println("");
	}

	//Duvida como saber quanto precisa colocar := ou ()

	private String ident;
	private Expr expr;
}