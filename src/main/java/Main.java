import com.google.ortools.sat.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


import static java.lang.Math.pow;
import com.google.ortools.sat.*;


public class Main {
    static {
        System.loadLibrary("jniortools");
    }
    public static void main(String[] args) throws IOException {
        File dir = new File(args[0]);
        int m = Integer.parseInt(args[1]);
        int k = Integer.parseInt(args[2]);
//        File dir = new File("C:\\Users\\duong\\IdeaProjects\\SensorOnBusProblem\\resource");

        spreadsheetResultRecording(dir, m, k);

//        List<String> fileNames = new ArrayList<>();
//        List<Integer> mipResults = new ArrayList<>();
//        List<Integer> greedyResults = new ArrayList<>();
//        List<Integer> gaResults = new ArrayList<>();
//        List<Long> mipRuntime = new ArrayList<>();
//        List<Long> greedyRuntime = new ArrayList<>();
//        List<Long> gaRuntime = new ArrayList<>();
//
//
//
//        PrintStream output = new PrintStream("./result.txt");
//        System.setOut(output);
//
//        for (int m = 2; m <= 59; m++) {
//            for (int k = 2; k <= 20; k++) {
//                System.out.println();
//                System.out.println("-----------------------------------------------------------------");
//                System.out.println("-----------------------------------------------------------------");
//                System.out.println("-----------------------------------------------------------------");
//                System.out.println("Starting evaluation on m = " + m + ", k = " + k);
//                for (File file : dir.listFiles()) {
//                    System.out.println("-----------------------------------------------------------------");
//                    System.out.println("Running on file " + file.getName());
//                    long startime;
//                    long endTime;
//                    BusMap busMap = parseTXT(file.getAbsolutePath());
////                    BusMap busMap2 = parseTXT(file.getAbsolutePath());
////                    BusMap busMap3 = parseTXT(file.getAbsolutePath());
//                    busMap.busMapInitEA(busMap.radius);
//
//                    fileNames.add(file.getName());
//
//                    if (busMap.criticalSquares.size() <= 25) {
//                        startime = System.nanoTime();
//                        mipResults.add(solveSOBPMIP(busMap, busMap.radius, m, k));
//                        endTime = System.nanoTime();
//                        mipRuntime.add(endTime - startime);
//                    }
//
//                    startime = System.nanoTime();
//                    Variant greedyAlgoResultVariant = solveSOBP(busMap, busMap.radius, m, k);
//
//                    greedyResults.add(greedyAlgoResultVariant.coverableCriticalSquares.size());
//                    endTime = System.nanoTime();
//                    greedyRuntime.add(endTime - startime);
//
//                    startime = System.nanoTime();
//                    List<Variant> initPopulation = new ArrayList<>();
//                    initPopulation.add(greedyAlgoResultVariant);
//                    gaResults.add(solveSOBPGreedyEA(busMap, busMap.radius, m, k, 150, 6000,
//                            30, (float) 0.1, 0, (float) 0.46, initPopulation).size());
//                    endTime = System.nanoTime();
//                    gaRuntime.add(endTime - startime);
//                    if (busMap.criticalSquares.size() > 25) {
//                        System.out.println("Finished map " + file.getName() + " with greedy = " + greedyResults.get(greedyResults.size() - 1)
//                                + " and GA = " + gaResults.get(gaResults.size() - 1));
//                    } else {
//                        System.out.println("Finished map " + file.getName() + " with greedy = " + greedyResults.get(greedyResults.size() - 1)
//                                + " and GA = " + gaResults.get(gaResults.size() - 1)
//                                + " and Optimal = " + mipResults.get(mipResults.size() - 1));
//                        System.out.println("Optimal result time: "+ mipRuntime.get(mipRuntime.size() - 1));
//
//                    }
//                    System.out.println("Greedy elapsed time " + greedyRuntime.get(greedyRuntime.size() - 1));
//                    System.out.println("GA elapsed time " + gaRuntime.get(gaRuntime.size() - 1));
//                }
//            }
//        }

//
//        BusMap busMap = parseXML("C:\\Users\\duong\\CLionProjects\\SensorOnBusProblem\\resource\\BusMapSample.XML",
//                 4, 4);
//        BusMap busMap2 = parseXML("C:\\Users\\duong\\CLionProjects\\SensorOnBusProblem\\resource\\BusMapSample.XML",
//                4, 4);


//        int k = 4;
//        int m = 5;
//
//        BusMap busMap = parseTXT("C:\\Users\\duong\\IdeaProjects\\SensorOnBusProblem\\resource\\30x36_200_3.00.txt");
////        BusMap busMap2 = parseTXT("C:\\Users\\duong\\IdeaProjects\\SensorOnBusProblem\\resource\\30x36_200_3.00.txt");
////        BusMap busMap3 = parseTXT("C:\\Users\\duong\\IdeaProjects\\SensorOnBusProblem\\resource\\30x36_200_3.00.txt");
//
//        busMap.busMapInitEA(busMap.radius);
//
//
////        System.out.println(solveSOBPMIP(busMap3, r, m, k));
//        Variant greedyAlgoResultVariant = solveSOBP(busMap, busMap.radius, m, k);
//        System.out.println(greedyAlgoResultVariant.coverableCriticalSquares.size());
//
//        System.out.println(solveSOBPSA(busMap, busMap.radius, m, k, 10000000, greedyAlgoResultVariant).coverableCriticalSquares.size());
//
//        // x' - x = C = k*x |
//        List<Variant> initPopulation = new ArrayList<>();
//        initPopulation.add(greedyAlgoResultVariant);
//        System.out.println(solveSOBPGreedyEA(busMap, busMap.radius, m, k, 100, 4000,
//                20, (float) 0.8, 400000, (float) 0.8, initPopulation).size());
//
//
////        System.out.println(solveSOBPMIP(busMap, busMap.radius, m, k));

    }

