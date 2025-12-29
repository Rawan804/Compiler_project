package ast;

public class HeightDeclNode extends DeclarationNode {
    // تم حذف المتغير المحلي value لأنه مخزن في الأب

    public HeightDeclNode(String value, int lineNumber) {
        // نمرر اسم الخاصية "height" والقيمة للأب
        super("height", value, lineNumber);
    }

    @Override
    public void print(int indent) {
        String prefix = " ".repeat(indent * 2);
        // استخدام getPropertyValue() لضمان ظهور النتيجة: Declaration: height: value
        System.out.println(prefix + "Declaration: height: " + getPropertyValue() + " (line: " + lineNumber + ")");
    }
}