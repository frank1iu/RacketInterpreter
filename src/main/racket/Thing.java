package racket;

import java.util.ArrayList;
import java.util.Objects;

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

    public Thing(String value, ArrayList<Thing> children) {
        if (children == null) {
            this.children = new ArrayList<Thing>();
        } else {
            this.children = children;
        }
        this.setValue(value);
    }

    public void setValue(String value) {
        if (value.startsWith("\"")) {
            if (!value.endsWith("\"") || value.length() == 1) {
                throw new RacketSyntaxError("expected a closing \"");
            } else {
                this.type = Type.STRING;
                this.value = value.substring(1, value.length() - 1);
            }
        } else if (value.equals("true") || value.equals("false")) {
            this.value = Boolean.valueOf(value);
            this.type = Type.BOOLEAN;
        } else if (value.equals("(void)")) {
            this.value = value;
            this.type = Type.VOID;
        } else {
            setValue2(value);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Thing thing = (Thing) o;
        return Objects.equals(value, thing.value)
                &&
                Objects.equals(children, thing.children)
                &&
                type == thing.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, children, type);
    }

    private void setValue2(String value) {
        try {
            this.value = Integer.parseInt(value);
            this.type = Type.INTEGER;
        } catch (NumberFormatException e) {
            this.value = value;
            this.type = Type.IDENTIFIER;
        }
    }

    // REQUIRES: none
    // MODIFIES: none
    // EFFECTS: returns a string representation of this
    public String toString() {
        if (this.children.size() == 0) {
            if (this.type == Type.STRING) {
                return "\"" + this.value.toString() + "\"";
            } else {
                return this.value.toString();
            }
        } else {
            String ret = "(" + this.value.toString();
            for (Thing t: this.children) {
                ret += " " + t.toString();
            }
            return ret + ")";
        }
    }

    // REQUIRES: none
    // MODIFIES: this
    // EFFECTS: adds a child to this
    public void addChild(Thing child) {
        this.children.add(child);
    }

    // REQUIRES: none
    // MODIFIES: this
    // EFFECTS: adds children to this
    public void addChildren(ArrayList<Thing> children) {
        this.children.addAll(children);
    }

    // REQUIRES: none
    // MODIFIES: none
    // EFFECTS: returns whether this is equal to param thing
    /*
    public boolean equals(Thing thing) {
        if (!this.value.equals(thing.getValue()) || this.type != thing.getType()) {
            return false;
        } else if (this.children.size() == 0 && thing.getChildren().size() == 0) {
            return true;
        } else if (this.children.size() != thing.getChildren().size()) {
            return false;
        } else {
            for (int i = 0; i < this.children.size(); i++) {
                if (this.children.get(i).equals(thing.getChildren().get(i))) {
                    continue;
                }
                return false;
            }
            return true;
        }
    }*/
}
