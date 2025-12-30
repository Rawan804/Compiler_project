package AST;

import java.util.List;

public class WhileNode extends Node {
    private Node condition;
    private List<Node> body;
    private List<Node> elseBody;

    public WhileNode(int line, Node condition, List<Node> body, List<Node> elseBody) {
        super(line, Kind.WHILE, "WhileNode");
        this.condition = condition;
        this.body = body;
        this.elseBody = elseBody;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + nodeName + " [line=" + line + "]");
        System.out.println(indent + "  Condition:");
        condition.print(indent + "    ");

        System.out.println(indent + "  Body:");
        for (Node stmt : body) {
            stmt.print(indent + "    ");
        }

        if (elseBody != null && !elseBody.isEmpty()) {
            System.out.println(indent + "  ElseBody:");
            for (Node stmt : elseBody) {
                stmt.print(indent + "    ");
            }
        }
    }

    public Node getCondition() {
        return condition;
    }

    public List<Node> getBody() {
        return body;
    }
}
