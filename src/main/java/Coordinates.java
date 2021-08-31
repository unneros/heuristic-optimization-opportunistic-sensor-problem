import java.util.Objects;

public class Coordinates {
    float x;
    float y;

    public Coordinates() {

    }

    public Coordinates(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates target = (Coordinates) o;
        return x == target.x && y == target.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
