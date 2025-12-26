package ast;

import visitor.ASTVisitor;

public class HeadNode extends HTMLElementNode {

    public HeadNode(int line, int column) {
        super("HEAD", line, column);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitHead(this);
    }
}
