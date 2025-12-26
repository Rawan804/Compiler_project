package ast;

import visitor.ASTVisitor;

public class ANode extends HTMLElementNode {
    public ANode(int line, int column) {
        super("A", line, column);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitA(this); // تحتاج إلى إضافته في ASTVisitor
    }
}