package ast;

import visitor.ASTVisitor;

public class InputNode extends HTMLElementNode  {

    public InputNode(int line, int column) {
        super("INPUT", line, column);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitInput(this); // يجب أن تضيف visitInput في ASTVisitor
    }
}
