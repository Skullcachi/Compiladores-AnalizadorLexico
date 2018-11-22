/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexic;

import java.util.HashMap;

/**
 *
 * @author cachi
 */
public class SymbolTable {
    static SymbolTable root = new SymbolTable(null);
    static SymbolTable top = root;
    HashMap table;
    String Operacion;
    SymbolTable prev;
    
    public SymbolTable (SymbolTable p)
    {
        table = new HashMap();
        prev = p;
    }
    //c = nombre de clase, sc = nombre de extends, s = class
    public static int putClass(String c, String sc, String s)
    {
        //busca la clase y si existe le da un push al hashtable, retorna 1 y significa error, la clase ya esta definida
        if(root.table.containsKey(c))
        {
            System.out.println("  Entrando a la Clase: " + c);
            push();
            return 1;
        }
        //si el extends viene null, se inserta la clase al root hash table y se cambia al ambito de la nueva clase, retorna 0 que significa que se inserto todo bien
        if(sc == null)
        {
            root.table.put(c, s);
            System.out.println("  Entrando a la Clase: " + c);
            top.table.put(c, s);
            push();
            return 0;
        }
        // si la  tabla no contiene el extends, retorna 2 que significa que esa clase no esta definida en el ambito actual.
        if (!root.table.containsKey(sc))
        {
            root.table.put(c, s);
            System.out.println("  Entrando a la Clase: " + c);
            top.table.put(c, s);
            push();
            return 2;
        }
        // se inserta la clase en el hashtable cuando ya existe el extends en el hashtable
        else
        {
            root.table.put(c, s);
            System.out.println("  Entrando a la Clase: " + c);
            top.table.put(c, s);
            push();
            return 0;
        }
    }
    
