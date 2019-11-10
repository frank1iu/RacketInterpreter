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

    @Override
    public void update(String event, Object data) {
        this.log.add("[" + event + "] " + data);
    }
}
