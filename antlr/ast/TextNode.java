package ast;

public class TextNode extends ASTNode {
    private final String text;

    public TextNode(String text, int line, int column) {
        super("TEXT", line, column);
        this.text = text;
    }
    public String getText() {
        return text;
    }

    @Override
    public void print(int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print("  ");
        }
        System.out.println("TEXT: \"" + text + "\" [" + line + ":" + column + "]");
    }
}