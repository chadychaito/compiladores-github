package AST;
import java.util.*;

public class PgmBody{

	public PgmBody(ArrayList<Variable> dcl, ArrayList<FuncDecl> f_declarations){
		this.decl = dcl;
		this.func_declarations = f_declarations;
	}

	public void genC(PW pw){
		if(!decl.isEmpty()){
			Iterator it = decl.iterator();
	        
	        /* Enquanto houve proximo */
	        while(it.hasNext()){
	        	Variable e = (Variable) it.next();
	        	
	        	e.genC(pw);
	        	
	        	/* Se tem proximo */
	        	if(it.hasNext()){
	        		pw.print(",");
	        	}

	        }
	        pw.print(";");

	        pw.println("");
	        pw.println("");
		}
 
		for(FuncDecl f: func_declarations){
			f.genC(pw);
		}
	}

	private ArrayList<Variable> decl;
	private ArrayList<FuncDecl> func_declarations;
}