package ast;

import org.antlr.v4.runtime.misc.Interval;
import java.util.ArrayList;
import java.util.List;
import antlr.CSSParser;
import antlr.CSSParserBaseVisitor;
import test.CSSSymbolTable;

public class CSSCompilerVisitor extends CSSParserBaseVisitor<ASTNode> {

    private final CSSSymbolTable symbolTable = new CSSSymbolTable();
    private String currentSelector = "";

    public CSSSymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    private String getCleanValue(org.antlr.v4.runtime.ParserRuleContext ctx) {
        if (ctx == null) return "";
        int a = ctx.start.getStartIndex();
        int b = ctx.stop.getStopIndex();
        Interval interval = new Interval(a, b);
        String fullText = ctx.start.getInputStream().getText(interval);

        if (fullText.contains(":")) {
            String value = fullText.substring(fullText.indexOf(":") + 1);
            if (value.endsWith(";")) {
                value = value.substring(0, value.length() - 1);
            }
            return value.trim();
        }
        return fullText.replace(";", "").trim();
    }

    private String extractValue(String fullText) {
        if (fullText.contains(":") && fullText.contains(";")) {
            return fullText.substring(fullText.indexOf(":") + 1, fullText.indexOf(";")).trim();
        }
        return fullText;
    }

    @Override
    public ASTNode visitStylesheet(CSSParser.StylesheetContext ctx) {
        StylesheetNode node = new StylesheetNode(ctx.getStart().getLine());
        for (CSSParser.RuleSetContext rCtx : ctx.ruleSet()) {
            node.addRuleSet((RuleSetNode) visit(rCtx));
        }
        return node;
    }

