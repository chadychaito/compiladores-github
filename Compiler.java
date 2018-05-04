import Lexer.*;
import Error.*;
import AST.*;
import java.util.*;

public class Compiler {

	// para geracao de codigo
	public static final boolean GC = false;

	public Program compile( char []p_input ) {
		lexer = new Lexer(p_input, error);
		error = new CompilerError(lexer);
		error.setLexer(lexer);
		lexer.nextToken();

		Program p = program();
		if(error.wasAnErrorSignalled())
			return null;
		return p;

	}

 	// program -> PROGRAM id BEGIN pgm_body END
	public Program program(){
		if(lexer.token != Symbol.PROGRAM)
			error.signal("Falta inicializar com PROGRAM");
		lexer.nextToken();

		String ident = id(); // Não precisa guardar

		if(lexer.token != Symbol.BEGIN)
			error.signal("Voce nao utilizou a palavra BEGIN");
		lexer.nextToken();

		PgmBody pb = pgm_body();

		if(lexer.token != Symbol.END)
			error.signal("Voce nao utilizou a palavra END");
		lexer.nextToken();

		return new Program (ident, pb);

	}

  	//id -> IDENTIFIER
	public String id(){
		String aux = lexer.getStringValue();
		if(lexer.token != Symbol.IDENT)
			error.signal("Indentificador nao encontrado");
		lexer.nextToken();

		return aux;
	}

  	//pgm_body -> decl func_declarations
	public PgmBody pgm_body(){
		ArrayList<Variable> decl_list = new ArrayList<Variable>(); //PARECE QUE ESTÁ FEITO
		decl(decl_list);

		ArrayList<FuncDecl> f_decl = func_declarations();

		return new PgmBody(decl_list, f_decl); //ARRUMAR
	}

  	//decl -> string_decl_list {decl} | var_decl_list {decl} | empty
	public void decl(ArrayList<Variable> vet){
		ArrayList<Variable> aux;
    	//Se lista de declarações de string
		if(lexer.token == Symbol.STRING){
			aux = string_decl_list();
			for(Variable e: aux){
    			vet.add(e);
			}
      		//Verifica se existe outra declaração
			if(lexer.token == Symbol.STRING || lexer.token == Symbol.FLOAT || lexer.token == Symbol.INT){
				decl(vet);
			}
		}
		else if (lexer.token == Symbol.INT || lexer.token == Symbol.FLOAT){
			aux = var_decl_list(); 
			for(Variable e: aux){
    			vet.add(e);
			}
      		//Verifica se existe outra declaração
			if(lexer.token == Symbol.STRING || lexer.token == Symbol.FLOAT || lexer.token == Symbol.INT){
				decl(vet);
			}
		}
	}

  	//string_decl_list -> string_decl {string_decl_tail}
	public ArrayList<Variable> string_decl_list(){
		ArrayList<Variable> str = new ArrayList<Variable>();
		string_decl(str); // String_decl return VARIABLE

		if(lexer.token == Symbol.STRING){
			string_decl_tail(str);
		}

		return str; // Retornando a lista de variaveis
	}

  	//string_decl -> STRING id := str ; | empty
	public void string_decl(ArrayList<Variable> str){
    	//Se começar com String então:
    	String aux = lexer.token.toString();
		if(lexer.token == Symbol.STRING){
			lexer.nextToken();
			String ident = id();

			//Se o sinal não é de recebe
			if(lexer.token != Symbol.ASSIGN){
				error.signal("Voce precisa inicializar a variavel");
			}
			lexer.nextToken();
			String string = str();

     		 //Se não tem ponto e virgula no final
			if(lexer.token != Symbol.SEMICOLON){
				error.signal("Voce nao colocou ;");
			}
			lexer.nextToken();

			str.add(new Variable(aux, ident, string));
		}
	}

  	//str-> STRINGLITERAL
	public String str(){
		String aux = lexer.getStringValue();
		if(lexer.token != Symbol.STRINGLITERAL){
			error.signal("Nao é STRING");
		}
		lexer.nextToken();

		return aux;
	}

