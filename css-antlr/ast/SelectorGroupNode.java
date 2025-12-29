package ast;

public class SelectorGroupNode extends ASTNode {
    public SelectorGroupNode(int lineNumber) {
        super("SelectorGroup", lineNumber);
    }

    public void addSelector(SelectorNode selector) {
        addChild(selector);
    }
}
