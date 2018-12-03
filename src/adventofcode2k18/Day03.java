package adventofcode2k18;

import java.io.IOException;
import java.util.ArrayList;

public class Day03 {

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        ArrayList<String> in = GetInput.get("03.txt");
        int[][] field = new int[1000][1000];
        for(String s :in) {
            String[] split = s.split(" ");
            String corner = split[2];
            String size = split[3];
            fillField(field, corner, size);
        }
        System.out.println(countOverlaps(field));
    }

    static void fillField(int[][] field, String corner, String size) {
        String[] cornerA = corner.split(",");
        String[] sizeA = size.split("x");
        int x = Integer.parseInt(cornerA[0]);
        int y = Integer.parseInt(cornerA[1].substring(0, cornerA[1].length()-1));

        int length = Integer.parseInt(sizeA[0]);
        int height = Integer.parseInt(sizeA[1]);

        for(int i = x; i < x+length; i++) {
            for(int j = y; j < y+height; j++) {
                field[i][j]++;
            }
        }
    }

    static int countOverlaps(int[][] field) {
        int r = 0;
        for(int[] ia : field) {
            for(int i : ia) {
                if(i > 1) {
                    r++;
                }
            }
        }
        return r;
    }
}