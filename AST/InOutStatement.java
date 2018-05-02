package AST;

import java.util.*;

public class InOutStatement extends Statement{

	public InOutStatement(String tipo, ArrayList<Variable> id_list){
		this.tipo = tipo;
		this.id_list = id_list;
	}


	public void genC(){
		if(this.tipo == "write"){
			System.out.println("printf (");
		}
		else{
			System.out.println("scanf (");
		}

		if(!id_list.isEmpty()){
			Iterator it = id_list.iterator();
	        while(it.hasNext()){
	        	Variable e = (Variable) it.next();
	        	e.genC();
	        	if(it.hasNext()){
	        		System.out.println(",");
	        	}

	        }
		} 

		System.out.println(");");
	}
	
	private String tipo;
	private ArrayList<Variable> id_list;
}