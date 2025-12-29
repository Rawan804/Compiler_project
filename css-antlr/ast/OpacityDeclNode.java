package ast;

public class OpacityDeclNode extends DeclarationNode {
    // تم حذف المتغير المحلي value لأنه أصبح مُخزناً في الأب كـ propertyValue

    public OpacityDeclNode(String value, int lineNumber) {
        // نمرر اسم الخاصية "opacity" والقيمة ورقم السطر للأب (super)
        super("opacity", value, lineNumber);
    }

    @Override
    public void print(int indent) {
        String prefix = " ".repeat(indent * 2);
        // نستخدم getPropertyValue() من الأب لضمان ظهور النتيجة بشكل منظم
        System.out.println(prefix + "Declaration: opacity: " + getPropertyValue() + " (line: " + lineNumber + ")");
    }
}