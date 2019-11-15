package racket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventEmitter {
    private HashMap<String, List<EventListener>> eventListeners = new HashMap<>();

    // MODIFIES: this
    // EFFECTS: attaches an EventListener to this, which listens for event event
    public void on(String event, EventListener eventListener) {
        if (!this.eventListeners.containsKey(event)) {
            ArrayList<EventListener> list = new ArrayList<>();
            list.add(eventListener);
            this.eventListeners.put(event, list);
        } else {
            this.eventListeners.get(event).add(eventListener);
        }
    }

    protected void emit(String event, Object data) {
        if (!this.eventListeners.containsKey(event)) {
            return;
        } else {
            for (EventListener eventListener : this.eventListeners.get(event)) {
                eventListener.update(event, data);
            }
        }
    }
}
