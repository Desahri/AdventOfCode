package adventofcode2k18;

import java.io.IOException;
import java.util.ArrayList;

public class Day11 {

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        ArrayList<String> in = GetInput.get("11.txt");

        int serialNumber = Integer.parseInt(in.get(0));
        int[][] fuelCells = new int[300][300];
        for (int x = 0; x < 300; x++) {
            for (int y = 0; y < 300; y++) {
                int xVal = x + 1;
                int yVal = y + 1;

                int rackID = xVal + 10;

                int powerLevel1 = rackID * yVal;

                int powerLevel2 = powerLevel1 + serialNumber;

                int powerLevel3 = powerLevel2 * rackID;

                int powerLevel4 = powerLevel3 < 100 ? 0 : (powerLevel3 / 100) % 10;

                fuelCells[x][y] = powerLevel4 - 5;
            }
        }

        int[] best3x3 = getBestRegion(fuelCells);

        System.out.print("answer: " + best3x3[0] + "," + best3x3[1]);
    }

    static int[] getBestRegion(int[][] cellValues) {
        int maxVal = 0;
        int xC = 1;
        int yC = 1;
        for (int x = 0; x < 298; x++) {
            for (int y = 0; y < 298; y++) {
                int sum = sum3x3(x, y, cellValues);
                if (sum > maxVal) {
                    maxVal = sum;
                    xC = x + 1;
                    yC = y + 1;
                }
            }
        }
        return new int[]{xC, yC};
    }

    static int sum3x3(int x, int y, int[][] arr) {
        return arr[x][y] + arr[x + 1][y] + arr[x + 2][y] +
                arr[x][y + 1] + arr[x + 1][y + 1] + arr[x + 2][y + 1] +
                arr[x][y + 2] + arr[x + 1][y + 2] + arr[x + 2][y + 2];
    }
}