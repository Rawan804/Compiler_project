package AST;
public class AssignNode extends Node {
    private Node target;
    private Node value;

    public AssignNode(int line,Node target, Node value) {
        super(line, Kind.ASSIGN, "AssignNode");
        this.target = target;
        this.value = value;
    }
    @Override
    public void print(String indent) {
        System.out.println(
                indent + nodeName +
                        " [line=" + line + "]"
        );
        target.print(indent + "  ");
        value.print(indent + "  ");
    }
    public Node getTarget() {
        return target;
    }

    public Node getValue() {
        return value;
    }
}
