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
}
