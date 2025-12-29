package AST;

import java.util.List;

public class CollectionNode extends Node {
    private CollectionType type;
    private List<Node> elements;
    private List<Node> keys;
    private List<Node> values;
    public CollectionNode(int line, CollectionType type, List<Node> elements) {
        super(line, Kind.COLLECTION, "CollectionNode");
        this.type = type;
        this.elements = elements;
    }
    public CollectionNode(int line, List<Node> keys, List<Node> values) {
        super(line, Kind.COLLECTION, "CollectionNode");
        this.type = CollectionType.DICT;
        this.keys = keys;
        this.values = values;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + nodeName +
                " [type=" + type + ", line=" + line + "]");

        if (type == CollectionType.DICT) {
            System.out.println("Arguments for dict");
            for (int i = 0; i < keys.size(); i++) {

                keys.get(i).print(indent + "  ");
                values.get(i).print(indent + "  ");
            }
        } else {
            for (Node e : elements) {
                e.print(indent + "  ");
            }
        }
    }
    public List<Node> getElements() {
        return elements;
    }
}

