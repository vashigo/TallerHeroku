/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arem;

import static spark.Spark.*;
import spark.Request;
import spark.Response;



/**
 *
 * @author andres_vaz
 */

public class SparkWebApp {
    /**
     * Implicitly sets up a web server and publishes a set of endpoints that can
     * be accessed through HTTP.
     *
     * @param args Command line arguments
     */
    
    public static void main(String[] args) {
        setPort(4567);
        port(getPort());
        get("/inputdata", (req, res) -> inputDataPage(req, res));
        get("/results", (req, res) -> resultsPage(req, res));
        

    }

    private static String inputDataPage(Request req, Response res) {
        String pageContent
                = "<!DOCTYPE html>"
                + "<html>"
                + "<body>"
                + "<h2>Media y desviación estandar de n numeros reales</h2>"
                + "<h3>introduzca cada valor separado por un espacio</h3>"
                + "<form action=\"/results\">"
                + "  Columna 1 (ejemplo: 160 591 114 229 230 270 128 1657 624 1503):<br>"
                + "  <input type=\"text\" name=\"columna1\" value=\"\" required>"
                + "  <br>"
                + "  Columna 2 (ejemplo: 15.0 69.9 6.5 22.4 28.4 65.9 19.4 198.7 38.8 138.2):<br>"
                + "  <input type=\"text\" name=\"columna2\" value=\"\" required"
                + "  <br><br>"
                + "  <input type=\"submit\" value=\"Submit\">"
                + "</form>"
                + "</body>"
                + "</html>";
        
        return pageContent;
    }

    private static String resultsPage(Request req, Response res) {
        String pageContent;
        String[] col1 = req.queryParams("columna1").split(" ");
        String[] col2 = req.queryParams("columna2").split(" ");
        double[] coll1= convertSaD(col1);
        double[] coll2= convertSaD(col2);
        
        double media1=promedio(coll1);
        double media2=promedio(coll2);
        //sacar desviacion estandar
        double DesvEstandar1=desviacion(coll1);
        double DesvEstandar2=desviacion(coll2);
        
        if (coll1.length == coll2.length){
            pageContent
            = "<!DOCTYPE html>"
            + "<html>"
            + "<body>"
            + "<h2>Los resultados son:</h2>"
            + "<h3 style=\"color:red;\">Columna 1:</h3>"
            + "<h4>"+"MEDIA:"+"  "+String.format("%.2f", media1)+"</h4>"
            + "<h4>"+"DESVIACIÓN ESTANDAR:"+"  "+String.format("%.2f", DesvEstandar1)+"</h4>"
            + "<h3 style=\"color:red;\">Columna 2:</h3>"
            + "<h4>"+"MEDIA:"+"  "+String.format("%.2f", media2)+"</h4>"
            + "<h4>"+"DESVIACIÓN ESTANDAR:"+"  "+String.format("%.2f", DesvEstandar2)+"</h4>";
        }else{
            pageContent="error: columna 1 y columna 2 deben contener la misma cantidad de datos";
        }
        return pageContent;
    }
        

    /**
     * This method reads the default port as specified by the PORT variable in
     * the environment.
     *
     * Heroku provides the port automatically so you need this to run the
     * project on Heroku.
     */
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
  /**
     * Método que calcula el promedio en una Linkedlist global
     */ 
    private static double promedio(double list[]) {
        double prom=0.0;
        for ( int i = 0; i < list.length; i++ ){
            prom += list[i];
        }
        
        return prom / ( double ) list.length;
    }    
    /**
     * Método que calcula la Desviacion estandar de una LinkedList
     */
    private static double desviacion (double list[]) {
      double prom, sum = 0; int i, n = list.length;
      prom = promedio(list);

      for ( i = 0; i < n; i++ ) 
        sum += Math.pow ( list[i] - prom, 2 );

      return Math.sqrt ( sum / (n-1) );
    }
    private static double[] convertSaD (String list[]) {
      double[] res = new double[list.length];

      for ( int i = 0; i < list.length; i++ ) 
        res[i] = Double.parseDouble(list[i]);

      return res;
    }

}