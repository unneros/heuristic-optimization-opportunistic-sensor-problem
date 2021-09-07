import java.math.BigInteger;
import java.util.*;

public class EAUtils {
    public List<Variant> generateRandomPopulation(BusMap busMap, int numberOfVariants, int m, int k) {
        List<Variant> randomlyGeneratedVariants = new ArrayList<Variant>();
        for (int i = 0; i < numberOfVariants; i++) {
            Variant variant = new Variant();
            variant.busRouteGene = this.generateRandomBinaryStringInIntRepresentation(busMap.busRoutes.size(), m);
            for (BusRoute busRoute : busMap.busRoutes) {
                variant.criticalPointGenes.add(this.generateRandomBinaryStringInBigIntegerRepresentation(busRoute.criticalPoints.size(), k));
            }
            randomlyGeneratedVariants.add(variant);
        }
        return randomlyGeneratedVariants;
    }

    public int evaluateVariant(Variant variant, BusMap busMap) {
        Set<CriticalSquare> coverableCriticalSquareByVariant = new HashSet<>();
        for (int j = 0; j < busMap.busRoutes.size(); j++) {
            boolean busRouteIsChosen = getBit(variant.busRouteGene, j);
            if (busRouteIsChosen) {
                for (int k = 0; k < busMap.busRoutes.get(j).criticalPoints.size(); k++) {
                    boolean criticalPointIsChosen = variant.criticalPointGenes.get(j).testBit(k);
                    if (criticalPointIsChosen) {
                        coverableCriticalSquareByVariant.addAll(busMap.busRoutes.get(j).criticalPoints.get(k).coverableCriticalSquares);
                    }
                }
            }
        }
        variant.coverableCriticalSquares = coverableCriticalSquareByVariant;
        return coverableCriticalSquareByVariant.size();
    }

    public void evaluatePopulation(List<Variant> population, BusMap busMap) {
        for (int i = 0; i < population.size(); i++) {
            Set<CriticalSquare> coverableCriticalSquareByVariant = new HashSet<>();
            Variant variant = population.get(i);

            for (int j = 0; j < busMap.busRoutes.size(); j++) {
                boolean busRouteIsChosen = getBit(variant.busRouteGene, j);
                if (busRouteIsChosen) {
                    for (int k = 0; k < busMap.busRoutes.get(j).criticalPoints.size(); k++) {
//                        boolean criticalPointIsChosen = getBit(variant.criticalPointGenes.get(j), k);
                        boolean criticalPointIsChosen = variant.criticalPointGenes.get(j).testBit(k);
                        if (criticalPointIsChosen) {
                            coverableCriticalSquareByVariant.addAll(busMap.busRoutes.get(j).criticalPoints.get(k).coverableCriticalSquares);
                        }
                    }
                }
            }
            variant.coverableCriticalSquares = coverableCriticalSquareByVariant;
            population.set(i, variant);
        }
    }

    public long generateRandomBinaryStringInIntRepresentation(int binaryStringLength, int numberOf1s) {
        long result = 0;
        if (binaryStringLength < numberOf1s) {
            for (int i = 0; i < binaryStringLength; i++) {
                result =  result + (long) (Math.pow(2, i));
            }
        } else {
            List<Integer> indexList = new ArrayList<>();
            for (int i = 0; i < binaryStringLength; i++) {
                indexList.add(i);
            }
            Collections.shuffle(indexList, new Random(System.currentTimeMillis()));
            for (int i = 0; i < numberOf1s; i++) {
                result = result + (long) (Math.pow(2, indexList.get(i)));
            }
        }
        return result;
    }

    public BigInteger generateRandomBinaryStringInBigIntegerRepresentation(int binaryStringLength, int numberOf1s) {
        BigInteger result = new BigInteger("0");
        if (binaryStringLength < numberOf1s) {
            for (int i = 0; i < binaryStringLength; i++) {
//                result =  result + (long) (Math.pow(2, i));
//                result.add(new BigInteger(String.valueOf(Math.pow(2, i))));
                result = result.flipBit(i);
            }
        } else {
            List<Integer> indexList = new ArrayList<>();
            for (int i = 0; i < binaryStringLength; i++) {
                indexList.add(i);
            }
            Collections.shuffle(indexList, new Random(System.currentTimeMillis()));
            for (int i = 0; i < numberOf1s; i++) {
//                result = result + (long) (Math.pow(2, indexList.get(i)));
//                result.add(new BigInteger(String.valueOf(Math.pow(2, indexList.get(i)))));
                result = result.flipBit(indexList.get(i));
            }
        }
        return result;
    }

