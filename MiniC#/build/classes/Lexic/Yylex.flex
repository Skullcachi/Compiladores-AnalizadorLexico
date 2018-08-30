package Lexic;
import java.io.*;

%%

%public

%class Yylex

%unicode

%line

%column

%type String



RESERVED_WORDS = "void" | "int" | "double" | "bool" | "string" | "class" | "interface" | "null" | "this" | "extends" | "implements" | "for" | "while" | "if" | "else" | "return" | "break" | "New" | "NewArray"

BOOLEAN = "true" | "false" | "TRUE" | "FALSE"

OPERATORS_OR_PUNTUATION_MARKS = "+" | "-" | "*" | "/" | "%" | "<" | "<=" | ">" | ">=" | "=" | "==" | "!=" | "&&" | "||" | "!" | ";" | "," | "." | "[" | "]" | "(" | ")" | "{" | "}" | "[]" | "()" | "{}"

D = [0-9]

L = [a-zA-Z_]

%{
    public String lexeme;
    public int line;
    public int column;
    public int length;
%}
%%
// OPERATOR_OR_PUNTUATION_MARKs
{OPERATORS_OR_PUNTUATION_MARKS}                                  { return "OPERATOR OR PUNTATION MARK: " + yytext() + " in line: " + (yyline + 1) + " columns: " + yycolumn + " - " + (yycolumn + yylength() - 1); }

// STRINGs
\"[^\"](\w|\t|\ ){0,29}\"                                           { return "STRING: " + yytext() + " in line: " + (yyline + 1) + " columns: " + yycolumn + " - " + (yycolumn + yylength() - 1); }

// BOOLEANs
{BOOLEAN}                                                        { return "BOOLEAN: " + yytext() + " in line: " + (yyline + 1) + " columns: " + yycolumn + " - " + (yycolumn + yylength() - 1); }

// RESERVED_WORDs
{RESERVED_WORDS}                                                 { return "RESERVED WORD: " + yytext() + " in line: " + (yyline + 1) + " columns: " + yycolumn + " - " + (yycolumn + yylength() - 1); }

// IDENTIFIERs
{L}({L}|{D})*                                                    { return "IDENTIFIER: " + yytext() + " in line: " + (yyline + 1) + " columns: " + yycolumn + " - " + (yycolumn + yylength() - 1); }

// INTs
{D}+	                                                         { return "INTEGER: " + yytext() + " in line: " + (yyline + 1) + " columns: " + yycolumn + " - " + (yycolumn + yylength() - 1); }

// HEXADECIMALs
"0x"[0-9A-F]+ | "0x"[0-9a-f]+ | "0X"[0-9A-F]+ | "0X"[0-9a-f]+    { return "INTEGER HEXADECIMAL: " + yytext() + " in line: " + yyline + " columns: " + yycolumn + " - " + (yycolumn + yylength() - 1); }

// DOUBLEs
[-+]?{D}+"." | [-+]?{D}+"."({D}+ | ("E" | "e")"+"{D}+ | {D}+("E" | "e")"+"{D}+) { return "DOUBLE: " + yytext() + " in line: " + (yyline + 1) + " columns: " + yycolumn + " - " + (yycolumn + yylength() - 1);}

// LINE COUNTER
[ \n]                                                            { /*lleva la cuenta de lineas*/ }

// WHITESPACEs TABs BRAKELINEs NEWLINEs
[\s]+                                                            { /*se ignoran los espacios y tabuladores*/ }

// ERRORs
.	                                                         { lexeme = yytext(); line = (yyline + 1); column = yycolumn; length = yylength(); return "Lexical error:"; }
