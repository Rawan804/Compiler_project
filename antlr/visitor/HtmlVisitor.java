package visitor;

import ast.*;
import ast.jinja.*;
import antlr.HTMLParser;
import antlr.HTMLParserBaseVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.Token;
import SymbolTable.HTMLJinjaSymbolTable;
import java.util.List;

public class HtmlVisitor extends HTMLParserBaseVisitor<ASTNode> {

    private HTMLJinjaSymbolTable symbolTable = new HTMLJinjaSymbolTable();

    public HTMLJinjaSymbolTable getSymbolTable() {
        return symbolTable;
    }

    @Override
    public ASTNode visitHtmlNode(HTMLParser.HtmlNodeContext ctx) {
        symbolTable.enterScope("html");
        Token start = ctx.start;
        HtmlNode htmlNode = new HtmlNode(start.getLine(), start.getCharPositionInLine());

        if (ctx.doctype() != null) visit(ctx.doctype()); // زيارة فقط بدون إضافة للشجرة حسب تصميمك

        if (ctx.head() != null) htmlNode.addChild(visit(ctx.head()));
        if (ctx.body() != null) htmlNode.addChild(visit(ctx.body()));

        return htmlNode;
    }

    @Override
    public ASTNode visitHeadNode(HTMLParser.HeadNodeContext ctx) {
        symbolTable.enterScope("head");
        HeadNode headNode = new HeadNode(ctx.start.getLine(), ctx.start.getCharPositionInLine());

        if (ctx.attribute() != null) {
            for (HTMLParser.AttributeContext attrCtx : ctx.attribute()) {
                headNode.addAttribute((AttributeNode) visit(attrCtx));
            }
        }
        if (ctx.title() != null) headNode.addChild(visit(ctx.title()));

        // إصلاح: التأكد من إضافة الـ LinkNode للشجرة
        if (ctx.link() != null) {
            for (HTMLParser.LinkContext linkCtx : ctx.link()) {
                headNode.addChild(visit(linkCtx));
            }
        }

        symbolTable.exitScope();
        return headNode;
    }

