package adventofcode2k18;

import java.io.IOException;
import java.util.ArrayList;

public class Day03 {

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        ArrayList<String> in = GetInput.get("03.txt");
        //solution part 1
        /*
        int[][] field = new int[1000][1000];
        for(String s :in) {
            String[] split = s.split(" ");
            String corner = split[2];
            String size = split[3];
            fillField(field, corner, size);
        }
        System.out.println(countOverlaps(field));
        */

        //solution part 2
        class FabricArea {
            String id;
            int x;
            int y;
            int length;
            int height;

            FabricArea(String id, String corner, String size) {
                this.id = id;
                String[] cornerA = corner.split(",");
                String[] sizeA = size.split("x");
                this.x = Integer.parseInt(cornerA[0]);
                this.y = Integer.parseInt(cornerA[1].substring(0, cornerA[1].length() - 1));

                this.length = Integer.parseInt(sizeA[0]);
                this.height = Integer.parseInt(sizeA[1]);
            }

            boolean overlaps(FabricArea area) {
                if (area.x > this.x + this.length - 1 || area.y > this.y + this.height - 1) {
                    return false;
                }
                if (area.x + area.length - 1 < this.x || area.y + area.height - 1 < this.y) {
                    return false;
                }
                return true;
            }
        }
        FabricArea[] areas = new FabricArea[in.size()];
        int i = 0;
        for (String s : in) {
            String[] split = s.split(" ");
            String id = split[0];
            String corner = split[2];
            String size = split[3];
            areas[i] = new FabricArea(id, corner, size);
            i++;
        }
        area1:
        for (FabricArea ar : areas) {
            for (FabricArea ar2 : areas) {
                if (ar != ar2 && ar.overlaps(ar2)) {
                    continue area1;
                }
            }
            System.out.println(ar.id);
            return;
        }
    }

    static void fillField(int[][] field, String corner, String size) {
        String[] cornerA = corner.split(",");
        String[] sizeA = size.split("x");
        int x = Integer.parseInt(cornerA[0]);
        int y = Integer.parseInt(cornerA[1].substring(0, cornerA[1].length() - 1));

        int length = Integer.parseInt(sizeA[0]);
        int height = Integer.parseInt(sizeA[1]);

        for (int i = x; i < x + length; i++) {
            for (int j = y; j < y + height; j++) {
                field[i][j]++;
            }
        }
    }

    static int countOverlaps(int[][] field) {
        int r = 0;
        for (int[] ia : field) {
            for (int i : ia) {
                if (i > 1) {
                    r++;
                }
            }
        }
        return r;
    }
}