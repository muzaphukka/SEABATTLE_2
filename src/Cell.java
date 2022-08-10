import java.util.function.Predicate;

public enum Cell implements Predicate<Cell[]> {
    SEA,
    DECK,
    HIT,
    DEAD,
    MISS;

    @Override
    public boolean test(Cell[] cells) {
        return false;
    }
}
