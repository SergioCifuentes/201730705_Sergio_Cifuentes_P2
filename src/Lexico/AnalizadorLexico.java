/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexico;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author sergio
 */
public class AnalizadorLexico {

    public static int[][] transicion;

    String expresionLetra = "//^[a-z]|^[A-Z]*/";
    Pattern pat = Pattern.compile("[a-z]");
    Pattern pat2 = Pattern.compile("[0-9]");
    Pattern pat3 = Pattern.compile("[+|-|*|%|=|>|<|(|)|{|}|;|(|)|{|}]");

    Pattern palabrasReservadas = Pattern.compile("[variable|entero|mientras|hacer|si|sino|decimal|boolean|cadena|funcion]");
    Pattern booleanos = Pattern.compile("[VERDADERO|FALSO]");
    Pattern pat6 = Pattern.compile("[+|-|*|/|%|=|>|<|>=|<=|==]");
    Pattern agrupacion = Pattern.compile("[(|)|{|}]");
    Pattern signo = Pattern.compile("[;]");
    Pattern comillasSimples = Pattern.compile("['|]");

    public AnalizadorLexico() {
        llenarTabla();
    }
    private int numPalabra;
    private int caracterPalabra;

    public ArrayList<Token> obtenerToken(String linea) {
        ArrayList<Token> token = new ArrayList<>();
        Matcher mat = null;
        numPalabra = 0;
        caracterPalabra = 0;
        String[] palabra = linea.split(" ");
        for (int i = 0; i < palabra.length; i++) {

            String palabraAnalizar;
            if (numPalabra == palabra.length) {
                numPalabra = 0;
            }
            System.out.println(caracterPalabra + "carter2");
            if (caracterPalabra != 0) {
                palabraAnalizar = palabra[numPalabra].substring(caracterPalabra);
                System.out.println("spli" + palabraAnalizar);
            } else {
                palabraAnalizar = palabra[numPalabra];
            }

            numPalabra++;
            int estado = 0;
            System.out.println("ppp" + palabraAnalizar);
            for (int j = 0; j < palabraAnalizar.length(); j++) {
                char caracter = palabraAnalizar.charAt(j);
                if (j == 0) {
                    estado = 0;
                }
                if (pat.matcher(Character.toString(caracter)).matches()) {
                    System.out.println("ssssssi" + estado);
                    estado = transicion[estado][0];
                } else if (pat2.matcher(Character.toString(caracter)).matches()) {
                    estado = transicion[estado][1];
                } else if (pat3.matcher(Character.toString(caracter)).matches()) {
                    int aux = estado;
                    estado = transicion[estado][2];
                    if (j == 0 && j < palabraAnalizar.length() - 1) {
                        numPalabra--;
                        Matcher mat4 = pat3.matcher(palabraAnalizar.substring(j + 1, j + 1));

                        if (mat4.matches()) {
                            if (i == palabraAnalizar.length() - 2) {
                                numPalabra++;
                                caracterPalabra = 0;
                            } else {
                                caracterPalabra = j + 2;
                            }
                            palabraAnalizar = caracter + "" + palabraAnalizar.charAt(j + 1);
                            finalizarPalabra(palabraAnalizar, estado, token);
                        } else {
                            caracterPalabra = caracterPalabra + 1;
                            String cambio = Character.toString(palabraAnalizar.charAt(0));
                            finalizarPalabra(cambio, estado, token);
                        }
                    } else if (j == 0) {
                        caracterPalabra = 0;
                        finalizarPalabra(palabraAnalizar, estado, token);
                    } else {
                        numPalabra--;
                        caracterPalabra = caracterPalabra + j;

                        System.out.println(palabraAnalizar.substring(0, j) + " " + aux);
                        finalizarPalabra(palabraAnalizar.substring(0, j), aux, token);

                        System.out.println(caracterPalabra + "ccccc");

                    }
                } else if (caracter == '.') {
                    estado = transicion[estado][3];
                } else if (caracter == '"') {
                    estado = transicion[estado][4];
                } else if (comillasSimples.matcher(Character.toString(caracter)).matches()) {
                    estado = transicion[estado][5];

                }
                if (estado == 0) {
                    return null;
                }

            }
            int fin = finalizar(estado, palabraAnalizar);
            if (fin == 0) {
                return null;
            } else {
                token.add(new Token(finalizar(estado, palabraAnalizar), palabraAnalizar));
            }

        }

        return token;
    }

    public static void llenarTabla() {
        transicion = new int[14][9];
        for (int i = 0; i < transicion.length; i++) {
            for (int j = 0; j < transicion[0].length; j++) {
                transicion[i][j] = 0;

            }

        }
        //E0 
        transicion[0][0] = 1;
        transicion[0][1] = 2;
        transicion[0][2] = 3;
        transicion[0][4] = 7;
        transicion[0][5] = 8;
        transicion[0][6] = 9;

        //E1 Letras
        transicion[1][0] = 1;
        transicion[1][1] = 4;

        //E2 Numeros
        transicion[2][1] = 2;
        transicion[2][3] = 5;

        //E3 Simbolos
        transicion[3][2] = 3;

        //E6 Flotante
        transicion[6][1] = 6;

        //E4 Identificador
        transicion[4][0] = 4;
        transicion[4][1] = 4;

        //E5 Pos-Flotante
        transicion[5][1] = 6;

        //E6 Flotante
        transicion[6][1] = 6;

        //E7 Pos-Cadena
        transicion[7][0] = 7;
        transicion[7][2] = 7;
        transicion[7][4] = 10;
        //E8 Pos-Caracter
        transicion[8][0] = 11;
        transicion[8][2] = 11;

        //E9 Pos-Comentario
        transicion[9][7] = 13;

        //E11 Pos-Caracter con Char
        transicion[11][5] = 12;

        //E13 Pos-Comentario
        transicion[13][8] = 14;

    }

    private void finalizarPalabra(String palabraAnalizar, int estado, ArrayList<Token> list) {

        Token token = new Token(finalizar(estado, palabraAnalizar), palabraAnalizar);

        list.add(token);
        System.out.println(palabraAnalizar + token.getTipoString());
    }

    private int finalizar(int estado, String caracter) {
        System.out.println("error " + estado + caracter);
        int es = 0;
        switch (estado) {
            case 1:
                System.out.println(caracter);
                if (palabrasReservadas.matcher(caracter).matches()) {
                    return 1;
                }

                if (booleanos.matcher(caracter).matches()) {
                    return 2;
                } else {
                    return 8;
                }
            case 2:
                return Token.TIPO_NUMERO_ENTERO;
            case 3:
                if (agrupacion.matcher(caracter).matches()) {
                    return Token.TIPO_AGRUPACION;
                }
                if(signo.matcher(caracter).matches()){
                    return Token.TIPO_SIGNO;
                }
                return Token.TIPO_OPERADOR;

            case 4:
                return Token.TIPO_IDENTIFICADOR;
            case 5:
                return 0;

            case 6:
                return Token.TIPO_NUMERO_FLOTANTE;
            case 10:
                return Token.TIPO_CADENA;
            default:
                throw new AssertionError();
        }

    }

}
