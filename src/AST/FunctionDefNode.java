package AST;


import java.util.ArrayList;
import java.util.List;

public class FunctionDefNode extends Node {
    private IdentifierNode functionName;
    private List<IdentifierNode> parameters;
    private List<Node> body;
    private List<Node> decorators;

    public FunctionDefNode(int line, IdentifierNode functionName,
                           List<IdentifierNode> parameters,
                           List<Node> body,
                           List<Node> decorators) {
        super(line, Kind.FUNC_DEF, "FunctionDefNode");
        this.functionName = functionName;
        this.parameters = parameters;
        this.body = body;
        this.decorators = decorators;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + nodeName + " [line=" + line + "]");
        System.out.println(indent + "  Function Name:");
        functionName.print(indent + "    ");
        if(parameters != null && !parameters.isEmpty()) {
            System.out.println(indent + "  Parameters:");
            for(IdentifierNode param : parameters) {
                param.print(indent + "    ");
            }
        }
        if(decorators != null && !decorators.isEmpty()) {
            System.out.println(indent + "  Decorators:");
            for(Node dec : decorators) {
                dec.print(indent + "    ");
            }
        }
        if(body != null && !body.isEmpty()) {
            System.out.println(indent + "  Body:");
            for(Node stmt : body) {
                stmt.print(indent + "    ");
            }
        }
    }
    public IdentifierNode getFunctionName() { return functionName; }
    public List<IdentifierNode> getParameters() { return parameters; }
    public List<Node> getBody() { return body; }
    public List<Node> getDecorators() { return decorators; }

}
