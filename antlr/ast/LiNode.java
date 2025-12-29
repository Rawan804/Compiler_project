package ast;

public class LiNode extends HTMLElementNode {
    public LiNode(int line, int column) {
        super("LI", line, column);  // صححت من "Li" إلى "LI"
    }

    @Override
    public void print(int indent) {
        for (int i = 0; i < indent; i++) System.out.print("  ");
        System.out.println("<li> [" + line + ":" + column + "]");

        for (AttributeNode attr : attributes) attr.print(indent + 1);
        for (ASTNode child : children) child.print(indent + 1);
    }
}