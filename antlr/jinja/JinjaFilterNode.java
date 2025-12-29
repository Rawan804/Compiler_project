package ast.jinja;

import ast.ASTNode;

public class JinjaFilterNode extends ASTNode {
    private final String filterName;

    public JinjaFilterNode(String filterName, int line, int column) {
        super("JINJA_FILTER", line, column);
        this.filterName = filterName;
    }

    public String getFilterName() { return filterName; }

    @Override
    public void print(int indent) {
        for (int i = 0; i < indent; i++) System.out.print("  ");
        System.out.println("| " + filterName + " [" + line + ":" + column + "]");
    }
}