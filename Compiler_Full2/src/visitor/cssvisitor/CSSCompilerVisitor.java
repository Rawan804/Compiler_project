package visitor.cssvisitor;

import AST.cssast.*;
import LexerandParser.cssantlr.CSSParser;
import LexerandParser.cssantlr.CSSParserBaseVisitor;
import SymbolTable.cssSymbolTable.CSSSymbolTable;


import java.util.ArrayList;
import java.util.List;

public class CSSCompilerVisitor extends CSSParserBaseVisitor<ASTNode> {

    private final CSSSymbolTable symbolTable = new CSSSymbolTable();
    private String currentSelector = "";

    public CSSSymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    private String extractValue(String fullText) {
        if (fullText.contains(":") && fullText.contains(";")) {
            return fullText.substring(fullText.indexOf(":") + 1, fullText.indexOf(";")).trim();
        }
        return fullText.replace(";", "").trim();
    }

    @Override
    public ASTNode visitStylesheetNode(CSSParser.StylesheetNodeContext ctx) {
        StylesheetNode node = new StylesheetNode(ctx.getStart().getLine());
        for (CSSParser.RuleSetContext rCtx : ctx.ruleSet()) {
            node.addRuleSet((RuleSetNode) visit(rCtx));
        }
        return node;
    }

    @Override
    public ASTNode visitRuleSetNode(CSSParser.RuleSetNodeContext ctx) {
        if (ctx.selectorGroup() != null) {
            currentSelector = ctx.selectorGroup().getText();
        }

        RuleSetNode node = new RuleSetNode(ctx.getStart().getLine());
        node.setSelectorGroup((SelectorGroupNode) visit(ctx.selectorGroup()));
        for (CSSParser.DeclarationContext dCtx : ctx.declaration()) {
            node.addDeclaration((DeclarationNode) visit(dCtx));
        }
        return node;
    }

    @Override
    public ASTNode visitSelectorGroupNode(CSSParser.SelectorGroupNodeContext ctx) {
        SelectorGroupNode node = new SelectorGroupNode(ctx.getStart().getLine());
        for (CSSParser.SelectorContext sCtx : ctx.selector()) {
            node.addSelector((SelectorNode) visit(sCtx));
        }
        return node;
    }

    @Override
    public ASTNode visitSelectorNode(CSSParser.SelectorNodeContext ctx) {
        SelectorNode node = new SelectorNode(ctx.getStart().getLine());
        for (CSSParser.SimpleSelectorContext sCtx : ctx.simpleSelector()) {
            ASTNode child = visit(sCtx);
            if (child instanceof SimpleSelectorNode) {
                node.setSimpleSelector((SimpleSelectorNode) child);
            }
        }
        return node;
    }

    @Override
    public ASTNode visitIdSelectorNode(CSSParser.IdSelectorNodeContext ctx) {
        SimpleSelectorNode simple = new SimpleSelectorNode(ctx.getStart().getLine());
        simple.addModifier(new SelectorModifierNode(SelectorModifierNode.Type.ID, ctx.getText(), ctx.getStart().getLine()));
        return simple;
    }

    @Override
    public ASTNode visitClassSelectorNode(CSSParser.ClassSelectorNodeContext ctx) {
        SimpleSelectorNode simple = new SimpleSelectorNode(ctx.getStart().getLine());
        simple.addModifier(new SelectorModifierNode(SelectorModifierNode.Type.CLASS, ctx.getText(), ctx.getStart().getLine()));
        return simple;
    }

    @Override
    public ASTNode visitElementSelectorNode(CSSParser.ElementSelectorNodeContext ctx) {
        SimpleSelectorNode simple = new SimpleSelectorNode(ctx.getStart().getLine());
        simple.setElementName(new ElementNameNode(ctx.getText(), ctx.getStart().getLine()));
        return simple;
    }

    @Override public ASTNode visitColorDeclNode(CSSParser.ColorDeclNodeContext ctx) { return visit(ctx.colorDecl()); }
    @Override public ASTNode visitBackgroundColorDeclNode(CSSParser.BackgroundColorDeclNodeContext ctx) { return visit(ctx.backgroundColorDecl()); }
    @Override public ASTNode visitWidthDeclNode(CSSParser.WidthDeclNodeContext ctx) { return visit(ctx.widthDecl()); }
    @Override public ASTNode visitHeightDeclNode(CSSParser.HeightDeclNodeContext ctx) { return visit(ctx.heightDecl()); }
    @Override public ASTNode visitMarginDeclNode(CSSParser.MarginDeclNodeContext ctx) { return visit(ctx.marginDecl()); }
    @Override public ASTNode visitPaddingDeclNode(CSSParser.PaddingDeclNodeContext ctx) { return visit(ctx.paddingDecl()); }
    @Override public ASTNode visitFontSizeDeclNode(CSSParser.FontSizeDeclNodeContext ctx) { return visit(ctx.fontSizeDecl()); }
    @Override public ASTNode visitDisplayDeclNode(CSSParser.DisplayDeclNodeContext ctx) { return visit(ctx.displayDecl()); }
    @Override public ASTNode visitPositionDeclNode(CSSParser.PositionDeclNodeContext ctx) { return visit(ctx.positionDecl()); }
    @Override public ASTNode visitFontWeightDeclNode(CSSParser.FontWeightDeclNodeContext ctx) { return visit(ctx.fontWeightDecl()); }
    @Override public ASTNode visitTextAlignDeclNode(CSSParser.TextAlignDeclNodeContext ctx) { return visit(ctx.textAlignDecl()); }
    @Override public ASTNode visitOpacityDeclNode(CSSParser.OpacityDeclNodeContext ctx) { return visit(ctx.opacityDecl()); }


