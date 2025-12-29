package ast;

public class SimpleSelectorNode extends ASTNode {
    public SimpleSelectorNode(int lineNumber) {
        super("SimpleSelector", lineNumber);
    }

    public void setElementName(ElementNameNode elementName) {
        addChild(elementName);
    }

    public void addModifier(SelectorModifierNode modifier) {
        addChild(modifier);
    }
}
