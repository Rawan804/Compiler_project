package ast;

public class DeclarationNode extends ASTNode {
    private String propertyName;
    private String propertyValue; // إضافة متغير لتخزين القيمة

    // تعديل الكونستركتور ليستقبل الاسم والقيمة
    public DeclarationNode(String propertyName, String propertyValue, int lineNumber) {
        super("Declaration", lineNumber);
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    @Override
    public void print(int indent) {
        String prefix = " ".repeat(indent * 2);
        // التعديل هنا ليطبع: Declaration: propertyName: propertyValue
        System.out.println(prefix + "Declaration: " + propertyName + ": " + propertyValue + " (line: " + lineNumber + ")");

        // طباعة الأبناء إذا كان هناك تفاصيل إضافية (مثل وحدات القياس)
        for (ASTNode child : children) {
            child.print(indent + 1);
        }
    }
}