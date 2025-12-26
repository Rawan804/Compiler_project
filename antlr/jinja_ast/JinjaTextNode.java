package ast.jinja;

import ast.ASTNode;
import visitor.ASTVisitor;

public abstract class JinjaTextNode extends ASTNode {
    private final String text;

    public JinjaTextNode(String text, int line, int col) {
        super("JINJA_TEXT", line, col);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    // يجب إضافة دالة accept
    @Override
    public abstract <T> T accept(ASTVisitor<T> visitor);

    @Override
    public String toString() {
        return "{{ " + text + " }}";
    }
}