  	//string_decl_tail -> string_decl {string_decl_tail}
	public void string_decl_tail(ArrayList<Variable> str){
		string_decl(str);
		if(lexer.token == Symbol.STRING){
			string_decl_tail(str);
		}
	}

  	//var_decl_list -> var_decl {var_decl_tail}
	public ArrayList<Variable> var_decl_list(){
		ArrayList<Variable> var = new ArrayList<Variable>();
		var_decl(var);
		if(lexer.token == Symbol.INT || lexer.token == Symbol.FLOAT)
			var_decl_tail(var);
		return var;
	}

  	//var_decl -> var_type id_list ; | empty
	public void var_decl(ArrayList<Variable> var){
    	//Verifica se tem declaração de INT ou FLOAT
		if(lexer.token == Symbol.INT || lexer.token == Symbol.FLOAT){
			String tipo = var_type(); // Var_type retorna "STRING" com tipo
			id_list(tipo, var);

      		//Se não tiver ponto e virgula
			if(lexer.token != Symbol.SEMICOLON)
				error.signal("Faltou ;");

			lexer.nextToken();
		}
	}

  	//var_type -> FLOAT | INT
	public String var_type(){
		String aux = lexer.token.toString(); 
		if(lexer.token != Symbol.FLOAT && lexer.token != Symbol.INT)
			error.signal("Faltou inicializar variavel com FLOAT ou INT");
		lexer.nextToken();

		return aux;
	}

  	//any_type -> var_type | VOID
	public String any_type(){
    	//Se não for VOID
    	String aux = lexer.token.toString();
		if(lexer.token != Symbol.VOID)
			var_type();
		else
			lexer.nextToken();

		return aux;
	}


  	//id_list -> id id_tail 
	public void id_list(String tipo, ArrayList<Variable> var){
		String id = id();
		var.add(new Variable(tipo, id , ""));
		id_tail(var);
	}

  	//id_tail -> , id id_tail | empty
	public void id_tail(ArrayList<Variable> var){
		if(lexer.token == Symbol.COMMA){
			lexer.nextToken();
			var.add(new Variable("", id(), ""));
			id_tail(var);
		}
	}

  	//var_decl_tail -> var_decl {var_decl_tail}
	public void var_decl_tail(ArrayList<Variable> var){
		var_decl(var);

    	//Se tiver mais declarações
		if(lexer.token == Symbol.INT || lexer.token == Symbol.FLOAT)
			var_decl_tail(var);
	}

  	//param_decl_list -> param_decl param_decl_tail
	public ArrayList<Variable> param_decl_list(){
		ArrayList<Variable> param = new ArrayList<Variable>();
		param.add(param_decl());
		param_decl_tail(param);

		return param;
	}

  	//param_decl -> var_type id
	public Variable param_decl(){
		String tipo = var_type();
		String ident = id();
		return new Variable(tipo, ident, "");
	}

  	//param_decl_tail -> , param_decl param_decl_tail | empty
	public void param_decl_tail(ArrayList<Variable> param){
		if(lexer.token == Symbol.COMMA){
			lexer.nextToken();
			param.add(param_decl());
			param_decl_tail(param);
		}
	}

  	//func_declarations -> func_decl {func_decl_tail}
	public ArrayList<FuncDecl> func_declarations(){
		ArrayList<FuncDecl> functions_decl = new ArrayList<FuncDecl>();

		func_decl(functions_decl); //Func_decl() retorna um objeto do tipo FuncDecl
		if(lexer.token == Symbol.FUNCTION)
			func_decl_tail(functions_decl);

		return functions_decl;
	}

