package ast;

public class DisplayDeclNode extends DeclarationNode {
    // لم نعد بحاجة لتعريف متغير value هنا لأنه مخزن في الأب (propertyValue)

    public DisplayDeclNode(String value, int lineNumber) {
        // نرسل الاسم "display" والقيمة الفعليه للجسم (super)
        super("display", value, lineNumber);
    }

    @Override
    public void print(int indent) {
        String prefix = " ".repeat(indent * 2);
        // نستخدم getPropertyValue() من الأب لضمان التناسق مع بقية الشجرة
        System.out.println(prefix + "Declaration: display: " + getPropertyValue() + " (line: " + lineNumber + ")");
    }
}