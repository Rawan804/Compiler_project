package ast;

public class BorderDeclNode extends DeclarationNode {
    // نحتفظ بالمتغيرات إذا كنتِ تحتاجينها مستقبلاً في تحليل منطقي
    public String size;
    public String style;
    public String color;

    public BorderDeclNode(String size, String style, String color, int lineNumber) {
        // ندمج القيم الثلاث في نص واحد ونرسلها للأب كقيمة للـ border
        super("border", (size + " " + style + " " + color).trim(), lineNumber);
        this.size = size;
        this.style = style;
        this.color = color;
    }

    @Override
    public void print(int indent) {
        String prefix = " ".repeat(indent * 2);
        // نستخدم getPropertyValue() التي تحتوي على القيمة المدمجة
        System.out.println(prefix + "Declaration: border: " + getPropertyValue() + " (line: " + lineNumber + ")");
    }
}