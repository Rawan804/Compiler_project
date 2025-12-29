package ast.jinja;

import ast.ASTNode;

public class JinjaExprNode extends ASTNode {
    private final String expression;

    public JinjaExprNode(String expression, int line, int column) {
        super("JINJA_EXPR", line, column);
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

    public String getText() {
        return expression;
    }
    @Override
    public void print(int indent) {
        for (int i = 0; i < indent; i++) System.out.print("  ");
        // ⬇️ أضف طباعة الـ expression
        System.out.println("JINJA_EXPR: {{ " + expression + " }} [" + line + ":" + column + "]");
    }
}


