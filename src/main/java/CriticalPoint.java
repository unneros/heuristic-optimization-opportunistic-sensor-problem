import java.util.HashSet;
import java.util.Set;

public class CriticalPoint extends Coordinates {
    boolean isBeginning;
    int polyLineOrder;
    CriticalSquare criticalSquare;
    Set<CriticalSquare> coverableCriticalSquares = new HashSet<>();

    CriticalPoint(boolean isBeginning, float x, float y, int polyLineOrder, CriticalSquare criticalSquare) {
        this.x = x;
        this.y = y;
        this.isBeginning = isBeginning;
        this.polyLineOrder = polyLineOrder;
        this.criticalSquare = criticalSquare;
    }

    CriticalPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean isMergableWith(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CriticalPoint target = (CriticalPoint) o;
        return x == target.x && y == target.y && criticalSquare.equals(target.criticalSquare) && isBeginning != target.isBeginning
                && Math.abs(target.polyLineOrder - polyLineOrder) == 1;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CriticalPoint target = (CriticalPoint) o;
        return x == target.x && y == target.y && criticalSquare.equals(target.criticalSquare) && isBeginning == target.isBeginning
                && target.polyLineOrder == polyLineOrder;
    }
}
