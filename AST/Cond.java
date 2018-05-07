package AST;
import java.util.*;

public class Cond{

	public Cond(){
		this.condL = new Expr();
		this.compop = "";
		this.condR = new Expr();
	}

	public Cond(Expr condL, String compop, Expr condR){
		this.condL = condL;
		this.condR = condR;
		this.compop = compop;
	}


	public void genC(PW pw){
		condL.genC(pw);
		pw.print(" " + compop + " ");
		condR.genC(pw);
	}

	public String getCompop(){
		return this.compop;
	}
	

	private Expr condL;
	private Expr condR;
	private String compop;

}