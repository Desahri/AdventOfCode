package adventofcode2k18;

import java.io.IOException;
import java.util.*;

public class Day01 {
	
	public static void main(String[] args) throws IOException {
		ArrayList<String> in = GetInput.get("01.txt");
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
	}
}