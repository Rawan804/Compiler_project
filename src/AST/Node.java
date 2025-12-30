package AST;

import java.util.ArrayList;
import java.util.List;

public abstract class Node {
    protected int line;          // رقم السطر
    protected String nodeName;   // اسم العقدة
    protected Kind kind;         // نوع العقدة

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

    /**
     * دالة مجردة → polymorphism
     * كل عقدة تطبع نفسها بطريقتها
     */
    public abstract void print(String indent);

}

