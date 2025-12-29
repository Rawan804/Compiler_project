package ast;

public class RuleSetNode extends ASTNode {
    public RuleSetNode(int lineNumber) {
        super("RuleSet", lineNumber);
    }

    public void setSelectorGroup(SelectorGroupNode selectorGroup) {
        addChild(selectorGroup);
    }

    public void addDeclaration(DeclarationNode declaration) {
        addChild(declaration);
    }
}
