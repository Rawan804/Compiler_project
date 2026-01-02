package AST;

import java.util.List;

public class ImportNode extends Node {

    private IdentifierNode module;
    private List<IdentifierNode> names;
    private boolean isFromImport;

    public ImportNode(int line,
                      IdentifierNode module,
                      List<IdentifierNode> names,

                      boolean isFromImport) {

        super(line, Kind.IMPORT, "ImportNode");
        this.module = module;
        this.names = names;

        this.isFromImport = isFromImport;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + nodeName + " [line=" + line + "]");

        if (isFromImport) {
            System.out.println(indent + "  From Module:");
            if (module != null) module.print(indent + "    ");

            System.out.println(indent + "  Import Names:");
            for (IdentifierNode name : names) {
                name.print(indent + "    ");
            }
        }
        else {
            if (module != null) {
                System.out.println(indent + "  Module:");
                module.print(indent + "    ");
            }
            if (names != null ) {
                System.out.println(indent + "  Import Names:");
                for (IdentifierNode name : names) {
                    name.print(indent + "    ");
                }
            }
        }
    }


    // Getters (اختياري)
    public IdentifierNode getModuleName() { return module; }
    public List<IdentifierNode> getNames() { return names; }
    public boolean isFromImport() { return isFromImport; }
}
