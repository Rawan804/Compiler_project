package AST;

public class NumberNode extends Node {

    public Number value;

    public NumberNode(int line, Number value) {
        super(line, Kind.NUMBER, "NumberNode");
        this.value = value;
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
