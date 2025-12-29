package ast;

public class InputNode extends HTMLElementNode {
    public InputNode(int line, int column) {
        super("INPUT", line, column);
    }

    @Override
    public void print(int indent) {
        for (int i = 0; i < indent; i++) System.out.print("  ");
        System.out.println("<input/> [" + line + ":" + column + "]");  // self-closing

        for (AttributeNode attr : attributes) attr.print(indent + 1);
    }
}