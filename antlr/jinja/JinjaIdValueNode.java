package ast.jinja;

public class JinjaIdValueNode extends JinjaValueNode {
    public JinjaIdValueNode(String id, int line, int column) {
        super(id, line, column);
    }

    @Override
    public void print(int indent) {
        for (int i = 0; i < indent; i++) System.out.print("  ");
        System.out.println("JINJA_ID: " + getValue() + " [" + line + ":" + column + "]");
    }
}