package racket;

public interface EventListener {
    // REQUIRES: none
    // MODIFIES: none
    // EFFECTS: called whenever an event is emitted by the subject
    void update(String event, Object data);
}
