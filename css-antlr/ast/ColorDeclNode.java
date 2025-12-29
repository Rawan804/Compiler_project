package ast;

public class ColorDeclNode extends DeclarationNode {
    // لم نعد بحاجة لتعريف colorValue هنا لأنها ستُخزن في propertyValue الموجودة في الأب

    public ColorDeclNode(String colorValue, int lineNumber) {
        // نرسل اسم الخاصية الثابت "color" والقيمة المتغيرة ورقم السطر للأب
        super("color", colorValue, lineNumber);
    }

    @Override
    public void print(int indent) {
        String prefix = " ".repeat(indent * 2);
        // نستخدم getPropertyValue() لجلب القيمة من الأب لضمان التناسق
        System.out.println(prefix + "Declaration: color: " + getPropertyValue() + " (line: " + lineNumber + ")");
    }
}