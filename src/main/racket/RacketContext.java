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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RacketContext that = (RacketContext) o;
        return Objects.equals(context, that.context) && Objects.equals(parent, that.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(context, parent);
    }

    public boolean containsKey(String key) {
        this.emit("containsKeyQuery", key);
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
