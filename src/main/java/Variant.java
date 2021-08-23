import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Variant {
    long busRouteGene;
    List<BigInteger> criticalPointGenes = new ArrayList<>();
    Set<CriticalSquare> coverableCriticalSquares = new HashSet<>();

    public boolean isVariantGood(int m, int k) {
        int count = 0;
        long tempBusRouteGene = busRouteGene;
        while (tempBusRouteGene != 0) {
            tempBusRouteGene &= (tempBusRouteGene - 1);
            count++;
        }
        if (count > m) {
            return false;
        }

        for (BigInteger criticalPointGene : criticalPointGenes) {
            if (criticalPointGene.bitCount() > k) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        return (int) busRouteGene;
    }

    @Override
    public boolean equals(Object o) {
        Variant variant = (Variant) o;
        for (int i = 0; i < criticalPointGenes.size(); i++) {
            if (!criticalPointGenes.get(i).equals(variant.criticalPointGenes.get(i))) {
                return false;
            }
        }
        if (busRouteGene == variant.busRouteGene) {
            return true;
        } else {
            return false;
        }
    }
}
