package racket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class ThingTest {
    private final Thing thing1 = new Thing("true", null);
    private final Thing thingWithChildren = new Thing("and", new ArrayList<Thing>(Arrays.asList(
            new Thing("true", null), new Thing("false", null))));
    private final RacketFunc func = new RacketFunc("func", null, new String[]{"a", "b"});
    @Test
    public void testToString() {
        Assertions.assertEquals(thing1.toString(), "true");
        Assertions.assertEquals(thingWithChildren.toString(), "(and true false)");
        Assertions.assertEquals(func.toString(), "(func a b)");
    }
    @Test
    public void testEquals() {
        Assertions.assertTrue(thingWithChildren.equals(thingWithChildren));
        Assertions.assertFalse(thing1.equals(thingWithChildren));
    }
    @Test
    public void testAdd() {
        final Thing thing2 = new Thing("and", null);
        thing2.addChild(thing1);
        thing2.addChildren(new ArrayList<Thing>(Arrays.asList(thing1, thing1)));
        Assertions.assertEquals(thing2.toString(), "(and true true true)");
    }
}