    //Busca si ya existe la clase de tipo interfaz, retorna true si existe la interfaz en la hashtable y false si no existe.
    public static boolean getInterfaces(String I)
    {
        if(!root.table.containsKey(I))
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    
    //cuando viene una variable (int, double o string) se inserta a la hashtable del ambito actual
    public static boolean put(Object name, Object tipo)
    {
        if(!top.table.containsKey(name))
        {
            Info Data = new Info(tipo.toString(),null);            
            top.table.put(name, Data);
            System.out.println("  Nuevo Identificador: "+name+" -> Ambito Actual: "+top);
            return true;
        }
        return false;
    }
    
    //obtiene la hashtable del ambiente actual
    public static sym get(String name)
    {
        for(SymbolTable e = top; e != null; e = e.prev)
        {
            sym found = (sym) (e.table.get(name));
            if (found != null)
            {
                return found;
            }            
        }
        return null;
    }
    
    //crea una nueva hashtable con el ambito nuevo
    public static void push()
    {
        top = new SymbolTable(top);
        System.out.println(" -> Ambito Actual: "+top);
    }
    
    //regresa al ambito padre
    public static void pop()
    {
        top = top.prev;
        System.out.println(" -> Ambito Actual: "+top);
    }
    
    //devuelve todo lo que hay adentro de la tabla, si hay un ambito padre, imprime lo de ese ambito y luego lo del ambito actual
    public String toString()
    {
        if(prev != null)
        {
            return prev.toString() + table;            
        }
        else
        {
            return ""+table;
        }
    }
    
    //Lvalue es la parte izquierda de la producción, valor es lo que voy a asignar
    public static void Validar(String Lvalue, String Valor)
    {        
        Info aux = (Info) (top.table.get(Lvalue));
        if(Valor.contains("+") || Valor.contains("-") || Valor.contains("*") || Valor.contains("/"))
        {
            String Resultado = Operate(Valor);
            
            //Se detecto un string del lado derecho
            if(Resultado.equals("-1"))
            {
                System.out.println("  Error: No se puede realizar la operacion de asignacion para el valor de  " + Lvalue + " -> Ambito Actual: "+top);
            }
            else //Si se pudo resolver el lado derecho de la producción
            {               
                if(aux.type == "string" )
                {                    
                   System.out.println("  Error: No se puede asignar un valor numerico a un string " + Lvalue + " -> Ambito Actual: "+top);
                }
                else
                {                     
                    String tipo;
                    //Se verifica si el resultado es de tipo int o double
                    if(IsFloat(Resultado))
                    {                        
                        tipo = "integer";
                    }
                    else
                    {
                        tipo = "double";
                    }
                    //Se asigna el valor del lado izquierdo al lado derecho
                    if(aux.type == tipo)
                    {
                        aux.value = Resultado;
                        top.table.replace(Lvalue, aux);
                        System.out.println(" Nuevo valor para el identificador "+Lvalue+ " -> Valor: "+Resultado + " -> Ambito Actual: "+top);
                    }
                    else
                    {
                       System.out.println(" Error: No se puede asignar un valor a la variable " +Lvalue + " -> " + aux.type + " & " + tipo + " no son compatibles" + " -> Ambito Actual: "+top);   
                    }
                }
            }
        }
        //Si el lado derecho no es una operacion aritmetica, si no que es una asignacion
        else
        {
                boolean Variable = isNumeric(Valor);
                String tipo;
                if (Variable == true)
                {
                    if(aux.type == "string" )
                    {
                        System.out.println("  Error: No se puede asignar un valor numerico a un string " + Lvalue + " -> Ambito Actual: "+top);
                    }
                    else
                    {
                        if(IsFloat(Valor))
                        {
                            tipo = "integer";
                        }
                        else
                        {
                            tipo = "double";
                        }
                        if(aux.type == tipo)
                        {
                            aux.value = Valor;
                            top.table.replace(Lvalue, aux);            
                            System.out.println("  Nuevo valor para el identificador "+Lvalue+ " -> Valor: "+Valor + " -> Ambito Actual: "+top);
                        }
                        else
                        {
                           System.out.println("  Error: No se puede asignar un valor a la variable " +Lvalue + " -> " + aux.type + " & " + tipo + " no son compatibles" + " -> Ambito Actual: "+top);  
                        }
                    }
                }
                // el tipo no es numerico, es string
                else
                {
                    String tipoLval = aux.type;
                    if(top.table.containsKey(Valor))
                    {
                        //Verificar tipos
                         Info tmp = (Info) (top.table.get(Valor));
                         if(tipoLval == tmp.type)
                         {
                             if(tmp.value == null)
                             {
                                 System.out.println("  Error: No se puede asignar un valor nulo a la variable " + Lvalue + " -> Ambito Actual: "+top);
                             }
                             else
                             {
                                aux.value = tmp.value;
                                top.table.replace(Lvalue, aux.value);
                                System.out.println("  Nuevo valor para el identificador "+Lvalue+ " -> Valor: "+aux.value + " -> Ambito Actual: "+top);
                             }
                         }
                         else
                         {
                             System.out.println("  Error: No se puede asignar un valor a la variable " +Lvalue + " -> " + tipoLval + " & " + tmp.type + " no son compatibles" + " -> Ambito Actual: "+top);
                         }
                        
                    }
                    else
                    {
                        if(Valor.contains("\""))
                        {
                            if(aux.type == "string")
                            {
                                 aux.value = Valor;
                                 top.table.replace(Lvalue, aux);            
                                 System.out.println("  Nuevo valor para el identificador "+Lvalue+ " -> Valor: "+Valor + " -> Ambito Actual: "+top);
                                
                            }
                            else
                            {
                                System.out.println("  Error: No se puede asignar un valor String a la variable " + Lvalue + " -> Ambito Actual: "+top);
                            }
                        }
                        else
                        {
                            System.out.println("  Error: No se puede asignar el valor de una variable no existente a la variable " + Lvalue + " -> Ambito Actual: "+top);
                        }
                    }
                }
        }
    }
    
    public static boolean isNumeric(String S)
    {
        try
        {
            double d = Double.parseDouble(S);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
    
    public static String ReturnVal(String S)
    {
        Info aux = (Info) (top.table.get(S));       
        return aux.value.toString();
    }
    
    //S es el lado derecho de la producción, devuelve un string con el resultado de la operacion
    public static String Operate(String S)
    {
        Posfijo Stage1 = new Posfijo(S);
        String Posfijo = Stage1.getPostFix();    
        Resolve Stage2 = new Resolve(Posfijo);
        return Stage2.getResult();
    }
    
    private static boolean IsFloat(String Result)
    {
        float floatNumber = Float.parseFloat(Result);

        if(floatNumber % 1 == 0)
        {
           return true;
        }
        else
        {
            return false;
        }
    }   
}
