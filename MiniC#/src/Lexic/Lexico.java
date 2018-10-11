/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SERGIO-PC
 */
public class Lexico {
    
    
    static String PATH = "C:/Users/cachi/Documents/Landivar/Compiladores/Compiladores-AnalizadorLexico/MiniC#/src/Lexic/Yylex.flex";
    static String PATHCUP = "C:/Users/cachi/Documents/Landivar/Compiladores/Compiladores-AnalizadorLexico/MiniC#/src/Lexic/asintactico.cup";
        
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        GenerarLexer(PATH);
        GenerarCup(PATHCUP);
    }
    
    public static void GenerarLexer(String path){
        File output = new File(path);
        jflex.Main.generate(output);
    }
    
    public static void GenerarCup(String path){
        File output = new File(path);
        String[] asintactico = { "-parser","asintactico", path};
        try
        {
            java_cup.Main.main(asintactico);
        }
        catch (Exception ex)
        {
            Logger.getLogger(Lexico.class.getName()).log(Level.SEVERE, null, ex);
        }
        /* Los archivos se generan en la carpeta raiz del proyecto, por lo que hay que moverlos para que funcionen correctamente*/
        moveFile("asintactico.java");
        moveFile("sym.java");
    }
    
    public static boolean moveFile(String fileName)
    {
        boolean fileMoved = false;
        File file =  new File(fileName);
        if (file.exists())
        {
            System.out.println("moving cup generated files to the correct path");
            Path currentRelativePath = Paths.get("");
            String newDir = currentRelativePath.toAbsolutePath().toString()
                    + File.separator + "src" + File.separator
                    + "Lexic" + File.separator + file.getName();
            if (file.renameTo(new File(newDir)))
            {
                System.out.println("the cup generated file has been moved successfully.");
                fileMoved = true;
            }
            else
            {
                System.out.println("ERROR, the file could not be moved.");                
            }
        }
        else
        {
            System.out.println("File could not be found!");
        }
        return fileMoved;
    }
    /*public List<String> transformar(String entrada) throws IOException {
        ManejadorFichero m = new ManejadorFichero();
        List<String> salida = new LinkedList<String>();

        try 
        {
            Yylex lex = new Yylex( new FileReader(entrada) );
            String token;
            token = lex.yylex();
            while( token!= null)
            {
                salida.add(token);
                token = lex.yylex();
            }

        } 
        catch (IOException ex) 
        {
            //ex.printStackTrace();
            String s = "Se ha producido un error al no reconocerse en el léxico un elemento del texto:\n" + ex.getStackTrace().toString();

            throw new IOException(s);

        }

        System.out.println("Introduzca la ruta del fichero de salida.");
        String fsalida = leerCadena();
        m.sobreescribir(fsalida,salida);
        System.out.println("Programa finalizado con exito. Revise el archivo de salida.");

        return salida;

    }*/
    
    /**

    * Método principal, para la ejecución de la clase.

    * @param args irrelevante en este caso.

    * @throws ClassNotFoundException

    */

    /*public static void main(String[] args) throws ClassNotFoundException{

        System.out.println("Introduzca la ruta del fichero que contendrá el léxico.");
        String file = leerCadena();

        //El fichero a transformar

        Lexico lex = null;

        //String file= “src/lexico/Lexer.flex“;


        try 
        {
            lex = new Lexico(file);
            ClassLoader loader = lex.getClass().getClassLoader();
            loader.getSystemClassLoader();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        System.out.println("Introduzca la ruta del archivo de entrada.");
        String entrada = leerCadena();

        try 
        {
            lex.transformar(entrada);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }*/
}
