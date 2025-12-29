package ast;

import java.util.ArrayList;
import java.util.List;

public abstract class HTMLElementNode extends ASTNode {
    protected List<ASTNode> children = new ArrayList<>();
    protected List<AttributeNode> attributes = new ArrayList<>();

    public HTMLElementNode(String name, int line, int column) {
        super(name, line, column);
    }

    public void addChild(ASTNode node) {
        children.add(node);
    }

    public void addAttribute(AttributeNode attribute) {
        attributes.add(attribute);
    }

    public List<ASTNode> getChildren() {
        return children;
    }

    public List<AttributeNode> getAttributes() {
        return attributes;
    }

    @Override
    public void print(int indent) {
        // طباعة اسم العقدة وموقعها
        for (int i = 0; i < indent; i++) {
            System.out.print("  ");
        }
        System.out.println("<" + nodeName + "> [" + line + ":" + column + "]");

        // طباعة السمات
        for (AttributeNode attr : attributes) {
            attr.print(indent + 1);
        }

        // طباعة الأولاد
        for (ASTNode child : children) {
            child.print(indent + 1);
        }
    }
}