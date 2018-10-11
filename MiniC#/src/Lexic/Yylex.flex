package Lexic;
import java.io.*;
import java_cup.runtime.*;
%%

%public

%class Yylex

%unicode

%line

%column

%cup

%type Symbol

MULTILINE_COMMENT = "/*" [^*] ~"*/" | "/*" "*"+ "/"

UNFINISHED_COMMENT = "/*" [^*]+

SINGLELINE_COMMENT = "//" [^\r\n]* [\r|\n|\r\n]?

/*RESERVED_WORDS = "void" | "int" | "double" | "bool" | "string" | "class" | "interface" | "null" | "this" | "extends" | "implements" | "for" | "while" | "if" | "else" | "return" | "break" | "New" | "NewArray" | "Print" | "ReadInteger" | "ReadLine" | "Malloc"*/

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
"+"                                                                  { return symbol(sym.SUMA, new String(yytext()));}
"-"                                                                  { return symbol(sym.RESTA, new String(yytext()));}
"*"                                                                  { return symbol(sym.MULT, new String(yytext()));}
"/"                                                                  { return symbol(sym.DIV, new String(yytext()));}
"%"                                                                  { return symbol(sym.MOD, new String(yytext()));}
"<"                                                                  { return symbol(sym.MENOR, new String(yytext()));}
"<="                                                                 { return symbol(sym.MENOR_IGUAL, new String(yytext()));}
">"                                                                  { return symbol(sym.MAYOR, new String(yytext()));}
">="                                                                 { return symbol(sym.MAYOR_IGUAL, new String(yytext()));}
"="                                                                  { return symbol(sym.ASIGNAR, new String(yytext()));}
"=="                                                                 { return symbol(sym.IGUAL, new String(yytext()));}
"!="                                                                 { return symbol(sym.DIFERENTE, new String(yytext()));}
"&&"                                                                 { return symbol(sym.AND, new String(yytext()));}
"||"                                                                 { return symbol(sym.OR, new String(yytext()));}
"!"                                                                  { return symbol(sym.NOT, new String(yytext()));}
";"                                                                  { return symbol(sym.SEMI_COLON, new String(yytext()));}
","                                                                  { return symbol(sym.COMA, new String(yytext()));}
"."                                                                  { return symbol(sym.PUNTO, new String(yytext()));}
"["                                                                  { return symbol(sym.COR_IZQ, new String(yytext()));}
"]"                                                                  { return symbol(sym.COR_DER, new String(yytext()));}
"("                                                                  { return symbol(sym.PAR_IZQ, new String(yytext()));}
")"                                                                  { return symbol(sym.PAR_DER, new String(yytext()));}
"{"                                                                  { return symbol(sym.LLAVE_IZQ, new String(yytext()));}
"}"                                                                  { return symbol(sym.LLAVE_DER, new String(yytext()));}
"[]"                                                                 { return symbol(sym.CORCHETES, new String(yytext()));}
/*"()"                                                                 { return symbol(sym.PARENTESIS, new String(yytext()));}*/
"{}"                                                                 { return symbol(sym.LLAVES, new String(yytext()));}

// STRINGs
\"[^\r\n]+\"                                                         { 
                                                                        /*return "STRING: " + yytext() + " in line: " + (yyline + 1) + " columns: " + (yycolumn + 1) + " - " + ((yycolumn + 1) + yylength() - 1);*/
                                                                        return symbol(sym.STRINGCONSTANT, new String(yytext()));
                                                                     }

// BOOLEANs
"true" | "TRUE"                                                      { 
                                                                        /*return "BOOLEAN: " + yytext() + " in line: " + (yyline + 1) + " columns: " + (yycolumn + 1) + " - " + ((yycolumn + 1) + yylength() - 1);*/ 
                                                                        return symbol(sym.BOOLCONSTANT, new String(yytext()));
                                                                    }

"false" | "FALSE"                                                      { 
                                                                        /*return "BOOLEAN: " + yytext() + " in line: " + (yyline + 1) + " columns: " + (yycolumn + 1) + " - " + ((yycolumn + 1) + yylength() - 1);*/ 
                                                                        return symbol(sym.BOOLCONSTANT, new String(yytext()));
                                                                    }

// RESERVED_WORDs
/*{RESERVED_WORDS}                                                   { return "RESERVED WORD: " + yytext() + " in line: " + (yyline + 1) + " columns: " + (yycolumn + 1) + " - " + ((yycolumn + 1) + yylength() - 1); }*/

"void"                                                               { return symbol(sym.VOID, new String("RESERVED WORD: " + yytext())); }

"int"                                                               { return symbol(sym.INT, new String("RESERVED WORD: " + yytext())); } 

"double"                                                             { return symbol(sym.DOUBLE, new String("RESERVED WORD: " + yytext())); }

"bool"                                                               { return symbol(sym.BOOL, new String("RESERVED WORD: " + yytext())); }

"string"                                                             { return symbol(sym.STRING, new String("RESERVED WORD: " + yytext())); }

"class"                                                              { return symbol(sym.CLASS, new String("RESERVED WORD: " + yytext())); }

"interface"                                                          { return symbol(sym.INTERFACE, new String("RESERVED WORD: " + yytext())); }

"null"                                                               { return symbol(sym.NULL, new String("RESERVED WORD: " + yytext())); }

