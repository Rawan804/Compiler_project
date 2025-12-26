package ast.jinja;

import ast.HTMLElementNode;
import visitor.ASTVisitor;

public class JinjaForNode extends HTMLElementNode {

    public JinjaForNode(int line, int column) {
        super("JINJA_FOR", line, column);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitJinjaFor(this);
    }
}
