package AST;
import java.util.*;

public class ReturnStatement extends Statement{

	public ReturnStatement(String tipo, Expr expr){
		this.tipo = tipo;
		this.expr = expr;
	}


	public void genC(PW pw){
		pw.print("return ");
		expr.genC(pw);
		pw.println(";");
	}
	
	private String tipo;
	private Expr expr;
}