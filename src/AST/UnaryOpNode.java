package AST;

public class UnaryOpNode extends Node {
    private String operator;
    private Node operand;

    public UnaryOpNode(int line, String operator, Node operand) {
        super(line, Kind.UNARY_OP, "UnaryOpNode");
        this.operator = operator;
        this.operand = operand;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + nodeName + " [op=" + operator + ", line=" + line + "]");
        operand.print(indent + "  ");
    }

    public Node getOperand() {
        return operand;
    }
}
