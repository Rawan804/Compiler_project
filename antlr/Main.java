import antlr.HTMLLexer;
import antlr.HTMLParser;
import ast.ASTNode;
import visitor.HtmlVisitor;
import SymbolTable.HTMLJinjaSymbolTable;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String filePath = "C:\\Users\\Raghad\\Desktop\\untitled2\\src\\antlr\\html.txt";

        try {
            String htmlCode = readFile(filePath);
            HTMLLexer lexer = new HTMLLexer(CharStreams.fromString(htmlCode));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            HTMLParser parser = new HTMLParser(tokens);

            HTMLParser.HtmlContext parseTree = parser.html();

            HtmlVisitor visitor = new HtmlVisitor();
            ASTNode astRoot = visitor.visit(parseTree);
            HTMLJinjaSymbolTable symbolTable = visitor.getSymbolTable();

            if (astRoot != null) {
                System.out.println("\n=== Abstract Syntax Tree (AST) ===");
                astRoot.print(0);
            } else {
                System.out.println("❌ AST is null!");
            }

            System.out.println("\n✅ تم بناء الشجرة والجدول بنجاح.");
            manageSymbolTable(symbolTable);

        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String readFile(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)), "UTF-8");
    }

    private static void manageSymbolTable(HTMLJinjaSymbolTable table) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Symbol Table Manager ---");
            System.out.println("[1] Add  [2] Update  [3] Delete  [4] Print Table  [5] Exit");
            System.out.print("Choice: ");
            String choice = sc.nextLine();

            if (choice.equals("5")) break;

            switch (choice) {
                case "1":
                    System.out.print("Enter Name: "); String name = sc.nextLine();
                    System.out.print("Enter Type: "); String type = sc.nextLine();
                    // ⬇️ التعديل هنا: يجب طلب القيمة أيضاً
                    System.out.print("Enter Value: "); String value = sc.nextLine();
                    table.define(name, type, value);
                    System.out.println("✔ Added successfully.");
                    break;
                case "4":
                    table.printTable();
                    break;
                case "2":
                    // إذا أردتِ تفعيل التحديث
                    System.out.print("Name to Update: "); String uName = sc.nextLine();
                    System.out.print("New Type: "); String uType = sc.nextLine();
                    System.out.print("New Scope: "); String uScope = sc.nextLine();
                    System.out.print("New Value: "); String uValue = sc.nextLine();
                    table.update(uName, uType, uScope, uValue);
                    break;
                case "3":
                    // إذا أردتِ تفعيل الحذف
                    System.out.print("Name to Delete: "); String dName = sc.nextLine();
                    table.delete(dName);
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}