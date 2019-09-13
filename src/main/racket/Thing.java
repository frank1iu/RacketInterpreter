package racket;

import java.util.ArrayList;

public class Thing {
    private Object value;

    public ArrayList<Thing> getChildren() {
        return children;
    }

    private ArrayList<Thing> children;

    public Object getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }

    private Type type;
    public Thing(Object value, ArrayList<Thing> children) {
        this.children = children;
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
    public void addChild(Thing child) {
        this.children.add(child);
    }
    public void addChildren(ArrayList<Thing> children) {
        this.children.addAll(children);
    }
}
