package ast;

public class EntityNode extends ASTNode {
    private final String entity;

    public EntityNode(String entity, int line, int column) {
        super("ENTITY", line, column);
        this.entity = entity;
    }

    public String getEntity() { return entity; }

    @Override
    public void print(int indent) {
        for (int i = 0; i < indent; i++) System.out.print("  ");
        System.out.println("ENTITY: &" + entity + "; [" + line + ":" + column + "]");
    }
}