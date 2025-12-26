package ast;

import visitor.ASTVisitor;

public class LinkNode extends HTMLElementNode {
    public LinkNode(int line, int column) {
        super("LINK", line, column);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitLink(this); // تحتاج إلى إضافته في ASTVisitor
    }
}