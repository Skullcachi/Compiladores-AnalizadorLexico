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

%%
// OPERATOR_OR_PUNTUATION_MARKs
{OPERATORS_OR_PUNTUATION_MARKS}                                  { return "OPERATOR OR PUNTATION MARK: " + yytext() + " in line: " + yyline + " columns: " + yycolumn + " - " + (yycolumn + yylength()); }

// STRINGs
\"[^\"](\w|\t|\ )*\"                                             { return "STRING: " + yytext() + " in line: " + yyline + " columns: " + yycolumn + " - " + (yycolumn + yylength()); }

// BOOLEANs
{BOOLEAN}                                                        { return "BOOLEAN: " + yytext() + " in line: " + yyline + " columns: " + yycolumn + " - " + (yycolumn + yylength()); }

// RESERVED_WORDs
{RESERVED_WORDS}                                                 { return "RESERVED WORD: " + yytext() + " in line: " + yyline + " columns: " + yycolumn + " - " + (yycolumn + yylength()); }

// IDENTIFIERs
{L}({L}|{D})*                                                    { return "IDENTIFIER: " + yytext() + " in line: " + yyline + " columns: " + yycolumn + " - " + (yycolumn + yylength()); }

// INTs
{D}+	                                                         { return "INTEGER: " + yytext() + " in line: " + yyline + " columns: " + yycolumn + " - " + (yycolumn + yylength()); }

// HEXADECIMALs
"0x"[0-9A-F]+ | "0x"[0-9a-f]+ | "0X"[0-9A-F]+ | "0X"[0-9A-F]+    { return "INTEGER HEXADECIMAL: " + yytext() + " in line: " + yyline + " columns: " + yycolumn + " - " + (yycolumn + yylength()); }

// DOUBLEs
[-+]?{D}+"."({D}+ | ("E" | "e")"+"{D}+ | {D}+("E" | "e")"+"{D}+) { return "DOUBLE: " + yytext() + " in line: " + yyline + " columns: " + yycolumn + " - " + (yycolumn + yylength());}

// LINE COUNTER
[ \n]                                                            { /*lleva la cuenta de lineas*/ }

// WHITESPACEs TABs BRAKELINEs NEWLINEs
[\s]+                                                            { /*se ignoran los espacios y tabuladores*/ }

// ERRORs
.	                                                         { return "Lexical error: lexema " + yytext() + " is INVALID, found in line: : " + yyline + " columns: " + yycolumn + " - " + (yycolumn + yylength()); }