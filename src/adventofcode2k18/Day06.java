package adventofcode2k18;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day06 {

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        ArrayList<String> in = GetInput.get("06.txt");
        //solution part 1
        /*
        for(String s : in) {
            String[] coord = s.split(", ");
            new Coordinate(
                    Integer.parseInt(coord[0]),
                    Integer.parseInt(coord[1])
            );
        }
        Coordinate.generateField();
        System.out.println("answer: " + Coordinate.getLargestFiniteArea());
        */

        //solution part 2
        for (String s : in) {
            String[] coord = s.split(", ");
            new Coordinate(
                    Integer.parseInt(coord[0]),
                    Integer.parseInt(coord[1])
            );
        }
        Coordinate.generateTotalDistances();
        System.out.println("answer: " + Coordinate.getNrCloseRegions());
    }

    /**
     * class used to represent a coordinate
     * contains a static list of all coordinates ever created
     */
    static class Coordinate {
        static final int MAX_TOTAL_DISTANCE = 10000;
        static List<Coordinate> coordinates = new ArrayList<>();
        static int highestX = 0;
        static int highestY = 0;
        static int lowestX = Integer.MAX_VALUE;
        static int lowestY = Integer.MAX_VALUE;
        static int[][] closestCoord;
        static int[][] shortestDistance;
        static int[][] totalDistance;
        final int id;
        final int x;
        final int y;

        Coordinate(int x, int y) {
            this.id = coordinates.size();
            this.x = x;
            this.y = y;
            if (highestX < x + 1) highestX = x + 1;
            if (highestY < y + 1) highestY = y + 1;

            if (lowestX > x) lowestX = x;
            if (lowestY > y + 1) lowestY = y;
            coordinates.add(this);
        }

        /**
         * USED FOR PART 1
         * <p>
         * fills the closestCoord 2dArray (represents the id of a Coordinate closest to that point)
         * and the shortestDistance 2dArray (represents the shortest distance from a point to this point)
         * a point is represented in both arrays as 2dArray[x][y]
         */
        static void generateField() {
            closestCoord = new int[highestX][highestY];
            shortestDistance = new int[highestX][highestY];

            Coordinate coord = coordinates.get(0);
            for (int x = 0; x < highestX; x++) {
                for (int y = 0; y < highestY; y++) {
                    closestCoord[x][y] = coord.id;
                    shortestDistance[x][y] = Math.abs(coord.x - x) + Math.abs(coord.y - y);
                }
            }
            for (Coordinate c : coordinates) {
                if (c == coord) {
                    continue;
                }
                for (int x = 0; x < highestX; x++) {
                    for (int y = 0; y < highestY; y++) {
                        int dist = Math.abs(c.x - x) + Math.abs(c.y - y);
                        if (shortestDistance[x][y] == dist) closestCoord[x][y] = -1;
                        else if (shortestDistance[x][y] > dist) {
                            shortestDistance[x][y] = dist;
                            closestCoord[x][y] = c.id;
                        }
                    }
                }
            }
        }

        /**
         * USED FOR PART 1
         * <p>
         * returns the size of the largest finite area
         * an area is a collection of points where exactly one Coordinate is the closest to
         */
        static int getLargestFiniteArea() {
            int[] area = new int[coordinates.size()];
            boolean[] isInfinite = new boolean[coordinates.size()];
            for (int x = 0; x < highestX; x++) {
                for (int y = 0; y < highestY; y++) {
                    if (closestCoord[x][y] == -1) {
                        continue;
                    }
                    if (x == 0 || x == highestX - 1 ||
                            y == 0 || y == highestY - 1) {
                        isInfinite[closestCoord[x][y]] = true;
                    }
                    area[closestCoord[x][y]]++;
                }
            }
            int max = 0;
            for (int i = 0; i < area.length; i++) {
                if (!isInfinite[i] && area[i] > max) max = area[i];
            }
            return max;
        }

        /**
         * USED FOR PART 2
         * <p>
         * fills the totalDistance 2dArray (represents the sum of all manhattan distances from each coordinate)
         */
        static void generateTotalDistances() {
            totalDistance = new int[lowestX + MAX_TOTAL_DISTANCE][lowestY + MAX_TOTAL_DISTANCE];
            for (int x = 0; x < totalDistance.length; x++) {
                for (int y = 0; y < totalDistance[x].length; y++) {
                    for (Coordinate c : coordinates) {
                        int dist = Math.abs(c.x - x) + Math.abs(c.y - y);
                        totalDistance[x][y] += dist;
                    }
                }
            }
        }

        /**
         * USED FOR PART 2
         * <p>
         * returns the number of points that have a total distance smaller than the max total distance
         */
        static int getNrCloseRegions() {
            int nr = 0;
            for (int x = 0; x < totalDistance.length; x++) {
                for (int y = 0; y < totalDistance[x].length; y++) {
                    if (totalDistance[x][y] < MAX_TOTAL_DISTANCE) {
                        nr++;
                    }
                }
            }
            return nr;
        }
    }
}