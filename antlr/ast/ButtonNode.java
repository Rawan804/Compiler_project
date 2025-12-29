package ast;

public class ButtonNode extends HTMLElementNode {
    public ButtonNode(int line, int column) {
        super("BUTTON", line, column);
    }

    @Override
    public void print(int indent) {
        for (int i = 0; i < indent; i++) System.out.print("  ");
        System.out.println("<button> [" + line + ":" + column + "]");

        for (AttributeNode attr : attributes) attr.print(indent + 1);
        for (ASTNode child : children) child.print(indent + 1);
    }
}