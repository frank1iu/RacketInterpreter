package racket;

import java.util.HashMap;

public class RacketContext {
    private HashMap<String, Thing> context;
    private RacketContext parent;

    public RacketContext getParent() {
        return parent;
    }

    // REQUIRES: none
    // MODIFIES: none
    // EFFECTS: puts a thing and a key into the context

    public void put(String key, Thing item) {
        this.context.put(key, item);
    }

    // REQUIRES: none
    // MODIFIES: none
    // EFFECTS: gets the thing associated with key

    public Thing get(String key) {
        if (this.context.containsKey(key)) {
            return this.context.get(key);
        } else {
            if (this.parent != null) {
                return this.parent.get(key);
            } else {
                return null;
            }
        }
    }

    // REQUIRES: none
    // MODIFIES: none
    // EFFECTS: returns whether key is in the RacketContext or any of its parents

    public boolean containsKey(String key) {
        if (this.context.containsKey(key)) {
            return true;
        } else {
            if (this.parent != null && this.parent.containsKey(key)) {
                return true;
            } else {
                return false;
            }
        }
    }

    public RacketContext(RacketContext parent) {
        this.context = new HashMap<String, Thing>();
        this.parent = parent;
    }
}
