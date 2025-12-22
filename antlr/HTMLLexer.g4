lexer grammar HTMLLexer;


OPEN : '<' -> pushMode(HTML_TAG_MODE);

JINJA_EXPR_OPEN : '{{' -> pushMode(JINJA_EXPR_MODE);
JINJA_STMT_OPEN : '{%' -> pushMode(JINJA_STMT_MODE);
JINJA_COMMENT   : '{#' .*? '#}' -> skip;
WS_CONTENT : [ \t\r\n]+ -> skip;
TEXT : ~[<{&]+ ;
ENTITY : '&' [a-zA-Z#]+ ';';
JINJA_ID : [a-zA-Z_][a-zA-Z0-9_]* ;
DOT      : '.';
NUMBER   : [0-9]+;
STRING_J : '"' (~["\\] | '\\' .)* '"';

mode HTML_TAG_MODE;

CLOSE : '>' -> popMode;
SLASH : '/';
EQ    : '=';
HTML  : 'html';
HEAD  : 'head';
BODY  : 'body';
TITLE : 'title';
DIV   : 'div';
P     : 'p';
H1    : 'h1'; H2 : 'h2'; H3 : 'h3'; H4 : 'h4'; H5 : 'h5'; H6 : 'h6';
SPAN  : 'span';
IMG   : 'img';
UL    : 'ul';
FORM:'form';
INPUT:'input';
TYPE_KW:'type';
TYPE:'"text"'|'"number"'|'"submit"'|'"password"';
NAME:'name';
LI    : 'li';
SRC   : 'src';
BR:'br';
HREF_KW:'href';
A:'a';
DOCTYPE:'DOCTYPE';
DOC:'!';
HTML_KW:'HTML';
REL_KW:'rel';
REL:'"stylesheet"';
LINK:'link';
BUTTON_KW:'button';
BUTTON:'"submeit"'|'"button"';
METHOD_KW:'method';
VAL:'$'?'{{'TEXT* '.' TEXT'}}';
METHOD:'"post"' | '"PUSH"' | '"DELET"';
STYLE:'style';


ATR   : [a-zA-Z_] [a-zA-Z0-9_\-]* ;

STRING
    : '"' (~["\\\r\n] | '\\' .)* '"'
    | '\'' (~['\\\r\n] | '\\' .)* '\''
    ;

WS_TAG : [ \t\r\n]+ -> skip;

mode JINJA_EXPR_MODE;

JINJA_EXPR_CLOSE : '}}' -> popMode;
PIPE     : '|';

EXPR_ID     : [a-zA-Z_][a-zA-Z0-9_]* -> type(JINJA_ID);
EXPR_DOT    : '.' -> type(DOT);
EXPR_NUM    : [0-9]+ -> type(NUMBER);
EXPR_STR    : '"' (~["\\] | '\\' .)* '"' -> type(STRING_J);

WS_JINJA : [ \t\r\n]+ -> skip;

mode JINJA_STMT_MODE;

JINJA_STMT_CLOSE : '%}' -> popMode;

FOR    : 'for';
ENDFOR : 'endfor';
IN     : 'in';
IF     : 'if';
ELSE   : 'else';
ENDIF  : 'endif';

OP : '==' | '!=' | '>=' | '<=' | '>' | '<';


STMT_ID     : [a-zA-Z_][a-zA-Z0-9_]* -> type(JINJA_ID);
STMT_DOT    : '.' -> type(DOT);
STMT_NUM    : [0-9]+ -> type(NUMBER);
STMT_STR    : '"' (~["\\] | '\\' .)* '"' -> type(STRING_J);

WS_JINJA_STMT : [ \t\r\n]+ -> skip;