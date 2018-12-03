package adventofcode2k18;

import java.io.IOException;
import java.util.ArrayList;

public class Day02 {

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        ArrayList<String> in = GetInput.get("02.txt");
        //solution part 1
        /*
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
        */

        //solution part 2
        for (String id1 : in) {
            for(String id2 : in) {
                int index = oneDifferentIndex(id1, id2);
                if(!id1.equals(id2) && index != -1) {
                    System.out.print("answer: ");
                    char[] ca = id1.toCharArray();
                    for(int i = 0; i < id1.length(); i++) {
                        if(i != index) {
                            System.out.print(ca[i]);
                        }
                    }
                    System.out.println();
                    return;
                }
            }
        }
    }

    static int oneDifferentIndex(String s1, String s2) {
        assert s1.length() == s2.length();
        int difIndex = -1;
        char[] s1Arr = s1.toCharArray();
        char[] s2Arr = s2.toCharArray();
        for(int i = 0; i < s1Arr.length; i++) {
            if(difIndex == -1 && s1Arr[i] != s2Arr[i]) {
                difIndex = i;
            } else if(difIndex >= 0 && s1Arr[i] != s2Arr[i]) {
                return -1;
            }
        }
        return difIndex;
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