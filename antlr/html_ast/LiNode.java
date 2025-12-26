package ast;

import visitor.ASTVisitor;

public class LiNode extends HTMLElementNode {

    public LiNode(int line, int column) {
        super("Li", line, column);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitLi(this);
    }
}
