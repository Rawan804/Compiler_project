package AST;


import java.util.ArrayList;
import java.util.List;

public class TryNode extends Node {

    private List<Node> tryBody;
    private List<Node> exceptTypes;
    private List<List<Node>> exceptBodies;


    public TryNode(int line,
                   List<Node> tryBody,
                   List<Node> exceptTypes,
                   List<List<Node>> exceptBodies)
    {

        super(line, Kind.TRY, "TryNode");
        this.tryBody = tryBody;
        this.exceptTypes = exceptTypes;
        this.exceptBodies = exceptBodies;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + nodeName + " [line=" + line + "]");
        System.out.println(indent + "  TryBody:");
        for (Node stmt : tryBody) {
            stmt.print(indent + "    ");
        }
        if (exceptBodies != null && !exceptBodies.isEmpty()) {
            for (int i = 0; i < exceptBodies.size(); i++) {
                System.out.print(indent + "  ExceptType: ");
                if (exceptTypes != null && exceptTypes.size() > i && exceptTypes.get(i) != null) {
                    exceptTypes.get(i).print("");
                } else {
                    System.out.println("Any");
                }
                System.out.println(indent + "  ExceptBody:");
                for (Node stmt : exceptBodies.get(i)) {
                    stmt.print(indent + "    ");
                }
            }
        }


    }

    public List<Node> getTryBody() {
        return tryBody;
    }



    public List<List<Node>> getExceptBodies() {
        return exceptBodies;
    }


}