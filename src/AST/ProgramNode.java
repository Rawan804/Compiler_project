package AST;


import java.util.ArrayList;
import java.util.List;

public class ProgramNode extends Node {

    private List<Node> statements;

    public ProgramNode() {
        super(0, Kind.PROGRAM, "ProgramNode");
        this.statements = new ArrayList<Node>();
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "ProgramNode");
        for (Node stmt : statements) {
            if (stmt != null) {
                stmt.print(indent + "  ");
            }
        }
    }

    public void addStatement(Node assignX) {

        if (assignX != null) {
            statements.add(assignX);
        }
    }
public  List<Node> getStatements() {
        return statements;
    }
    public void setStatements(List<Node> statements) {}
}

