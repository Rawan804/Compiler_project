package AST;
public class DotNode extends  Node{
    private Node object;
    private   Node attribute;

    public DotNode(int line, Node object,Node attribute) {
        super(line, Kind.DOT, "Dot Node");
        this.object = object;
        this.attribute = attribute;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + nodeName + " [line=" + line + "]");
        object.print(indent + "  ");
        attribute.print(indent + "  ");
    }

    public Node getObject() {
        return object;
    }
}
 ;
