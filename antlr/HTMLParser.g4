parser grammar HTMLParser;

options { tokenVocab = HTMLLexer; }



doctype
    : OPEN DOC DOCTYPE HTML CLOSE
    #doctypeNode
    ;

html
    : doctype
      OPEN HTML CLOSE
        head?
        body?
      OPEN SLASH HTML CLOSE
    #htmlNode
    ;


head
    : OPEN HEAD attribute* CLOSE
        title?
        link*
      OPEN SLASH HEAD CLOSE
    #headNode
    ;

title
    : OPEN TITLE attribute* CLOSE
        inlineContent*
      OPEN SLASH TITLE CLOSE
    #titleNode
    ;

body
    : OPEN BODY attribute* CLOSE
        content*
      OPEN SLASH BODY CLOSE
    #bodyNode
    ;


content
    : element        #elementContent
    | jinjaExpr      #jinjaExprContent
    | jinjaStmt      #jinjaStmtContent
    | textContent    #textContentContent
    ;


element
    : div
    | h1 | h2 | h3 | h4 | h5 | h6
    | p
    | span
    | a
    | ul
    | li
    | form
    | button
    | input
    | img
    | br
    | link
    ;


textContent
    : TEXT        #plainText
    | VAL         #valText
    ;

inlineContent
    : textContent
    | jinjaExpr
    | jinjaStmt
    ;



div
    : OPEN DIV attribute* CLOSE
        content*
      OPEN SLASH DIV CLOSE
    #divNode
    ;

ul
    : OPEN UL attribute* CLOSE
        li*content*
      OPEN SLASH UL CLOSE
    #ulNode
    ;

li
    : OPEN LI attribute* CLOSE
        content*
      OPEN SLASH LI CLOSE
    #liNode
    ;

form
    : OPEN FORM attribute* METHOD_KW EQ METHOD CLOSE
        content*
      OPEN SLASH FORM CLOSE
    #formNode
    ;


h1
    : OPEN H1 attribute* CLOSE inlineContent* OPEN SLASH H1 CLOSE
    #h1Node
    ;
h2
    : OPEN H2 attribute* CLOSE inlineContent* OPEN SLASH H2 CLOSE
    #h2Node
    ;
h3
    : OPEN H3 attribute* CLOSE inlineContent* OPEN SLASH H3 CLOSE
    #h3Node
    ;
h4
    : OPEN H4 attribute* CLOSE inlineContent* OPEN SLASH H4 CLOSE
    #h4Node
    ;
h5
    : OPEN H5 attribute* CLOSE inlineContent* OPEN SLASH H5 CLOSE
    #h5Node
    ;
h6
    : OPEN H6 attribute* CLOSE inlineContent* OPEN SLASH H6 CLOSE
    #h6Node
    ;

p
    : OPEN P attribute* CLOSE inlineContent* OPEN SLASH P CLOSE
    #pNode
    ;

span
    : OPEN SPAN attribute* CLOSE inlineContent* OPEN SLASH SPAN CLOSE
    #spanNode
    ;

a
    : OPEN A attribute* (HREF_KW EQ STRING) CLOSE inlineContent* OPEN SLASH A CLOSE
    #aNode
    ;

button
    : OPEN BUTTON_KW attribute*  (TYPE_KW EQ TYPE)* (NAME EQ STRING)* CLOSE inlineContent* OPEN SLASH BUTTON_KW CLOSE
    #buttonNode
    ;


input
    : OPEN INPUT attribute* (TYPE_KW EQ TYPE)* (NAME EQ STRING)* SLASH? CLOSE
    #inputNode
    ;

img
    : OPEN IMG attribute* (SRC EQ STRING)? SLASH? CLOSE
    #imgNode
    ;

br
    : OPEN BR SLASH? CLOSE
    #brNode
    ;

link
    : OPEN LINK attribute* (REL_KW EQ F)? (HREF_KW EQ STRING)? SLASH? CLOSE
    #linkNode
    ;


attribute
    :STYLE EQ STRING #styleAttribute
    | ATR                             #booleanAttribute
    | ATR EQ STRING                   #stringAttribute
    | ATR EQ jinjaExpr                #jinjaAttribute

    ;


jinjaExpr
    : JINJA_EXPR_OPEN jinjaExpression JINJA_EXPR_CLOSE
    #jinjaExprNode
    ;

jinjaExpression
    : jinjaValue (PIPE jinjaFilter)*
    #jinjaExpressionNode
    ;

jinjaValue
    : JINJA_ID (DOT JINJA_ID)*     #jinjaIdValue
    | NUMBER                      #jinjaNumberValue
    | STRING_J                    #jinjaStringValue
    ;

jinjaFilter
    : JINJA_ID
    #jinjaFilterNode
    ;


jinjaStmt
    : jinjaIf
    | jinjaFor
    ;


jinjaIf
    : JINJA_STMT_OPEN IF condition JINJA_STMT_CLOSE
        content*
      ( JINJA_STMT_OPEN ELSE JINJA_STMT_CLOSE content* )?
      JINJA_STMT_OPEN ENDIF JINJA_STMT_CLOSE
    #jinjaIfNode
    ;

condition
    : jinjaValue (OP jinjaValue)?
    #conditionNode
    ;


jinjaFor
    : JINJA_STMT_OPEN FOR JINJA_ID IN jinjaValue JINJA_STMT_CLOSE
        content*
      JINJA_STMT_OPEN ENDFOR JINJA_STMT_CLOSE
    #jinjaForNode
    ;
