package ast;

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
    public void print(int indent) {
        for (int i = 0; i < indent; i++) System.out.print("  ");
        System.out.println("@" + name + " = \"" + value + "\" [" + line + ":" + column + "]");
    }
}