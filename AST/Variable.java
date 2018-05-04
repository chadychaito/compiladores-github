package AST;

public class Variable {
    private String tipo;
    private String ident;
    private String valor;

    public Variable(String tipo, String nome, String valor) {
        this.tipo = tipo;
        this.ident = nome;
        this.valor = valor;
    }


    public void genC(PW pw) {
        if(this.valor != "") 
            if(this.tipo == "string"){
                 pw.print("char[] " + this.ident + " = " + '"' + this.valor + '"'+ ";");
            }
            else{
                pw.print(this.tipo + " " + this.ident + " = " + this.valor + ";");
            }
        else{
            if(this.tipo != "")
                pw.print(this.tipo + " " + this.ident);
            else{
                pw.print(this.ident);
            }
        }
    }

}
