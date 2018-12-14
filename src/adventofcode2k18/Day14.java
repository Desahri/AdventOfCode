package adventofcode2k18;

import java.io.IOException;
import java.util.ArrayList;

public class Day14 {

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        ArrayList<String> in = GetInput.get("14.txt");

        //solution part 1
        /*
        int recipeNr = Integer.parseInt(in.get(0));

        StringBuilder recipes = new StringBuilder("37");
        int elf1 = 0;
        int elf2 = 1;

        while (recipeNr + 10 > recipes.length()) {
            int combi = (recipes.charAt(elf1) - '0') + (recipes.charAt(elf2) - '0');
            recipes.append(combi);
            elf1 = (elf1 + (recipes.charAt(elf1) - '0') + 1) % recipes.length();
            elf2 = (elf2 + (recipes.charAt(elf2) - '0') + 1) % recipes.length();
        }
        System.out.println("answer: " + recipes.substring(recipeNr, recipeNr + 10));
        */

        //solution part 2
        String recipeNr = in.get(0);

        StringBuilder recipes = new StringBuilder("37");
        int elf1 = 0;
        int elf2 = 1;

        int i = 0;
        while (/*!recipes.toString().contains(recipeNr)*/ i < 500000000) { //code that was commented out takes too long
            int combi = (recipes.charAt(elf1) - '0') + (recipes.charAt(elf2) - '0');
            recipes.append(combi);
            elf1 = (elf1 + (recipes.charAt(elf1) - '0') + 1) % recipes.length();
            elf2 = (elf2 + (recipes.charAt(elf2) - '0') + 1) % recipes.length();
            i++;
        }
        for (int x = 0; x < recipes.length() - recipeNr.length(); x++) {
            if (recipes.substring(x, x + recipeNr.length()).equals(recipeNr)) {
                System.out.println("answer: " + (x));
                break;
            }
        }
    }
}