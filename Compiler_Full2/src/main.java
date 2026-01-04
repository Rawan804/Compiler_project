import AST.cssast.StylesheetNode;
import AST.hast.ASTNode;
import AST.past.Node;

import LexerandParser.cssantlr.CSSLexer;
import LexerandParser.cssantlr.CSSParser;
import LexerandParser.hantlr.HTMLLexer;
import LexerandParser.hantlr.HTMLParser;
import LexerandParser.pantlr.python_lexer;
import LexerandParser.pantlr.python_parser;
import SymbolTable.cssSymbolTable.CSSSymbolTable;
import SymbolTable.hSymbolTable.HTMLJinjaSymbolTable;
import SymbolTable.psymbol_table.SymbolTable;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import visitor.cssvisitor.CSSCompilerVisitor;
import visitor.hvisitor.HtmlVisitor;
import visitor.pvisitor.PythonASTVisitor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

class Main {


    private static final String HTML_FILE_1 = "src/samples/html1.txt";
    private static final String HTML_FILE_2 = "src/samples/html2.txt";
    private static final String HTML_FILE_3 = "src/samples/html3.txt";
    private static final String HTML_FILE_4 = "src/samples/html4.txt";
    private static final String CSS_FILE = "src/samples/css.txt";
    private static final String PYTHON_FILE = "src/samples/python_test.txt";
    private static HTMLJinjaSymbolTable htmlSymbolTable = null;
    private static CSSSymbolTable cssSymbolTable = null;
    private static SymbolTable pythonSymbolTable = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Main Menu =====");
            System.out.println("Choose compiler:");
            System.out.println("[1] HTML/Jinja Compiler - Compile all HTML files");
            System.out.println("[2] CSS Compiler");
            System.out.println("[3] Python Compiler");
            System.out.println("[4] Exit");
            System.out.print("Choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    runHTMLCompiler();
                    break;
                case "2":
                    runCSSCompiler();
                    break;
                case "3":
                    runPythonCompiler();
                    break;

                case "4":
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice!\n");
            }
        }
    }

    private static void runHTMLCompiler() {
        System.out.println("\n HTML/Jinja Compiler");
        System.out.println("=====================\n");

        try {
            System.out.println(" Compiling ALL HTML files automatically...\n");

            htmlSymbolTable = null;

            compileHTMLFile(HTML_FILE_1, "HTML File 1");
            compileHTMLFile(HTML_FILE_2, "HTML File 2");
            compileHTMLFile(HTML_FILE_3, "HTML File 3");
            compileHTMLFile(HTML_FILE_4, "HTML File 4");

            System.out.println("\n All HTML files compiled successfully!");


        } catch (Exception e) {
            System.err.println(" HTML Compilation Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void compileHTMLFile(String filePath, String fileName) throws Exception {
        System.out.println("\n Compiling: " + fileName + " (" + filePath + ")");
        System.out.println("========================================");

        try {
            String htmlCode = readFile(filePath);

            HTMLLexer lexer = new HTMLLexer(CharStreams.fromString(htmlCode));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            HTMLParser parser = new HTMLParser(tokens);

            HTMLParser.HtmlContext parseTree = parser.html();

            HtmlVisitor visitor = new HtmlVisitor();
            ASTNode astRoot = visitor.visit(parseTree);


            if (htmlSymbolTable == null) {
                htmlSymbolTable = visitor.getSymbolTable();
            } else {
                HTMLJinjaSymbolTable currentTable = visitor.getSymbolTable();
            }

            System.out.println("\n=== Results ===");

            if (astRoot != null) {
                System.out.println("\n1. Abstract Syntax Tree (AST):");
                astRoot.print(0);
            } else {
                System.out.println(" AST is null!");
            }

            System.out.println("\n2. Symbol Table:");
            if (htmlSymbolTable != null) {
                htmlSymbolTable.printTable();
            } else {
                System.out.println(" No symbol table available");
            }

            System.out.println("\n Successfully compiled: " + fileName);
        } catch (IOException e) {
            System.err.println("File not found: " + filePath);
        }
    }

    private static void runCSSCompiler() {
        System.out.println("\n CSS Compiler");
        System.out.println("===============\n");

        try {
            System.out.println(" Compiling CSS file automatically...\n");

            compileCSSFile(CSS_FILE, "CSS File");

            System.out.println("\n CSS file compiled successfully!");


        } catch (Exception e) {
            System.err.println(" CSS Compilation Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void compileCSSFile(String filePath, String fileName) throws Exception {
        System.out.println("\n Compiling: " + fileName + " (" + filePath + ")");
        System.out.println("========================================");

        try {
            CharStream input = CharStreams.fromPath(Paths.get(filePath));

            CSSLexer lexer = new CSSLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            CSSParser parser = new CSSParser(tokens);

            ParseTree tree = parser.stylesheet();

            CSSCompilerVisitor visitor = new CSSCompilerVisitor();
            Object astResult = visitor.visit(tree);
            StylesheetNode ast = null;

            if (astResult instanceof StylesheetNode) {
                ast = (StylesheetNode) astResult;
            }

            try {
                java.lang.reflect.Method method = visitor.getClass().getMethod("getSymbolTable");
                cssSymbolTable = (CSSSymbolTable) method.invoke(visitor);
            } catch (Exception e) {
                System.out.println("CSS symbol table access failed");
            }

            System.out.println("\n=== CSS Results ===");

            if (ast != null) {
                System.out.println("\n1. AST Tree Structure:");
                ast.print(0);
            } else {
                System.out.println(" AST is null!");
            }

            System.out.println("\n2. Symbol Table:");
            if (cssSymbolTable != null) {
                cssSymbolTable.printTable();
            } else {
                System.out.println("No CSS symbol table available");
            }

            System.out.println("\nSuccessfully compiled: " + fileName);
        } catch (IOException e) {
            System.err.println(" File not found: " + filePath);
        }
    }

    private static void runPythonCompiler() {
        System.out.println("\n Python Compiler");
        System.out.println("==================\n");

        try {
            System.out.println(" Compiling Python file automatically...\n");

            compilePythonFile(PYTHON_FILE, "Python File");

            System.out.println("\n Python file compiled successfully!");



        } catch (Exception e) {
            System.err.println("Python Compilation Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void compilePythonFile(String filePath, String fileName) throws Exception {
        System.out.println("\n Compiling: " + fileName + " (" + filePath + ")");
        System.out.println("========================================");

        try {
            InputStream is = new FileInputStream(filePath);
            CharStream input = CharStreams.fromStream(is);
            python_lexer lexer = new python_lexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            python_parser parser = new python_parser(tokens);

            ParseTree tree = parser.prog();
            PythonASTVisitor visitor = new PythonASTVisitor();
            Node ast = visitor.visit(tree);

            pythonSymbolTable = visitor.getSymbolTable();

            System.out.println("\n=== Python Results ===");

            if (ast != null) {
                System.out.println("\n1. Abstract Syntax Tree (AST):");
                ast.print("");
            } else {
                System.out.println(" AST is null!");
            }

            System.out.println("\n2. Symbol Table:");
            if (pythonSymbolTable != null) {
                pythonSymbolTable.printTable();
            } else {
                System.out.println(" No symbol table available");
            }

            System.out.println("\n Successfully compiled: " + fileName);
        } catch (IOException e) {
            System.err.println(" File not found: " + filePath);
        }
    }





    private static void manageCSSSymbolTable(CSSSymbolTable table) {
        Scanner scanner = new Scanner(System.in);

    }


    private static String readFile(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)), "UTF-8");
    }
}