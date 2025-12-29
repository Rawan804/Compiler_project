package ast;

import java.util.ArrayList;
import java.util.List;

public abstract class ASTNode {
    protected String nodeName;
    protected int lineNumber;
    protected List<ASTNode> children;

    public ASTNode(String nodeName, int lineNumber) {
        this.nodeName = nodeName;
        this.lineNumber = lineNumber;
        this.children = new ArrayList<>();
    }

    public void addChild(ASTNode child) {
        if (child != null) {
            children.add(child);
        }
    }

    public List<ASTNode> getChildren() {
        return children;
    }

    public String getNodeName() {
        return nodeName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    // Polymorphic print method
    public void print(int indent) {
        String prefix = " ".repeat(indent * 2);
        System.out.println(prefix + nodeName + " (line: " + lineNumber + ")");
        for (ASTNode child : children) {
            child.print(indent + 1);
        }
    }
}
