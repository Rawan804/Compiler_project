package ast;

public class SelectorModifierNode extends ASTNode {
    public enum Type { ID, CLASS, PSEUDO }
    public Type type;
    public String value;

    public SelectorModifierNode(Type type, String value, int lineNumber) {
        super("SelectorModifier", lineNumber);
        this.type = type;
        this.value = value;
    }

    @Override
    public void print(int indent) {
        String prefix = " ".repeat(indent * 2);
        System.out.println(prefix + nodeName + " (" + type + "): " + value + " (line: " + lineNumber + ")");
    }
}