  	//func_decl -> FUNCTION any_type id ({param_decl_list}) BEGIN func_body END | empty
	public void func_decl(ArrayList<FuncDecl> func){ // ARRAYLIST DE FuncDecl.java -- Vão ser criados varias declarações de variaveis
		if(lexer.token == Symbol.FUNCTION){
			lexer.nextToken();
			String tipo = any_type();
			String ident = id();
			ArrayList<Variable> param_list = new ArrayList<Variable>();

      		//Verifica se tem ' ( '
			if(lexer.token != Symbol.LPAR){
				error.signal("Faltou (");
			}
			lexer.nextToken();

     		 //Verificar se existe um param_decl_list
			if(lexer.token == Symbol.INT || lexer.token == Symbol.FLOAT){
				param_list = param_decl_list();
			}

      		//Verifica se tem ' ) '
			if(lexer.token != Symbol.RPAR){
				error.signal("Faltou aa)");
			}
			lexer.nextToken();

      		//Verifica se tem BEGIN
			if(lexer.token != Symbol.BEGIN){
				error.signal("Faltou BEGIN");
			}
			lexer.nextToken();

			FuncBody f_body = func_body(); 

      		//Verifica se tem END
			if(lexer.token != Symbol.END){
				error.signal("Faltou END");
			}
			lexer.nextToken();
			func.add(new FuncDecl(tipo, ident , param_list, f_body));
		}
	}

  	//func_decl_tail -> func_decl {func_decl_tail}
	public void func_decl_tail(ArrayList<FuncDecl> functions_decl){
		func_decl(functions_decl);
		if(lexer.token == Symbol.FUNCTION){
			func_decl_tail(functions_decl);
		}
	}

  	//func_body -> decl stmt_list
	public FuncBody func_body(){
		ArrayList<Variable> decl_var = new ArrayList<Variable>();
		decl(decl_var);
		ArrayList<Statement> list_stmt = stmt_list();

		return new FuncBody(decl_var, list_stmt);
	}

  	//stmt_list -> stmt stmt_tail | empty
	public ArrayList<Statement> stmt_list(){
    	//Verifica se STMT_LIST não é EMPTY
    	ArrayList<Statement> list_stmt = new ArrayList<Statement>();
		if(lexer.token == Symbol.IDENT || lexer.token == Symbol.READ || lexer.token == Symbol.WRITE || lexer.token == Symbol.RETURN || lexer.token == Symbol.FOR){
			list_stmt.add(stmt());
			stmt_tail(list_stmt);
		}
		return list_stmt;
	}

  	//stmt_tail -> stmt stmt_tail | empty
	public void stmt_tail(ArrayList<Statement> list_stmt){
    	//Verifica se STMT_TAIL não é EMPTY
		if(lexer.token == Symbol.IDENT || lexer.token == Symbol.READ || lexer.token == Symbol.WRITE || lexer.token == Symbol.RETURN || lexer.token == Symbol.FOR){
			list_stmt.add(stmt());
			stmt_tail(list_stmt);
		}
	}

  	//stmt -> id assign_stmt | read_stmt | write_stmt | return_stmt | if_stmt | for_stmt | id call_expr
	public Statement stmt(){
  		Statement aux_stmt = null;
  		//id
  		if(lexer.token == Symbol.IDENT){
  			String ident = id();
  			Expr expr = new Expr();
  			//assign_stmt
  			if(lexer.token == Symbol.ASSIGN){
  				expr = assign_stmt();
  			}

  			//call_expr
  			else if(lexer.token == Symbol.LPAR){
  				call_expr(expr);
  			}
  			else{
  				error.signal("Faltou := ou (");
  			}

	  		aux_stmt = new CompositeStatement(ident, expr);
  		}

  		//read_stmt
		else if(lexer.token == Symbol.READ){
			String aux = lexer.token.toString();
			ArrayList<Variable> r_stmt = read_stmt();
			aux_stmt = new InOutStatement(aux, r_stmt);
		}
  		//write_stmt
		else if (lexer.token == Symbol.WRITE){
			String aux = lexer.token.toString();
			ArrayList<Variable> w_stmt = write_stmt();
			aux_stmt = new InOutStatement(aux, w_stmt);
		}
  		//return_stmt
		else if (lexer.token == Symbol.RETURN){
			String aux = lexer.token.toString();
			Expr expr = return_stmt();
			aux_stmt = new ReturnStatement(aux, expr);
		}
  		//if_stmt
		else if (lexer.token == Symbol.IF){
			aux_stmt = if_stmt();
		}
  		//for_stmt
		else if (lexer.token == Symbol.FOR){
			aux_stmt = for_stmt();
		}
		return aux_stmt;

	}

