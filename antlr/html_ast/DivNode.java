package ast;

import visitor.ASTVisitor;

public class DivNode extends HTMLElementNode {

    public DivNode(int line, int column) {
        super("DIV", line, column);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitDiv(this);
    }
}
