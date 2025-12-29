package ast;

import java.util.List;

public class MarginDeclNode extends DeclarationNode {
    // نحتفظ بالقائمة في حال احتجتِ مستقبلاً للوصول لكل قيمة على حدة
    public List<String> values;

    public MarginDeclNode(List<String> values, int lineNumber) {
        // ندمج عناصر القائمة في نص واحد مفصول بمسافات ليرسل للأب
        // مثال: [10px, 20px] تصبح "10px 20px"
        super("margin", String.join(" ", values), lineNumber);
        this.values = values;
    }

    @Override
    public void print(int indent) {
        String prefix = " ".repeat(indent * 2);
        // نستخدم getPropertyValue() التي تحتوي الآن على القيم مدمجة بجانب كلمة Declaration
        System.out.println(prefix + "Declaration: margin: " + getPropertyValue() + " (line: " + lineNumber + ")");
    }
}