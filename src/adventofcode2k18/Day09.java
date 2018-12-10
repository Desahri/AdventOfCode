package adventofcode2k18;

import java.io.IOException;
import java.util.ArrayList;

public class Day09 {

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        ArrayList<String> in = GetInput.get("09.txt");

        //solution part 1 and 2
        String[] data = in.get(0).split(" ");
        int players = Integer.parseInt(data[0]);
        int lastMarble = Integer.parseInt(data[6]);

        //USED FOR PART 2
        lastMarble *= 100;

        //init
        int[] scores = new int[players];
        MarbleRing marbleRing = new MarbleRing();

        for (int i = 1; i <= lastMarble; i++) {
            if (i * 100 % lastMarble == 0) System.out.println((i * 100) / lastMarble + "%");
            //if multiple of 23, add marble to score of current player and remove marble 7 places before and remove
            if (mod(i, 23) == 0) {
                int removed = marbleRing.remove();
                scores[((i - 1) % players)] += (i + removed);
            } else {
                marbleRing.add(i);
            }
            //System.out.println(marbleRing);
        }

        //print scores and find highest
        int r = 0;
        for (int i = 0; i < scores.length; i++) {
            int x = scores[i];
            System.out.println((i + 1) + ": " + x);
            if (x > r) {
                r = x;
            }
        }
        System.out.println("answer: " + r);
    }

    static int mod(int x, int n) {
        int r = x % n;
        if (r < 0) {
            r += n;
        }
        return r;
    }

    static class MarbleRing {
        ArrayList<Integer> marbleRing;

        int currentMarble = 0; //index

        MarbleRing() {
            marbleRing = new ArrayList<>();
            marbleRing.add(0);
        }

        //adds x depending on the current marble
        public void add(int x) {
            int i = currentMarble + 2;
            i = mod(i, size());
            if (i == 0) {
                marbleRing.add(x);
                currentMarble = size() - 1;
            } else {
                marbleRing.add(i, x);
                currentMarble = i;
            }
        }

        //removes assuming a 23rd element has reached
        public int remove() {
            int i = currentMarble - 7;
            i = mod(i, size());
            if (i == size() - 1) {
                currentMarble = 0;
            } else {
                currentMarble = i;
            }
            return marbleRing.remove(i);
        }

        public int size() {
            return marbleRing.size();
        }
    }
}