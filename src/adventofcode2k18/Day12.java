package adventofcode2k18;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day12 {

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        ArrayList<String> in = GetInput.get("12.txt");

        String initialStateString = in.get(0).replaceAll("initial state: ", "");
        for (int i = 2; i < in.size(); i++) {
            String[] arg = in.get(i).split(" ");
            Rule.addRule(arg[0], arg[2].charAt(0));
        }
        int GENERATION = 20;
        int EXTRA_SIZE = 2 * (GENERATION + 1);

        boolean[] pots = new boolean[initialStateString.length() + 2 * EXTRA_SIZE];
        for (int i = EXTRA_SIZE; i < EXTRA_SIZE + initialStateString.length(); i++) {
            pots[i] = (initialStateString.charAt(i - EXTRA_SIZE) == '#');
        }
        boolean[] nextPots;
        boolean[] fivePots = new boolean[5];
        for (int gen = 0; gen < GENERATION; gen++) {
            nextPots = new boolean[initialStateString.length() + 2 * EXTRA_SIZE];
            for (int i = 2; i < pots.length - 2; i++) {
                fivePots[0] = pots[i - 2];
                fivePots[1] = pots[i - 1];
                fivePots[2] = pots[i];
                fivePots[3] = pots[i + 1];
                fivePots[4] = pots[i + 2];

                nextPots[i] = Rule.changePotTo(fivePots);
            }
            pots = nextPots;
        }


        int sum = 0;
        for (int i = 0; i < pots.length; i++) {
            if (pots[i]) {
                sum += (i - EXTRA_SIZE);
                //System.out.print('#');
            } else {
                //System.out.print('.');
            }
        }
        System.out.println("\nanswer: " + sum);
    }

    static class Rule {
        static List<Rule> rules = new ArrayList<>();
        boolean[] state;
        boolean result;

        private Rule(String before, char after) {
            state = new boolean[5];
            for (int i = 0; i < 5; i++) {
                state[i] = before.charAt(i) == '#';
            }
            result = after == '#';
        }

        static void addRule(String before, char after) {
            assert before.length() == 5;
            if (after != before.charAt(2)) {
                rules.add(new Rule(before, after));
            }
        }

        static boolean changePotTo(boolean[] pots) {
            assert pots.length == 5;
            for (Rule rule : rules) {
                if (isSame(rule.state, pots)) {
                    return rule.result;
                }
            }
            return pots[2];
        }

        static boolean isSame(boolean[] pot1, boolean[] pot2) {
            assert (pot1.length != pot2.length);
            for (int i = 0; i < pot1.length; i++) {
                if (pot1[i] != pot2[i]) {
                    return false;
                }
            }
            return true;
        }
    }
}