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
    public Thing(String value, ArrayList<Thing> children) {
        if (children == null) {
            this.children = new ArrayList<Thing>();
        } else {
            this.children = children;
        }
        if (value.equals("true") || value.equals("false")) {
            this.value = new Boolean(value.toString());
            this.type = Type.BOOLEAN;
        } else if (value.equals("(void)")) {
            this.value = value;
            this.type = Type.VOID;
        } else {
            try {
                this.value = new Integer(Integer.parseInt(value.toString()));
                this.type = Type.INTEGER;
            } catch (NumberFormatException e) {
                this.value = value;
                this.type = Type.IDENTIFIER;
            }
        }
    }
    // REQUIRES: none
    // MODIFIES: none
    // EFFECTS: returns a string representation of this
    public String toString() {
        if (this.children.size() == 0) {
            return this.value.toString();
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
    public boolean equals(Thing thing) {
        if (!this.value.equals(thing.getValue()) || this.type != thing.getType()) {
            return false;
        } else if (this.children.size() == 0 && thing.getChildren().size() == 0) {
            return true;
        } else if (this.children.size() != thing.getChildren().size()){
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
    }
}
