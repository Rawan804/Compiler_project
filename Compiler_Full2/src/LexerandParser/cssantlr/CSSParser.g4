parser grammar CSSParser;

options {
    tokenVocab = CSSLexer;
}

stylesheet
    : ruleSet* EOF
    #StylesheetNode
    ;

ruleSet
    : selectorGroup LBRACE declaration* RBRACE
    #RuleSetNode
    ;

/* =====================
   Selectors
   ===================== */
selectorGroup
    : selector (COMMA selector)*
    #SelectorGroupNode
    ;

selector
    : simpleSelector+
    #SelectorNode
    ;

simpleSelector
    : CSS_ELEMENT
    #ElementSelectorNode
    | CSS_ID
    #IdSelectorNode
    | CSS_CLASS
    #ClassSelectorNode
    | CSS_STAR
    #StarSelectorNode
    | CSS_PSEUDO
    #PseudoSelectorNode
    | notSelector
    #NotSelectorNode
    ;

notSelector
    : CSS_NOT LPAREN selector RPAREN
    #NotSelectorNodeAlt
    ;

/* =====================
   Declarations
   ===================== */
declaration
    : colorDecl
    #ColorDeclNode
    | backgroundColorDecl
    #BackgroundColorDeclNode
    | widthDecl
    #WidthDeclNode
    | heightDecl
    #HeightDeclNode
    | marginDecl
    #MarginDeclNode
    | paddingDecl
    #PaddingDeclNode
    | borderDecl
    #BorderDeclNode
    | displayDecl
    #DisplayDeclNode
    | positionDecl
    #PositionDeclNode
    | fontSizeDecl
    #FontSizeDeclNode
    | fontWeightDecl
    #FontWeightDeclNode
    | textAlignDecl
    #TextAlignDeclNode
    | opacityDecl
    #OpacityDeclNode
    | gridTemplateColumnsDecl
    #GridTemplateColumnsDeclNode
    | gridGapDecl
    #GridGapDeclNode
    ;

/* =====================
   Individual declarations
   ===================== */

colorDecl
    : CSS_PROP_COLOR COLON (CSS_COLOR | CSS_ELEMENT) SEMICOLON
    #ColorDeclNodeAlt
    ;

backgroundColorDecl
    : CSS_PROP_BG_COLOR COLON (CSS_COLOR | NONE_KW | CSS_ELEMENT) SEMICOLON
    #BackgroundColorDeclNodeAlt
    ;

widthDecl
    : CSS_PROP_WIDTH COLON sizeValue SEMICOLON
    #WidthDeclNodeAlt
    ;

heightDecl
    : CSS_PROP_HEIGHT COLON sizeValue SEMICOLON
    #HeightDeclNodeAlt
    ;

marginDecl
    : CSS_PROP_MARGIN COLON sizeValue SEMICOLON
    #MarginDeclNodeAlt
    ;

paddingDecl
    : CSS_PROP_PADDING COLON sizeValue SEMICOLON
    #PaddingDeclNodeAlt
    ;

borderDecl
    : CSS_PROP_BORDER COLON borderValue SEMICOLON
    #BorderDeclNodeAlt
    ;

displayDecl
    : CSS_PROP_DISPLAY COLON (CSS_VALUE_DISPLAY | NONE_KW | CSS_ELEMENT) SEMICOLON
    #DisplayDeclNodeAlt
    ;

positionDecl
    : CSS_PROP_POSITION COLON (CSS_VALUE_POSITION | CSS_ELEMENT) SEMICOLON
    #PositionDeclNodeAlt
    ;

fontSizeDecl
    : CSS_PROP_FONT_SIZE COLON (CSS_VALUE_GENERAL | CSS_ELEMENT)+ SEMICOLON
    #FontSizeDeclNodeAlt
    ;

fontWeightDecl
    : CSS_PROP_FONT_WEIGHT COLON
      (CSS_VALUE_FONT_WEIGHT | CSS_VALUE_GENERAL | CSS_ELEMENT)
      SEMICOLON
    #FontWeightDeclNodeAlt
    ;

textAlignDecl
    : CSS_PROP_TEXT_ALIGN COLON (CSS_VALUE_TEXT_ALIGN | CSS_ELEMENT) SEMICOLON
    #TextAlignDeclNodeAlt
    ;

opacityDecl
    : CSS_PROP_OPACITY COLON (CSS_VALUE_GENERAL | CSS_ELEMENT) SEMICOLON
    #OpacityDeclNodeAlt
    ;

/* =====================
   Grid
   ===================== */
gridTemplateColumnsDecl
    : CSS_PROP_GRID_TEMPLATE_COLUMNS COLON gridValue SEMICOLON
    #GridTemplateColumnsDeclNodeAlt
    ;

gridGapDecl
    : CSS_PROP_GRID_GAP COLON sizeValue SEMICOLON
    #GridGapDeclNodeAlt
    ;

gridValue
    : CSS_VALUE_GRID_FUNCTION LPAREN CSS_VALUE_GENERAL COMMA
      (CSS_VALUE_GENERAL | CSS_VALUE_GRID_UNIT) RPAREN
    #GridFunctionNode
    | (CSS_VALUE_GENERAL | CSS_VALUE_GRID_UNIT | AUTO_KW)+
    #GridValueListNode
    ;

/* =====================
   Shared values
   ===================== */
sizeValue
    : (CSS_VALUE_GENERAL | AUTO_KW | CSS_ELEMENT)+
    #SizeValueNode
    ;

borderValue
    : NONE_KW
    #NoneBorderNode
    | (CSS_VALUE_GENERAL | CSS_VALUE_BORDER_STYLE | CSS_COLOR | CSS_ELEMENT)+
    #BorderValueListNode;