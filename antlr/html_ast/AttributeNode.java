package ast;

import visitor.ASTVisitor;

public class AttributeNode extends ASTNode {

    private final String name;
    private final String value;

    public AttributeNode(String name, String value, int line, int column) {
        super("ATTRIBUTE", line, column);
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitAttribute(this);
    }
}
