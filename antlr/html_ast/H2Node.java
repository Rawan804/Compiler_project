package ast;

import visitor.ASTVisitor;

public class H2Node extends HTMLElementNode {
    public H2Node(int line, int column) {
        super("H2", line, column);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitH2(this);
    }
}