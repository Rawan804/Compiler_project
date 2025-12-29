package ast;

public class HeadNode extends HTMLElementNode {
    public HeadNode(int line, int column) {
        super("HEAD", line, column);
    }

    @Override
    public void print(int indent) {
        for (int i = 0; i < indent; i++) System.out.print("  ");
        System.out.println("<head> [" + line + ":" + column + "]");

        for (AttributeNode attr : attributes) attr.print(indent + 1);
        for (ASTNode child : children) child.print(indent + 1);
    }
}