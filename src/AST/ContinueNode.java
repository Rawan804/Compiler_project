package AST;

public class ContinueNode extends Node {

    public ContinueNode(int line) {
        super(line, Kind.CONTINUE, "ContinueNode");
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + nodeName + " [line=" + line + "]");
    }
}
