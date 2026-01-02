package ast.jinja;

import ast.ASTNode;
import ast.HTMLElementNode;
import java.util.ArrayList;
import java.util.List;

public class JinjaForNode extends HTMLElementNode {
    private String variable;
    private String iterable;

    public JinjaForNode(String variable, String iterable, int line, int column) {
        super("JINJA_FOR", line, column);
        this.variable = variable;
        this.iterable = iterable;
    }

    public String getVariable() { return variable; }
    public String getIterable() { return iterable; }

    @Override
    public void print(int indent) {
        for (int i = 0; i < indent; i++) System.out.print("  ");
        System.out.println("{% for " + variable + " in " + iterable + " %} [" + line + ":" + column + "]");

        for (ast.ASTNode child : getChildren()) {
            child.print(indent + 1);
        }
    }
}