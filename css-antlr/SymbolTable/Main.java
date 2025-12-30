package SymbolTable;

import ast.CSSCompilerVisitor;
import ast.StylesheetNode;
import antlr.CSSLexer;
import antlr.CSSParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.nio.file.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            // 1. تحديد مسار الملف وقراءته مرة واحدة للعمليتين
            String filePath = "C:\\Users\\Raghad\\Desktop\\uni\\src\\recources\\css.txt";
            CharStream input = CharStreams.fromPath(Paths.get(filePath));

            // 2. التحليل (Lexer & Parser)
            CSSLexer lexer = new CSSLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            CSSParser parser = new CSSParser(tokens);

            // 3. بناء شجرة التحليل (Parse Tree)
            ParseTree tree = parser.stylesheet();

            // 4. استخدام الـ Visitor لبناء الـ AST واستخراج جدول الرموز
            CSSCompilerVisitor visitor = new CSSCompilerVisitor();

            // تحويل النتيجة إلى StylesheetNode لطباعة الشجرة
            StylesheetNode ast = (StylesheetNode) visitor.visit(tree);

            // الحصول على جدول الرموز من الـ visitor
            CSSSymbolTable symbolTable = visitor.getSymbolTable();

            // --- أولاً: طباعة هيكل الـ AST (من كود TestASTVisitor) ---
            System.out.println("===== AST Tree Structure =====");
            if (ast != null) {
                ast.print(0);
            } else {
                System.out.println("❌ AST is null!");
            }

            // --- ثانياً: طباعة جدول الرموز الأولي (من كود TestCSSSymbol) ---
            System.out.println("\n--- Initial Symbol Table from File ---");
            symbolTable.printTable();

            // --- ثالثاً: المدير التفاعلي (الـ Menu) ---
            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println("\n--- CSS Table Manager ---");
                System.out.println("[1] Add Property    [2] Update Value   [3] Delete Property");
                System.out.println("[4] Delete Selector [5] Print Table    [6] Exit");
                System.out.print("Your choice: ");

                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        System.out.print("Selector (e.g., .btn): "); String s = scanner.nextLine();
                        System.out.print("Property (e.g., color): "); String p = scanner.nextLine();
                        System.out.print("Value (e.g., blue): "); String v = scanner.nextLine();
                        symbolTable.define(s, p, v);
                        System.out.println("✅ Added successfully.");
                        symbolTable.printTable();
                        break;

                    case "2":
                        System.out.print("Selector: "); String us = scanner.nextLine();
                        System.out.print("Property to update: "); String up = scanner.nextLine();
                        System.out.print("New Value: "); String uv = scanner.nextLine();
                        if (symbolTable.update(us, up, uv)) {
                            System.out.println("🔄 Updated successfully.");
                            symbolTable.printTable();
                        } else {
                            System.out.println("❌ Error: Selector or Property not found!");
                        }
                        break;

                    case "3":
                        System.out.print("Selector: "); String ds = scanner.nextLine();
                        System.out.print("Property to delete: "); String dp = scanner.nextLine();
                        if (symbolTable.deleteProperty(ds, dp)) {
                            System.out.println("🗑️ Property deleted.");
                            symbolTable.printTable();
                        } else {
                            System.out.println("❌ Error: Not found!");
                        }
                        break;

                    case "4":
                        System.out.print("Selector to delete: "); String dsel = scanner.nextLine();
                        if (symbolTable.deleteSelector(dsel)) {
                            System.out.println("🗑️ Selector deleted.");
                            symbolTable.printTable();
                        } else {
                            System.out.println("❌ Error: Selector not found!");
                        }
                        break;

                    case "5":
                        symbolTable.printTable();
                        break;

                    case "6":
                        running = false;
                        System.out.println("Exiting CSS Manager...");
                        break;

                    default:
                        System.out.println("⚠️ Invalid option!");
                }
            }
            scanner.close();

        } catch (NoSuchFileException e) {
            System.err.println("Error: Could not find css.txt at path: " + e.getFile());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred:");
            e.printStackTrace();
        }
    }
}