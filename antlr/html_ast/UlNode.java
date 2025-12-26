package ast;

import visitor.ASTVisitor;

public class UlNode extends HTMLElementNode {
    public UlNode(int line, int column) {
        super("UL", line, column);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitUl(this);
    }
}