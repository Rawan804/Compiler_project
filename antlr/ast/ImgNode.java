package ast;

public class ImgNode extends HTMLElementNode {
    public ImgNode(int line, int column) {
        super("IMG", line, column);
    }

    @Override
    public void print(int indent) {
        for (int i = 0; i < indent; i++) System.out.print("  ");
        System.out.println("<img/> [" + line + ":" + column + "]");  // self-closing

        for (AttributeNode attr : attributes) attr.print(indent + 1);
    }
}