package ast;

import visitor.ASTVisitor;

public class HtmlNode extends HTMLElementNode {

    public HtmlNode(int line, int column) {
        super("HTML", line, column);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitHtml(this);
    }
}
