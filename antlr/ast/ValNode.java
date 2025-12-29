package ast;

public class ValNode extends ASTNode {
    private final String value;

    public ValNode(String value, int line, int column) {
        super("VAL", line, column);
        this.value = value;
    }

    public String getValue() { return value; }

    @Override
    public void print(int indent) {
        for (int i = 0; i < indent; i++) System.out.print("  ");
        System.out.println("VAL: " + value + " [" + line + ":" + column + "]");
    }
}