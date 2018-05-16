package AST;
import java.util.*;

public class FuncBody{

	public FuncBody(ArrayList<Variable> dcl, ArrayList<Statement> list_stmt){
		this.decl = dcl;
		this.list_stmt = list_stmt;
	}

	public void genC(PW pw){
		if(!decl.isEmpty()){

			Iterator it = decl.iterator();
			
			int currentIdentAux = pw.get(); //Guardando o valor da Identação atual
			while(it.hasNext()){

				Variable e = (Variable) it.next();
				
				e.genC(pw);
				
				pw.set(0); //Setando pra 0
				if(it.hasNext()){
	        		pw.print(", ");
				}
			}
			pw.println(";");
			pw.set(currentIdentAux);
	        pw.println("");
		} 
		for(Statement stmt: list_stmt){
			stmt.genC(pw);
		}
	}

	private ArrayList<Variable> decl;
	private ArrayList<Statement> list_stmt;
}