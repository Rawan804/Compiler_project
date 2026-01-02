package AST;


public class BooleanNode extends Node {
    private boolean value;

    public BooleanNode(int line, boolean value) {
        super(line, Kind.BOOLEAN, "BooleanNode");
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + nodeName + " [value=" + value + ", line=" + line + "]");
    }
}