    public static List<String> getResultsList(File file, int m, int k) throws IOException {
        List<String> result = new ArrayList<>();
        result.add(file.getName());

        long startime;
        long endTime;
        BusMap busMap = parseTXT(file.getAbsolutePath());
        busMap.busMapInitEA(busMap.radius);

        if (busMap.criticalSquares.size() <= 25) {
            startime = System.nanoTime();
            result.add(Integer.toString(solveSOBPMIP(busMap, busMap.radius, m, k)));
            endTime = System.nanoTime();
            result.add(Double.toString((double) (endTime - startime)/1_000_000_000));
        } else {
            result.add("x");
            result.add("x");
        }

        startime = System.nanoTime();
        Variant greedyAlgoResultVariant = solveSOBP(busMap, busMap.radius, m, k);
        result.add(Integer.toString(greedyAlgoResultVariant.coverableCriticalSquares.size()));
        endTime = System.nanoTime();
        result.add(Double.toString((double) (endTime - startime)/1_000_000_000));

        startime = System.nanoTime();
        List<Variant> initPopulation = new ArrayList<>();
        initPopulation.add(greedyAlgoResultVariant);
        result.add(Integer.toString(solveSOBPGreedyEA(busMap, busMap.radius, m, k, 150, 6000,
                30, (float) 0.1, 0, (float) 0.46, initPopulation).size()));
        endTime = System.nanoTime();
        result.add(Double.toString((double) (endTime - startime)/1_000_000_000));

        // SA
        startime = System.nanoTime();
        result.add(Integer.toString(solveSOBPSA(busMap, busMap.radius, m, k, 10000000, greedyAlgoResultVariant).coverableCriticalSquares.size()));
        endTime = System.nanoTime();
        result.add(Double.toString((double) (endTime - startime)/1_000_000_000));

        return result;
    }

