/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexic;

public class Info {
    
    public String type;
    public String elementType;
    public String vars = "";
    public String ambito = "";
    public Object value;
    
    public  Info(String tipo,String tipoDato, Object val)
    {
        type = tipo;
        value = val;
        elementType = tipoDato;
    }
    
    public Info()
    {
        this.value = "??";
        this.type = "??";
        this.elementType = "??";        
    }
    
   public String getType()
   {
       return type;
   }
   
   public Object getValue()
   {
       return value;
   }
    
}
