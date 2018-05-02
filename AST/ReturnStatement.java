package AST;
import java.util.*;

public class ReturnStatement extends Statement{

	public ReturnStatement(String tipo, Expr expr){
		this.tipo = tipo;
		this.expr = expr;
	}


	public void genC(){
		System.out.println("return ");
		this.expr.genC();
	}
	
	private String tipo;
	private Expr expr;
}