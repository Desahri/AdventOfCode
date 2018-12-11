package adventofcode2k18;

import java.io.IOException;
import java.util.ArrayList;

public class Day08 {

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        ArrayList<String> in = GetInput.get("08.txt");
        //solution part 1
        /*
        Node.createNode(in.get(0));

        int ans = 0;
        for (Node n : Node.nodes) {
            ans += n.getMetadataSum();
        }
        System.out.print("answer: " + ans);
        */

        //solution part 2
        Node root = (Node) Node.createNode(in.get(0))[1];
        System.out.print(root.getValue());
    }

    static class Node {
        static ArrayList<Node> nodes = new ArrayList<>();

        Node[] childs;
        int[] metadata;

        private Node() {

        }

        //object[0] trimmed string, object[1] the created node
        static Object[] createNode(String s) {
            Node n = new Node();
            String[] split = s.split(" ");
            n.childs = new Node[Integer.parseInt(split[0])];
            n.metadata = new int[Integer.parseInt(split[1])];
            s = buildString(split, 2);
            for (int i = 0; i < n.childs.length; i++) {
                Object[] o = createNode(s);
                s = (String) o[0];
                n.childs[i] = (Node) o[1];
            }
            split = s.split(" ");
            for (int i = 0; i < n.metadata.length; i++) {
                n.metadata[i] = Integer.parseInt(split[i]);
            }
            s = buildString(split, n.metadata.length);
            nodes.add(n);
            return new Object[]{s, n};
        }

        //puts all strings in the array in a single string, with spaces between it
        static String buildString(String[] sa, int index) {
            StringBuilder builder = new StringBuilder();
            for (int i = index; i < sa.length - 1; i++) {
                builder.append(sa[i]);
                builder.append(" ");
            }
            builder.append(sa[sa.length - 1]);
            return builder.toString();
        }

        int getMetadataSum() {
            int r = 0;
            for (int x : metadata) {
                r += x;
            }
            return r;
        }

        //USED FOR PART 2
        int getValue() {
            if (childs.length == 0) {
                return getMetadataSum();
            }
            int r = 0;
            for (int i : metadata) {
                if ((i - 1 < childs.length)) {
                    r += childs[i - 1].getValue();
                }
            }
            return r;
        }
    }
}