    public static void spreadsheetResultRecording(File path, int starting_m, int starting_k) throws IOException {
        File dir = path;
        String[] titles = new String[] {"File name", "Optimal", "Optimal runtime", "Greedy", "Greedy runtime", "GA", "GA runtime", "SA", "SA runtime"};
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        FileOutputStream out = new FileOutputStream(
//                new File("./result.xlsx"));
        for (int m = starting_m; m < 59; m++) {
            for (int k = starting_k; k < 20; k++) {
                PrintStream output = new PrintStream("./m" + m + "k"+ k +".txt");
                System.setOut(output);
//                XSSFSheet spreadsheet
//                        = workbook.createSheet("m = " + m + ", k = " + k);
//                XSSFRow row = spreadsheet.createRow(rowid++);
//                int cellid = 0;
//                for (String title : titles) {
////                    Cell cell = row.createCell(cellid++);
////                    cell.setCellValue(title);
//                }
                System.out.format("%20s %17s %17s %17s %17s %17s %17s %17s %17s", titles[0], titles[1], titles[2], titles[3], titles[4], titles[5], titles[6], titles[7], titles[8]);
                System.out.println();

                for (File file : dir.listFiles()) {
//                    row = spreadsheet.createRow(rowid++);
//                    for (String result : getResultsList(file, m, k)) {
////                        Cell cell = row.createCell(cellid++);
////                        cell.setCellValue(result);
//                    }
                    List<String> results = getResultsList(file, m, k);
                    System.out.format("%20s %17s %17s %17s %17s %17s %17s %17s %17s", results.get(0),  results.get(1),  results.get(2),  results.get(3), results.get(4), results.get(5), results.get(6), results.get(7), results.get(8));
                    System.out.println();
                }
                output.close();
            }
        }
//        workbook.write(out);
//        out.close();
    }

    public static Set<CriticalSquare> solveSOBPGreedyEA(BusMap busMap, float r, int m, int k,
                                      int numberOfVariants, int numberOfEvaluations,
                                      int numberOfSelectedParents, float bitFlipChance,
                                      float convergenceThreshold, float criticalPointChangeChance, List<Variant> initPopulation) {
//        if (!busMap.isInitialized) {
//            busMap.busMapInitEA(r);
//        }

        EAUtils eaUtils = new EAUtils();
        List<Variant> populations = eaUtils.generateRandomPopulation(busMap, numberOfVariants*3, m, k);
//        populations.add(solveSOBP(busMap, r, m, k));
        populations.addAll(initPopulation);
        eaUtils.evaluatePopulation(populations, busMap);
        int convergingGenerationCounter = 0;
        int prevFitness = 0;
        int currentFitness = 0;
        float trueBitFlipChance = bitFlipChance;
        float trueCriticalPointChangeChance = criticalPointChangeChance;
        for (int i = 0; i < numberOfEvaluations; i++) {
//            // local search
//            Variant bestVariant = populations.get(0);
//            for (Variant variant : populations) {
//                if (variant.coverableCriticalSquares.size() > bestVariant.coverableCriticalSquares.size()) {
//                    bestVariant = variant;
//                }
//            }
//            populations.addAll(eaUtils.localSearch(bestVariant, 10, 3, 100));

//            // scale up mutation chance when converging
//            if (prevFitness == currentFitness) {
//                convergingGenerationCounter++;
//            } else if (convergingGenerationCounter > 0){
//                convergingGenerationCounter--;
//            }
//            if (convergingGenerationCounter > 0) {
//                trueBitFlipChance = trueBitFlipChance + (1 - trueBitFlipChance)/(1 + convergenceThreshold/convergingGenerationCounter);
//                trueCriticalPointChangeChance = trueCriticalPointChangeChance + (1 - trueCriticalPointChangeChance)/(1 + convergenceThreshold/convergingGenerationCounter);
//            }

            // select parents
            Collections.sort(populations, (Variant a, Variant b) -> {
                return b.coverableCriticalSquares.size() - a.coverableCriticalSquares.size();
            });
            List<Variant> selectedParents;
            if (populations.size() < numberOfSelectedParents) {
                selectedParents = new ArrayList<>(populations);
            } else {
                selectedParents = new ArrayList<>(populations.subList(0, numberOfSelectedParents));
            }

            // recombine parents
            List<Variant> offsprings = new ArrayList<>();
            int offspringIndex = 0;
            for (Variant variantA : selectedParents) {
                for (Variant variantB: selectedParents) {
                    offsprings.add(eaUtils.variantCrossOver(variantA, variantB, m));
                }
            }

            // mutate offsprings
            for (int j = 0; j < offsprings.size(); j++) {
                eaUtils.mutateVariant(offsprings.get(j), bitFlipChance, criticalPointChangeChance);
            }

            // evaluate offsprings
            eaUtils.evaluatePopulation(offsprings, busMap);

            Collections.sort(offsprings, (Variant variantA, Variant variantB) -> {
                return variantB.coverableCriticalSquares.size() - variantA.coverableCriticalSquares.size();
            });

            // select individuals
            populations.addAll(offsprings);

            List<Variant> randomlyChosenUnfitVariants = new ArrayList<>(populations);
            Collections.shuffle(randomlyChosenUnfitVariants);
            randomlyChosenUnfitVariants = randomlyChosenUnfitVariants.subList(0, numberOfVariants/10);
//            populations = new ArrayList<>(populations.subList(0, numberOfVariants));
            Collections.shuffle(populations);
            populations = eaUtils.tournamentSelection(populations, 4);
            populations.addAll(randomlyChosenUnfitVariants);
            populations = new ArrayList<>(new HashSet<>(populations));
            Collections.sort(populations, (Variant variantA, Variant variantB) -> {
                return variantB.coverableCriticalSquares.size() - variantA.coverableCriticalSquares.size();
            });
//            System.out.println("Current evaluation: " + i + " best result: " + populations.get(0).coverableCriticalSquares.size());
            prevFitness = currentFitness;
            currentFitness = populations.get(0).coverableCriticalSquares.size();
        }
        return populations.get(0).coverableCriticalSquares;
    }

    public static Variant solveSOBPSA(BusMap busMap, float r, int m, int k, int numberOfEvaluations, Variant initialVariant) {
        //starting point initialization
        Variant variant;
        EAUtils eaUtils = new EAUtils();
        if (initialVariant == null) {
            variant = eaUtils.generateRandomPopulation(busMap, 1, m, k).get(0);
        } else {
            variant = new Variant();
            variant.busRouteGene = new Long(initialVariant.busRouteGene);
            variant.criticalPointGenes = new ArrayList<>(initialVariant.criticalPointGenes);
            eaUtils.evaluateVariant(variant, busMap);
        }
        Variant bestVariant = variant;
        for (int i = 0; i < numberOfEvaluations; i++) {
            //get neighbour variant' of variant
            Variant neighbour = eaUtils.getVariantNeighbour(variant);

            //evaluate neighbour variant'
            eaUtils.evaluateVariant(neighbour, busMap);

            //calculate temperature T
            double T = (1 - (float) (i + 1)/numberOfEvaluations);

            //if evaluate(variant') > evaluate(variant) -> accept variant' as the variant
            // else if exp(delta/T) > rand(0,1) -> accept variant'
            double delta = neighbour.coverableCriticalSquares.size() - variant.coverableCriticalSquares.size();
            if (delta > 0 || Math.exp(delta/T) > Math.random()) {
                variant = neighbour;
            }
            if (variant.coverableCriticalSquares.size() > bestVariant.coverableCriticalSquares.size()) {
                bestVariant = variant;
            }
//            System.out.println("SA Running at evaluation number: " + i + " with result: " + variant.coverableCriticalSquares.size() + " and best result: " + bestVariant.coverableCriticalSquares.size());
        }
        return bestVariant;
    }

    public static Pair<Set<CriticalSquare>, BigInteger> largestSet(BusMap busMap, float r, int k, BusRoute busRoute) {
//        for (CriticalSquare criticalSquare : busMap.uncoveredCriticalSquares) {
//            busRoute.calculateIntervals(criticalSquare, r);
//        }
//
//        // for each critical points, calculate which critical square it can cover
//        Collections.sort(busRoute.criticalPoints, (criticalPoint1, criticalPoint2) -> {
//            if (criticalPoint1.polyLineOrder > criticalPoint2.polyLineOrder) {
//                return Integer.MAX_VALUE;
//            } else if (criticalPoint1.polyLineOrder == criticalPoint2.polyLineOrder) {
//                if ((pow(criticalPoint1.x - busRoute.polyPoints.get(criticalPoint1.polyLineOrder).x, 2) + pow(criticalPoint1.y - busRoute.polyPoints.get(criticalPoint1.polyLineOrder).y, 2)
//                        > pow(criticalPoint2.x - busRoute.polyPoints.get(criticalPoint2.polyLineOrder).x, 2) + pow(criticalPoint2.y - busRoute.polyPoints.get(criticalPoint1.polyLineOrder).y, 2))) {
//                    return 1;
//                } else if ((pow(criticalPoint1.x - busRoute.polyPoints.get(criticalPoint1.polyLineOrder).x, 2) + pow(criticalPoint1.y - busRoute.polyPoints.get(criticalPoint1.polyLineOrder).y, 2)
//                        == pow(criticalPoint2.x - busRoute.polyPoints.get(criticalPoint2.polyLineOrder).x, 2) + pow(criticalPoint2.y - busRoute.polyPoints.get(criticalPoint1.polyLineOrder).y, 2))){
//                    return criticalPoint1.isBeginning ? -1 : 1;
//                } else {
//                    return -1;
//                }
//            } else {
//                return Integer.MIN_VALUE;
//            }
//        });

        Set<CriticalSquare> coveredCriticalSquares = new HashSet<>(busMap.criticalSquares);
        coveredCriticalSquares.removeAll(busMap.uncoveredCriticalSquares);

        int numberOfCriticalPoitns = busRoute.criticalPoints.size();
        int[][] g_ij = new int[numberOfCriticalPoitns][numberOfCriticalPoitns];
        Set<CriticalSquare> coveredCriticalSquaresAtI = new HashSet<>();
        ArrayList<Set<CriticalSquare>> observableCrticalSquare = new ArrayList<>();
        for (int i = 0; i < numberOfCriticalPoitns; i++) {
            if (busRoute.criticalPoints.get(i).isBeginning) {
                coveredCriticalSquaresAtI.add(busRoute.criticalPoints.get(i).criticalSquare);
                observableCrticalSquare.add(new HashSet<>(coveredCriticalSquaresAtI));
            } else {
                observableCrticalSquare.add(new HashSet<>(coveredCriticalSquaresAtI));
                coveredCriticalSquaresAtI.remove(busRoute.criticalPoints.get(i).criticalSquare);
            }
        }

        // calculate g[i][j] for all critical points
        for (int i = 0; i < numberOfCriticalPoitns; i++) {
            for (int j = 0; j < numberOfCriticalPoitns; j++) {
                Set<CriticalSquare> observableCriticalSquaresAtJ = new HashSet<>(observableCrticalSquare.get(j));
                observableCriticalSquaresAtJ.removeAll(coveredCriticalSquares);
                if (i != 0) {
                    observableCriticalSquaresAtJ.removeAll(observableCrticalSquare.get(i));
                }
                g_ij[i][j] = observableCriticalSquaresAtJ.size();
            }
        }

        int[][] f_ij = new int[numberOfCriticalPoitns][k + 1];
        Set<CriticalSquare> chosenCriticalSquares = new HashSet<>();
        BigInteger chosenCriticalPointGene;

        Map<Pair, Set<CriticalSquare>> f_ijCriticalSquareMap = new HashMap<>();
        Map<Pair, BigInteger> f_ijCriticalPointMap = new HashMap<>();
        Pair<Set<CriticalSquare>, BigInteger> result = new Pair<>(new HashSet<>(), new BigInteger("0"));
        for (int j = 0; j < k + 1; j++) {
            BigInteger tempCriticalPointGene;
            for (int i = 0; i < numberOfCriticalPoitns; i++) {
                f_ij[i][j] = 0;
                if (j > 0) {
                    for (int u = i + 1; u < numberOfCriticalPoitns; u++) {
                        int val = f_ij[u][j - 1] + g_ij[i][u];
                        Set<CriticalSquare> tempValCQSet = new HashSet<>();
                        if (f_ij[u][j - 1] == 0) {
                            tempValCQSet = new HashSet<>(observableCrticalSquare.get(u));
                            if (i != 0) {
                                tempValCQSet.removeAll(observableCrticalSquare.get(i));
                            }
                            tempCriticalPointGene = new BigInteger("0");
                            tempCriticalPointGene = tempCriticalPointGene.flipBit(u);
                        } else {
                            tempValCQSet = new HashSet<>(observableCrticalSquare.get(u));
                            if (i != 0) {
                                tempValCQSet.removeAll(observableCrticalSquare.get(i));
                            }
                            tempValCQSet.addAll(f_ijCriticalSquareMap.get(new Pair(u, j - 1)));
                            tempCriticalPointGene = f_ijCriticalPointMap.get(new Pair(u, j - 1));
                            if (!f_ijCriticalPointMap.get(new Pair(u, j - 1)).testBit(u)) {
                                tempCriticalPointGene = tempCriticalPointGene.flipBit(u);
                            }
                        }
                        if (f_ij[i][j] < val) {
//                            if (chosenCriticalSquareList.size() < j && i == 0) {
//                                chosenCriticalSquareList.add(observableCrticalSquare.get(u));
//                            } else if (chosenCriticalSquareList.size() == j && i == 0) {
//                                chosenCriticalSquareList.set(j - 1, observableCrticalSquare.get(u));
//                            }
//                            chosenCriticalSquares.addAll(observableCrticalSquare.get(u));
                            f_ij[i][j] = val;
                            f_ijCriticalSquareMap.put(new Pair(i, j), tempValCQSet);
                            f_ijCriticalPointMap.put(new Pair(i, j), tempCriticalPointGene);
                            if (i == 0) {
                                chosenCriticalSquares = tempValCQSet;
                                chosenCriticalPointGene = tempCriticalPointGene;
                                result = new Pair<>(chosenCriticalSquares, chosenCriticalPointGene);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    public static Variant solveSOBP(BusMap busMap, float r, int m, int k) {
        Set<CriticalSquare> coveredCriticalSquares = new HashSet<>();
        Set<CriticalSquare> tempCoveredCriticalSquares;
        BigInteger tempCriticalPointGene = null;
        List<BusRoute> chosenBusRoutes = new ArrayList<>();
        List<Integer> chosenBusRoutesIndexes = new ArrayList<>();

//        if (!busMap.isInitialized) {
//            busMap.busMapInitEA(r);
//        }

        Variant variant = new Variant();
        EAUtils eaUtils = new EAUtils();
        variant.busRouteGene = 0;
        for (BusRoute busRoute : busMap.busRoutes) {
            variant.criticalPointGenes.add(eaUtils.generateRandomBinaryStringInBigIntegerRepresentation(busRoute.criticalPoints.size(), k));
        }

        Set<CriticalSquare> Y;
        BigInteger X;
        for (int i = 1; i <= m; i++) {
            tempCoveredCriticalSquares = new HashSet<>();
            int chosenBusRouteIndex = -1;
            for (int j = 0; j < busMap.busRoutes.size(); j++) {
                Y = new HashSet<>();
                X = new BigInteger("0");
                if (!busMap.busRoutes.get(j).chosen) {
//                    busMap.busRoutes.get(j).criticalPoints = new ArrayList<>();
                    Pair<Set<CriticalSquare>, BigInteger> largestSetResult = largestSet(busMap, r, k, busMap.busRoutes.get(j));
                    Y = largestSetResult.a;
                    X = largestSetResult.b;
                } else {
                    Y = new HashSet<>();
                }
                if (Y.size() >= tempCoveredCriticalSquares.size()) {
                    tempCoveredCriticalSquares = Y;
                    tempCriticalPointGene = X;
                    chosenBusRouteIndex = j;
                }
            }
            if (!chosenBusRoutesIndexes.contains(chosenBusRouteIndex)) {
                variant.busRouteGene = variant.busRouteGene + (long) (Math.pow(2, chosenBusRouteIndex));
                variant.criticalPointGenes.set(chosenBusRouteIndex, tempCriticalPointGene);
            }
            coveredCriticalSquares.addAll(tempCoveredCriticalSquares);
            busMap.busRoutes.get(chosenBusRouteIndex).chosen = true;
            chosenBusRoutes.add(busMap.busRoutes.get(chosenBusRouteIndex));
            chosenBusRoutesIndexes.add(chosenBusRouteIndex);
            busMap.uncoveredCriticalSquares.removeAll(tempCoveredCriticalSquares);
            variant.coverableCriticalSquares = coveredCriticalSquares;
        }
//        Set<CriticalSquare> result = new HashSet<>(busMap.uncoveredCriticalSquares);
//        result.removeAll(coveredCriticalSquares);
//
//        busMap.uncoveredCriticalSquares = result;
        return variant;
    }

    public static int solveSOBPMIP(BusMap busMap, float r, int m, int k) {
        CpModel model = new CpModel();

//        if (!busMap.isInitialized) {
//            busMap.busMapInitEA(r);
//        }


        // maximize sigma c_i
        Map<Pair<Integer, Integer>, IntVar> criticalSquaresMap = new HashMap<>(); // first x, second y of critical square
        for (CriticalSquare criticalSquare : busMap.uncoveredCriticalSquares) {
            criticalSquaresMap.put(new Pair<Integer, Integer>((int) criticalSquare.x, (int) criticalSquare.y),
                    model.newIntVar(0, 1, "c_" + criticalSquare.x + "_" + criticalSquare.y));
        }
        IntVar criticalSquaresVarList[] = new IntVar[criticalSquaresMap.size()];
        for (int i = 0; i < criticalSquaresMap.size(); i++) {
            criticalSquaresVarList[i] = (IntVar) criticalSquaresMap.values().toArray()[i];
        }

        // sigma x_i = m
        ArrayList<IntVar> criticalPointVarList = new ArrayList<>();
        ArrayList<IntVar> busRouteVarList = new ArrayList<>();
        // 1st value in pair is routeID, 2nd is critical point index, pointing towards IntVar of critical point
        Map<Pair<Integer, Integer>, IntVar> criticalPointsMap = new HashMap<>();
        for (BusRoute busRoute : busMap.busRoutes) {
            IntVar tempBusRoute = model.newIntVar(0, 1, "x_" + busRoute.routeID);
            busRouteVarList.add(tempBusRoute);

            // sigma p_ij = k*x_i
            criticalPointVarList = new ArrayList<>();
            for (int i = 0; i < busRoute.criticalPoints.size(); i++) {
                CriticalPoint criticalPoint = busRoute.criticalPoints.get(i);
                IntVar tempCriticalPoint = model.newIntVar(0, 1, "p_" + busRoute.routeID + "_" +i);
                criticalPointsMap.put(new Pair<>(busRoute.routeID, i), tempCriticalPoint);
                criticalPointVarList.add(tempCriticalPoint);
                // p <= c if p covers c, wrong, commented out
//                for (CriticalSquare criticalSquare : criticalPoint.coverableCriticalSquares) {
//                    model.addLessOrEqual(criticalPointVarList.get(criticalPointVarList.size() - 1),
//                            criticalSquaresMap.get(new Pair<Integer, Integer>((int) criticalSquare.x, (int) criticalSquare.y)));
//                }
            }
            IntVar criticalPointVarListArray[] = new IntVar[criticalPointVarList.size()];
            for (int j = 0; j < criticalPointVarList.size(); j++) {
                criticalPointVarListArray[j] = criticalPointVarList.get(j);
            }
            model.addLessOrEqual(new SumOfVariables(criticalPointVarListArray),
                    new ScalProd(new IntVar[]{busRouteVarList.get(busRouteVarList.size()-1)}, new long[]{k}));
        }
        IntVar busRouteVarListArray[] = new IntVar[busRouteVarList.size()];
        for (int i = 0; i < busRouteVarList.size(); i++) {
            busRouteVarListArray[i] = busRouteVarList.get(i);
        }
        model.addLessOrEqual(new SumOfVariables(busRouteVarListArray), m);

        // (P | P | P) = C
        for (CriticalSquare criticalSquare : busMap.criticalSquares) {
            List<IntVar> criticalPointsCoverCriticalSquareVarList = new ArrayList<>();
            for (Pair<Integer, Integer> criticalPointIndex : criticalSquare.criticalPointsIndex) {
                criticalPointsCoverCriticalSquareVarList.add(criticalPointsMap.get(criticalPointIndex));
            }
            IntVar criticalPointsCoverCriticalSquareVarListAray[] = new IntVar[criticalPointsCoverCriticalSquareVarList.size()];
            for (int i = 0; i < criticalPointsCoverCriticalSquareVarList.size(); i++) {
                criticalPointsCoverCriticalSquareVarListAray[i] = criticalPointsCoverCriticalSquareVarList.get(i);
            }
            model.addLessOrEqual(criticalSquaresMap.get(new Pair<>((int) criticalSquare.x, (int) criticalSquare.y)), new SumOfVariables(criticalPointsCoverCriticalSquareVarListAray));
        }
        model.addGreaterOrEqual(new SumOfVariables(criticalSquaresVarList), 4);
        model.maximize(new SumOfVariables(criticalSquaresVarList));

        CpSolver solver = new CpSolver();
        CpSolverStatus status = solver.solve(model);
        if (status == CpSolverStatus.OPTIMAL) {
            int result = 0;
            for (IntVar criticalSquareVar : criticalSquaresVarList) {
                result = result + (int) solver.value(criticalSquareVar);
        }
        return result;
        }
        return 0;
    }

    static BusMap parseXMLWithRandomCriticalSquares(String path, int numberOfCriticalSquares, int gridMapX, int gridMapY) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        BusMap busMap = new BusMap();

        try {

            // optional, but recommended
            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(path));

            // optional, but recommended
            // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

//            System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
//            System.out.println("------");

            NodeList list = doc.getElementsByTagName("BusRoute");

            Random generator = new Random(System.currentTimeMillis());

            for (int temp = 0; temp < list.getLength(); temp++) {
                BusRoute busRoute = new BusRoute();

                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;

                    // get staff's attribute
                    busRoute.routeID = Integer.parseInt(element.getAttribute("routeID"));

                    // get text
                    NodeList polyPointList = element.getElementsByTagName("PolyPoint");
                    for (int i = 0; i < polyPointList.getLength(); i++) {
                        busRoute.polyPoints.add(new Coordinates(
                                Float.parseFloat(polyPointList.item(i).getAttributes().item(0).getNodeValue())*gridMapX/4,
                                Float.parseFloat(polyPointList.item(i).getAttributes().item(1).getNodeValue())*gridMapY/4));
                    }
                }
                busMap.busRoutes.add(busRoute);
            }
            Set<CriticalSquare> criticalSquares = new HashSet<>();
            for (int i = 0; i < numberOfCriticalSquares; i++) {
                int randomNumX = (int) (generator.nextDouble()*100000);
                int randomNumY = (int) (generator.nextDouble()*100000);
                criticalSquares.add(new CriticalSquare(randomNumX % gridMapX, randomNumY % gridMapY));
            }
            busMap.criticalSquares = criticalSquares;
            busMap.uncoveredCriticalSquares = criticalSquares;

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return busMap;
    }

    static BusMap parseXML(String path, int gridMapX, int gridMapY) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        BusMap busMap = new BusMap();

        try {

            // optional, but recommended
            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(path));

            // optional, but recommended
            // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

//            System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
//            System.out.println("------");

            NodeList list = doc.getElementsByTagName("BusRoute");

            for (int temp = 0; temp < list.getLength(); temp++) {
                BusRoute busRoute = new BusRoute();

                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;

                    // get staff's attribute
                    busRoute.routeID = Integer.parseInt(element.getAttribute("routeID"));

                    // get text
                    NodeList polyPointList = element.getElementsByTagName("PolyPoint");
                    for (int i = 0; i < polyPointList.getLength(); i++) {
                        busRoute.polyPoints.add(new Coordinates(
                                Float.parseFloat(polyPointList.item(i).getAttributes().item(0).getNodeValue())*gridMapX/4,
                                Float.parseFloat(polyPointList.item(i).getAttributes().item(1).getNodeValue())*gridMapY/4));
                    }
                }
                busMap.busRoutes.add(busRoute);
            }
            Set<CriticalSquare> criticalSquares = new HashSet<>();
            NodeList cqlist = doc.getElementsByTagName("CriticalSquare");
            for (int temp = 0; temp < cqlist.getLength(); temp++) {
                CriticalSquare cq = new CriticalSquare(Integer.parseInt(cqlist.item(temp).getAttributes().item(0).getNodeValue()), Integer.parseInt(cqlist.item(temp).getAttributes().item(1).getNodeValue()));
                criticalSquares.add(cq);
            }

            busMap.criticalSquares = criticalSquares;
            busMap.uncoveredCriticalSquares = criticalSquares;

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return busMap;
    }

    static BusMap parseTXT(String path) throws IOException {
        BusMap busMap = new BusMap();
        String currentLine;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        int lineIndex = 0;
        int gridMapX;
        int gridMapY;
        int numberOfBusRoutes;
        int currentBusRoutesIndex = 0;
        float radius;

        // first line
        currentLine = bufferedReader.readLine();
        String[] splitString = currentLine.split(" ");
        gridMapX = Integer.parseInt(splitString[0]);
        gridMapY = Integer.parseInt(splitString[1]);
        radius = Float.parseFloat(splitString[2]);
        busMap.radius = radius;

        // second line
        currentLine = bufferedReader.readLine();
        numberOfBusRoutes = Integer.parseInt(currentLine);

        // bus routes parsing
        BusRoute busRoute = new BusRoute();
        while ((currentLine = bufferedReader.readLine()) != null) {
            splitString = currentLine.split(" ");
            if (splitString.length == 1) {
                if (currentBusRoutesIndex > 0) {
                    busMap.busRoutes.add(busRoute);
                }
                if (currentBusRoutesIndex == numberOfBusRoutes) {
                    break;
                } else {
                    busRoute = new BusRoute();
                    busRoute.routeID = currentBusRoutesIndex;
                }
                currentBusRoutesIndex++;
            } else {
                busRoute.polyPoints.add(new CriticalPoint(Float.parseFloat(splitString[0]), Float.parseFloat(splitString[1])));
            }
        }
        while ((currentLine = bufferedReader.readLine()) != null) {
            splitString = currentLine.split(" ");
            busMap.criticalSquares.add(new CriticalSquare(Integer.parseInt(splitString[0]), Integer.parseInt(splitString[1])));
        }
        busMap.uncoveredCriticalSquares = new HashSet<>(busMap.criticalSquares);
        return busMap;
    }
}
