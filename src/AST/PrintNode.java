package AST;

import java.util.List;

public class PrintNode extends Node {

    private List<Node> arguments;   // القيم المطبوعة

    public PrintNode(int line, List<Node> arguments) {
        super(line, Kind.PRINT, "PrintNode");
        this.arguments = arguments;
    }

    public List<Node> getArguments() {
        return arguments;
    }

    @Override
    public void print(String indent) {
        System.out.println(
                indent + nodeName +
                        " [line=" + line + "]"
        );

        for (Node arg : arguments) {
            arg.print(indent + "  ");
        }
    }
}
