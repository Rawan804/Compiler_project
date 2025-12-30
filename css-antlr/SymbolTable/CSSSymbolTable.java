package SymbolTable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CSSSymbolTable {
    private final Map<String, List<String>> table = new LinkedHashMap<>();

    public void define(String selector, String property, String value) {
        table.computeIfAbsent(selector, k -> new ArrayList<>())
                .add(property + ": " + value);
    }

    public boolean update(String selector, String property, String newValue) {
        if (table.containsKey(selector)) {
            List<String> decls = table.get(selector);
            for (int i = 0; i < decls.size(); i++) {
                if (decls.get(i).startsWith(property + ":")) {
                    decls.set(i, property + ": " + newValue);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean deleteSelector(String selector) {
        return table.remove(selector) != null;
    }

    public boolean deleteProperty(String selector, String property) {
        if (table.containsKey(selector)) {
            return table.get(selector).removeIf(p -> p.startsWith(property + ":"));
        }
        return false;
    }

    public List<String> getProperties(String selector) {
        return table.get(selector);
    }

    public void printTable() {
        if (table.isEmpty()) {
            System.out.println("\n===== CSS SYMBOL TABLE IS EMPTY =====");
            return;
        }
        System.out.println("\n===== CSS SYMBOL TABLE (Visitor Mode) =====");
        System.out.printf("%-20s | %-40s\n", "Selector", "Declarations (Property: Value)");
        System.out.println("------------------------------------------------------------");
        table.forEach((selector, decls) -> {
            if (decls.isEmpty()) {
                System.out.printf("%-20s | %-40s\n", selector, "(No properties)");
            } else {
                System.out.printf("%-20s | %-40s\n", selector, decls.get(0));
                for (int i = 1; i < decls.size(); i++) {
                    System.out.printf("%-20s | %-40s\n", "", decls.get(i));
                }
            }
            System.out.println("------------------------------------------------------------");
        });
    }
}