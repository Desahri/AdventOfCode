package adventofcode2k18;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class Day07 {

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        ArrayList<String> in = GetInput.get("07.txt");

        //first create all nodes
        ArrayList<Character> unhandled = new ArrayList<>();
        for (String line : in) {
            String[] split = line.split(" ");
            char before = split[1].charAt(0);
            char after = split[7].charAt(0);

            if (Node.nodes.containsKey(before)) {
                Node.nodes.get(before).willActivate.add(after);
            } else {
                unhandled.add(before);
                Node.nodes.put(before, new Node(before, true, null, after));
            }
            if (Node.nodes.containsKey(after)) {
                Node n = Node.nodes.get(after);
                n.isActive = false;
                n.needsActive.add(before);
            } else {
                unhandled.add(after);
                Node.nodes.put(after, new Node(after, false, before, null));
            }
        }

        System.out.print("answer: ");
        //add all active nodes to the priority queue (prioitizes a over b over c over d etc.)
        PriorityQueue<Character> pq = new PriorityQueue<>();
        for (char key : Node.nodes.keySet()) {
            Node n = Node.nodes.get(key);
            if (n.isActive) {
                pq.add(key);
            }
        }

        //while the queue is not empty, print out active nodes and remove them from the queue,
        //then check if nodes that depend on the just removed node are now able to be active
        //if so, add them to the queue
        while (!pq.isEmpty()) {
            char c = pq.remove();
            System.out.print(c);
            unhandled.remove(Character.valueOf(c));
            Node.nodes.get(c).isActive = true;
            for (char activates : Node.nodes.get(c).willActivate) {
                if (Node.nodes.get(activates).canActivate()) {
                    pq.add(activates);
                }
            }
        }
    }

    static class Node {
        static HashMap<Character, Node> nodes = new HashMap<>();

        final char id;
        boolean isActive;
        List<Character> needsActive = new ArrayList<>();
        List<Character> willActivate = new ArrayList<>();

        Node(char id, boolean isActive, Character activator, Character activates) {
            this.id = id;
            this.isActive = isActive;
            if (activator != null) {
                needsActive.add(activator);
            }
            if (activates != null) {
                willActivate.add(activates);
            }
        }

        boolean canActivate() {
            for (char c : needsActive) {
                if (!nodes.get(c).isActive) {
                    return false;
                }
            }
            return true;
        }
    }
}