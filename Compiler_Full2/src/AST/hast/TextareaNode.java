// TextareaNode.java
package AST.hast;

public class TextareaNode extends HTMLElementNode {
    public TextareaNode(int line, int column) {
        super("TEXTAREA", line, column);
    }

    @Override
    public void print(int indent) {
        for (int i = 0; i < indent; i++) System.out.print("  ");
        System.out.println("<textarea> [" + line + ":" + column + "]");

        for (AttributeNode attr : attributes) attr.print(indent + 1);
        for (ASTNode child : children) child.print(indent + 1);
    }
}