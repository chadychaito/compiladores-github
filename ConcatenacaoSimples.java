public class ConcatenacaoSimples {
    String expr;

    public void ConcatenacaoSimples(){
    	this.expr = "";
    }

    public String getExpr(){
    	return this.expr;
    }

    public void setExpr(String str_expr){
    	this.expr = str_expr;
    }

    public static void main(String[] args) {
    	ConcatenacaoSimples conc = new ConcatenacaoSimples();
    	conc.setExpr("Chady");
        System.out.println(conc.getExpr());

        conc.setExpr(conc.getExpr()+"Hahahaha");
        System.out.println(conc.getExpr());
    }
}