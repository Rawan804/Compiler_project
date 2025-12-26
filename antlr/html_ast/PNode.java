package ast;

import visitor.ASTVisitor;

public class PNode extends HTMLElementNode {

    public PNode(int line, int column) {
        super("P", line, column);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitP(this);
    }
}
