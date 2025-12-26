package ast;

import visitor.ASTVisitor;

public class ButtonNode extends HTMLElementNode  {

    public ButtonNode(int line, int column) {
        super("BUTTON", line, column);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitButton(this); // يجب أن تضيف visitButton في ASTVisitor
    }
}
