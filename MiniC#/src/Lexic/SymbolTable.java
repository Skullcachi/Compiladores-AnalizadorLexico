/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author cachi
 */
public class SymbolTable
{
    public static SymbolTable root = new SymbolTable(null);
    public static SymbolTable top = root;
    public static HashMap m_table = new HashMap();
    public static ArrayList<Salida> output = new ArrayList<>();
    public HashMap table;
    public String Operacion;
    public SymbolTable prev;
    
    public SymbolTable (SymbolTable p)
    {
        table = new HashMap();
        prev = p;
    }
    
    public static int putClass(String c, String sc, Info s)
    {
        if(root.table.containsKey(c))
        {
            System.out.println("  Entrando a la Clase: " + c);
            push();
            return 1;
        }
        if(sc == null)
        {
            root.table.put(c, s);
            System.out.println("  Entrando a la Clase: " + c);
            top.table.put(c, s);
            push();
            s = new Info();
            s.ambito = "Main";
            s.elementType = "Clase";
            Salida v_class =new Salida(c, s);
            output.add(v_class);
            return 0;
        }
        if (!root.table.containsKey(sc))
        {
            root.table.put(c, s);
            System.out.println("  Entrando a la Clase: " + c);
            top.table.put(c, s);
            push();
            return 2;
        }
        else
        {
            root.table.put(c, s);
            System.out.println("  Entrando a la Clase: " + c);
            top.table.put(c, s);
            push();
            s = new Info();
            s.ambito = "Main";
            s.elementType = "Clase";
            Salida v_class =new Salida(c, s);
            output.add(v_class);            
            return 0;
        }
    }
    
    public static void getInterfaces(String I)
    {
        String[] Interfaces;
        
        if(I.contains(","))
        {
            Interfaces = I.split(",");
            
            for(int i=0; i< Interfaces.length; i++)
            {
                if(!root.table.containsKey(Interfaces[i]))
                {
                   System.out.println("  La interfaz " + Interfaces[i] + " no existe en el ambito actual" + " -> Ambito Actual: "+top); 
                }
            }
        }
        else
        {
            if(!root.table.containsKey(I))
            {
                System.out.println("  La interfaz " + I + " no existe en el ambito actual" + " -> Ambito Actual: "+top);
            }
        }
    }
    
    public static boolean put(Object name, Object tipovar ,Object tipo)
    {
        if(!top.table.containsKey(name))
        {
            Info Data = new Info(tipo.toString(),tipovar.toString(),null);            
            top.table.put(name, Data);
            Data.ambito = top.prev.table.toString();
            System.out.println("  Nuevo Identificador: "+name+" -> Ambito Actual: "+top);
                                      if (Data != null) 
                                      {
                                 if (Data.elementType.compareTo("m")==0) 
                                 {
                                           if (!m_table.containsKey(name)) 
                                           {
                                                    m_table.put(name, Data);
                                                    Salida out = new Salida(name.toString(), Data);
                                                    output.add(out);                                                    
                                             }
                                 }
                                 else
                                 {
                                        Salida out = new Salida(name.toString(), Data);
                                        output.add(out);
                                 }
}
            return true;
        }
        return false;
    }
    
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
    
    public static void push()
    {
        top = new SymbolTable(top);
        System.out.println(" -> Ambito Actual: "+top);
    }
    
    public static void pop()
    {
        top = top.prev;
        System.out.println(" -> Ambito Actual: "+top);
    }
    

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
    
