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

		if(!id_list.isEmpty()){
			Iterator it = id_list.iterator();
	        while(it.hasNext()){
	        	Variable e = (Variable) it.next();
	        	e.genC(pw);
	        	if(it.hasNext()){
	        		pw.print(",");
	        	}

	        }
		} 

		pw.print(");");
		pw.println("");
	}
	
	private String tipo;
	private ArrayList<Variable> id_list;
}