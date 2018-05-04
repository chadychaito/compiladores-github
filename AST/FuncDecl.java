package AST;

import java.util.*;

public class FuncDecl{

	public FuncDecl(String tipo, String ident, ArrayList<Variable> param_list, FuncBody f_body){
		this.tipo = tipo;
		this.ident = ident;
		this.param_list = param_list;
		this.f_body = f_body;
	}

	public void genC(PW pw) {
        pw.print(this.tipo + " " + this.ident + "(");
        // Se existe param_decl_list 
        if(!param_list.isEmpty()){
	        Iterator it = param_list.iterator();
	        
	        while(it.hasNext()){
	        	Variable e = (Variable) it.next();
	        	
	        	e.genC(pw);
	        	
	        	if(it.hasNext()){
	        		pw.print(",");
	        	}

	        }
	    }

        pw.println("){");
	
        f_body.genC(pw);
    }

	private String tipo;
	private String ident;
	private ArrayList<Variable> param_list;
	private FuncBody f_body;
}