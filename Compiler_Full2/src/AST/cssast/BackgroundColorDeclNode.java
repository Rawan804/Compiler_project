package AST.cssast;

public class BackgroundColorDeclNode extends DeclarationNode {

    public BackgroundColorDeclNode(String value, int lineNumber) {
        super("background-color", value, lineNumber);
    }

    @Override
    public void print(int indent) {
        String prefix = " ".repeat(indent * 2);
        System.out.println(prefix + "Declaration: background-color: " + getPropertyValue() + " (line: " + lineNumber + ")");
    }
}