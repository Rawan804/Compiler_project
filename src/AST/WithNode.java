package AST;

import java.util.List;

public class WithNode extends Node {
    private Node contextExpr;
    private IdentifierNode optionalVar;
    private List<Node> body;

    public WithNode(int line, Node contextExpr, IdentifierNode optionalVar, List<Node> body) {
        super(line, Kind.WITH, "WithNode");
        this.contextExpr = contextExpr;
        this.optionalVar = optionalVar;
        this.body = body;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + nodeName + " [line=" + line + "]");

        System.out.println(indent + "  Context Expression:");
        contextExpr.print(indent + "    ");

        if(optionalVar != null) {
            System.out.println(indent + "  As Variable:");
            optionalVar.print(indent + "    ");
        }

        if(body != null && !body.isEmpty()) {
            System.out.println(indent + "  Body:");
            for(Node stmt : body) {
                stmt.print(indent + "    ");
            }
        }
    }


    public Node getContextExpr() { return contextExpr; }
    public IdentifierNode getOptionalVar() { return optionalVar; }
    public List<Node> getBody() { return body; }

}
