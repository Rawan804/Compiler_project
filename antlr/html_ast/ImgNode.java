package ast;

import visitor.ASTVisitor;

public class ImgNode extends HTMLElementNode  {

    public ImgNode(int line, int column) {
        super("IMG", line, column);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitImg(this);
    }
}
