import com.google.ortools.sat.*;

import java.io.IOException;
import java.util.*;

public class CPSolverTesting {
    static {
        System.loadLibrary("jniortools");
    }
    public static void main(String args[]) throws IOException {
        Main main = new Main();
        BusMap busMap = main.parseTXT("C:\\Users\\duong\\IdeaProjects\\SensorOnBusProblem\\resource\\25x30_100_1.00.txt");
//        BusMap busMap = main.parseXML("C:\\Users\\duong\\CLionProjects\\SensorOnBusProblem\\resource\\BusMapSample.XML", 4, 4);
        solveSOBPMIP(busMap, (float) 1, 2, 2);
//        CpModel model = new CpModel();
//
//        int numVals = 2;
//
//        IntVar x = model.newIntVar(0, numVals - 1, "x");
//        IntVar y = model.newIntVar(0, numVals - 1, "y");
//        IntVar z = model.newIntVar(0, numVals - 1, "z");
//
//        ArrayList<IntVar> varList = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            IntVar temp = model.newIntVar(0, 1, Integer.toString(i));
//            varList.add(temp);
//        }
//        IntVar varListArray[] = new IntVar[varList.size()];
//        for (int i = 0; i < varList.size(); i++) {
//            varListArray[i] = varList.get(i);
//        }
//        model.addEquality(new SumOfVariables(varListArray), 3);
//
//
//        model.addDifferent(x, y);
//        IntVar[] sumList = {x, y, z};
//        model.maximize(new SumOfVariables(sumList));
//        CpSolver solver = new CpSolver();
//        CpSolverStatus status = solver.solve(model);
//        if (status == CpSolverStatus.OPTIMAL) {
//            System.out.println("x = " + solver.value(x));
//            System.out.println("y = " + solver.value(y));
//            System.out.println("z = " + solver.value(z));
//            System.out.println(solver.value(varList.get(0)));
//            System.out.println(solver.value(varList.get(1)));
//            System.out.println(solver.value(varList.get(2)));
//            System.out.println(solver.value(varList.get(3)));
//            System.out.println(solver.value(varList.get(4)));
//        }
    }

    public static int solveSOBPMIP(BusMap busMap, float r, int m, int k) {
        CpModel model = new CpModel();

        busMap.busMapInitEA(r);

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
            model.addEquality(new SumOfVariables(criticalPointVarListArray),
                    new ScalProd(new IntVar[]{busRouteVarList.get(busRouteVarList.size()-1)}, new long[]{k}));
        }
        IntVar busRouteVarListArray[] = new IntVar[busRouteVarList.size()];
        for (int i = 0; i < busRouteVarList.size(); i++) {
            busRouteVarListArray[i] = busRouteVarList.get(i);
        }
        model.addEquality(new SumOfVariables(busRouteVarListArray), m);

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
        System.out.println(status);
        if (status == CpSolverStatus.OPTIMAL) {
            int result = 0;
            for (IntVar busRouteVar : criticalSquaresVarList) {
                result = result + (int) solver.value(busRouteVar);
            }
            return result;
        }
        return 0;
    }
}
