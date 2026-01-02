lexer grammar CSSLexer;
LBRACE    : '{' ;
RBRACE    : '}' ;
COLON     : ':' ;
SEMICOLON : ';' ;
COMMA     : ',' ;
CSS_PROP_COLOR        : 'color' ;
CSS_PROP_BG_COLOR     : 'background-color' ;
CSS_PROP_WIDTH        : 'width' ;
CSS_PROP_HEIGHT       : 'height' ;
CSS_PROP_MARGIN       : 'margin' ;
CSS_PROP_PADDING      : 'padding' ;
CSS_PROP_BORDER       : 'border' ;
CSS_PROP_FONT_SIZE    : 'font-size' ;
CSS_PROP_FONT_WEIGHT  : 'font-weight' ;
CSS_PROP_TEXT_ALIGN   : 'text-align' ;
CSS_PROP_DISPLAY      : 'display' ;
CSS_PROP_POSITION     : 'position' ;
CSS_PROP_OPACITY      : 'opacity' ;
CSS_VALUE_BORDER_STYLE : 'solid' | 'dashed' | 'dotted' | 'double' | 'inset' | 'outset' ;
CSS_VALUE_DISPLAY      : 'block' | 'inline' | 'inline-block' | 'flex' | 'grid' | 'none' ;
CSS_VALUE_POSITION     : 'static' | 'relative' | 'absolute' | 'fixed' | 'sticky' ;
CSS_VALUE_TEXT_ALIGN   : 'left' | 'right' | 'center' | 'justify' ;
CSS_VALUE_FONT_WEIGHT  : 'normal' | 'bold' | 'bolder' | 'lighter' | [1-9] '00' ;
CSS_PROP_GRID_TEMPLATE_COLUMNS : 'grid-template-columns' ;
CSS_PROP_GRID_TEMPLATE_ROWS : 'grid-template-rows' ;
CSS_PROP_GRID_GAP : 'gap' ;
CSS_VALUE_GRID_FUNCTION : 'repeat' ;
CSS_VALUE_GRID_UNIT : 'fr' ;

CSS_COLOR
    : '#' [a-fA-F0-9]+
    | 'white' | 'black' | 'red' | 'blue' | 'green' | 'transparent' | 'gray'
    ;

CSS_VALUE_GENERAL
    : [0-9]+ ('.' [0-9]+)? (('px' | 'em' | 'rem' | '%' | 'vw' | 'vh')?)?
    ;

NONE_KW : 'none' ;
AUTO_KW : 'auto' ;

CSS_ID       : '#' [a-zA-Z_][a-zA-Z0-9_-]* ;
CSS_CLASS    : '.' [a-zA-Z_][a-zA-Z0-9_-]* ;
CSS_PSEUDO   : ':' [a-zA-Z_-]+ ;
CSS_STAR     : '*' ;
CSS_ELEMENT  : [a-zA-Z_][a-zA-Z0-9_-]* ;
CSS_COMMENT : '/*' .*? '*/' -> skip ;

WS : [ \t\r\n]+ -> skip ;

ANY : . -> skip ;
