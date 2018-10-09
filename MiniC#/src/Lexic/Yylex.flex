package Lexic;
import java.io.*;

%%

%public

%class Yylex

%unicode

%line

%column

%cup

%type String

MULTILINE_COMMENT = "/*" [^*] ~"*/" | "/*" "*"+ "/"

UNFINISHED_COMMENT = "/*" [^*]+

SINGLELINE_COMMENT = "//" [^\r\n]* [\r|\n|\r\n]?

/*RESERVED_WORDS = "void" | "int" | "double" | "bool" | "string" | "class" | "interface" | "null" | "this" | "extends" | "implements" | "for" | "while" | "if" | "else" | "return" | "break" | "New" | "NewArray" | "Print" | "ReadInteger" | "ReadLine" | "Malloc"*/
VOID = "void"
INT = "int"
DOUBLE = "double"
BOOL = "bool"
STRING = "string"
CLASS = "class"
INTERFACE = "interface"
NULL = "null"
THIS = "this"
EXTENDS = "extends"
IMPLEMENTS = "implements"
FOR = "for"
WHILE = "while"
IF = "if"
ELSE = "else"
RETURN = "return"
BREAK = "break"
NEW = "new"
NEWARRAY = "newarray"
PRINT = "print"
READINTEGER = "readinteger"
READLINE = "readline"
MALLOC = "malloc"

/*BOOLEAN = "true" | "false" | "TRUE" | "FALSE"*/

/*OPERATORS_OR_PUNTUATION_MARKS = "+" | "-" | "*" | "/" | "%" | "<" | "<=" | ">" | ">=" | "=" | "==" | "!=" | "&&" | "||" | "!" | ";" | "," | "." | "[" | "]" | "(" | ")" | "{" | "}" | "[]" | "()" | "{}"*/

D = [0-9]

L = [a-zA-Z_]

%{
    public String lexeme;
    public int line;
    public int column;
    public int length;

    private Symbol symbol(int type){
        return new Symbol(type, yyline, yycolumn);
    }

    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline, yycolumn, value);
    }
%}
%%
// OPERATOR_OR_PUNTUATION_MARKs
/*{OPERATORS_OR_PUNTUATION_MARKS}                                      { return "OPERATOR OR PUNTATION MARK: " + yytext() + " in line: " + (yyline + 1) + " columns: " + (yycolumn + 1) + " - " + ((yycolumn + 1) + yylength() - 1); }*/
"+"                                                                  { return symbol(sys.SUMA);}
"-"                                                                  { return symbol(sys.RESTA);}
"*"                                                                  { return symbol(sys.MULT);}
"/"                                                                  { return symbol(sys.DIV);}
"%"                                                                  { return symbol(sys.MOD);}
"<"                                                                  { return symbol(sys.MENOR);}
"<="                                                                 { return symbol(sys.MENOR_IGUAL);}
">"                                                                  { return symbol(sys.MAYOR);}
">="                                                                 { return symbol(sys.MAYOR_IGUAL);}
"="                                                                  { return symbol(sys.ASIGNAR);}
"=="                                                                 { return symbol(sys.IGUAL);}
"!="                                                                 { return symbol(sys.DIFERENTE);}
"&&"                                                                 { return symbol(sys.AND);}
"||"                                                                 { return symbol(sys.OR);}
"!"                                                                  { return symbol(sys.NOT);}
";"                                                                  { return symbol(sys.SEMI_COLON);}
","                                                                  { return symbol(sys.COMA);}
"."                                                                  { return symbol(sys.PUNTO);}
"["                                                                  { return symbol(sys.COR_IZQ);}
"]"                                                                  { return symbol(sys.COR_DER);}
"("                                                                  { return symbol(sys.PAR_IZQ);}
")"                                                                  { return symbol(sys.PAR_DER);}
"{"                                                                  { return symbol(sys.LLAVE_IZQ);}
"}"                                                                  { return symbol(sys.LLAVE_DER);}
"[]"                                                                 { return symbol(sys.CORCHETES);}
"()"                                                                 { return symbol(sys.PARENTESIS);}
"{}"                                                                 { return symbol(sys.LLAVES);}

// STRINGs
\"[^\r\n]+\"                                                         { 
                                                                        /*return "STRING: " + yytext() + " in line: " + (yyline + 1) + " columns: " + (yycolumn + 1) + " - " + ((yycolumn + 1) + yylength() - 1);*/
                                                                        return symbol(sys.STRING, new String(yytext()));
                                                                     }

// BOOLEANs
"true" | "TRUE"                                                      { 
                                                                        /*return "BOOLEAN: " + yytext() + " in line: " + (yyline + 1) + " columns: " + (yycolumn + 1) + " - " + ((yycolumn + 1) + yylength() - 1);*/ 
                                                                        return symbol(sys.TRUE);
                                                                    }

"false" | "FALSE"                                                      { 
                                                                        /*return "BOOLEAN: " + yytext() + " in line: " + (yyline + 1) + " columns: " + (yycolumn + 1) + " - " + ((yycolumn + 1) + yylength() - 1);*/ 
                                                                        return symbol(sys.FALSE);
                                                                    }

