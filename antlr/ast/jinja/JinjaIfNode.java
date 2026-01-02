package ast.jinja;

import ast.ASTNode;
import ast.HTMLElementNode;
import java.util.ArrayList;
import java.util.List;

public class JinjaIfNode extends HTMLElementNode {
    private String condition;
    private List<ASTNode> elseBlock = new ArrayList<>();

    public JinjaIfNode(String condition, int line, int column) {
        super("JINJA_IF", line, column);
        this.condition = condition;
    }

    public String getCondition() { return condition; }

    public void addElseChild(ASTNode node) {
        elseBlock.add(node);
    }

    public List<ASTNode> getElseBlock() {
        return elseBlock;
    }

    @Override
    public void print(int indent) {
        for (int i = 0; i < indent; i++) System.out.print("  ");
        System.out.println("{% if " + condition + " %} [" + line + ":" + column + "]");

        // طباعة then block (الأولاد العاديين)
        for (ast.ASTNode child : getChildren()) {
            child.print(indent + 1);
        }

        // طباعة else block إذا موجود
        if (!elseBlock.isEmpty()) {
            for (int i = 0; i < indent; i++) System.out.print("  ");
            System.out.println("{% else %}");

            for (ASTNode child : elseBlock) {
                child.print(indent + 1);
            }
        }

        for (int i = 0; i < indent; i++) System.out.print("  ");
        System.out.println("{% endif %}");
    }
}