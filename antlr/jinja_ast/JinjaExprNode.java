package ast.jinja;

import ast.ASTNode;
import visitor.ASTVisitor;

public class JinjaExprNode extends ASTNode {

    private final String expression;

    public JinjaExprNode(String expression, int line, int column) {
        super("JINJA_EXPR", line, column);
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitJinjaExpr(this);
    }

    // إضافة دالة toString أو getText
    public String getText() {
        return expression;
    }
}