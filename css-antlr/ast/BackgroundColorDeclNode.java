package ast;

public class BackgroundColorDeclNode extends DeclarationNode {
    // تم حذف تعريف المتغير value المحلي لأنه أصبح مخزناً في الأب كـ propertyValue

    public BackgroundColorDeclNode(String value, int lineNumber) {
        // نرسل اسم الخاصية "background-color" والقيمة الفعليه للأب
        super("background-color", value, lineNumber);
    }

    @Override
    public void print(int indent) {
        String prefix = " ".repeat(indent * 2);
        // نستخدم getPropertyValue() من الأب لضمان ظهور النتيجة: Declaration: background-color: value
        System.out.println(prefix + "Declaration: background-color: " + getPropertyValue() + " (line: " + lineNumber + ")");
    }
}