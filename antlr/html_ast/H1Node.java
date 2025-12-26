package ast;

import visitor.ASTVisitor;

public class H1Node extends HTMLElementNode {
    public H1Node(int line, int column) {
        super("H1", line, column);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitH1(this);
    }
}