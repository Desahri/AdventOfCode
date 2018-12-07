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

        //solution part 1
        /*
        //first create all nodes
        for (String line : in) {
            String[] split = line.split(" ");
            char before = split[1].charAt(0);
            char after = split[7].charAt(0);

            if (Node.nodes.containsKey(before)) {
                Node.nodes.get(before).willActivate.add(after);
            } else {
                Node.nodes.put(before, new Node(before, true, null, after));
            }
            if (Node.nodes.containsKey(after)) {
                Node n = Node.nodes.get(after);
                n.isActive = false;
                n.needsActive.add(before);
            } else {
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
            Node.nodes.get(c).isActive = true;
            for (char activates : Node.nodes.get(c).willActivate) {
                if (Node.nodes.get(activates).canActivate()) {
                    pq.add(activates);
                }
            }
        }
        */

        //solution part 2

        final int WORK_TIME = 60; //time it at least takes to work on a letter
        final int WORKERS = 5; //maximum number of workers

        //first create all nodes
        for (String line : in) {
            String[] split = line.split(" ");
            char before = split[1].charAt(0);
            char after = split[7].charAt(0);

            if (Node.nodes.containsKey(before)) {
                Node.nodes.get(before).willActivate.add(after);
            } else {
                Node.nodes.put(before, new Node(before, true, null, after));
            }
            if (Node.nodes.containsKey(after)) {
                Node n = Node.nodes.get(after);
                n.isActive = false;
                n.needsActive.add(before);
            } else {
                Node.nodes.put(after, new Node(after, false, before, null));
            }
        }

        //add all active nodes to the priority queue (prioitizes a over b over c over d etc.)
        PriorityQueue<Character> pq = new PriorityQueue<>();
        for (char key : Node.nodes.keySet()) {
            Node n = Node.nodes.get(key);
            if (n.isActive) {
                pq.add(key);
            }
        }
        int time = 0; //current time in seconds
        int working = 0; //current number of active workers
        ArrayList<Node> handling = new ArrayList<>(); //all nodes being worked on by workers

        //for each node that is being handled, check if done
        //if done, set the node to active, remove from the handling list and check for each character that
        //the finished node may activate if it can actually be activated and add it to the queue if that is the case
        //while the queue is not empty and there are workers available, remove character from queue,
        //add the timeDone value corresponding to the node of that character and add the node to the handling list
        //finally increase the time
        do {
            //fill queue
            for (int i = 0; i < handling.size(); i++) {
                Node n = handling.get(i);
                if (n.timeDone == time) {
                    working--;
                    n.isActive = true;
                    handling.remove(n);
                    for (char activates : n.willActivate) {
                        if (Node.nodes.get(activates).canActivate()) {
                            pq.add(activates);
                        }
                    }
                    i--;
                }
            }
            //empty queue
            while (!pq.isEmpty() && working < WORKERS) {
                char c = pq.remove();
                Node n = Node.nodes.get(c);
                n.timeDone = time + WORK_TIME + c - 64; //65 is the int value of A
                handling.add(n);
                working++;
            }
            time++;
        } while (working > 0);//should stop once no more nodes
        System.out.println("answer: " + (time - 1));
    }

    static class Node {
        static HashMap<Character, Node> nodes = new HashMap<>();

        final char id;
        boolean isActive;
        List<Character> needsActive = new ArrayList<>();
        List<Character> willActivate = new ArrayList<>();

        //USED FOR PART 2
        int timeDone = Integer.MAX_VALUE;

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