  	//assign_stmt -> assign_expr ;
	public Expr assign_stmt(){
		Expr exp = assign_expr();
		if(lexer.token != Symbol.SEMICOLON){
			error.signal("Faltou ;");
		}
		lexer.nextToken();

		return exp; 
	}

  	//assign_expr -> := expr
	public Expr assign_expr(){

		if(lexer.token != Symbol.ASSIGN){
			error.signal("Faltou :=");
		}
		lexer.nextToken();

		Expr exp = new Expr();
		expr(exp);

		return exp;
	}

	//read_stmt-> READ ( id_list );
	public ArrayList<Variable> read_stmt(){
		//Se não começar com READ
		String aux = lexer.token.toString();
		if(lexer.token != Symbol.READ){
			error.signal("Faltou READ");
		}
		lexer.nextToken();

		//Se não tiver ' ( '
		if(lexer.token != Symbol.LPAR){
			error.signal("Faltou (");
		}
		lexer.nextToken();

		ArrayList<Variable> var = new ArrayList<Variable>();
		id_list("", var);

		//Se não tiver ' ) '
		if(lexer.token != Symbol.RPAR){
			error.signal("Faltou )");
		}
		lexer.nextToken();

		//Se não tiver ;
		if(lexer.token != Symbol.SEMICOLON){
			error.signal("Faltou ;");
		}
		lexer.nextToken();

		return var;
	}

	//write_stmt -> WRITE ( id_list );
	public ArrayList<Variable> write_stmt(){
		String aux = lexer.token.toString();
		//Se não começar com READ
		if(lexer.token != Symbol.WRITE){
			error.signal("Faltou WRITE");
		}
		lexer.nextToken();

		//Se não tiver ' ( '
		if(lexer.token != Symbol.LPAR){
			error.signal("Faltou (");
		}
		lexer.nextToken();

		ArrayList<Variable> var = new ArrayList<Variable>();
		id_list("", var);

		//Se não tiver ' ) '
		if(lexer.token != Symbol.RPAR){
			error.signal("Faltou )");
		}
		lexer.nextToken();

		//Se não tiver ;
		if(lexer.token != Symbol.SEMICOLON){
			error.signal("Faltou ;");
		}
		lexer.nextToken();

		return var;
	}

	//return_stmt -> RETURN expr ;
	public Expr return_stmt(){
		//Se não começa com RETURN
		if(lexer.token != Symbol.RETURN){
			error.signal("Faltou RETURN");
		}
		lexer.nextToken();

		Expr exp = new Expr();
		expr(exp);

		//Se não tiver ;
		if(lexer.token != Symbol.SEMICOLON){
			error.signal("Faltou ;");
		}
		lexer.nextToken();

		return exp;
	}


	//expr-> factor expr_tail
	public void expr(Expr expr){
		factor(expr);
		expr_tail(expr);
	}

	//expr_tail -> addop factor expr_tail | empty
	public void expr_tail(Expr expr){
		//Verificando se expr_tail não é EMPTY
		if(lexer.token == Symbol.PLUS || lexer.token == Symbol.MINUS){
			addop(expr); // ADICIONAR NA STRING
			factor(expr);
			expr_tail(expr);
		}
	}

	//factor -> postfix_expr factor_tail
	public void factor(Expr expr){
		postfix_expr(expr);
		factor_tail(expr);
	}

