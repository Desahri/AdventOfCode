package adventofcode2k18;

import java.io.IOException;
import java.util.ArrayList;

public class Day05 {

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        ArrayList<String> in = GetInput.get("05.txt");

        String chemical = in.get(0);
        System.out.println("answer: " + react(chemical).length());


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
}