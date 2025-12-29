package ast.jinja;

import ast.ASTNode;

public class JinjaValueNode extends ASTNode {
    private final String value;

    public JinjaValueNode(String value, int line, int column) {
        super("JINJA_VALUE", line, column);
        this.value = value;
    }

    public String getValue() { return value; }

    @Override
    public void print(int indent) {
        for (int i = 0; i < indent; i++) System.out.print("  ");
        System.out.println("JINJA_VALUE: " + value + " [" + line + ":" + column + "]");
    }
}