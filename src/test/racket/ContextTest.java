package racket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContextTest {
    @Test
    public void testContext() {
        final RacketContext parent = new RacketContext(null);
        parent.put("a", new Thing("true", null));
        Assertions.assertTrue(parent.containsKey("a"));
        Assertions.assertTrue(parent.get("a").equals(new Thing("true", null)));

        final RacketContext child = new RacketContext(parent);
        Assertions.assertTrue(child.containsKey("a"));
        Assertions.assertTrue(child.get("a").equals(new Thing("true", null)));
    }
}
