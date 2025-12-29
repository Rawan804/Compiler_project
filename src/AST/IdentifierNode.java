package AST;


public class IdentifierNode extends Node {

    private String name;

    public IdentifierNode(int line, String name) {
        super(line, Kind.IDENTIFIER, "IdentifierNode");
        this.name = name;
    }

    @Override
    public void print(String indent) {
        System.out.println(
                indent + nodeName +
                        " [name=" + name +
                        ", line=" + line + "]"
        );
    }

    public String getName() {
        return name;
    }

}
