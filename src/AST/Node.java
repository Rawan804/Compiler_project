package AST;

import java.util.ArrayList;
import java.util.List;

public abstract class Node {
    protected int line;         
    protected String nodeName;   
    protected Kind kind;         

    public Node(int line, Kind kind, String nodeName) {
        this.line = line;
        this.kind = kind;
        this.nodeName = nodeName;
    }
    public int getLine() {
        return line;
    }

    public Kind getKind() {
        return kind;
    }

    public String getNodeName() {
        return nodeName;
    }

    
    public abstract void print(String indent);

}

