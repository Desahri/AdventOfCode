package adventofcode2k18;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day12 {

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        ArrayList<String> in = GetInput.get("12.txt");

        //solution part 1
        /*
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
        */

        //solution part 2
        String initialStateString = in.get(0).replaceAll("initial state: ", "");
        for (int i = 2; i < in.size(); i++) {
            String[] arg = in.get(i).split(" ");
            Rule.addRule(arg[0], arg[2].charAt(0));
        }
        int lowestPot = 0;
        boolean[] pots = new boolean[initialStateString.length()];
        for (int i = 0; i < initialStateString.length(); i++) {
            pots[i] = (initialStateString.charAt(i) == '#');
        }

        boolean[] nextPots;
        boolean[] fivePots = new boolean[5];
        int a = 0;
        while (a < 5000) {
            System.out.println(lowestPot);
            nextPots = new boolean[pots.length + 4];
            lowestPot -= 2;
            for (int i = 0; i < nextPots.length; i++) {
                fivePots[0] = i - 4 < pots.length && i - 4 >= 0 && pots[i - 4];
                fivePots[1] = i - 3 < pots.length && i - 3 >= 0 && pots[i - 3];
                fivePots[2] = i - 2 < pots.length && i - 2 >= 0 && pots[i - 2];
                fivePots[3] = i - 1 < pots.length && i - 1 >= 0 && pots[i - 1];
                fivePots[4] = i - 0 < pots.length && i - 0 >= 0 && pots[i - 0];

                nextPots[i] = Rule.changePotTo(fivePots);
            }
            Object[] o = trimPots(nextPots, lowestPot);
            nextPots = (boolean[]) o[0];
            lowestPot = (int) o[1];
            pots = nextPots;
            a++;
        }


        int sum = 0;
        for (int i = 0; i < pots.length; i++) {
            if (pots[i]) {
                sum += (i + lowestPot);
                System.out.print('#');
            } else {
                System.out.print('.');
            }
        }
        System.out.println("\nanswer: " + sum);
        System.out.println("Could not get the answer directly, but a pattern arose\nreal answer: " + (sum / 1000) +
                "0000000" + (sum % 1000));

    }

    static Object[] trimPots(boolean[] pots, int lowestPot) {
        int lowest = 0;
        int highest = 0;
        for (int i = 0; i < pots.length; i++) {
            if (pots[i]) {
                lowestPot += i;
                lowest = i;
                break;
            }
        }

        for (int i = pots.length - 1; i >= 0; i--) {
            if (pots[i]) {
                highest = i;
                break;
            }
        }

        boolean[] newPots = new boolean[highest - lowest + 1];

        for (int i = 0; i < newPots.length; i++) {
            newPots[i] = pots[i + lowest];
        }

        return new Object[]{newPots, lowestPot};
    }

    static void printPots(boolean[] pots) {
        for (boolean b : pots) {
            System.out.print(b ? '#' : '.');
        }
        System.out.println();
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
            assert (pot1.length == pot2.length);
            for (int i = 0; i < pot1.length; i++) {
                if (pot1[i] != pot2[i]) {
                    return false;
                }
            }
            return true;
        }
    }
}