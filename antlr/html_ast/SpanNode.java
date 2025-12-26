package ast;

import visitor.ASTVisitor;

public class SpanNode extends HTMLElementNode {

    public SpanNode(int line, int column) {
        super("SPAN", line, column);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitSpan(this);
    }
}
