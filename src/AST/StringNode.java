package AST;

public class StringNode extends  Node{
private  String value;

    public StringNode(int line, String value ) {
        super(line, Kind.STRING, "StringNode");
        this.value=value;
    }

    @Override
    public void print(String indent) {
        System.out.println(
                indent + nodeName +
                        " [value=" + value +
                        ", line=" + line + "]"
        );

    }
}
