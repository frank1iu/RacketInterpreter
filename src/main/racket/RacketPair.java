package racket;

public class RacketPair extends Thing {
    private Thing thing1;
    private Thing thing2;

    public RacketPair(Thing thing1, Thing thing2) {
        super(thing1.getValue().toString(), null);
        if (thing2.getType() != Type.EMPTY && !thing2.getClass().getName().equals("racket.RacketPair")) {
            throw new GenericRacketError("cons: second argument must be a list, but received "
                    + thing1.toString() + " and " + thing2.toString());
        }
        this.thing1 = thing1;
        this.thing2 = thing2;
        super.setType(Type.PAIR);
    }

    protected Thing first() {
        return thing1;
    }

    protected Thing rest() {
        return thing2;
    }

    @Override
    public String toString() {
        return "(cons " + thing1.toString() + " " + thing2.toString() + ")";
    }
}
