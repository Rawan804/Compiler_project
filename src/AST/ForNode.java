package AST;

import java.util.List;

public class ForNode extends Node {
    private Node iterator;
    private Node iterable;
    private List<Node> body;
    private List<Node> elseBody;

    public ForNode(int line, Node iterator, Node iterable, List<Node> body, List<Node> elseBody) {
        super(line, Kind.FOR, "ForNode");
        this.iterator = iterator;
        this.iterable = iterable;
        this.body = body;
        this.elseBody = elseBody;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + nodeName + " [line=" + line + "]");
        System.out.println(indent + "  Iterator:");
        iterator.print(indent + "    ");
        System.out.println(indent + "  Iterable:");
        iterable.print(indent + "    ");

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

    public Node getIterator() {
        return iterator;
    }

    public Node getIterable() {
        return iterable;
    }

    @Override
    public String getNodeName() {
        return super.getNodeName();
    }

    public List<Node> getBody() {
        return body;
    }
}
