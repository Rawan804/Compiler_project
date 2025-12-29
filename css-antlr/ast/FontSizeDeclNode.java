package ast;

public class FontSizeDeclNode extends DeclarationNode {
    // حذفنا المتغير المحلي value لأنه أصبح مخزناً في الأب كـ propertyValue

    public FontSizeDeclNode(String value, int lineNumber) {
        // نرسل الاسم "font-size" والقيمة للأب
        super("font-size", value, lineNumber);
    }

    @Override
    public void print(int indent) {
        String prefix = " ".repeat(indent * 2);
        // نستخدم getPropertyValue() من الأب لضمان ظهور النتيجة: Declaration: font-size: value
        System.out.println(prefix + "Declaration: font-size: " + getPropertyValue() + " (line: " + lineNumber + ")");
    }
}