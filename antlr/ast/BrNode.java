package ast;

public class BrNode extends HTMLElementNode {
    public BrNode(int line, int column) {
        super("BR", line, column);
    }

    @Override
    public void print(int indent) {
        for (int i = 0; i < indent; i++) System.out.print("  ");
        System.out.println("<br/> [" + line + ":" + column + "]");  // self-closing

        // طباعة السمات فقط (لأنه ما في children)
        for (AttributeNode attr : attributes) attr.print(indent + 1);
    }
}