    public Variant variantCrossOver(Variant variantA, Variant variantB, int m) {
        // crossing bus route gene 1/2 1/2
        long busRouteGeneLeftShiftedA = (variantA.busRouteGene >> (long) Math.floor(variantA.criticalPointGenes.size() / 2)) << (int) Math.floor(variantA.criticalPointGenes.size() / 2);
//        long busRouteGeneRightShiftedB = (variantB.busRouteGene << (long) Math.ceil(variantB.criticalPointGenes.size() / 2)) >> (int) Math.ceil(variantB.criticalPointGenes.size() / 2);
        long busRouteGeneRightShiftedB = (((1 << (long) Math.ceil(variantB.criticalPointGenes.size() / 2)) - 1) & (variantB.busRouteGene));
        long busRouteGeneCrossedOver = busRouteGeneLeftShiftedA ^ busRouteGeneRightShiftedB;

        // moving critical points genes accordingly to the bus route gene
        List<BigInteger> criticalPointGenesCrossedOver = new ArrayList<>(variantA.criticalPointGenes.subList(0, variantA.criticalPointGenes.size()/2));
        criticalPointGenesCrossedOver.addAll(variantB.criticalPointGenes.subList(variantB.criticalPointGenes.size()/2, variantB.criticalPointGenes.size()));

        // flip bits to ensure m constraint
        Pair<List<Integer>, List<Integer>> bitPositions = bitPositions(busRouteGeneCrossedOver, variantA.criticalPointGenes.size());
        List<Integer> bitPositions1s = bitPositions.b;
        List<Integer> bitPositions0s = bitPositions.a;
        int numberOfFlips = bitPositions1s.size() - m;
        if (numberOfFlips > 0) {
            Collections.shuffle(bitPositions1s);
            for (int i = 0; i < numberOfFlips; i++) {
                busRouteGeneCrossedOver = busRouteGeneCrossedOver ^ ((long) 1 << bitPositions1s.get(i));
            }
        } else if (numberOfFlips < 0) {
            Collections.shuffle(bitPositions0s);
            for (int i = 0; i < -numberOfFlips; i++) {
                busRouteGeneCrossedOver = busRouteGeneCrossedOver ^ ((long) 1 << bitPositions0s.get(i));
            }
        }

        Variant variant = new Variant();
        variant.busRouteGene = busRouteGeneCrossedOver;
        variant.criticalPointGenes = criticalPointGenesCrossedOver;

        return variant;
    }

    public void mutateVariant(Variant variant, float busRouteBitFlipChance, float criticalPointBitFlipChance) {
        Random rand = new Random();
        int randomVal = rand.nextInt(1000);
        while (rand.nextInt(1000) <= busRouteBitFlipChance*1000) {
            Pair<List<Integer>, List<Integer>> bitPositions = bitPositions(new Long(variant.busRouteGene), variant.criticalPointGenes.size());
            List<Integer> bitPositions1s = bitPositions.b;
            List<Integer> bitPositions0s = bitPositions.a;

            variant.busRouteGene = swapBits(variant.busRouteGene,
                    bitPositions1s.get(rand.nextInt(bitPositions1s.size())),
                    bitPositions0s.get(rand.nextInt(bitPositions0s.size())));
        }
        int randomVal2 = rand.nextInt(1000);
        while (rand.nextInt(1000) <= criticalPointBitFlipChance*1000) {
            int randomCriticalPointsGeneIndex = rand.nextInt(variant.criticalPointGenes.size());
            Pair<List<Integer>, List<Integer>> bitPositions = bitPositions(variant.criticalPointGenes.get(randomCriticalPointsGeneIndex), 32);
            List<Integer> bitPositions1s = bitPositions.b;
            List<Integer> bitPositions0s = bitPositions.a;

            if (bitPositions1s.size() > 0 && bitPositions0s.size() > 0) {
                variant.criticalPointGenes.set(randomCriticalPointsGeneIndex,
                        swapBits(variant.criticalPointGenes.get(randomCriticalPointsGeneIndex),
                                bitPositions1s.get(rand.nextInt(bitPositions1s.size())),
                                bitPositions0s.get(rand.nextInt(bitPositions0s.size()))));
            }
        }
    }

