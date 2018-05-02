package AST;

import java.util.*;

public class VarDecl{

	public FuncDecl(String tipo, String ident, ArrayList<Variable> param_list, FuncBody f_body){
		this.tipo = tipo;
		this.id_list = id_list;
	}

	public void genC() {
        System.out.println(this.tipo + " " + this.ident + "(");

        // Se existe param_decl_list 
        if(!param_list.isEmpty()){
	        Iterator it = param_list.iterator();
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