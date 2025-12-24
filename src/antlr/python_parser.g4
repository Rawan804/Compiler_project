parser grammar python_parser;

options { tokenVocab=python_lexer; }



prog
    : stat* EOF
    ;


stat
    :classStmt          #ClassState
   | decoratedDef        #DecoratedDefState
    | defStmt             #DefState
    | forStmt             #ForState
    | whileStmt           #WhileState
    | ifStmt              #IfStat
    | tryStmt            #TryState
    | withStmt            #WithStat
    | returnStmt          #ReturnStat
    | importStmt          #ImportStat
    | breakStmt           #BreakState
    | continueStmt        #Continue
    | printStmt           #PrintStat
    | assignment          #AssignmentStat
    | decoratorCall       #DecoratorCallStat
    | array               #ArrayStat
    | expr                #ExprStat
    ;



importStmt
    : FROM IDENTIFIER IMPORT IDENTIFIER (COMMA IDENTIFIER)*?
    | IMPORT IDENTIFIER (COMMA IDENTIFIER)*
    | IMPORT IDENTIFIER AS IDENTIFIER
    ;



withStmt
    : WITH expr (AS IDENTIFIER)? COL stat*
    ;

forStmt
    :
      FOR IDENTIFIER IN expr (COL)? stat*

    ;

whileStmt
    : WHILE expr COL stat*
      (ELSE COL stat*)?
    ;

ifStmt
    : IF expr (COL)? stat*
      (ELIF expr (COL)? stat*)*
      (ELSE (COL)? stat*)?
    | IF expr (NOT)? IN expr (COL)? stat*
    | IF (expr (IS)?)? NULL (COL)? stat*
    ;



tryStmt
    : TRY COL stat*
      (EXCEPT IDENTIFIER? COL stat*)+
      (ELSE COL stat*)?
      (FINALLY COL stat*)?
    ;




breakStmt
    : BREAK
    ;

continueStmt
    : CONTINUE
    ;

returnStmt
    : RETURN expr (COMMA expr)*
    ;

printStmt
    : PRINT OPEN_B expr (COMMA expr)* CLOSE_B
    ;


classStmt
    : CLASS IDENTIFIER COL stat*
    ;


decorator
    : AT decoratorCall
    ;

decoratorCall
    : IDENTIFIER (DOT IDENTIFIER)* OPEN_B ((IDENTIFIER)* expr (COMMA expr)*)? CLOSE_B
    | IDENTIFIER (DOT IDENTIFIER)+ (ASSIGN)? LSB (expr (COMMA expr)*)? RSB

    ;

decoratedDef
    : decorator+ defStmt
    ;


defStmt
    : DEF IDENTIFIER OPEN_B (IDENTIFIER (COMMA IDENTIFIER)*)? CLOSE_B COL stat*
    ;



listStmt
    : IDENTIFIER ASSIGN LSB (expr (COMMA expr)*)? RSB
    ;

tuplesStmt
    : IDENTIFIER ASSIGN OPEN_B
      (expr (COMMA expr)* COMMA?)?
      CLOSE_B
    ;

setStmt
    : IDENTIFIER ASSIGN LKB (expr (COMMA expr)*)? RKB
    ;

dictStmt
    : IDENTIFIER ASSIGN LKB
      (expr COL expr (COMMA expr COL expr)*)?
      RKB
    ;

array
    : listStmt
    | tuplesStmt
    | setStmt
    | dictStmt

    | RANGE OPEN_B expr (COMMA expr)*? CLOSE_B
    ;



assignment
    : IDENTIFIER ASSIGN expr
    | IDENTIFIER (DOT IDENTIFIER)+ ASSIGN expr
    | IDENTIFIER (LSB expr RSB)+ ASSIGN expr
    ;



expr
    : expr (PLUS | MINUS) term
    | expr (GREATERTHAN | SMALLERTHAN | EQ | GREATEROREQ | SMALLOREQ) term
    | expr (AND | OR) term
    |expr (PLUS | MINUS|MUL | DIV) ASSIGN expr
    | term
    ;

term
    : term (MUL | DIV) factor
    | factor
    ;

factor
    // 1️⃣ method call: line.split(...)
    : IDENTIFIER (DOT IDENTIFIER)+ OPEN_B
        (expr (COMMA expr)*)?
      CLOSE_B

    // 2️⃣ function call: foo(...)
    | IDENTIFIER OPEN_B
        (
            IDENTIFIER ASSIGN expr (COMMA IDENTIFIER ASSIGN expr)*
          | expr (COMMA expr)*
        )?
      CLOSE_B

    // 3️⃣ indexing + attribute: a[0], a.b, a.b[0]
    | IDENTIFIER (DOT IDENTIFIER)? (LSB expr (COMMA expr)* RSB)+

    // 4️⃣ attribute only: a.b.c
    | IDENTIFIER (DOT IDENTIFIER)+

    // 5️⃣ compound assignment: += -= *= /=
    | IDENTIFIER (PLUS ASSIGN | MINUS ASSIGN | MUL ASSIGN | DIV ASSIGN) expr

    // 6️⃣ assignment expression
    | assignment

    // 7️⃣ array literal
    | array

    // 8️⃣ dict literal
    | LKB (expr COL expr (COMMA expr COL expr)* COMMA?)? RKB

    // 9️⃣ grouping
    | OPEN_B expr (COMMA expr)* CLOSE_B

    // 🔟 list literal
    | LSB expr (COMMA expr)* RSB

    // 1️⃣1️⃣ unary not
    | NOT factor

    // 1️⃣2️⃣ literals
    | NUMBER
    | STRING
    | TRUE
    | FALSE
    | NULL

    // ⚠️ آخر شي دائمًا
    | IDENTIFIER
    ;
