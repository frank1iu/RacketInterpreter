package racket;

import java.util.ArrayList;

public class RacketLogger implements EventListener {
    public ArrayList<String> getLog() {
        return log;
    }

    private ArrayList<String> log;

    public RacketLogger() {
        this.log = new ArrayList<>();
    }

    // REQUIRES: none
    // MODIFIES: this
    // EFFECTS: add an event with the associated data to this.log
    @Override
    public void update(String event, Object data) {
        this.log.add("[" + event + "] " + data);
    }
}
