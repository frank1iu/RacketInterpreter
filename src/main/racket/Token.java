package racket;

public class Token {
    private Object value;

    public Object getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }

    private Type type;
    public Token(Object value) {
        if (value.equals("true") || value.equals("false")) {
            this.value = new Boolean(value.toString());
            this.type = Type.PRIMITIVE;
        } else {
            try {
                this.value = new Integer(Integer.parseInt(value.toString()));
                this.type = Type.PRIMITIVE;
            } catch (NumberFormatException e) {
                this.value = value;
                this.type = Type.IDENTIFIER;
            }
        }
    }
}
