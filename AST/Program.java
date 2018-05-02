package AST;
import java.util.*;

public class Program{
	private String id;
	private PgmBody pgm_body;

	public Program(String id, PgmBody pb){
		this.id = id;
		this.pgm_body = pb;
	}

	public void genC(){
		System.out.println("#include <stdio.h> \n#include <stlib.h>\n#include <string.h>");
		pgm_body.genC();
		System.out.println("}");
	}
}
