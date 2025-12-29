package ast;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import antlr.CSSLexer;
import antlr.CSSParser;
import ast.CSSCompilerVisitor;
import test.CSSSymbolTable;

public class TestASTVisitor {
    public static void main(String[] args) {
        try {
            String cssFilePath = "C:\\Users\\Raghad\\Desktop\\uni\\src\\recources\\css.txt";
            CharStream input = CharStreams.fromPath(java.nio.file.Paths.get(cssFilePath));

            CSSLexer lexer = new CSSLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            CSSParser parser = new CSSParser(tokens);

            ParseTree tree = parser.stylesheet();
  CSSCompilerVisitor visitor = new CSSCompilerVisitor();

            StylesheetNode ast = (StylesheetNode) visitor.visit(tree);

            System.out.println("===== AST Tree Structure =====");
            ast.print(0);

        } catch (Exception e) {
            System.err.println("Error during execution:");
            e.printStackTrace();
        }
    }
}