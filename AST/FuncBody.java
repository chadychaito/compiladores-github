package AST;
import java.util.*;

public class FuncBody{

	public FuncBody(ArrayList<Variable> dcl, ArrayList<Statement> list_stmt){
		this.decl = dcl;
		this.list_stmt = list_stmt;
	}

	public void genC(PW pw){
		if(!decl.isEmpty()){
			int currentIdentAux = pw.get(); //Guardando o valor da Identação atual
			Iterator it = decl.iterator(); //Criando iterador
			Variable e = (Variable) it.next(); //Pegando primeiro elemento da DECL.
			e.genC(pw); //Printando o primeiro elemento da DECL.
			
			while(it.hasNext()){
				Variable aux = (Variable) it.next();
				if(aux.getTipo() != ""){
					pw.set(0); //Setando pw pra 0
					pw.println(";");
					pw.set(currentIdentAux);
				}
				else{
					pw.set(0); //Setando pw pra 0
					pw.print(", ");
					pw.set(currentIdentAux);
				}
				aux.genC(pw);
			}
			pw.set(0); //Setando pw pra 0
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