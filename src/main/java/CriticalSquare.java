import java.util.ArrayList;
import java.util.List;

public class CriticalSquare extends Coordinates implements Cloneable {
    // First value in pair is routeID, second value in pair is index of critical point in that route
    List<Pair<Integer, Integer >> criticalPointsIndex = new ArrayList<>();
    CriticalSquare(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public CriticalSquare clone() {
        CriticalSquare newCriticalSquare = new CriticalSquare((int) this.x, (int) this.y);
        newCriticalSquare.criticalPointsIndex = new ArrayList<>(criticalPointsIndex);
        return newCriticalSquare;
    }
}
