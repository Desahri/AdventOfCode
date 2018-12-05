package adventofcode2k18;

import java.io.IOException;
import java.util.ArrayList;

public class Day05 {

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        ArrayList<String> in = GetInput.get("05.txt");

        //solution part 1
        /*
        String chemical = in.get(0);
        System.out.println("answer: " + react(chemical).length());
        */

        //solution part 2
        char[] ALL_SMALL = new char[26];
        ALL_SMALL[0] = 'a';
        for (int i = 1; i < 26; i++) {
            ALL_SMALL[i] = (char) ('a' + i);
        }

        String chemical = in.get(0);
        char[] chemArr = chemical.toCharArray();
        int smallest = Integer.MAX_VALUE;
        for (char letter : ALL_SMALL) {
            char[] chemArr2 = removeAllChars(chemArr, letter);
            int length = react(new String(chemArr2)).length();
            if (length < smallest) {
                smallest = length;
            }
        }
        System.out.println("answer: " + smallest);
    }

    static String react(String chem) {
        boolean hasChanged = false;
        char[] chemChars = chem.toCharArray();
        for (int i = 0; i < chemChars.length - 1; i++) {
            if ((Character.isLowerCase(chemChars[i]) && Character.isUpperCase(chemChars[i + 1]) && Character.toLowerCase(chemChars[i + 1]) == chemChars[i]) ||
                    (Character.isUpperCase(chemChars[i]) && Character.isLowerCase(chemChars[i + 1]) && Character.toUpperCase(chemChars[i + 1]) == chemChars[i])) {
                chemChars = removeElements(chemChars, i);
                hasChanged = true;
                i -= (i == 0 ? 1 : 2);
            }
        }
        if (hasChanged) {
            return react(new String(chemChars));
        }
        return chem;
    }

    static char[] removeElements(char[] chem, int index) {
        char[] r = new char[chem.length - 2];
        int passedIndex = 0;
        for (int i = 0; i < r.length; i++) {
            if (i == index) {
                passedIndex = 2;
            }
            r[i] = chem[i + passedIndex];
        }
        return r;
    }

    static char[] removeAllChars(char[] chem, char c) {
        char small = Character.toLowerCase(c);
        char large = Character.toUpperCase(c);
        char[] r = new char[chem.length - countChars(chem, small) - countChars(chem, large)];
        int passedIndex = 0;
        for (int i = 0; i < chem.length; i++) {
            if (chem[i] == small || chem[i] == large) {
                passedIndex++;
                continue;
            }
            r[i - passedIndex] = chem[i];
        }
        return r;
    }

    static int countChars(char[] chem, char c) {
        int count = 0;
        for (char x : chem) {
            if (x == c) {
                count++;
            }
        }
        return count;
    }
}