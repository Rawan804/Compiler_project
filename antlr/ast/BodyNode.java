package ast;

public class BodyNode extends HTMLElementNode {
    public BodyNode(int line, int column) {
        super("BODY", line, column);
    }

    // ⬇️ إذا بدك طباعة خاصة للـ body
    @Override
    public void print(int indent) {
        for (int i = 0; i < indent; i++) System.out.print("  ");
        System.out.println("<body> [" + line + ":" + column + "]");

        for (AttributeNode attr : attributes) attr.print(indent + 1);
        for (ASTNode child : children) child.print(indent + 1);
    }
}