    @Override
    public ASTNode visitColorDeclNodeAlt(CSSParser.ColorDeclNodeAltContext ctx) {
        String val = extractValue(ctx.getText());
        symbolTable.define(currentSelector, "color", val);
        return new ColorDeclNode(val, ctx.getStart().getLine());
    }

    @Override
    public ASTNode visitBackgroundColorDeclNodeAlt(CSSParser.BackgroundColorDeclNodeAltContext ctx) {
        String val = extractValue(ctx.getText());
        symbolTable.define(currentSelector, "background-color", val);
        return new BackgroundColorDeclNode(val, ctx.getStart().getLine());
    }

    @Override
    public ASTNode visitWidthDeclNodeAlt(CSSParser.WidthDeclNodeAltContext ctx) {
        String val = extractValue(ctx.getText());
        symbolTable.define(currentSelector, "width", val);
        return new WidthDeclNode(val, ctx.getStart().getLine());
    }

    @Override
    public ASTNode visitHeightDeclNodeAlt(CSSParser.HeightDeclNodeAltContext ctx) {
        String val = extractValue(ctx.getText());
        symbolTable.define(currentSelector, "height", val);
        return new HeightDeclNode(val, ctx.getStart().getLine());
    }

    @Override
    public ASTNode visitMarginDeclNodeAlt(CSSParser.MarginDeclNodeAltContext ctx) {
        String val = extractValue(ctx.getText());
        symbolTable.define(currentSelector, "margin", val);
        List<String> values = new ArrayList<>();
        for (String s : val.split("\\s+")) { if (!s.isEmpty()) values.add(s); }
        return new MarginDeclNode(values, ctx.getStart().getLine());
    }

    @Override
    public ASTNode visitPaddingDeclNodeAlt(CSSParser.PaddingDeclNodeAltContext ctx) {
        String val = extractValue(ctx.getText());
        symbolTable.define(currentSelector, "padding", val);
        List<String> values = new ArrayList<>();
        for (String s : val.split("\\s+")) { if (!s.isEmpty()) values.add(s); }
        return new PaddingDeclNode(values, ctx.getStart().getLine());
    }

    @Override
    public ASTNode visitFontSizeDeclNodeAlt(CSSParser.FontSizeDeclNodeAltContext ctx) {
        String val = extractValue(ctx.getText());
        symbolTable.define(currentSelector, "font-size", val);
        return new FontSizeDeclNode(val, ctx.getStart().getLine());
    }

    @Override
    public ASTNode visitDisplayDeclNodeAlt(CSSParser.DisplayDeclNodeAltContext ctx) {
        String val = extractValue(ctx.getText());
        symbolTable.define(currentSelector, "display", val);
        return new DisplayDeclNode(val, ctx.getStart().getLine());
    }

    @Override
    public ASTNode visitPositionDeclNodeAlt(CSSParser.PositionDeclNodeAltContext ctx) {
        String val = extractValue(ctx.getText());
        symbolTable.define(currentSelector, "position", val);
        return new PositionDeclNode(val, ctx.getStart().getLine());
    }

    @Override
    public ASTNode visitFontWeightDeclNodeAlt(CSSParser.FontWeightDeclNodeAltContext ctx) {
        String val = extractValue(ctx.getText());
        return new FontWeightDeclNode(val, ctx.getStart().getLine());
    }

    @Override
    public ASTNode visitTextAlignDeclNodeAlt(CSSParser.TextAlignDeclNodeAltContext ctx) {
        String val = extractValue(ctx.getText());
        return new TextAlignDeclNode(val, ctx.getStart().getLine());
    }

    @Override
    public ASTNode visitOpacityDeclNodeAlt(CSSParser.OpacityDeclNodeAltContext ctx) {
        String val = extractValue(ctx.getText());
        return new OpacityDeclNode(val, ctx.getStart().getLine());
    }

    public void showResults() {
        symbolTable.printTable();
    }
}