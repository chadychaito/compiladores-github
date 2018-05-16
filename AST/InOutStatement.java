package AST;

import java.util.*;

public class InOutStatement extends Statement{

	public InOutStatement(String tipo, ArrayList<Variable> id_list){
		this.tipo = tipo;
		this.id_list = id_list;
	}


	public void genC(PW pw){
		if(this.tipo == "write"){
			pw.print("printf (");
		}
		else{
			pw.print("scanf (");
		}
		
		int currentIdentAux = pw.get(); //Guardando o valor da Identação atual
		pw.set(0); //Setando pra 0

		if(!id_list.isEmpty()){
			Iterator it = id_list.iterator();

	        while(it.hasNext()){
	        	Variable e = (Variable) it.next();
	        	e.genC(pw);
	        	if(it.hasNext()){
	        		pw.print(", ");
	        	}
			}
		} 

		pw.print(");");
		pw.set(currentIdentAux); //Setando para o valor da Identação atual
		pw.println("");
	}
	
	private String tipo;
	private ArrayList<Variable> id_list;
}