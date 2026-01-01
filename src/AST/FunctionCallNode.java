package AST;

import java.util.List;

public class FunctionCallNode extends Node {
    private Node functionName;
    private List<Node> arguments;

    public FunctionCallNode(int line, Node functionName, List<Node> arguments) {
        super(line, Kind.FUNC_CALL, "FunctionCallNode");
        this.functionName = functionName;
        this.arguments = arguments;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + nodeName + " [line=" + line + "]");
        System.out.println(indent + "  functionName:");
        functionName.print(indent + "  ");

        if (!arguments.isEmpty()) {
            System.out.println(indent + "  parameters:");
            for (Node arg : arguments) {
                if (arg != null) {
                    arg.print(indent + "    ");
                } else {
                    System.out.println(indent + "    null");
                }
            }
        }
    }


    public List<Node> getArguments() {
        return arguments;
    }

    public Node getFunctionName() {
        return functionName;
    }
}
