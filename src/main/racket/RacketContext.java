package racket;

import java.util.HashMap;
import java.util.Objects;

public class RacketContext extends EventEmitter {
    private HashMap<String, Thing> context;
    private RacketContext parent;

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
            this.emit("getQuerySuccess", key + ": " + this.context.get(key));
            return this.context.get(key);
        } else {
            if (this.parent != null) {
                this.emit("getQuerySuccess", key + ": " + this.parent.get(key));
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
        this.emit("containsKeyQuery", key);
        if (this.context.containsKey(key)) {
            return true;
        } else {
            return this.parent != null && this.parent.containsKey(key);
        }
    }

    public RacketContext(RacketContext parent) {
        this.context = new HashMap<String, Thing>();
        this.parent = parent;
    }
}
