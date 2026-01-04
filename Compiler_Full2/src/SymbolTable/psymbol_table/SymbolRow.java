package SymbolTable.psymbol_table;

public class SymbolRow {
    private final String name;
    private String type;
    private int line;

    public SymbolRow(String name) {
        this.name = name;
    }
    public String getName() { return name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public int getLine() { return line; }
    public void setLine(int line) { this.line = line; }

    @Override
    public String toString() {
        return String.format("%s (type=%s, line=%d, attrs=%s)", name, type, line);
    }
}
