package AST;

import java.util.*;

public class Expr{

    public Expr(){
        this.expr = "";
    }

    public String getExpr(){
        return this.expr;
    }

    public void setExpr(String str_expr){
        this.expr = str_expr;
    }

    public void genC(){
        System.out.println(expr);
    }


    private String expr;
}