"this"                                                               { return symbol(sym.THIS, new String("RESERVED WORD: " + yytext())); }

"extends"                                                            { return symbol(sym.EXTENDS, new String("RESERVED WORD: " + yytext())); }

"implements"                                                         { return symbol(sym.IMPLEMENTS, new String("RESERVED WORD: " + yytext())); }

"for"                                                                { return symbol(sym.FOR, new String("RESERVED WORD: " + yytext())); }

"while"                                                              { return symbol(sym.WHILE, new String("RESERVED WORD: " + yytext())); }

"if"                                                                 { return symbol(sym.IF, new String("RESERVED WORD: " + yytext())); }

"else"                                                               { return symbol(sym.ELSE, new String("RESERVED WORD: " + yytext())); }

"return"                                                             { return symbol(sym.RETURN, new String("RESERVED WORD: " + yytext())); }

"break"                                                              { return symbol(sym.BREAK, new String("RESERVED WORD: " + yytext())); }

"New"                                                                { return symbol(sym.NEW, new String("RESERVED WORD: " + yytext())); }

"NewArray"                                                           { return symbol(sym.NEWARRAY, new String("RESERVED WORD: " + yytext())); }

"Print"                                                              { return symbol(sym.PRINT, new String("RESERVED WORD: " + yytext())); }

"ReadInteger"                                                        { return symbol(sym.READINTEGER, new String("RESERVED WORD: " + yytext())); }

"ReadLine"                                                           { return symbol(sym.READLINE, new String("RESERVED WORD: " + yytext())); }

"Malloc"                                                             { return symbol(sym.MALLOC, new String("RESERVED WORD: " + yytext())); }

"GetByte"                                                            { return symbol(sym.GETBYTE, new String("RESERVED WORD: " + yytext())); }

"SetByte"                                                            { return symbol(sym.SETBYTE, new String("RESERVED WORD: " + yytext())); }

// IDENTIFIERs
{L}({L}|{D})*                                                    { 
                                                                    if(yylength() > 31)
                                                                    {
                                                                        String aux = yytext().substring(0,31);
                                                                        
                                                                        //return "IDENTIFIER: " + aux + " in line: " + (yyline + 1) + " columns: " + (yycolumn + 1) + " - " + ((yycolumn + 1) + yylength() - 1) + " exceeded the max limit length. Identifier truncated.";                                                                       
                                                                        return symbol(sym.IDENT, new String(aux));
                                                                    }
                                                                    else 
                                                                    {
                                                                        //return "IDENTIFIER: " + yytext() + " in line: " + (yyline + 1) + " columns: " + (yycolumn + 1) + " - " + ((yycolumn + 1) + yylength() - 1); 
                                                                        return symbol(sym.IDENT, new String("IDENTIFIER: " + yytext()));
                                                                    }
                                                                 }
// INTs
{D}+	                                                        { 
                                                                    //return "INTEGER: " + yytext() + " in line: " + (yyline + 1) + " columns: " + (yycolumn + 1) + " - " + ((yycolumn + 1) + yylength() - 1); 
                                                                    return symbol(sym.INTCONSTANT, new Integer(yytext()));
                                                                }

// HEXADECIMALs
0[xX][0-9a-fA-F]+                                           { 
                                                                //return "INTEGER HEXADECIMAL: " + yytext() + " in line: " + yyline + " columns: " + (yycolumn + 1) + " - " + ((yycolumn + 1) + yylength() - 1); 
                                                                return symbol(sym.HEXADECIMAL, new String(yytext()));
                                                            }

// DOUBLEs
[-+]?[0-9]+"."|[-+]?[0-9]+"."([0-9]+|("E"|"e")[-+]?[0-9]+|[0-9]+("E"|"e")[-+]?[0-9]+)   {
                                                                                            //return "DOUBLE: " + yytext() + " in line: " + (yyline + 1) + " columns: " + (yycolumn + 1) + " - " + ((yycolumn + 1) + yylength() - 1); 
                                                                                            return symbol(sym.DOUBLECONSTANT, new String("DOUBLE: " + yytext()));
                                                                                        }  

// LINE COUNTER
[ \n]                                                            { /*lleva la cuenta de lineas*/ }

// WHITESPACEs TABs BRAKELINEs NEWLINEs
[\s]+                                                            { /*se ignoran los espacios y tabuladores*/ }

//UNFINISHED COMMENTs
{UNFINISHED_COMMENT}                                            { 
                                                                    //return "Unfinished comment " + yytext() + " found in line: " + (yyline + 1) + " columns: " + (yycolumn + 1) + " - " + ((yycolumn + 1) + yylength() - 1); 
                                                                    System.out.print("Unfinished comment " + yytext() + " found in line: " + (yyline + 1) + " columns: " + (yycolumn + 1) + " - " + ((yycolumn + 1) + yylength() - 1));
}

//MULTILINE COMMENTs
{MULTILINE_COMMENT}                                              { /*se ignoran los comentarios de bloque*/ }

//SINGLELINE COMMENTs
{SINGLELINE_COMMENT}                                             { /*se ignoran los comentarios de linea*/ }

// ERRORs
.	                                                         { 
                                                                    lexeme = yytext(); line = (yyline + 1); column = (yycolumn + 1); length = yylength(); 
                                                                    System.out.print("Lexical error: invalid token "); 
                                                                }
