package ast;

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

    // ⬅️ الطباعة الأساسية للعقدة
    public void print(int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print("  ");
        }
        System.out.println(nodeName + " [" + line + ":" + column + "]");
    }
}