package ast;

public class PositionDeclNode extends DeclarationNode {
    // تم حذف المتغير المحلي value لأنه أصبح مخزناً في الأب كـ propertyValue

    public PositionDeclNode(String value, int lineNumber) {
        // نمرر اسم الخاصية "position" والقيمة ورقم السطر للأب (super)
        super("position", value, lineNumber);
    }

    @Override
    public void print(int indent) {
        String prefix = " ".repeat(indent * 2);
        // نستخدم getPropertyValue() من الأب لضمان ظهور النتيجة بشكل منظم
        // النتيجة: Declaration: position: absolute (line: X)
        System.out.println(prefix + "Declaration: position: " + getPropertyValue() + " (line: " + lineNumber + ")");
    }
}