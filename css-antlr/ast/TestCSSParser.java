package ast;

import antlr.CSSLexer;
import antlr.CSSParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.nio.file.*;

public class TestCSSParser {

    public static void main(String[] args) {
        try {
            // قراءة ملف CSS
            CharStream input = CharStreams.fromPath(
                    Paths.get("C:\\Users\\Raghad\\Desktop\\uni\\src\\recources\\css.txt")
            );

            // إنشاء Lexer
            CSSLexer lexer = new CSSLexer(input);

            // تحويل التوكنز إلى TokenStream
            CommonTokenStream tokens = new CommonTokenStream(lexer);

            // إنشاء Parser
            CSSParser parser = new CSSParser(tokens);

            // بدء التحليل من القاعدة الجذر
            ParseTree tree = parser.stylesheet();

            // طباعة شجرة البارسر
            System.out.println("===== PARSE TREE =====");
            System.out.println(tree.toStringTree(parser));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
