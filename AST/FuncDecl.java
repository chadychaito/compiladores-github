package AST;

import java.util.*;

public class FuncDecl{

	public FuncDecl(String tipo, String ident, ArrayList<Variable> param_list, FuncBody f_body){
		this.tipo = tipo;
		this.ident = ident;
		this.param_list = param_list;
		this.f_body = f_body;
	}

	public void genC() {
        System.out.println(this.tipo + " " + this.ident + "(");
        for(Variable e: param_list){
			Variable aux = e.genC();
			System.out.println(aux);

        }
        System.out.println(");");

        f_body.genC();
    }

	private String tipo;
	private String ident;
	private ArrayList<Variable> param_list;
	private FuncBody f_body;
}