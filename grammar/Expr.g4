grammar Expr;

//? significa que es opcional
//* Repeticion

/* Estructura general del programa */
prog    : 'program' ID ';'
          vars_declGlobal*?  funcs_decl*?
          mainBody
          'end' ;

mainBody: 'main' body;

vars_declGlobal : 'var' ID  ':' type ';' ;

vars_decl : 'var' ID  ':' type ';' ;

funcs_decl : 'void' ID '(' params? ')'
            '[' vars_decl*?
             body ']' ';';


//main_body : 'main' body ;

body     : '{' (vars_decl | statement)* '}' ;

statement: assign ';'          # assignStat
        | condition           # condStat
        | cycle               # cycleStat
        | f_call ';'          # funcCallStat
        | expression ';'          # expressiao
        | printExpr ';'           # printStat
        | ';'                 # emptyStat
        ;

assign   : ID '=' expression ;

condition: 'if' '(' expression ')' body ('else' body)? ;

cycle    : 'while' '(' expression ')' body ;

f_call   : ID '(' (expression (',' expression)*)? ')' ;

printExpr    : 'print' '(' expression ')' ;

expression    : exp (MENOR_QUE exp | MAYOR_QUE exp| IGUALDAD_ABS exp | DESIGUALDAD exp)?  ;


    exp : term  (SUMA term | RESTA term)? # aritExpr
                ;





term       : factor (MULT factor | DIVIDE factor)?    # mulExpr
           | factor                    # factorExpr
           ;

factor     : '(' expression ')'
           | (SUMA | RESTA)
           | ID
           | cte
           ;


cte     : INT_NUM      # intConst
        | FLOAT_NUM    # floatConst
        ;

params  : param (',' param)* ;
param   : ID ':' type ;

type    : 'int' | 'float' ;

/* Tokens */
INT_NUM : [0-9]+ ;
FLOAT_NUM : [0-9]+ '.' [0-9]+ ;
ID      : [a-zA-Z][a-zA-Z0-9]* ;
WS      : [ \t\r\n]+ -> skip ;
COMMENT : '//' .*? '\n' -> skip ;
// Delimitadores y puntuación
SUMA       : '+' ;
RESTA       : '-' ;
MULT       : '*' ;
DIVIDE       : '/' ;
MAYOR_QUE       : '>' ;
MENOR_QUE       : '<' ;
IGUALDAD_ABS       : '==' ;
DESIGUALDAD       : '!=' ;
COMMA      : ',' ;
LPAREN     : '(' ;
RPAREN     : ')' ;
LBRACE     : '{' ;
RBRACE     : '}' ;
SQUARE_BRACKET_LEFT     : '[' ;
SQUARE_BRACKET_RIGHT     : ']' ;

