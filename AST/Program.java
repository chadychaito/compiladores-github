package AST;
import java.util.*;

public class Program{
	private String id;
	private PgmBody pgm_body;

	public Program(String id, PgmBody pb){
		this.id = id;
		this.pgm_body = pb;
	}

	public void genC(PW pw){
		pw.println("#include <stdio.h>");
		pw.println("#include <stlib.h>");
		pw.println("#include <string.h>");
		pw.println("");
		
		pgm_body.genC(pw);

		System.out.println("}");
	}
}
