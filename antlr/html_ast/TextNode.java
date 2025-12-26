package ast;

import visitor.ASTVisitor;

public class TextNode extends ASTNode {
    private final String text;

    public TextNode(String text, int line, int column) {
        super("TEXT", line, column);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitText(this);
    }
}