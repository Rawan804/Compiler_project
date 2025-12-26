parser grammar CSSParser;

options {
    tokenVocab = CSSLexer;
}

stylesheet
    : ruleSet* EOF
    ;

ruleSet
    : selectorGroup LBRACE declaration* RBRACE
    ;

selectorGroup
    : selector (COMMA selector)*
    ;

selector
    : (CSS_ELEMENT | CSS_ID | CSS_CLASS | CSS_PSEUDO | CSS_STAR)+
    ;

declaration
    : colorDecl
    | backgroundColorDecl
    | widthDecl
    | heightDecl
    | marginDecl
    | paddingDecl
    | borderDecl
    | displayDecl
    | positionDecl
    | fontSizeDecl
    | fontWeightDecl
    | textAlignDecl
    | opacityDecl
    ;

colorDecl
    : CSS_PROP_COLOR COLON (CSS_COLOR | CSS_ELEMENT) SEMICOLON
    ;

backgroundColorDecl
    : CSS_PROP_BG_COLOR COLON (CSS_COLOR | NONE_KW | CSS_ELEMENT) SEMICOLON
    ;

widthDecl
    : CSS_PROP_WIDTH COLON sizeValue SEMICOLON
    ;

heightDecl
    : CSS_PROP_HEIGHT COLON sizeValue SEMICOLON
    ;

sizeValue
    : (CSS_VALUE_GENERAL | AUTO_KW | CSS_ELEMENT)+
    ;

marginDecl
    : CSS_PROP_MARGIN COLON boxValue SEMICOLON
    ;

paddingDecl
    : CSS_PROP_PADDING COLON boxValue SEMICOLON
    ;

boxValue
    : (CSS_VALUE_GENERAL | AUTO_KW | CSS_ELEMENT)+
    ;

borderDecl
    : CSS_PROP_BORDER COLON borderValue SEMICOLON
    ;

borderValue
    : NONE_KW
    | (CSS_VALUE_GENERAL | CSS_VALUE_BORDER_STYLE | CSS_COLOR | CSS_ELEMENT)+
    ;

displayDecl
    : CSS_PROP_DISPLAY COLON (CSS_VALUE_DISPLAY | NONE_KW | CSS_ELEMENT) SEMICOLON
    ;

positionDecl
    : CSS_PROP_POSITION COLON (CSS_VALUE_POSITION | CSS_ELEMENT) SEMICOLON
    ;

fontSizeDecl
    : CSS_PROP_FONT_SIZE COLON (CSS_VALUE_GENERAL | CSS_ELEMENT)+ SEMICOLON
    ;

fontWeightDecl
    : CSS_PROP_FONT_WEIGHT COLON (CSS_VALUE_FONT_WEIGHT | CSS_VALUE_GENERAL | CSS_ELEMENT) SEMICOLON
    ;

textAlignDecl
    : CSS_PROP_TEXT_ALIGN COLON (CSS_VALUE_TEXT_ALIGN | CSS_ELEMENT) SEMICOLON
    ;

opacityDecl
    : CSS_PROP_OPACITY COLON (CSS_VALUE_GENERAL | CSS_ELEMENT) SEMICOLON
    ;