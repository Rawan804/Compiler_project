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
}