    public List<Variant> localSearch(Variant variant, int k1, int k2, int k3) {
        List<Variant> result = new ArrayList<Variant>();
        Random rand = new Random();

        // local search bus route level to k1
        Pair<List<Integer>, List<Integer>> bitPositions = bitPositions(new Long(variant.busRouteGene), variant.criticalPointGenes.size());
        List<Integer> bitPositions1s = bitPositions.b;
        List<Integer> bitPositions0s = bitPositions.a;
        for (int i = 0; i < k1; i++) {
            Variant neighbourVariant = new Variant();
            neighbourVariant.criticalPointGenes = variant.criticalPointGenes;

            neighbourVariant.busRouteGene = swapBits(variant.busRouteGene,
                    bitPositions1s.get(rand.nextInt(bitPositions1s.size())),
                    bitPositions0s.get(rand.nextInt(bitPositions0s.size())));
            result.add(neighbourVariant);
        }

        // local search critical points level to k2
        for (int i = 0; i < k2; i++) {
            Variant neighbourVariant = new Variant();
            neighbourVariant.busRouteGene = variant.busRouteGene;
            neighbourVariant.criticalPointGenes = variant.criticalPointGenes;

            Collections.shuffle(bitPositions1s);
            int randomCriticalPointsGeneIndex = bitPositions1s.get(0);
            Pair<List<Integer>, List<Integer>> bitPositionsCriticalPointsGene = bitPositions(variant.criticalPointGenes.get(randomCriticalPointsGeneIndex), 32);
            List<Integer> bitPositions1sCriticalPointsGene = bitPositionsCriticalPointsGene.b;
            List<Integer> bitPositions0sCriticalPointsGene = bitPositionsCriticalPointsGene.a;
            if (bitPositions1sCriticalPointsGene.size() > 0 && bitPositions0sCriticalPointsGene.size() > 0) {
                neighbourVariant.criticalPointGenes.set(randomCriticalPointsGeneIndex,
                        swapBits(variant.criticalPointGenes.get(randomCriticalPointsGeneIndex),
                                bitPositions1sCriticalPointsGene.get(rand.nextInt(bitPositions1sCriticalPointsGene.size())),
                                bitPositions0sCriticalPointsGene.get(rand.nextInt(bitPositions0sCriticalPointsGene.size()))));
            }
            result.add(neighbourVariant);
        }
        
        // local search both critical points level and bus route level to k3
        for (int i = 0; i < k3; i++) {
            Variant neighbourVariant = new Variant();
            neighbourVariant.busRouteGene = variant.busRouteGene;
            neighbourVariant.criticalPointGenes = variant.criticalPointGenes;

            neighbourVariant.busRouteGene = swapBits(variant.busRouteGene,
                    bitPositions1s.get(rand.nextInt(bitPositions1s.size())),
                    bitPositions0s.get(rand.nextInt(bitPositions0s.size())));

            Collections.shuffle(bitPositions1s);
            int randomCriticalPointsGeneIndex = bitPositions1s.get(0);
            Pair<List<Integer>, List<Integer>> bitPositionsCriticalPointsGene = bitPositions(variant.criticalPointGenes.get(randomCriticalPointsGeneIndex), 32);
            List<Integer> bitPositions1sCriticalPointsGene = bitPositionsCriticalPointsGene.b;
            List<Integer> bitPositions0sCriticalPointsGene = bitPositionsCriticalPointsGene.a;
            if (bitPositions1sCriticalPointsGene.size() > 0 && bitPositions0sCriticalPointsGene.size() > 0) {
                neighbourVariant.criticalPointGenes.set(randomCriticalPointsGeneIndex,
                        swapBits(variant.criticalPointGenes.get(randomCriticalPointsGeneIndex),
                                bitPositions1sCriticalPointsGene.get(rand.nextInt(bitPositions1sCriticalPointsGene.size())),
                                bitPositions0sCriticalPointsGene.get(rand.nextInt(bitPositions0sCriticalPointsGene.size()))));
            }
            result.add(neighbourVariant);
        }
        return result;
    }

    public List<Variant> tournamentSelection(List<Variant> population, int bracketSize) {
        List<Variant> result = new ArrayList<>();
        Collections.shuffle(population);
        int bracketIndex = 0;

        for (Variant variant : population.subList(0, population.size() % bracketSize)) {
            result.add(variant);
        }
        Variant bestBracketVariant = null;
        int counter = 0;
        for (Variant variant : population.subList(population.size() % bracketSize, population.size())) {
            if (bestBracketVariant == null || counter == 0 || variant.coverableCriticalSquares.size() > bestBracketVariant.coverableCriticalSquares.size()) {
                bestBracketVariant = variant;
            }
            counter++;
            if (counter == bracketSize) {
                result.add(bestBracketVariant);
                counter = 0;
            }
        }
        return result;
    }

