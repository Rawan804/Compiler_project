package AST;

public class BinaryOpNode extends Node {

    private String operator;
    private Node left;
    private Node right;

    public BinaryOpNode(int line, Node left, String operator, Node right) {
        super(line, Kind.BINARY_OP, "BinaryOpNode");
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public void print(String indent) {
        System.out.println(
                indent + nodeName +
                        " [op=" + operator +
                        ", line=" + line + "]"
        );
        left.print(indent + "  ");
        right.print(indent + "  ");
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public String getOperator() {
        return operator;
    }
}
