package AST;
import java.util.*;

public class PgmBody{

	public PgmBody(ArrayList<Variable> dcl, ArrayList<FuncDecl> f_declarations){
		this.decl = dcl;
		this.func_declarations = f_declarations;
	}

	public void genC(){
		if(!decl.isEmpty()){
			Iterator it = decl.iterator();
	        while(it.hasNext()){
	        	Variable e = (Variable) it.next();
	        	e.genC();
	        	if(it.hasNext()){
	        		System.out.println(",");
	        	}

	        }
		} 
		for(FuncDecl f: func_declarations){
			f.genC();
		}
	}

	private ArrayList<Variable> decl;
	private ArrayList<FuncDecl> func_declarations;
}