import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class BusRoute {
    boolean chosen = false;
    int routeID;
    List<Coordinates> polyPoints = new ArrayList<>();
    List<CriticalPoint> criticalPoints = new ArrayList<>();
    CriticalPoint criticalPoint1;
    CriticalPoint criticalPoint2;

    void getLineSegmentsIntersection(float p0_x, float p0_y, float p1_x, float p1_y,
                                     float p2_x, float p2_y, float p3_x, float p3_y)
    {
        float s1_x, s1_y, s2_x, s2_y;
        s1_x = p1_x - p0_x;     s1_y = p1_y - p0_y;
        s2_x = p3_x - p2_x;     s2_y = p3_y - p2_y;

        float s, t;
        s = (-s1_y * (p0_x - p2_x) + s1_x * (p0_y - p2_y)) / (-s2_x * s1_y + s1_x * s2_y);
        t = ( s2_x * (p0_y - p2_y) - s2_y * (p0_x - p2_x)) / (-s2_x * s1_y + s1_x * s2_y);

        if (s >= 0 && s <= 1 && t >= 0 && t <= 1) // collision
        {
            if (criticalPoint1.x == -1) {
//                criticalPoint1 = new CriticalPoint(p0_x + (t * s1_x), p0_y + (t * s1_y));
                criticalPoint1.x = p0_x + (t * s1_x);
                criticalPoint1.y = p0_y + (t * s1_y);
            }
            else {
//                criticalPoint2 = new CriticalPoint(p0_x + (t * s1_x), p0_y + (t * s1_y));
                criticalPoint2.x = p0_x + (t * s1_x);
                criticalPoint2.y = p0_y + (t * s1_y);
            }
        }
    }

    void getLineSegmentAndCircleIntersection(float x1, float y1, float x2, float y2,
                                             float x0, float y0, float radius, Coordinates criticalSquare) {
        // line segments that join (x1, y1) and (x2, y2) can be represented as
        // x= t*x1 + (1−t)*x2 and y= t*y1 + (1−t)*y2, with 0<=t<=1
        float b = 2*((x2 - x0)*(x1 - x2) + (y1 - y0)*(y1 - y2));
        float a = (float) (pow((x1 - x2),2) + pow((y1 - y2), 2));
        float c = (float) (pow((x2 - x0), 2) + pow((y2 - y0), 2) - pow(radius, 2));
        float delta = (float) pow(b, 2) - 4*a*c;

        if (delta >= 0) { // line collision
            float t1 = (float) (-b + sqrt(delta))/2*a;
            float t2 = (float) (-b - sqrt(delta))/2*a;

            if (t1 >= 0 && t1 <= 1) { // line segment collision
                CriticalPoint t1Coor = new CriticalPoint(t1*x1 + (1 - t1)*x2, t1*y1 + (1 - t1)*y2);
                if (!(t1Coor.x > criticalSquare.x && t1Coor.x < criticalSquare.x + 1)
                        && !(t1Coor.y > criticalSquare.y && t1Coor.y < criticalSquare.y + 1)) {
                    if (criticalPoint1.x == -1) {
                        criticalPoint1 = t1Coor;
                    }
                    else {
                        criticalPoint2 = t1Coor;
                    }
                }
            }
            if (t2 >= 0 && t2 <= 1) { // line segment collision
                CriticalPoint t2Coor = new CriticalPoint(t2*x1 + (1 - t2)*x2, t2*y1 + (1 - t2)*y2);
                if (!(t2Coor.x > criticalSquare.x && t2Coor.x < criticalSquare.x + 1)
                        && !(t2Coor.y > criticalSquare.y && t2Coor.y < criticalSquare.y + 1)) {
                    if (criticalPoint1.x == -1) {
                        criticalPoint1 = t2Coor;
                    }
                    else {
                        criticalPoint2 = t2Coor;
                    }
                }
            }
        }
    }

    void isPointInCriticalZone(float x1, float y1, Coordinates criticalSquare,
                               float radius) {
        float x0 = criticalSquare.x;
        float y0 = criticalSquare.y;

        if ((x1 <= x0 + 1 && x1 >= x0 && y1 <= y0 + 1 + radius && y1 >= y0 - radius)
                || (x1 <= x0 + 1 + radius && x1 >= x0 - radius && y1 <= y0 + 1 && y1 >= y0)
                || sqrt(pow(x1 - x0, 2) + pow(y1 - y0, 2)) <= radius
                || sqrt(pow(x1 - x0, 2) + pow(y1 - y0 - 1, 2)) <= radius
                || sqrt(pow(x1 - x0 - 1, 2) + pow(y1 - y0 - 1, 2)) <= radius
                || sqrt(pow(x1 - x0 - 1, 2) + pow(y1 - y0, 2)) <= radius) {
            if (criticalPoint1.x == -1) {
                criticalPoint1.x = x1;
                criticalPoint1.y = y1;
            } else {
                criticalPoint2.x = x1;
                criticalPoint2.y = y1;
            }
        }
//        } else if ((x1 <= x0 + 1 + radius && x1 >= x0 - radius && y1 <= y0 + 1 && y1 >= y0)
//                || sqrt(pow(x1 - x0, 2) + pow(y1 - y0, 2)) <= radius
//                || sqrt(pow(x1 - x0, 2) + pow(y1 - y0 - 1, 2)) <= radius
//                || sqrt(pow(x1 - x0 - 1, 2) + pow(y1 - y0 - 1, 2)) <= radius
//                || sqrt(pow(x1 - x0 - 1, 2) + pow(y1 - y0, 2)) <= radius) {
//            if (criticalPoint1.x == 0) {
//                criticalPoint1.x = x1;
//                criticalPoint1.y = y1;
//            } else {
//                criticalPoint2.x = x1;
//                criticalPoint2.y = y1;
//            }
//        }
//        } else if (sqrt(pow(x1 - x0, 2) + pow(y1 - y0, 2)) <= radius
//                || sqrt(pow(x1 - x0, 2) + pow(y1 - y0 - 1, 2)) <= radius
//                || sqrt(pow(x1 - x0 - 1, 2) + pow(y1 - y0 - 1, 2)) <= radius
//                || sqrt(pow(x1 - x0 - 1, 2) + pow(y1 - y0, 2)) <= radius) {
//            if (criticalPoint1.x == 0) {
//                criticalPoint1.x = x1;
//                criticalPoint1.y = y1;
//            }
//            else {
//                criticalPoint2.x = x1;
//                criticalPoint2.y = y1;
//            }
//        }
    }

    void calculateIntervals(CriticalSquare criticalSquare, float r) {
        for (int i = 0; i < polyPoints.size() - 1; i++) {
            this.criticalPoint1 = new CriticalPoint(-1, -1);
            this.criticalPoint2 = new CriticalPoint(-1, -1);
            // bottom line
            getLineSegmentsIntersection(polyPoints.get(i).x, polyPoints.get(i).y, polyPoints.get(i + 1).x, polyPoints.get(i + 1).y,
                    criticalSquare.x, criticalSquare.y - r, criticalSquare.x + 1, criticalSquare.y - r);

            // top line
            getLineSegmentsIntersection(polyPoints.get(i).x, polyPoints.get(i).y, polyPoints.get(i+1).x, polyPoints.get(i+1).y,
                    criticalSquare.x, criticalSquare.y + 1 + r, criticalSquare.x + 1,
                    criticalSquare.y + 1 + r);

            // left line
            getLineSegmentsIntersection(polyPoints.get(i).x, polyPoints.get(i).y, polyPoints.get(i+1).x, polyPoints.get(i+1).y,
                    criticalSquare.x - r, criticalSquare.y, criticalSquare.x - r, criticalSquare.y + 1);

            // right line
            getLineSegmentsIntersection(polyPoints.get(i).x, polyPoints.get(i).y, polyPoints.get(i+1).x, polyPoints.get(i+1).y,
                    criticalSquare.x + 1 + r, criticalSquare.y, criticalSquare.x + 1 + r,
                    criticalSquare.y + 1);

            // bottom left corner
            getLineSegmentAndCircleIntersection(polyPoints.get(i).x, polyPoints.get(i).y, polyPoints.get(i+1).x, polyPoints.get(i+1).y,
                    criticalSquare.x, criticalSquare.y, r, criticalSquare);

            // top left corner
            getLineSegmentAndCircleIntersection(polyPoints.get(i).x, polyPoints.get(i).y, polyPoints.get(i+1).x, polyPoints.get(i+1).y,
                    criticalSquare.x, criticalSquare.y + 1, r, criticalSquare);

            // bottom right corner
            getLineSegmentAndCircleIntersection(polyPoints.get(i).x, polyPoints.get(i).y, polyPoints.get(i+1).x, polyPoints.get(i+1).y,
                    criticalSquare.x + 1, criticalSquare.y, r, criticalSquare);

            // top right corner
            getLineSegmentAndCircleIntersection(polyPoints.get(i).x, polyPoints.get(i).y, polyPoints.get(i+1).x, polyPoints.get(i+1).y,
                    criticalSquare.x + 1, criticalSquare.y + 1, r, criticalSquare);

            // check if polyline start point is in the critical zone
            isPointInCriticalZone(polyPoints.get(i).x, polyPoints.get(i).y, criticalSquare, r);
            isPointInCriticalZone(polyPoints.get(i+1).x, polyPoints.get(i+1).y, criticalSquare, r);

            // pushing critical points to list
            if (criticalPoint1.x != -1 && criticalPoint2.x != -1) {
                if (pow(criticalPoint1.x - polyPoints.get(i).x, 2) + pow(criticalPoint1.y - polyPoints.get(i).y, 2)
                        > pow(criticalPoint2.x - polyPoints.get(i).x, 2) + pow(criticalPoint2.y - polyPoints.get(i).y, 2)) {
                    criticalPoints.add(new CriticalPoint(false, criticalPoint1.x, criticalPoint1.y, i, criticalSquare));
                    criticalPoints.add(new CriticalPoint(true, criticalPoint2.x, criticalPoint2.y, i, criticalSquare));
                } else {
                    criticalPoints.add(new CriticalPoint(true, criticalPoint1.x, criticalPoint1.y, i, criticalSquare));
                    criticalPoints.add(new CriticalPoint(false, criticalPoint2.x, criticalPoint2.y, i, criticalSquare));
                }
            }  else if (criticalPoint1.x != -1 && criticalPoint2.x == -1) {
                criticalPoints.add(new CriticalPoint(false, criticalPoint1.x, criticalPoint1.y, i, criticalSquare));
                criticalPoints.add(new CriticalPoint(true, criticalPoint1.x, criticalPoint1.y, i, criticalSquare));
            } else if (criticalPoint1.x == -1 && criticalPoint2.x != -1) {
                criticalPoints.add(new CriticalPoint(false, criticalPoint2.x, criticalPoint2.y, i, criticalSquare));
                criticalPoints.add(new CriticalPoint(true, criticalPoint2.x, criticalPoint2.y, i, criticalSquare));
            }
        }
    }
}