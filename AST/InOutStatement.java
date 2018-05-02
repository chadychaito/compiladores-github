package AST;

import java.util.*;

public class InOutStatement extends Statement{

	public InOutStatement(String tipo, ArrayList<Variable> expr_list){
		this.tipo = tipo;
		this.expr_list = expr_list;
	}


	public void genC(){
		if(this.tipo == "READ"){
			System.out.println("printf (");
		}
		else{
			System.out.println("scanf (");
		}

		for (Variable expr : expr_list){
			expr.genC();
		}

		System.out.println(");");
	}
	
	private String tipo;
	private ArrayList<Variable> expr_list;
}