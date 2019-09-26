package racket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContextTest {
    @Test
    public void testContext() {
        final RacketContext parent = new RacketContext(null);
        parent.put("a", new Thing("true", null));
        Assertions.assertEquals(parent.containsKey("a"), true);
        Assertions.assertEquals(parent.get("a").equals(new Thing("true", null)), true);

        final RacketContext child = new RacketContext(parent);
        Assertions.assertEquals(child.containsKey("a"), true);
        Assertions.assertEquals(child.get("a").equals(new Thing("true", null)), true);
    }
}
