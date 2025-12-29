package ast;

public class LinkNode extends HTMLElementNode {
    public LinkNode(int line, int column) {
        super("LINK", line, column);
    }

    @Override
    public void print(int indent) {
        for (int i = 0; i < indent; i++) System.out.print("  ");
        System.out.println("<link/> [" + line + ":" + column + "]");  // self-closing

        for (AttributeNode attr : attributes) attr.print(indent + 1);
    }
}