// RESERVED_WORDs
/*{RESERVED_WORDS}                                                   { return "RESERVED WORD: " + yytext() + " in line: " + (yyline + 1) + " columns: " + (yycolumn + 1) + " - " + ((yycolumn + 1) + yylength() - 1); }*/

{VOID}                                                               { return symbol(sys.VOID); }

{INT}                                                                { return symbol(sys.INT); } 

{DOUBLE}                                                             { return symbol(sys.DOUBLE); }

{BOOL}                                                               { return symbol(sys.BOOL); }

{STRING}                                                             { return symbol(sys.STRING); }

{CLASS}                                                              { return symbol(sys.CLASS); }

{INTERFACE}                                                          { return symbol(sys.INTERFACE); }

{NULL}                                                               { return symbol(sys.NULL); }

{THIS}                                                               { return symbol(sys.THIS); }

{EXTENDS}                                                            { return symbol(sys.EXTENDS); }

{IMPLEMENTS}                                                         { return symbol(sys.IMPLEMENTS); }

{FOR}                                                                { return symbol(sys.FOR); }

{WHILE}                                                              { return symbol(sys.WHILE); }

{IF}                                                                 { return symbol(sys.IF); }

{ELSE}                                                               { return symbol(sys.ELSE); }

{RETURN}                                                             { return symbol(sys.RETURN); }

{BREAK}                                                              { return symbol(sys.BREAK); }

{NEW}                                                                { return symbol(sys.NEW); }

{NEWARRAY}                                                           { return symbol(sys.NEWARRAY); }

{PRINT}                                                              { return symbol(sys.PRINT); }

{READINTEGER}                                                        { return symbol(sys.READINTEGER); }

{READLINE}                                                           { return symbol(sys.READLINE); }

{MALLOC}                                                             { return symbol(sys.MALLOC); }

// IDENTIFIERs
{L}({L}|{D})*                                                    { 
                                                                    if(yylength() > 31)
                                                                    {
                                                                        String aux = yytext().substring(0,31);
                                                                        
                                                                        //return "IDENTIFIER: " + aux + " in line: " + (yyline + 1) + " columns: " + (yycolumn + 1) + " - " + ((yycolumn + 1) + yylength() - 1) + " exceeded the max limit length. Identifier truncated.";                                                                       
                                                                        return symbol(sym.ID, new String(aux));
                                                                    }
                                                                    else 
                                                                    {
                                                                        //return "IDENTIFIER: " + yytext() + " in line: " + (yyline + 1) + " columns: " + (yycolumn + 1) + " - " + ((yycolumn + 1) + yylength() - 1); 
                                                                        return symbol(sym.ID, new String(yytext()));
                                                                    }
                                                                 }
// INTs
{D}+	                                                        { 
                                                                    //return "INTEGER: " + yytext() + " in line: " + (yyline + 1) + " columns: " + (yycolumn + 1) + " - " + ((yycolumn + 1) + yylength() - 1); 
                                                                    return symbol(sym.INTEGER, new Integer(yytext()));
                                                                }

// HEXADECIMALs
0[xX][0-9a-fA-F]+                                           { 
                                                                //return "INTEGER HEXADECIMAL: " + yytext() + " in line: " + yyline + " columns: " + (yycolumn + 1) + " - " + ((yycolumn + 1) + yylength() - 1); 
                                                                return symbol(sym.HEXADECIMAL, new String(yytext()));
                                                            }

// DOUBLEs
[-+]?[0-9]+"."|[-+]?[0-9]+"."([0-9]+|("E"|"e")[-+]?[0-9]+|[0-9]+("E"|"e")[-+]?[0-9]+)   {
                                                                                            //return "DOUBLE: " + yytext() + " in line: " + (yyline + 1) + " columns: " + (yycolumn + 1) + " - " + ((yycolumn + 1) + yylength() - 1); 
                                                                                            return symbol(sym.DOUBLE, new String(yytext()));
                                                                                        }  

// LINE COUNTER
[ \n]                                                            { /*lleva la cuenta de lineas*/ }

// WHITESPACEs TABs BRAKELINEs NEWLINEs
[\s]+                                                            { /*se ignoran los espacios y tabuladores*/ }

//UNFINISHED COMMENTs
{UNFINISHED_COMMENT}                                            { 
                                                                    //return "Unfinished comment " + yytext() + " found in line: " + (yyline + 1) + " columns: " + (yycolumn + 1) + " - " + ((yycolumn + 1) + yylength() - 1); 
                                                                    
}

//MULTILINE COMMENTs
{MULTILINE_COMMENT}                                              { /*se ignoran los comentarios de bloque*/ }

//SINGLELINE COMMENTs
{SINGLELINE_COMMENT}                                             { /*se ignoran los comentarios de linea*/ }

// ERRORs
.	                                                         { lexeme = yytext(); line = (yyline + 1); column = (yycolumn + 1); length = yylength(); return "Lexical error:"; }
