package AST;



public class NullNode extends Node {

    public NullNode(int line) {
        super(line, Kind.NULL, "NullNode");
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + nodeName + " [line=" + line + "]");
    }
}
