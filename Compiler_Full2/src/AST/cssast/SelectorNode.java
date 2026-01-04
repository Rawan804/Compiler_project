package AST.cssast;

public class SelectorNode extends ASTNode {
    public SelectorNode(int lineNumber) {
        super("Selector", lineNumber);
    }

    public void setSimpleSelector(SimpleSelectorNode simpleSelector) {
        addChild(simpleSelector);
    }
}