	//factor_tail -> mulop postfix_expr factor_tail | empty
	public void factor_tail(Expr expr){
		//Verificando se facto_tail não é EMPTY
		if(lexer.token == Symbol.MULT || lexer.token == Symbol.DIV){
			mulop(expr); //ADICIONAR NA STRING
			postfix_expr(expr);
			factor_tail(expr);
		}
	}

	//postfix_expr -> primary | id call_expr
	public void postfix_expr(Expr expr){

		//Se id
		if(lexer.token == Symbol.IDENT){
			expr.setExpr((expr.getExpr() + id()));

			//Se tem ' ( '
			if(lexer.token == Symbol.LPAR){
				call_expr(expr);
			}
		}
		//Se primary
		else{
			primary(expr);
		}

	}

	//call_expr -> ( {expr_list} )
	public void call_expr(Expr expr){
		//Se não tem ' ( '
		if(lexer.token != Symbol.LPAR){
			error.signal("Faltou (");
		}
		expr.setExpr((expr.getExpr() + "("));
		lexer.nextToken();

		//Se existe expr_list então tem IDENT ou FLOATLITERAL ou INTLERAL ou LPAR
		if(lexer.token == Symbol.IDENT || lexer.token == Symbol.FLOATLITERAL || lexer.token == Symbol.INTLITERAL || lexer.token == Symbol.LPAR){
			expr_list(expr);
		}

		//Se não tem ' ) '
		if(lexer.token != Symbol.RPAR){
			error.signal("Faltou )");
		}
		expr.setExpr((expr.getExpr() + ")"));
		lexer.nextToken();
	}


	//expr_list -> expr expr_list_tail
	public void expr_list(Expr expr){
		expr(expr);
		expr_list_tail(expr);
	}

	//expr_list_tail -> , expr expr_list_tail | empty
	public void expr_list_tail(Expr expr){
		//Verificando se expr_list_tail não é EMPTY
		if(lexer.token == Symbol.COMMA){
			lexer.nextToken();
			expr.setExpr(expr.getExpr()+",");
			expr(expr);
			expr_list_tail(expr);
		}
	}

	//primary -> (expr) | id |  INTLITERAL | FLOATLITERAL
	public void primary(Expr expr){
		//Se começa com ' ( ' então é EXPR
		if(lexer.token == Symbol.LPAR){
			lexer.nextToken();

			expr(expr);

			//Se não tem terminar com ' ) '
			if(lexer.token != Symbol.RPAR){
				error.signal("Faltou ) ");
			}
			lexer.nextToken();
		}

		//Se for ID
		else if (lexer.token == Symbol.IDENT){
			expr.setExpr(expr.getExpr()+id());
		}

		//Se for INT
		else if(lexer.token == Symbol.INT){
			expr.setExpr(expr.getExpr()+lexer.token.toString());
			lexer.nextToken();
		}
		//Se for FLOAT
		else if(lexer.token == Symbol.FLOAT){
			expr.setExpr(expr.getExpr()+lexer.token.toString());
			lexer.nextToken();
		}
		else{
			error.signal("Faltou expressão, ou ID, ou INTLITERAL ou FLOATLITERAL");
		}
	}

	//addop -> + | -
	public void addop(Expr expr){
		//Verifica se o token é um + ou um -
		if(lexer.token != Symbol.PLUS && lexer.token != Symbol.MINUS){
			error.signal("Faltou + ou -");
		}
		expr.setExpr(expr.getExpr()+lexer.token.toString());
		lexer.nextToken();
	}

	//mulop -> * | /
	public void mulop(Expr expr){
		//Verifica se o token é um + ou um -
		if(lexer.token != Symbol.MULT && lexer.token != Symbol.DIV){
			error.signal("Faltou * ou /");
		}
		expr.setExpr(expr.getExpr()+lexer.token.toString());
		lexer.nextToken();
	}