    public List<Variant> saSelection(List<Variant> population, float temperature, float averageCoverableSize) {
        List<Variant> result = new ArrayList<>();
        for (Variant variant : population) {
            float delta = variant.coverableCriticalSquares.size() - averageCoverableSize;
            if (delta > 0 || Math.exp(delta/temperature) > Math.random()) {
                result.add(variant);
            }
        }
        return result;
    }

    public Variant getVariantNeighbour(Variant variant) {
        Random rand = new Random();
        Variant neighbour = new Variant();
        neighbour.busRouteGene = new Long(variant.busRouteGene);
        neighbour.criticalPointGenes = new ArrayList<>(variant.criticalPointGenes);

        Pair<List<Integer>, List<Integer>> bitPositions = bitPositions(new Long(variant.busRouteGene), variant.criticalPointGenes.size());
        List<Integer> bitPositions1s = bitPositions.b;
        List<Integer> bitPositions0s = bitPositions.a;

        if (rand.nextInt(100) <= 25) {
            neighbour.busRouteGene = swapBits(neighbour.busRouteGene,
                    bitPositions1s.get(rand.nextInt(bitPositions1s.size())),
                    bitPositions0s.get(rand.nextInt(bitPositions0s.size())));
        } else {
            Collections.shuffle(bitPositions1s);
            int randomCriticalPointsGeneIndex = bitPositions1s.get(0);
            BigInteger targetCriticalPointGene = neighbour.criticalPointGenes.get(randomCriticalPointsGeneIndex);
            Pair<List<Integer>, List<Integer>> bitPositionsCriticalPointGene = bitPositions(targetCriticalPointGene,
                    targetCriticalPointGene.bitLength());
            List<Integer> bitPositions1sCriticalPointGene = bitPositionsCriticalPointGene.b;
            List<Integer> bitPositions0sCriticalPointGene = bitPositionsCriticalPointGene.a;

            if (bitPositions1sCriticalPointGene.size() > 0 && bitPositions0sCriticalPointGene.size() > 0) {
                neighbour.criticalPointGenes.set(randomCriticalPointsGeneIndex,
                        swapBits(neighbour.criticalPointGenes.get(randomCriticalPointsGeneIndex),
                                bitPositions1sCriticalPointGene.get(rand.nextInt(bitPositions1sCriticalPointGene.size())),
                                bitPositions0sCriticalPointGene.get(rand.nextInt(bitPositions0sCriticalPointGene.size()))));
            }
        }
        return neighbour;
    }

    private boolean getBit(long n, int k) {
        return ((n >> k) & 1) == 1;
    }

    private long swapBits(long n, long p1, int p2)
    {
        /* Move p1'th to rightmost side */
        long bit1 = (n >> p1) & 1;

        /* Move p2'th to rightmost side */
        long bit2 = (n >> p2) & 1;

        /* XOR the two bits */
        long x = (bit1 ^ bit2);

        /* Put the xor bit back to
        their original positions */
        x = (x << p1) | (x << p2);

        /* XOR 'x' with the original
        number so that the
        two sets are swapped */
        long result = n ^ x;
        return result;
    }

    private BigInteger swapBits(BigInteger n, int p1, int p2)
    {
        /* Move p1'th to rightmost side */
        BigInteger bit1 = n.shiftRight((int) p1).testBit(0) ? new BigInteger("1") : new BigInteger("0");

        /* Move p2'th to rightmost side */
        BigInteger bit2 = n.shiftRight((int) p2).testBit(0) ? new BigInteger("1") : new BigInteger("0");

        /* XOR the two bits */
        BigInteger x = (bit1.xor(bit2));

        /* Put the xor bit back to
        their original positions */
        x = (x.shiftLeft(p1)).or(x.shiftLeft(p2));

        /* XOR 'x' with the original
        number so that the
        two sets are swapped */
        BigInteger result = n.xor(x);
        return result;
    }


    private Pair<List<Integer>, List<Integer>> bitPositions(long number, int stringLength) {
        List<Integer> positions1s = new ArrayList<>();
        List<Integer> positions0s = new ArrayList<>();
        int position = 0;
        while (position < stringLength) {
            if ((number & 1) != 0) {
                positions1s.add(position);
            } else {
                positions0s.add(position);
            }
            position++;
            number = number >> 1;
        }
        return new Pair(positions0s, positions1s);
    }

    private Pair<List<Integer>, List<Integer>> bitPositions(BigInteger number, int stringLength) {
        List<Integer> positions1s = new ArrayList<>();
        List<Integer> positions0s = new ArrayList<>();
        for (int i = 0; i < stringLength; i++) {
            if (number.testBit(i)) {
                positions1s.add(i);
            } else {
                positions0s.add(i);
            }
        }
        return new Pair(positions0s, positions1s);
    }
}
