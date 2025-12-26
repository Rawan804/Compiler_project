package ast;

import visitor.ASTVisitor;

public class TitleNode extends HTMLElementNode {
    private final String text;

    public TitleNode(int line, int column, String text) {
        super("TITLE", line, column);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitTitle(this);
    }
}