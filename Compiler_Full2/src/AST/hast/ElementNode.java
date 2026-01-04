package AST.hast;

public class ElementNode extends HTMLElementNode {
    public ElementNode(String tagName, int line, int column) {
        super(tagName.toUpperCase(), line, column);
    }

    @Override
    public void print(int indent) {

        for (int i = 0; i < indent; i++) {
            System.out.print("  ");
        }
        System.out.println("<" + nodeName + "> [" + line + ":" + column + "]");

        for (AttributeNode attr : attributes) {
            attr.print(indent + 1);
        }

        for (ASTNode child : children) {
            child.print(indent + 1);
        }
    }
}