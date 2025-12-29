package ast;

public class DoctypeNode extends ASTNode {
    public DoctypeNode(int line, int column) {
        super("DOCTYPE", line, column);
    }

    @Override
    public void print(int indent) {
        for (int i = 0; i < indent; i++) System.out.print("  ");
        System.out.println("<!DOCTYPE html> [" + line + ":" + column + "]");
    }
}