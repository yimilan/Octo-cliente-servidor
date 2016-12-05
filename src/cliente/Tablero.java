/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package cliente;

import java.util.StringTokenizer;

/**
 *
 * @author anyel
 */
public class Tablero {

     public static int FIL = 13;
    public static int COL = 9;
    public static int[][] tablero = new int[][]{
        {0, 0, 0, 0, 2, 0, 0, 0, 0},
        {0, 0, 0, 1, 1, 1, 0, 0, 0},
        {0, 0, 1, 1, 1, 1, 1, 0, 0},
        {0, 1, 1, 1, 0, 1, 1, 1, 0},
        {3, 1, 1, 1, 1, 1, 1, 1, 3},
        {1, 1, 1, 0, 1, 0, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1},
        {2, 1, 1, 1, 1, 1, 1, 1, 2},
        {0, 1, 1, 1, 1, 1, 1, 1, 0},
        {0, 0, 1, 1, 1, 1, 1, 0, 0},
        {0, 0, 0, 1, 1, 1, 0, 0, 0},
        {0, 0, 0, 0, 3, 0, 0, 0, 0}
    };
    
     public static String initStringTable(){
         String tableString = "";
        for (int i = 0; i < FIL; i++) {
            for (int j = 0; j < COL; j++) {
                tableString += tablero[i][j];
                    tableString += ","; 
            }
        }
        System.out.println(tableString);
        return tableString;
    }
    public static void reconstruirTable(String cadena){
        StringTokenizer tokens = new StringTokenizer(cadena,",");
        tablero = new int[FIL][COL];
        for (int i = 0; i < FIL; i++) {
            for (int j = 0; j < COL; j++) {     
                tablero[i][j] = Integer.parseInt(tokens.nextToken());
                System.out.print(" "+ tablero[i][j]);
            }
            System.out.println("");
        }
    }   
}
