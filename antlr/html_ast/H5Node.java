package ast;

import visitor.ASTVisitor;

public class H5Node extends HTMLElementNode {
    public H5Node(int line, int column) {
        super("H5", line, column);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitH5(this);
    }
}