package adventofcode2k18;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GetInput {
	private GetInput() {
		
	}
	static ArrayList<String> get(String textfileName) throws IOException {
		System.out.println("input path:\n" + textfileName + "\n");
		String path = GetInput.class.getClassLoader().getResource(textfileName).toString();
		BufferedReader in = new BufferedReader(new FileReader(path.substring(5)));
		
		String line;
		ArrayList<String> ret = new ArrayList<>();
		while ((line = in.readLine()) != null) {
			ret.add(line);
		}
		System.out.println("output:");
		for(String s: ret) {
			if(s.length() > 20) {
				System.out.print(s.substring(0, 19));
				System.out.println("...");
			} else {
				System.out.println(s);
			}
		}
		System.out.println("--------------------");
		in.close();
		return ret;
	}
}
