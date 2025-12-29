package ast;

public class FontWeightDeclNode extends DeclarationNode {
    // تم حذف المتغير المحلي value لأنه مخزن الآن في الأب

    public FontWeightDeclNode(String value, int lineNumber) {
        // نرسل اسم الخاصية "font-weight" والقيمة الفعليه للأب (super)
        super("font-weight", value, lineNumber);
    }

    @Override
    public void print(int indent) {
        String prefix = " ".repeat(indent * 2);
        // نستخدم getPropertyValue() من الأب لضمان ظهور النتيجة: Declaration: font-weight: value
        System.out.println(prefix + "Declaration: font-weight: " + getPropertyValue() + " (line: " + lineNumber + ")");
    }
}