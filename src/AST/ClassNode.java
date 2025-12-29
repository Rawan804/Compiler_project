package AST;

import java.util.List;

public class ClassNode extends Node {
    private IdentifierNode className;
    private List<Node> body;


    public ClassNode(int line, IdentifierNode className, List<Node> body) {
        super(line, Kind.CLASS, "ClassNode");
        this.className = className;
        this.body = body;
    }

    public IdentifierNode getClassName() {
        return className;
    }

    public List<Node> getBody() {
        return body;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + nodeName + " [line=" + line + "]");
        System.out.println(indent + "  class Name:");
        className.print(indent + "    ");
        if (body != null) {
            for (Node stmt : body) {
                stmt.print(indent + "  ");
            }
        }
    }

}
