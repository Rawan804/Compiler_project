package AST.hast.jinja;

import AST.hast.ASTNode;

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

        System.out.println("JINJA_EXPR: {{ " + expression + " }} [" + line + ":" + column + "]");
    }
}


