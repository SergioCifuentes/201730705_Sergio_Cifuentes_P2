/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexico;

/**
 *
 * @author sergio
 */
public class Token {
    public  final static int TIPO_PALABRA_RESERVADOA=1;
    public final static int TIPO_BOOLEAN=2;
    public final static int TIPO_OPERADOR=3;
    public final static int TIPO_AGRUPACION=4;
    public final static int TIPO_SIGNO=5;
    public final static int TIPO_NUMERO_ENTERO=6;
    public final static int TIPO_NUMERO_FLOTANTE=7;
    public final static int TIPO_IDENTIFICADOR=8;
    public final static int TIPO_CADENA=9 ;
    public final static int TIPO_CARACTER=10;
    public final static int TIPO_COMENTARIO=11;
    
    
    
    private int tipo;
    private String caracter;
    private int linea;
    
    

    public Token(int tipo, String caracter) {
        this.tipo = tipo;
        this.caracter = caracter;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getTipo() {
        return tipo;
    }

    public String getCaracter() {
        return caracter;
    }

    public int getLinea() {
        return linea;
    }
    
        public String getTipoString(){
        switch (tipo) {
            case 1:
                return "Palabra Reservada";
            case 2:
                return "Boolean";
            case 3:
                return "Operador";
            case 4:
                return "Agrupacion";
            case 5:
                return "Signo";
            case 6:
                return "Numero Entero";
            case 7:
                return "Numero Flotante";
            case 8:
                return "Identificador";
            case 9:
                return "Cadena";
            case 10:
                return "Caracter";
            case 11:
                return "Comentario";
                
            default:
                throw new AssertionError();
        }
    }
    
}
