package ast;

import visitor.ASTVisitor;

public class H4Node extends HTMLElementNode {
    public H4Node(int line, int column) {
        super("H4", line, column);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitH4(this);
    }
}