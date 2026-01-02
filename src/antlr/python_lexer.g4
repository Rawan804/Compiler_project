lexer grammar python_lexer;
TRUE : 'True';
FALSE : 'False';
NULL : 'None';
LKB : '{';
RKB : '}';
LSB : '[';
RSB : ']';
OPEN_B : '(';
CLOSE_B : ')';
COMMA : ',';
COL : ':';
DOT :'.';
AT:'@';
STRING
    : '"' (~["\\] | ESC)* '"'
    | '\'' (~['\\] | ESC)* '\''
    ;
WITH:'with';
IF : 'if';
ELIF : 'elif';
ELSE : 'else';
TRY:'try';
EXCEPT :'except';
FOR:'for';
IN:'in';
WHILE:'while';
FROM : 'from';
IMPORT : 'import';
AS : 'as';
DEF : 'def';
NOT : 'not';
AND : 'and';
OR : 'or';
IS:'is';
CLASS :'class';

NUMBER
    : '-'? [0-9]+ ('.' [0-9]+)? ([eE] [+\-]? [0-9]+)?
    ;
PLUS : '+';
MINUS : '-';
MUL : '*';
DIV : '/';
GREATERTHAN : '>';
GREATEROREQ:' >= ';
SMALLERTHAN : '<';
SMALLOREQ:' <= ';
ASSIGN : '=';
NOTEQ:'!=';
EQ : '==';
RETURN : 'return';
PRINT : 'print';
BREAK :'break';
CONTINUE : 'continue';
IDENTIFIER : [a-zA-Z_][a-zA-Z0-9_]*;


COMMENT : '#' ~[\r\n]* -> skip;

WS : [ \t\r\n]+ -> skip;

fragment ESC : '\\' [bfnrt"\\] | UNICODE;
fragment UNICODE : 'u' HEX HEX HEX HEX;
fragment HEX : [0-9a-fA-F];