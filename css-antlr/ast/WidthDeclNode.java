package ast;

public class WidthDeclNode extends DeclarationNode {
    // تم حذف 'public String value' لأن القيمة أصبحت تُخزن في الأب (propertyValue)

    public WidthDeclNode(String value, int lineNumber) {
        // نرسل الاسم "width" والقيمة الفعلية "value" للأب
        super("width", value, lineNumber);
    }

    @Override
    public void print(int indent) {
        String prefix = " ".repeat(indent * 2);
        // نستخدم getPropertyValue() من الأب لضمان ظهور السطر بشكل:
        // Declaration: width: 100px (line: 5)
        System.out.println(prefix + "Declaration: width: " + getPropertyValue() + " (line: " + lineNumber + ")");
    }
}