    public static void Validar(String Lvalue, String Valor)
    {
        if(top.table.containsKey(Lvalue))
        {
            Valor = Valor.replaceAll("&int", "");
            Valor = Valor.replaceAll("&dob", "");
            Valor = Valor.replaceAll("&str", "");
            Info aux = (Info) (top.table.get(Lvalue));
            if(Valor.contains("+") || Valor.contains("-") || Valor.contains("*") || Valor.contains("/"))
            {
                if(Valor.contains("\""))
                {
                  System.out.println("  Error: No se puede realizar la operacion de asignacion para el valor de  " + Lvalue + "-> No se puede operar numeros con cadenas" +" -> Ambito Actual: "+top);   
                }
                else
                {
                    String Resultado = Resolve(Valor);

                    if(Resultado.equals("-1"))
                    {
                        System.out.println("  Error: No se puede realizar la operacion de asignacion para el valor de  " + Lvalue + " -> Ambito Actual: "+top);
                    }
                    else
                    {               
                        if(aux.type == "string" )
                        {                    
                           System.out.println("  Error: No se puede asignar un valor numerico a un string " + Lvalue + " -> Ambito Actual: "+top);
                         }
                        else
                        {                     
                             String tipo;
                            if(RevFloat(Resultado))
                            {                        
                                tipo = "integer";
                            }
                            else
                            {
                                 tipo = "double";
                             }
                            if(aux.type == tipo)
                            {
                                aux.value = Resultado;
                                top.table.replace(Lvalue, aux);
                                System.out.println("  Nuevo valor para el identificador "+Lvalue+ " -> Valor: "+Resultado + " -> Ambito Actual: "+top);
                                        for (int i = 0; i < output.size(); i++) 
                                        {
                                                if (output.get(i).nombre.compareTo(Lvalue)==0)
                                                {
                                                    Salida element = new Salida(Lvalue, aux);
                                                    output.set(i, element);
                                                    break;
                                                }
                                          }                            
                            }
                            else
                            {
                               System.out.println("  Error: No se puede asignar un valor a la variable " +Lvalue + " -> " + aux.type + " & " + tipo + " no son compatibles" + " -> Ambito Actual: "+top);   
                            }
                        }
                    }
                }
            }
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
                            if(RevFloat(Valor))
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
                                    for (int i = 0; i < output.size(); i++) 
                                    {
                                            if (output.get(i).nombre.compareTo(Lvalue)==0)
                                            {
                                                Salida element = new Salida(Lvalue, aux);
                                                output.set(i, element);
                                                break;
                                            }
                                      }                    
                            }
                            else
                            {
                               System.out.println("  Error: No se puede asignar un valor a la variable " +Lvalue + " -> " + aux.type + " & " + tipo + " no son compatibles" + " -> Ambito Actual: "+top);  
                            }
                        }
                    }
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
                                    for (int i = 0; i < output.size(); i++) 
                                    {
                                            if (output.get(i).nombre.compareTo(Lvalue)==0)
                                            {
                                                Salida element = new Salida(Lvalue, aux);
                                                output.set(i, element);
                                                break;
                                            }
                                      }                      
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
                                    for (int i = 0; i < output.size(); i++) 
                                    {
                                            if (output.get(i).nombre.compareTo(Lvalue)==0)
                                            {
                                                Salida element = new Salida(Lvalue, aux);
                                                output.set(i, element);
                                                break;
                                            }
                                      }   

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
        else
        {
            System.out.println("  Error: No se puede asignar realizar la asignacion de valor a " + Lvalue + " ya que no esta declarada -> Ambito Actual: "+top);
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
    

    public static String Resolve(String S)
    {
        Posfijo Stage1 = new Posfijo(S);
        String Posfijo = Stage1.getPostFix();    
        Resolve Stage2 = new Resolve(Posfijo);
        return Stage2.getResult();
    }
    
   private static boolean RevFloat(String Result)
  {
      float N = Float.parseFloat(Result);
      
      if(N % 1 == 0)
      {
         return true;
      }
      else
      {
          return false;
      }
  }

  
  public static void parametros(String id, Object formals){//método que inserta los parámetros de su firma y los guarda en el hashmap m_table.
  
      if (m_table.containsKey(id)) {
          Info aux = (Info)m_table.get(id);
          aux.vars = formals.toString();
          m_table.replace(id, aux);
          for (int i = 0; i < output.size(); i++) {
              if (output.get(i).nombre.compareTo(id)==0) {
                  Salida element = new Salida(id, aux);
                  output.set(i, element);
                  break;
              }
          }
      }
      
      
}
   
  public static boolean tipos(String metodo, String var){
        if (m_table.containsKey(metodo)) {
            Info aux = (Info)m_table.get(metodo);
          ArrayList<String> firma = new ArrayList<>();//Arreglo que guarda la firma de métodos del metodo -> [var1, int], [var2, int]
          ArrayList<String> t_var = new ArrayList<>();//arreglo que guarda las variables que están siendo enviadas al metodo -> metodo(var1, var2)
          firma.addAll(Arrays.asList(aux.vars.split("-")));
          t_var.addAll(Arrays.asList(var.split(",")));
            if (t_var.size()==firma.size()) {//verifico si la firma del método y los parametros de la llamada al método tengan la misma longitud, sino, error.
                            for (int i = 0; i < firma.size(); i++) { //ciclo para recorrer la firma y los parametros enviados y comparar tipos.
                String variable_analizada = "";
                variable_analizada = firma.get(i);
                variable_analizada= variable_analizada.replace("[", "");//-> quito el [, enonces quedaría -> var1, int]
                variable_analizada= variable_analizada.replace("]", "");//-> quito el ], entonces quedaría -> var1, int
                ArrayList<String> tipo = new ArrayList<>();//arrgelo que guarda la firma de nuevo, pero limpia, sin caracteres basura
                tipo.addAll(Arrays.asList(variable_analizada.split(",")));//hago split por coma al array tipo.
                String tipo_aux = tipo.get(1);
                                if (top.table.containsKey(t_var.get(i))) {
                Info tipo_variable = (Info)top.table.get(t_var.get(i));//aquí obtengo la variable que se envía como parametro en la invocación y valido el tipo en las siguientes linea
                
                                if (tipo_aux.compareTo(tipo_variable.type)!=0) {
                                    String out = t_var.get(i);
                                    String out2 = tipo.get(1);
                                    System.out.println("El parámetro: "+ out + " no tiene al tipo de método necesario, se esperaba: "+out2+ "para: "+aux.vars);
                                }                                    
                                }
                                else{
                                    ArrayList<String> v_tipos = new ArrayList<>();
                                    v_tipos.addAll(Arrays.asList(t_var.get(i).split("&")));
                                    int f = i+1;
                                    switch(v_tipos.get(1)){
                                        case "integer":
                                            if (tipo_aux.compareTo("integer")!=0) {
                                    String out2 = tipo.get(1);
                                    System.out.println("El parámetro: "+ f + " no cumple con la firma, se esperaba: "+out2+" para: "+aux.vars);                                                
                                            }
                                            break;
                                        case "str":
                                            if (tipo_aux.compareTo("string")!=0) {
                                    String out2 = tipo.get(1);
                                    System.out.println("El parámetro: "+ f + " no cumple con la firma, se esperaba: "+out2+" para: "+aux.vars);                                                    
                                                
                                            }                                            
                                            break;
                                        case "dob":
                                            if (tipo_aux.compareTo("double")!=0) {
                                    String out2 = tipo.get(1);
                                    System.out.println("El parámetro: "+ f + " no cumple con la firma, se esperaba: "+out2+" para: "+aux.vars);                                                   
                                            }                                            
                                            break;
                                        case "null":
                                            if (tipo_aux.compareTo("null")!=0) {
                                    String out2 = tipo.get(1);
                                    System.out.println("El parámetro: "+ f + " no cumple con la firma, se esperaba: "+out2+" para: "+aux.vars);                                                    
                                            }                                            
                                            break;
                                        case "bool":
                                            if (tipo_aux.compareTo("boolean")!=0) {
                                    String out2 = tipo.get(1);
                                    System.out.println("El parámetro: "+ f + " no cumple con la firma, se esperaba: "+out2+" para: "+aux.vars);                                                  
                                            }                                            
                                            break;
                                        default:
                                            String out2 = tipo.get(i);
                                            System.out.println("El termino: "+f+" no cumple con lo que esperaba el parametro del método, se esperaba: "+out2);
                                            break;
                                    }
                                }

                
            }
            }
            else{
                System.out.println("El método: "+metodo+" ha sido invocado sin la cantidad de parametros necesitados");
            }
            

      }
      return false;
}   
  
  public static void mreturn(String tipo, Object regreso){
      
      if (regreso!=null) {
                if (top.table.containsKey(regreso)) {
          Info aux = (Info)top.table.get(regreso);
          if (tipo.toString().compareTo(aux.type)!=0) {
              System.out.println("La instrucción return está tratando regresando: +"+aux.type+", se esperaba: "+tipo);
          }
      }
      else{
           ArrayList<String> v_tipos = new ArrayList<>();
           v_tipos.addAll(Arrays.asList(regreso.toString().split("&"))); 
          if (v_tipos.size()==1)
          {
                   System.out.println("La variable:"+v_tipos.get(0)+" no ha sido declarada en el ámbito actual");
           }
          else
          { 
          switch(v_tipos.get(1)){
              case "int":
                  if (tipo.toString().compareTo("integer")!=0) {
                      System.out.println("La instrucción return está tratando regresando: Int, se esperaba: "+tipo);
                  }
                  break;
              case "str":
                  if (tipo.toString().compareTo("string")!=0) {
                      System.out.println("La instrucción return está tratando regresando: String, se esperaba: "+tipo);
                  }                  
                  break;
              case "dob":
                  if (tipo.toString().compareTo("double")!=0) {
                      System.out.println("La instrucción return está tratando regresando: Double, se esperaba: "+tipo);
                  }                  
                  break;
              case "null":
                  if (tipo.toString().compareTo("null")!=0) {
                      System.out.println("La instrucción return está tratando regresando: Null, se esperaba: "+tipo);
                  }                  
                  break;
              case "bool":
                  if (tipo.toString().compareTo("boolean")!=0) {
                      System.out.println("La instrucción return está tratando regresando: Boolean, se esperaba: "+tipo);
                  }                  
                  break;
                  default:
                      break;
          }
          }
      }
      }
      else{}
}
  
  public static boolean get_name(String n, String m)
  {
      if (m.compareTo("m")==0) {
          if (!m_table.containsKey(n)) {
              return false;
          }
          else{
          return true;
          }
      }
      else{
          if (!top.table.containsKey(n)) {
          return false;
      }
          else{
              return true;
          }
      }
    
}
  


}