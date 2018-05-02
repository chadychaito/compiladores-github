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


    public void genC() {
        if(this.valor != "") 
            System.out.println(this.tipo + " " + this.ident + " = " + this.valor + ";");
        else
            System.out.println(this.tipo + " " + this.ident);

    }

}
