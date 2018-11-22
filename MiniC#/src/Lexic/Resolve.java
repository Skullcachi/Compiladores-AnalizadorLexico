/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexic;
import java.util.Stack;

/**
 *
 * @author cachi
 */
public class Resolve {

    private static boolean Stop;
    
    String Operacion;
    
    public Resolve(String OP)
    {
        Operacion = Operar(OP);
    }
    
public static String Operar(String Op)
  {
      
     String[] post = Op.split(" ");   
   
    //Declaración de las pilas
    Stack < String > E = new Stack < String > (); //Pila entrada
    Stack < String > P = new Stack < String > (); //Pila de operandos    

    //Añadir post (array) a la Pila de entrada (E)
    for (int i = post.length - 1; i >= 0; i--) {
      E.push(post[i]);
    }

    //Algoritmo de Evaluación Postfija
    String operadores = "+-*/%";
    while (!E.isEmpty() && Stop == false) {
      if (operadores.contains("" + E.peek())) {
        P.push(evaluar(E.pop(), P.pop(), P.pop()) + "");
      }else {
        P.push(E.pop());
      }
    }

    //Mostrar resultados:
    //System.out.println("Expresion: " + Op);
    String Result = Rev(P.peek()).toString();
    //System.out.println("Resultado: " + Result);
    return Result;
  }

  private static float evaluar(String op, String n2, String n1) {
    
    float num1 = 0,num2 = 0; 
    boolean NullN1 = false,NullN2 = false;
    boolean NumN1 = SymbolTable.isNumeric(n1);
    boolean NumN2 = SymbolTable.isNumeric(n2);
    
    if (NumN1 == true)
    {
        num1 = Float.parseFloat(n1);
        
    }
    else
    {
        if (SymbolTable.ReturnVal(n1) == null)
        {
            num1 = 0;
            NullN1 = true;
        }
        else
        {
            String rev = String.valueOf(SymbolTable.ReturnVal(n1));
            boolean chk2 = SymbolTable.isNumeric(rev);
            if(chk2 == false)
            {
                Stop = true;
            }
            else
            {
                num1 = Float.parseFloat(SymbolTable.ReturnVal(n1));
            }
        }
    }
    
      if (NumN2 == true)
    {
        num2 = Float.parseFloat(n2);
    }
    else
    {
        if (SymbolTable.ReturnVal(n2) == null)
        {
            num2 = 0;
            NullN2 = true;
        }
        else
        {
            String rev = String.valueOf(SymbolTable.ReturnVal(n2));
            boolean chk2 = SymbolTable.isNumeric(rev);
            if(chk2 == false)
            {
                Stop = true;
            }
            else
            {
                num2 = Float.parseFloat(SymbolTable.ReturnVal(n2));
            }
        }
    }
      
    if (NullN1 == true || NullN2 == true || Stop == true)
    {
        Stop = true;
        return -1;
    }
    else
    {
        Stop = false;
        if (op.equals("+")) return (num1 + num2);
        if (op.equals("-")) return (num1 - num2);
        if (op.equals("*")) return (num1 * num2);
        if (op.equals("/")) return (num1 / num2);
        if (op.equals("%")) return (num1 % num2);
        return 0;
    }
  }
  
  private static Object Rev(String Result)
  {
      float N = Float.parseFloat(Result);
      
      if(N % 1 == 0)
      {
         return Math.round(Float.parseFloat(Result));
      }
      else
      {
          return Result;
      }
  }
  
  public String getResult()
  {
      return Operacion;
  }
    
}

