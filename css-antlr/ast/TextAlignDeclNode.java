package ast;

public class TextAlignDeclNode extends DeclarationNode {
    // تم حذف المتغير المحلي value لأنه أصبح مخزناً في الأب كـ propertyValue

    public TextAlignDeclNode(String value, int lineNumber) {
        // نمرر اسم الخاصية "text-align" والقيمة ورقم السطر للأب (super)
        super("text-align", value, lineNumber);
    }

    @Override
    public void print(int indent) {
        String prefix = " ".repeat(indent * 2);
        // نستخدم getPropertyValue() من الأب لضمان التناسق
        // النتيجة: Declaration: text-align: center (line: X)
        System.out.println(prefix + "Declaration: text-align: " + getPropertyValue() + " (line: " + lineNumber + ")");
    }
}