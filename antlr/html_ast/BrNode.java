package ast;

import visitor.ASTVisitor;

public class BrNode extends HTMLElementNode {
    public BrNode(int line, int column) {
        super("BR", line, column);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitBr(this); // تحتاج إلى إضافته في ASTVisitor
    }
}