    @Override
    public ASTNode visitTitleNode(HTMLParser.TitleNodeContext ctx) {
        TitleNode titleNode = new TitleNode(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        if (ctx.inlineContent() != null) {
            for (HTMLParser.InlineContentContext contentCtx : ctx.inlineContent()) {
                ASTNode child = visit(contentCtx);
                if (child != null) titleNode.addChild(child);
            }
        }
        return titleNode;
    }

    @Override
    public ASTNode visitBodyNode(HTMLParser.BodyNodeContext ctx) {
        symbolTable.enterScope("body");
        BodyNode bodyNode = new BodyNode(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        if (ctx.content() != null) {
            for (HTMLParser.ContentContext contentCtx : ctx.content()) {
                ASTNode child = visit(contentCtx);
                if (child != null) bodyNode.addChild(child);
            }
        }
        symbolTable.exitScope();
        return bodyNode;
    }

    // --- إصلاح الـ LinkNode ليعرض السمات كما في مخرجاتك ---
    @Override
    public ASTNode visitLinkNode(HTMLParser.LinkNodeContext ctx) {
        LinkNode linkNode = new LinkNode(ctx.start.getLine(), ctx.start.getCharPositionInLine());

        if (ctx.REL() != null) {
            String relVal = ctx.REL().getText().replace("\"", "");
            // إضافة للجدول: الاسم "rel"، النوع "Attribute"، القيمة relVal
            symbolTable.define("rel", "Attribute", relVal);
            linkNode.addAttribute(new AttributeNode("rel", relVal, ctx.REL().getSymbol().getLine(), ctx.REL().getSymbol().getCharPositionInLine()));
        }

        if (ctx.STRING() != null) {
            String hrefVal = ctx.STRING().getText().replace("\"", "");
            // إضافة للجدول: الاسم "href"، النوع "Attribute"، القيمة hrefVal
            symbolTable.define("href", "Attribute", hrefVal);
            linkNode.addAttribute(new AttributeNode("href", hrefVal, ctx.STRING().getSymbol().getLine(), ctx.STRING().getSymbol().getCharPositionInLine()));
        }
        return linkNode;
    }
    // --- معالجة الـ PNode والنصوص ---
    @Override
    public ASTNode visitPNode(HTMLParser.PNodeContext ctx) {
        PNode pNode = new PNode(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        if (ctx.inlineContent() != null) {
            for (HTMLParser.InlineContentContext c : ctx.inlineContent()) {
                ASTNode child = visit(c);
                if (child != null) pNode.addChild(child);
            }
        }
        return pNode;
    }

    @Override
    public ASTNode visitPlainText(HTMLParser.PlainTextContext ctx) {
        return new TextNode(ctx.TEXT().getText(), ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    @Override
    public ASTNode visitJinjaExprNode(HTMLParser.JinjaExprNodeContext ctx) {
        String expr = ctx.jinjaExpression().getText().trim();

        // نضع كلمة Dynamic بدلاً من تكرار التعبير، ليفهم المستخدم أن القيمة ستأتي لاحقاً
        symbolTable.define(expr, "Jinja_Variable", "Dynamic_Value");

        return new JinjaExprNode(expr, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    // --- العقد التي كانت مفقودة أو تسبب اختلافاً ---
    @Override
    public ASTNode visitANode(HTMLParser.ANodeContext ctx) {
        ANode aNode = new ANode(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        if (ctx.STRING() != null) {
            String href = ctx.STRING().getText().replace("\"", "");
            // أضيفي هذا السطر لملء الجدول
            symbolTable.define("href", "Link_Path", href);

            aNode.addAttribute(new AttributeNode("href", href, ctx.STRING().getSymbol().getLine(), ctx.STRING().getSymbol().getCharPositionInLine()));
        }
        // ... بقية الكود كما هو
        return aNode;
    }

    @Override
    public ASTNode visitH1Node(HTMLParser.H1NodeContext ctx) {
        H1Node node = new H1Node(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        if (ctx.inlineContent() != null) {
            for (HTMLParser.InlineContentContext c : ctx.inlineContent()) {
                ASTNode child = visit(c);
                if (child != null) node.addChild(child);
            }
        }
        return node;
    }

    @Override
    public ASTNode visitStringAttribute(HTMLParser.StringAttributeContext ctx) {
        String name = ctx.ATR().getText();
        String value = ctx.STRING().getText().replace("\"", "");

        // الآن نرسل القيمة الفعلية للجدول
        symbolTable.define(name, "Value_of_" + name, value);

        return new AttributeNode(name, value, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
    @Override
    public ASTNode visitJinjaForNode(HTMLParser.JinjaForNodeContext ctx) {
        // 1. استخراج المتغير (مثل user) والمجموعة (مثل users)
        String variable = ctx.JINJA_ID().getText();
        String iterable = ctx.jinjaValue().getText();

        // 2. إنشاء عقدة الـ AST للحلقة
        JinjaForNode forNode = new JinjaForNode(variable, iterable, ctx.start.getLine(), ctx.start.getCharPositionInLine());

        // 3. إدارة جدول الرموز (فتح نطاق جديد للحلقة)
        symbolTable.enterScope("for_" + variable);
        symbolTable.define(variable, "Loop_Iterator", "from " + iterable);

        // 4. زيارة محتوى الحلقة (الأبناء) وإضافتهم للعقدة
        if (ctx.content() != null) {
            for (HTMLParser.ContentContext contentCtx : ctx.content()) {
                ASTNode child = visit(contentCtx);
                if (child != null) {
                    forNode.addChild(child);
                }
            }
        }

        // 5. إغلاق النطاق بعد الانتهاء من زيارة الأبناء
        symbolTable.exitScope();

        return forNode;
    }

    // دوال الربط (Bridges)
    @Override public ASTNode visitElementContent(HTMLParser.ElementContentContext ctx) { return visit(ctx.element()); }
    @Override public ASTNode visitJinjaExprContent(HTMLParser.JinjaExprContentContext ctx) { return visit(ctx.jinjaExpr()); }
    @Override public ASTNode visitJinjaStmtContent(HTMLParser.JinjaStmtContentContext ctx) { return visit(ctx.jinjaStmt()); }
    @Override public ASTNode visitTextContentContent(HTMLParser.TextContentContentContext ctx) { return visit(ctx.textContent()); }
}