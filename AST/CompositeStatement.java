package AST;
import java.util.*;

public class CompositeStatement extends Statement{

	public CompositeStatement(String ident, Expr expr){
		this.ident = ident;
		this.expr = expr;
	}

	public void genC(){
		System.out.println(ident);
		String aux = expr.getExpr();
		
		//Se call_expr
		if(aux.charAt(0) == '('){
			System.out.println(" (");
			expr.genC();
			System.out.println(");");
		}
		else{
			System.out.println(" = ");
			expr.genC();
			System.out.println(";");
		}
	}

	//Duvida como saber quanto precisa colocar := ou ()

	private String ident;
	private Expr expr;
}