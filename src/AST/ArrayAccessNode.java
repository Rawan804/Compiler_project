package AST;

import java.util.List;

public class ArrayAccessNode extends Node {
    private Node array;
    private List<Node> indices;

    public ArrayAccessNode(int line, Node array, List<Node> indices) {
        super(line, Kind.ARRAY_ACCESS, "ArrayAccessNode");
        this.array = array;
        this.indices = indices;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + nodeName + " [line=" + line + "]");
        array.print(indent + "  ");
        for (Node idx : indices) {
            idx.print(indent + "  ");
        }
    }

    public List<Node> getIndices() {
        return indices;
    }

    public Node getArray() {
        return array;
    }
}
