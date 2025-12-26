package ast;

import visitor.ASTVisitor;

public class DoctypeNode extends ASTNode {

    public DoctypeNode(int line, int column) {
        super("DOCTYPE", line, column);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitDoctype(this);
    }
}