	//if_stmt -> IF ( cond ) THEN stmt_list else_part ENDIF
	public IfStatement if_stmt(){
		//Se não começa com IF
		if(lexer.token != Symbol.IF){
			error.signal("Faltou IF");
		}
		
		lexer.nextToken();



		//Se não tem '( '
		if(lexer.token != Symbol.LPAR){
			error.signal("Faltou (");
		}
		lexer.nextToken();

		Cond aux_cond = cond();

		//Se não tem ')'
		if(lexer.token != Symbol.RPAR){
			error.signal("Faltou )");
		}
		lexer.nextToken();

		//Se não tem THEN
		if(lexer.token != Symbol.THEN){
			error.signal("Faltou THEN");
		}
		lexer.nextToken();

		ArrayList<Statement> aux_stmt = stmt_list();
		ArrayList<Statement> aux_else_stmt = else_part();

		//Se não tem ENDIF
		if(lexer.token != Symbol.ENDIF){
			error.signal("Faltou ENDIF");
		}

		IfStatement if_st = new IfStatement(aux_cond, aux_stmt, aux_else_stmt);
		lexer.nextToken();
		return if_st;

	}

	//else_part -> ELSE stmt_list | empty
	public ArrayList<Statement> else_part(){
		ArrayList<Statement> aux = new ArrayList<Statement>();
		//Verifica se else_part não é EMPTY
		if(lexer.token == Symbol.ELSE){
			lexer.nextToken();
			return stmt_list();

		}
		return aux;
	}

	//cond -> expr compop expr
	public Cond cond(){
		Expr exprL = new Expr();
		Expr exprR = new Expr();
		expr(exprL);
		String auxcompop = compop();
		expr(exprR);
		return new Cond(exprL, auxcompop, exprR);

	}

	//compop -> < | > | =
	public String compop(){
		//Verifica se difrente de '<' e '>' e '='
		if(lexer.token != Symbol.LT && lexer.token != Symbol.GT && lexer.token != Symbol.EQUAL){
			error.signal("Faltou < ou > ou =");
		}
		String aux = lexer.token.toString();
		lexer.nextToken();
		return aux;
	}


	//for_stmt -> FOR ({id assign_expr}; {cond}; {assign_expr}) stmt_list ENDFOR
	public ForStatement for_stmt(){
		String idL = "";
		Expr exprL = new Expr();
		String idR = "";
		Expr exprR = new Expr();
		Cond cond = new Cond();
		//Se não começa com FOR
		if(lexer.token != Symbol.FOR){
			error.signal("Faltou FOR");
		}
		lexer.nextToken();

		//Se não tem ' ( '
		if(lexer.token != Symbol.LPAR)
			error.signal("Faltou (");

		else{
			lexer.nextToken();

			//Se existe assign_expr então tem IDENT
			if(lexer.token == Symbol.IDENT){
				idL += id();
				exprL = assign_expr();
			}

			//Se não tem ' ; '
			if(lexer.token != Symbol.SEMICOLON){
				error.signal("Faltou ;");
			}
			lexer.nextToken();

			//Se existe cond então tem IDENT ou FLOATLITERAL ou INTLERAL ou LPAR
			if(lexer.token == Symbol.IDENT || lexer.token == Symbol.FLOATLITERAL || lexer.token == Symbol.INTLITERAL || lexer.token == Symbol.LPAR){
				cond = cond();
			}

			//Se não tem ' ; '
			if(lexer.token != Symbol.SEMICOLON){
				error.signal("Faltou ;");
			}
			lexer.nextToken();

			//Se existe assign_expr então tem IDENT
			if(lexer.token == Symbol.IDENT){
				idR += id();
				exprR = assign_expr();
			}

			//Se não tem ' ) '
			if(lexer.token != Symbol.RPAR){
				error.signal("Faltou )");
			}
			lexer.nextToken();
		}

		ArrayList<Statement> aux_stmt = stmt_list();

		//Se não tem ENDFOR
		if(lexer.token != Symbol.ENDFOR){
			error.signal("Faltou ENDFOR");
		}
		lexer.nextToken();
		return new ForStatement (idL, exprL, cond, idR, exprR, aux_stmt);
	}


	private Lexer lexer;
	private CompilerError error;


}
