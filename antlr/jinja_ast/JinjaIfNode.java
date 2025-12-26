package ast.jinja;

import ast.HTMLElementNode;
import visitor.ASTVisitor;

public class JinjaIfNode extends HTMLElementNode {

    public JinjaIfNode(int line, int column) {
        super("JINJA_IF", line, column);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitJinjaIf(this);
    }
}
