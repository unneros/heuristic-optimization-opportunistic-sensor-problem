import java.util.ArrayList;
import java.util.List;

public class CriticalSquare extends Coordinates {
    // First value in pair is routeID, second value in pair is index of critical point in that route
    List<Pair<Integer, Integer >> criticalPointsIndex = new ArrayList<>();
    CriticalSquare(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
