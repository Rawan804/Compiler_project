package ast;

import visitor.ASTVisitor;

public class FormNode extends HTMLElementNode  {

    public FormNode(int line, int column) {
        super("FORM", line, column); // غيّر IMG إلى FORM
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitForm(this); // تأكد أن ASTVisitor يحتوي على visitForm
    }
}
