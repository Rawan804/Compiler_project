package AST;

import java.util.ArrayList;
import java.util.List;

public class ReturnNode extends Node {

    public List<Node> values;

    public ReturnNode(int line, List<Node> values) {
        super(line,Kind.RETURN,"ReturnNode");
        this.values = values;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "ReturnNode [line=" + line + "]");
        for (Node v : values) {
            v.print(indent + "  ");
        }
    }

    public List<Node> getValues() {
        return values;
    }

}
