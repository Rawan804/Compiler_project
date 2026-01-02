package ast.jinja;

import ast.ASTNode;

public abstract class JinjaTextNode extends ASTNode {
    private final String text;

    public JinjaTextNode(String text, int line, int col) {
        super("JINJA_TEXT", line, col);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public void print(int indent) {
        for (int i = 0; i < indent; i++) System.out.print("  ");
        System.out.println("JINJA_TEXT: " + text + " [" + line + ":" + column + "]");
    }

    @Override
    public String toString() {
        return "{{ " + text + " }}";
    }
}