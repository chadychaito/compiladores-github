package AST;
import java.util.*;

public class PgmBody{

	public PgmBody(ArrayList<Variable> dcl, ArrayList<FuncDecl> f_declarations){
		this.decl = dcl;
		this.func_declarations = f_declarations;
	}

	public void genC(){
		for(Variable e: decl){
			e.genC();
		}
		for(FuncDecl f: func_declarations){
			f.genC();
		}
	}

	private ArrayList<Variable> decl;
	private ArrayList<FuncDecl> func_declarations;
}