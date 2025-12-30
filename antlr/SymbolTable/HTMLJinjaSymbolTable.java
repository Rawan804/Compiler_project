package SymbolTable;

import java.util.*;

// كلاس داخلي لتخزين معلومات الرمز
class SymbolInfo {
    String type;
    String value;

    public SymbolInfo(String type, String value) {
        this.type = type;
        this.value = value;
    }
}

public class HTMLJinjaSymbolTable {
    private Map<String, SymbolInfo> symbols = new LinkedHashMap<>();
    private Stack<String> scopeStack = new Stack<>();

    public HTMLJinjaSymbolTable() {
        scopeStack.push("global");
    }

    public void enterScope(String scopeName) {
        scopeStack.push(scopeName);
    }

    public void exitScope() {
        if (scopeStack.size() > 1) scopeStack.pop();
    }

    public String getCurrentScope() {
        return scopeStack.peek();
    }

    // الدالة الأساسية لإضافة رمز مع قيمته
    public void define(String name, String type, String value) {
        String key = name + "@" + getCurrentScope();
        symbols.put(key, new SymbolInfo(type, value));
    }

    // دالة للإضافة مع تحديد النطاق يدوياً (تستخدم في الـ Update)
    public void define(String name, String type, String scope, String value) {
        String key = name + "@" + scope;
        symbols.put(key, new SymbolInfo(type, value));
    }

    // دالة قديمة للتوافق في حال لم يتم إرسال قيمة
    public void define(String name, String type) {
        define(name, type, "N/A");
    }

    public String lookup(String name) {
        for (int i = scopeStack.size() - 1; i >= 0; i--) {
            String key = name + "@" + scopeStack.get(i);
            if (symbols.containsKey(key)) return symbols.get(key).type;
        }
        return null;
    }

    public boolean update(String name, String newType, String newScope, String newValue) {
        for (String key : symbols.keySet()) {
            if (key.startsWith(name + "@")) {
                symbols.remove(key);
                define(name, newType, newScope, newValue);
                return true;
            }
        }
        return false;
    }

    public boolean delete(String name) {
        String keyToDelete = null;
        for (String key : symbols.keySet()) {
            if (key.startsWith(name + "@")) {
                keyToDelete = key;
                break;
            }
        }
        if (keyToDelete != null) {
            symbols.remove(keyToDelete);
            return true;
        }
        return false;
    }

    public void printTable() {
        System.out.println("\n--- SYMBOL TABLE ---");
        // تم توسيع الجدول ليشمل عمود Value
        System.out.printf("%-20s | %-20s | %-15s | %-20s\n", "Name", "Type", "Scope", "Value");
        System.out.println("-------------------------------------------------------------------------------------");
        for (Map.Entry<String, SymbolInfo> entry : symbols.entrySet()) {
            String[] parts = entry.getKey().split("@");
            SymbolInfo info = entry.getValue();
            System.out.printf("%-20s | %-20s | %-15s | %-20s\n",
                    parts[0], info.type, parts[1], info.value);
        }
    }
}