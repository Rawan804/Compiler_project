package ast;

import visitor.ASTVisitor;

public class BodyNode extends HTMLElementNode {

    public BodyNode(int line, int column) {
        super("BODY", line, column);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitBody(this);
    }
}
