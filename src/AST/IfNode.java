package AST;

import java.util.ArrayList;
import java.util.List;

public class IfNode extends Node {
    private Node condition;
    private List<Node> thenBody;
    private List<Node> elifConditions;
    private List<List<Node>> elifBodies;
    private List<Node> elseBody;

    // Constructor
    public IfNode(int line,
                  Node condition,
                  List<Node> thenBody,
                  List<Node> elifConditions,
                  List<List<Node>> elifBodies,
                  List<Node> elseBody) {
        super(line, Kind.IF, "IfNode");
        this.condition = condition;
        this.thenBody = thenBody;
        this.elifConditions = elifConditions;
        this.elifBodies = elifBodies;
        this.elseBody = elseBody;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + nodeName + " [line=" + line + "]");
        System.out.println(indent + "  Condition:");
        condition.print(indent + "    ");

        System.out.println(indent + "  ThenBody:");
        for (Node stmt : thenBody) {
            stmt.print(indent + "    ");
        }

        if (elifConditions != null && !elifConditions.isEmpty()) {
            for (int i = 0; i < elifConditions.size(); i++) {
                System.out.println(indent + "  ElifCondition:");
                elifConditions.get(i).print(indent + "    ");
                System.out.println(indent + "  ElifBody:");
                for (Node stmt : elifBodies.get(i)) {
                    stmt.print(indent + "    ");
                }
            }
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

    public List<Node> getThenBody() {
        return thenBody;
    }

    public List<Node> getElseBody() {
        return elseBody;
    }

    public List<List<Node>> getElifBodies() {
        return elifBodies;
    }
    public List<Node> getElifConditions() {
        return elifConditions;
    }
}
