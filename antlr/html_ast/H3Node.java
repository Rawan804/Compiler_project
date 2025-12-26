package ast;

import visitor.ASTVisitor;

public class H3Node extends HTMLElementNode {
    public H3Node(int line, int column) {
        super("H3", line, column);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitH3(this);
    }
}