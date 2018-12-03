package adventofcode2k18;

import java.io.IOException;
import java.util.ArrayList;

public class Day02 {

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        ArrayList<String> in = GetInput.get("02.txt");
        int two = 0;
        int three = 0;

        for (String id : in) {
            if (hasExactlyTwo(id)) {
                two++;
            }
            if (hasExactlyThree(id)) {
                three++;
            }
        }

        //calculate checksum
        int checksum = two * three;
        System.out.println("answer: " + checksum);
    }

    /**
     * returns whether the strings has a character that appears exactly twice
     */
    static boolean hasExactlyTwo(String id) {
        for (char c : id.toCharArray()) {
            if (getCharCount(id, c) == 2) {
                return true;
            }
        }
        return false;
    }

    /**
     * returns whether the strings has a character that appears exactly three times
     */
    static boolean hasExactlyThree(String id) {
        for (char c : id.toCharArray()) {
            if (getCharCount(id, c) == 3) {
                return true;
            }
        }
        return false;
    }

    private static int getCharCount(String s, char c) {
        int nr = 0;
        for (char ch : s.toCharArray()) {
            if (ch == c) {
                nr++;
            }
        }
        return nr;
    }
}