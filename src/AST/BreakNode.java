package AST;

public class BreakNode extends Node {

    public BreakNode(int line) {
        super(line, Kind.BREAK, "BreakNode");
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + nodeName + " [line=" + line + "]");
    }
}
