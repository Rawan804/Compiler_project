package symbol_table;

import java.util.*;

public class SymbolTable {

    private final List<Map<String, SymbolRow>> scopes = new ArrayList<>();
    private final List<Map<String, SymbolRow>> allScopes = new ArrayList<>();

    public SymbolTable() {
        allocate(); // global scope
    }

    public void allocate() {
        Map<String, SymbolRow> newScope = new LinkedHashMap<>();
        scopes.add(newScope);
        allScopes.add(newScope);
    }

    public void free() {
        if (!scopes.isEmpty())
            scopes.remove(scopes.size() - 1);
    }

    public SymbolRow insert(String name) {
        SymbolRow row = new SymbolRow(name);
        Map<String, SymbolRow> currentScope = scopes.get(scopes.size() - 1);
        currentScope.put(name, row);
        return row;
    }

    public SymbolRow lookup(String name) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            Map<String, SymbolRow> scope = scopes.get(i);
            if (scope.containsKey(name))
                return scope.get(name);
        }
        return null;
    }

    public void printTable() {
        System.out.println("===== SYMBOL TABLE =====");
        for (int i = 0; i < allScopes.size(); i++) {
            Map<String, SymbolRow> scope = allScopes.get(i);
            System.out.println("Scope #" + i + ":");
            if (scope.isEmpty()) {
                System.out.println("  <empty>");
            } else {
                System.out.printf("  %-15s | %-10s | %-4s | %s\n", "Name", "Type", "Line", "Attributes");
                System.out.println("  ----------------------------------------------------");
                for (SymbolRow row : scope.values()) {
                    System.out.printf("  %-15s | %-10s | %-4d | %s\n",
                            row.getName(), row.getType(), row.getLine(), row.getAttributes());
                }
            }
            System.out.println();
        }
        System.out.println("========================");
    }


}
