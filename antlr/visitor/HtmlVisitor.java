package htmljenja.visitor;

import htmljenja.ast.*;
import htmljenja.ast.jinja.*;
import htmljenja.antlr.HTMLParser;
import htmljenja.antlr.HTMLParserBaseVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.Token;
import htmljenja.SymbolTable.HTMLJinjaSymbolTable;
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

        symbolTable.define(expr, "Jinja_Variable", "Dynamic_Value");

        return new JinjaExprNode(expr, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    @Override
    public ASTNode visitANode(HTMLParser.ANodeContext ctx) {
        ANode aNode = new ANode(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        if (ctx.STRING() != null) {
            String href = ctx.STRING().getText().replace("\"", "");
            symbolTable.define("href", "Link_Path", href);

            aNode.addAttribute(new AttributeNode("href", href, ctx.STRING().getSymbol().getLine(), ctx.STRING().getSymbol().getCharPositionInLine()));
        }
        
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

        symbolTable.define(name, "Value_of_" + name, value);

        return new AttributeNode(name, value, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
    @Override
    public ASTNode visitDivNode(HTMLParser.DivNodeContext ctx) {
        DivNode divNode = new DivNode(ctx.start.getLine(), ctx.start.getCharPositionInLine());

        if (ctx.attribute() != null) {
            for (HTMLParser.AttributeContext attrCtx : ctx.attribute()) {
                divNode.addAttribute((AttributeNode) visit(attrCtx));
            }
        }

        if (ctx.content() != null) {
            for (HTMLParser.ContentContext contentCtx : ctx.content()) {
                ASTNode child = visit(contentCtx);
                if (child != null) divNode.addChild(child);
            }
        }

        return divNode;
    }

    @Override
    public ASTNode visitUlNode(HTMLParser.UlNodeContext ctx) {
        UlNode ulNode = new UlNode(ctx.start.getLine(), ctx.start.getCharPositionInLine());

        if (ctx.attribute() != null) {
            for (HTMLParser.AttributeContext attrCtx : ctx.attribute()) {
                ulNode.addAttribute((AttributeNode) visit(attrCtx));
            }
        }

        if (ctx.content() != null) {
            for (HTMLParser.ContentContext contentCtx : ctx.content()) {
                ASTNode child = visit(contentCtx);
                if (child != null) ulNode.addChild(child);
            }
        }

        return ulNode;
    }

    @Override
    public ASTNode visitLiNode(HTMLParser.LiNodeContext ctx) {
        LiNode liNode = new LiNode(ctx.start.getLine(), ctx.start.getCharPositionInLine());

        if (ctx.attribute() != null) {
            for (HTMLParser.AttributeContext attrCtx : ctx.attribute()) {
                liNode.addAttribute((AttributeNode) visit(attrCtx));
            }
        }

        if (ctx.content() != null) {
            for (HTMLParser.ContentContext contentCtx : ctx.content()) {
                ASTNode child = visit(contentCtx);
                if (child != null) liNode.addChild(child);
            }
        }

        return liNode;
    }

    @Override
    public ASTNode visitImgNode(HTMLParser.ImgNodeContext ctx) {
        ImgNode imgNode = new ImgNode(ctx.start.getLine(), ctx.start.getCharPositionInLine());

        if (ctx.attribute() != null) {
            for (HTMLParser.AttributeContext attrCtx : ctx.attribute()) {
                imgNode.addAttribute((AttributeNode) visit(attrCtx));
            }
        }

        if (ctx.STRING() != null) {
            String src = ctx.STRING().getText().replace("\"", "");
            symbolTable.define("src", "Image_Source", src);
            imgNode.addAttribute(new AttributeNode("src", src,
                    ctx.STRING().getSymbol().getLine(),
                    ctx.STRING().getSymbol().getCharPositionInLine()));
        }

        return imgNode;
    }

    @Override
    public ASTNode visitFormNode(HTMLParser.FormNodeContext ctx) {
        FormNode formNode = new FormNode(ctx.start.getLine(), ctx.start.getCharPositionInLine());

        if (ctx.attribute() != null) {
            for (HTMLParser.AttributeContext attrCtx : ctx.attribute()) {
                formNode.addAttribute((AttributeNode) visit(attrCtx));
            }
        }

        if (ctx.STRING() != null) {
            String action = ctx.STRING().getText().replace("\"", "");
            symbolTable.define("action", "Form_Action", action);
            formNode.addAttribute(new AttributeNode("action", action,
                    ctx.STRING().getSymbol().getLine(),
                    ctx.STRING().getSymbol().getCharPositionInLine()));
        }

        if (ctx.METHOD() != null) {
            String method = ctx.METHOD().getText().replace("\"", "");
            symbolTable.define("method", "Form_Method", method);
            formNode.addAttribute(new AttributeNode("method", method,
                    ctx.METHOD().getSymbol().getLine(),
                    ctx.METHOD().getSymbol().getCharPositionInLine()));
        }

        if (ctx.content() != null) {
            for (HTMLParser.ContentContext contentCtx : ctx.content()) {
                ASTNode child = visit(contentCtx);
                if (child != null) formNode.addChild(child);
            }
        }

        return formNode;
    }

    @Override
    public ASTNode visitSpanNode(HTMLParser.SpanNodeContext ctx) {
        SpanNode spanNode = new SpanNode(ctx.start.getLine(), ctx.start.getCharPositionInLine());

        if (ctx.attribute() != null) {
            for (HTMLParser.AttributeContext attrCtx : ctx.attribute()) {
                spanNode.addAttribute((AttributeNode) visit(attrCtx));
            }
        }

        if (ctx.inlineContent() != null) {
            for (HTMLParser.InlineContentContext contentCtx : ctx.inlineContent()) {
                ASTNode child = visit(contentCtx);
                if (child != null) spanNode.addChild(child);
            }
        }

        return spanNode;
    }

    @Override
    public ASTNode visitButtonNode(HTMLParser.ButtonNodeContext ctx) {
        ButtonNode buttonNode = new ButtonNode(ctx.start.getLine(), ctx.start.getCharPositionInLine());

        if (ctx.attribute() != null) {
            for (HTMLParser.AttributeContext attrCtx : ctx.attribute()) {
                buttonNode.addAttribute((AttributeNode) visit(attrCtx));
            }
        }

        if (ctx.TYPE() != null && !ctx.TYPE().isEmpty()) {
            for (TerminalNode typeNode : ctx.TYPE()) {
                String type = typeNode.getText().replace("\"", "");
                symbolTable.define("type", "Button_Type", type);
                buttonNode.addAttribute(new AttributeNode("type", type,
                        typeNode.getSymbol().getLine(),
                        typeNode.getSymbol().getCharPositionInLine()));
            }
        }

        if (ctx.content() != null) {
            for (HTMLParser.ContentContext contentCtx : ctx.content()) {
                ASTNode child = visit(contentCtx);
                if (child != null) buttonNode.addChild(child);
            }
        }

        return buttonNode;
    }
    @Override
    public ASTNode visitJinjaForNode(HTMLParser.JinjaForNodeContext ctx) {
        String variable = ctx.JINJA_ID().getText();
        String iterable = ctx.jinjaValue().getText();

        JinjaForNode forNode = new JinjaForNode(variable, iterable, ctx.start.getLine(), ctx.start.getCharPositionInLine());

        symbolTable.enterScope("for_" + variable);
        symbolTable.define(variable, "Loop_Iterator", "from " + iterable);

        if (ctx.content() != null) {
            for (HTMLParser.ContentContext contentCtx : ctx.content()) {
                ASTNode child = visit(contentCtx);
                if (child != null) {
                    forNode.addChild(child);
                }
            }
        }

        symbolTable.exitScope();

        return forNode;
    }

    @Override public ASTNode visitElementContent(HTMLParser.ElementContentContext ctx) { return visit(ctx.element()); }
    @Override public ASTNode visitJinjaExprContent(HTMLParser.JinjaExprContentContext ctx) { return visit(ctx.jinjaExpr()); }
    @Override public ASTNode visitJinjaStmtContent(HTMLParser.JinjaStmtContentContext ctx) { return visit(ctx.jinjaStmt()); }
    @Override public ASTNode visitTextContentContent(HTMLParser.TextContentContentContext ctx) { return visit(ctx.textContent()); }
    @Override
    public ASTNode visitInputNode(HTMLParser.InputNodeContext ctx) {
        InputNode inputNode = new InputNode(ctx.start.getLine(), ctx.start.getCharPositionInLine());

        if (ctx.attribute() != null) {
            for (HTMLParser.AttributeContext attrCtx : ctx.attribute()) {
                inputNode.addAttribute((AttributeNode) visit(attrCtx));
            }
        }

        if (ctx.TYPE() != null && !ctx.TYPE().isEmpty()) {
            for (TerminalNode typeNode : ctx.TYPE()) {
                String type = typeNode.getText().replace("\"", "");
                symbolTable.define("type", "Input_Type", type);
                inputNode.addAttribute(new AttributeNode("type", type,
                        typeNode.getSymbol().getLine(),
                        typeNode.getSymbol().getCharPositionInLine()));
            }
        }

        if (ctx.NAME() != null && !ctx.NAME().isEmpty()) {
            for (TerminalNode nameNode : ctx.NAME()) {
                String name = nameNode.getText().replace("\"", "");
                symbolTable.define(name, "Input_Name", name);
                inputNode.addAttribute(new AttributeNode("name", name,
                        nameNode.getSymbol().getLine(),
                        nameNode.getSymbol().getCharPositionInLine()));
            }
        }

        if (ctx.PLACEHOLDER() != null && !ctx.PLACEHOLDER().isEmpty()) {
            for (TerminalNode placeholderNode : ctx.PLACEHOLDER()) {
                String placeholder = placeholderNode.getText().replace("\"", "");
                symbolTable.define("placeholder", "Input_Placeholder", placeholder);
                inputNode.addAttribute(new AttributeNode("placeholder", placeholder,
                        placeholderNode.getSymbol().getLine(),
                        placeholderNode.getSymbol().getCharPositionInLine()));
            }
        }

        return inputNode;
    }

    @Override
    public ASTNode visitTextareaNode(HTMLParser.TextareaNodeContext ctx) {
        TextareaNode textareaNode = new TextareaNode(ctx.start.getLine(), ctx.start.getCharPositionInLine());

        if (ctx.attribute() != null) {
            for (HTMLParser.AttributeContext attrCtx : ctx.attribute()) {
                textareaNode.addAttribute((AttributeNode) visit(attrCtx));
            }
        }

        // معالجة name إذا كان موجوداً
        if (ctx.NAME() != null && !ctx.NAME().isEmpty()) {
            for (TerminalNode nameNode : ctx.NAME()) {
                String name = nameNode.getText().replace("\"", "");
                symbolTable.define(name, "Textarea_Name", name);
                textareaNode.addAttribute(new AttributeNode("name", name,
                        nameNode.getSymbol().getLine(),
                        nameNode.getSymbol().getCharPositionInLine()));
            }
        }

        if (ctx.PLACEHOLDER() != null && !ctx.PLACEHOLDER().isEmpty()) {
            for (TerminalNode placeholderNode : ctx.PLACEHOLDER()) {
                String placeholder = placeholderNode.getText().replace("\"", "");
                symbolTable.define("placeholder", "Textarea_Placeholder", placeholder);
                textareaNode.addAttribute(new AttributeNode("placeholder", placeholder,
                        placeholderNode.getSymbol().getLine(),
                        placeholderNode.getSymbol().getCharPositionInLine()));
            }
        }

        return textareaNode;
    }
}
