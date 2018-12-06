package adventofcode2k18;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day06 {

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        ArrayList<String> in = GetInput.get("06.txt");
        for(String s : in) {
            String[] coord = s.split(", ");
            new Coordinate(
                    Integer.parseInt(coord[0]),
                    Integer.parseInt(coord[1])
            );
        }
        Coordinate.generateField();
        System.out.println("answer: " + Coordinate.getLargestFiniteArea());

    }

    static class Coordinate {
        static List<Coordinate> coordinates = new ArrayList<>();
        static int highestX = 0;
        static int highestY = 0;

        static int[][] closestCoord;
        static int[][] shortestDistance;

        final int id;
        final int x;
        final int y;

        Coordinate(int x, int y) {
            this.id = coordinates.size();
            this.x = x;
            this.y = y;
            if(highestX < x+1) highestX = x+1;
            if(highestY < y+1) highestY = y+1;
            coordinates.add(this);
        }

        static void generateField() {
            closestCoord = new int[highestX][highestY];
            shortestDistance = new int[highestX][highestY];

            Coordinate coord = coordinates.get(0);
            for(int x = 0; x < highestX; x++) {
                for(int y = 0; y < highestY; y++) {
                    closestCoord[x][y] = coord.id;
                    shortestDistance[x][y] = Math.abs(coord.x - x) + Math.abs(coord.y - y);
                }
            }
            for(Coordinate c : coordinates) {
                if(c == coord) {
                    continue;
                }
                for(int x = 0; x < highestX; x++) {
                    for(int y = 0; y < highestY; y++) {
                        int dist = Math.abs(c.x - x) + Math.abs(c.y - y);
                        if(shortestDistance[x][y] == dist) closestCoord[x][y] = -1;
                        else if(shortestDistance[x][y] > dist) {
                            shortestDistance[x][y] = dist;
                            closestCoord[x][y] = c.id;
                        }
                    }
                }
            }
        }

        static int getLargestFiniteArea() {
            int[] area = new int[coordinates.size()];
            boolean[] isInfinite = new boolean[coordinates.size()];
            for(int x = 0; x < highestX; x++) {
                for (int y = 0; y < highestY; y++) {
                    if(closestCoord[x][y] == -1) {
                        continue;
                    }
                    if(x == 0 || x == highestX - 1 ||
                            y == 0 || y == highestY - 1) {
                        isInfinite[closestCoord[x][y]] = true;
                    }
                    area[closestCoord[x][y]]++;
                }
            }
            int max = 0;
            for(int i = 0; i < area.length; i++) {
                if(!isInfinite[i] && area[i] > max) max = area[i];
            }
            return max;
        }
    }
}