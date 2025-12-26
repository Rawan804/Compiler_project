package ast.jinja;

import ast.ASTNode;
import visitor.ASTVisitor;

public class JinjaValueNode extends ASTNode {

    public JinjaValueNode(int line, int column) {
        super("JINJA_VALUE", line, column);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitJinjaValue(this);
    }
}