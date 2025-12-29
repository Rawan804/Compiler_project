package ast;

import antlr.CSSLexer;
import org.antlr.v4.runtime.*;
import java.nio.file.*;

public class TestCSSLexer {
    public static void main(String[] args) {
        try {
            CharStream input = CharStreams.fromPath(
                    Paths.get("C:\\Users\\Raghad\\Desktop\\uni\\src\\recources\\css.txt")
            );
            CSSLexer lexer = new CSSLexer(input);

            Token token;
            while ((token = lexer.nextToken()).getType() != Token.EOF) {
                System.out.println(
                        lexer.getVocabulary().getSymbolicName(token.getType())
                                + " : " + token.getText()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
