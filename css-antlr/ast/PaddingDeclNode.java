package ast;

import java.util.List;

public class PaddingDeclNode extends DeclarationNode {
    // نحتفظ بالقائمة كمتغير محلي في حال أردتِ استخدامه لاحقاً للعمليات الحسابية
    public List<String> values;

    public PaddingDeclNode(List<String> values, int lineNumber) {
        // ندمج عناصر القائمة في نص واحد مفصول بمسافات ليرسل للأب
        // مثال: [15px, 10px] تصبح "15px 10px"
        super("padding", String.join(" ", values), lineNumber);
        this.values = values;
    }

    @Override
    public void print(int indent) {
        String prefix = " ".repeat(indent * 2);
        // نستخدم getPropertyValue() من الأب لضمان ظهور النتيجة بشكل متناسق:
        // Declaration: padding: 15px 10px (line: X)
        System.out.println(prefix + "Declaration: padding: " + getPropertyValue() + " (line: " + lineNumber + ")");
    }
}