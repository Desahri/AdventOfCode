package adventofcode2k18;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day01 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> in = GetInput.get("01.txt");
        //solution part 1
		/*
		int ans = 0;
		for(String line : in) {
			String op = line.substring(0, 1);
			String nr = line.substring(1);
			assert (op.equals("+") || op.equals("-"));
			if(op.equals("+")) {
			    ans += Integer.parseInt(nr);
			    continue;
            }
            ans -= Integer.parseInt(nr);

		}
		System.out.println("answer: " + ans);
		*/

        //solution part 2
        int freq = 0;
        List<Integer> frequencies = new ArrayList<>();
        frequencies.add(freq);
        while (true) {
            for (String line : in) {
                String op = line.substring(0, 1);
                String nr = line.substring(1);
                assert (op.equals("+") || op.equals("-"));
                if (op.equals("+")) {
                    freq += Integer.parseInt(nr);
                } else {
                    freq -= Integer.parseInt(nr);
                }
                if (frequencies.contains(freq)) {
                    System.out.println("answer: " + freq);
                    return;
                }
                frequencies.add(freq);
            }
        }
    }
}