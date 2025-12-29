package ast;

public class StylesheetNode extends ASTNode {
    public StylesheetNode(int lineNumber) {
        super("Stylesheet", lineNumber);
    }

    public void addRuleSet(RuleSetNode ruleSet) {
        addChild(ruleSet);
    }
}
