package AST;

import java.util.*;

public class InOutStatement extends Statement{

	public InOutStatement(String tipo, ArrayList<Variable> id_list){
		this.tipo = tipo;
		this.id_list = id_list;
	}


	public void genC(){
		System.out.println(this.tipo);
		if(this.tipo == "write"){
			System.out.println("printf (");
		}
		else{
			System.out.println("scanf (");
		}

		for (Variable id : id_list){
			id.genC();
		}

		System.out.println(");");
	}
	
	private String tipo;
	private ArrayList<Variable> id_list;
}