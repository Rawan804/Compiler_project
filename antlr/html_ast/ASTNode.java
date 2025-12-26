package ast;

import visitor.ASTVisitor;

public abstract class ASTNode {

    protected String nodeName;
    protected int line;
    protected int column;

    public ASTNode(String nodeName, int line, int column) {
        this.nodeName = nodeName;
        this.line = line;
        this.column = column;
    }

    public String getNodeName() {
        return nodeName;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public abstract <T> T accept(ASTVisitor<T> visitor);
}
