package ast;

import visitor.ASTVisitor;

public class H6Node extends HTMLElementNode {
    public H6Node(int line, int column) {
        super("H6", line, column);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitH6(this);
    }
}