    @Override
    public ASTNode visitRuleSet(CSSParser.RuleSetContext ctx) {
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
    public ASTNode visitSelectorGroup(CSSParser.SelectorGroupContext ctx) {
        SelectorGroupNode node = new SelectorGroupNode(ctx.getStart().getLine());
        for (CSSParser.SelectorContext sCtx : ctx.selector()) {
            node.addSelector((SelectorNode) visit(sCtx));
        }
        return node;
    }

    @Override
    public ASTNode visitSelector(CSSParser.SelectorContext ctx) {
        SelectorNode node = new SelectorNode(ctx.getStart().getLine());
        SimpleSelectorNode simpleNode = new SimpleSelectorNode(ctx.getStart().getLine());

        String text = ctx.getText();
        if (text.startsWith("#")) {
            simpleNode.addModifier(new SelectorModifierNode(SelectorModifierNode.Type.ID, text, ctx.getStart().getLine()));
        } else if (text.startsWith(".")) {
            simpleNode.addModifier(new SelectorModifierNode(SelectorModifierNode.Type.CLASS, text, ctx.getStart().getLine()));
        } else if (text.contains(":")) {
            simpleNode.addModifier(new SelectorModifierNode(SelectorModifierNode.Type.PSEUDO, text, ctx.getStart().getLine()));
        } else {
            simpleNode.setElementName(new ElementNameNode(text, ctx.getStart().getLine()));
        }

        node.setSimpleSelector(simpleNode);
        return node;
    }


    @Override
    public ASTNode visitColorDecl(CSSParser.ColorDeclContext ctx) {
        // 1. استخراج القيمة النظيفة (بدون الفاصلة المنقوطة) باستخدام الدالة المساعدة
        String value = getCleanValue(ctx);

        // 2. تحديث جدول الرموز بالقيمة المستخرجة
        symbolTable.define(currentSelector, "color", extractValue(ctx.getText()));

        // 3. إنشاء العقدة بإرسال (القيمة، رقم السطر) فقط
        // لأن كلاس ColorDeclNode هو من يرسل كلمة "color" للأب تلقائياً
        return new ColorDeclNode(value, ctx.getStart().getLine());
    }

    @Override
    public ASTNode visitBackgroundColorDecl(CSSParser.BackgroundColorDeclContext ctx) {
        // استخراج القيمة النظيفة
        String value = getCleanValue(ctx);

        // تحديث جدول الرموز
        symbolTable.define(currentSelector, "background-color", extractValue(ctx.getText()));

        // التعديل: نرسل (القيمة، رقم السطر) فقط
        return new BackgroundColorDeclNode(value, ctx.getStart().getLine());
    }

    @Override
    public ASTNode visitWidthDecl(CSSParser.WidthDeclContext ctx) {
        // استخراج القيمة النظيفة
        String value = getCleanValue(ctx);

        // تحديث جدول الرموز
        symbolTable.define(currentSelector, "width", extractValue(ctx.getText()));

        // التعديل: نرسل (القيمة، رقم السطر) فقط
        return new WidthDeclNode(value, ctx.getStart().getLine());
    }

    @Override
    public ASTNode visitHeightDecl(CSSParser.HeightDeclContext ctx) {
        // استخراج القيمة النظيفة
        String value = extractValue(ctx.getText());

        // تعريفها في جدول الرموز
        symbolTable.define(currentSelector, "height", value);

        // إنشاء العقدة بإرسال بارامترين فقط (القيمة ورقم السطر)
        return new HeightDeclNode(value, ctx.getStart().getLine());
    }

    @Override
    public ASTNode visitMarginDecl(CSSParser.MarginDeclContext ctx) {
        String val = extractValue(ctx.getText());
        symbolTable.define(currentSelector, "margin", val);

        List<String> values = new ArrayList<>();
        // نستخدم getCleanValue لجلب القيم بدون فواصل منقوطة وتوزيعها في القائمة
        for (String s : getCleanValue(ctx).split("\\s+")) {
            if (!s.isEmpty()) values.add(s);
        }

        // نرسل القائمة ورقم السطر فقط
        return new MarginDeclNode(values, ctx.getStart().getLine());
    }

    @Override
    public ASTNode visitPaddingDecl(CSSParser.PaddingDeclContext ctx) {
        // 1. استخراج النص الكامل للقيم (مثلاً: 10px 20px)
        String val = extractValue(ctx.getText());

        // 2. تخزينها في جدول الرموز (Symbol Table)
        symbolTable.define(currentSelector, "padding", val);

        // 3. تحويل النص إلى قائمة من القيم المنفردة
        List<String> values = new ArrayList<>();
        String cleanVal = getCleanValue(ctx);
        for (String s : cleanVal.split("\\s+")) {
            if (!s.isEmpty()) {
                values.add(s);
            }
        }

        // 4. إنشاء العقدة بإرسال القائمة ورقم السطر
        // الكلاس PaddingDeclNode سيتولى دمجها وإرسالها للأب
        return new PaddingDeclNode(values, ctx.getStart().getLine());
    }

    @Override
    public ASTNode visitBorderDecl(CSSParser.BorderDeclContext ctx) {
        symbolTable.define(currentSelector, "border", extractValue(ctx.getText()));
        return new BorderDeclNode(getCleanValue(ctx), "", "", ctx.getStart().getLine());
    }

    @Override
    public ASTNode visitFontSizeDecl(CSSParser.FontSizeDeclContext ctx) {
        symbolTable.define(currentSelector, "font-size", extractValue(ctx.getText()));
        return new FontSizeDeclNode(getCleanValue(ctx), ctx.getStart().getLine());
    }

    @Override
    public ASTNode visitDisplayDecl(CSSParser.DisplayDeclContext ctx) {
        symbolTable.define(currentSelector, "display", extractValue(ctx.getText()));
        return new DisplayDeclNode(getCleanValue(ctx), ctx.getStart().getLine());
    }

    @Override
    public ASTNode visitPositionDecl(CSSParser.PositionDeclContext ctx) {
        // استخراج القيمة (مثل: absolute أو relative)
        String value = extractValue(ctx.getText());

        // تعريفها في جدول الرموز
        symbolTable.define(currentSelector, "position", value);

        // إنشاء العقدة بإرسال بارامترين فقط
        return new PositionDeclNode(value, ctx.getStart().getLine());
    }

    @Override
    public ASTNode visitFontWeightDecl(CSSParser.FontWeightDeclContext ctx) {
        return new FontWeightDeclNode(getCleanValue(ctx), ctx.getStart().getLine());
    }

    @Override
    public ASTNode visitTextAlignDecl(CSSParser.TextAlignDeclContext ctx) {
        return new TextAlignDeclNode(getCleanValue(ctx), ctx.getStart().getLine());
    }

    @Override
    public ASTNode visitOpacityDecl(CSSParser.OpacityDeclContext ctx) {
        String value = extractValue(ctx.getText());
        // (اختياري) إضافة القيمة لجدول الرموز إذا لم تكن موجودة
        // symbolTable.define(currentSelector, "opacity", value);

        return new OpacityDeclNode(value, ctx.getStart().getLine());
    }

    public void showResults() {
        symbolTable.printTable();
    }
}