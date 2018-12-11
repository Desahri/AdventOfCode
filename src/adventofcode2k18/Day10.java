package adventofcode2k18;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day10 {

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        ArrayList<String> in = GetInput.get("10.txt");
        for (String star : in) {
            Sky.addStar(new Star(
                    Integer.parseInt(star.substring(10, 16).replaceAll(" ", "")),
                    Integer.parseInt(star.substring(18, 24).replaceAll(" ", "")),
                    Integer.parseInt(star.substring(36, 38).replaceAll(" ", "")),
                    Integer.parseInt(star.substring(40, 42).replaceAll(" ", ""))
            ));
        }
        for (int i = 0; i < 20000; i++) {
            Sky.printSky(i);
        }

    }

    static class Sky {
        static List<Star> stars = new ArrayList<>();

        static void addStar(Star star) {
            stars.add(star);
        }

        static boolean printSky(int t) {

            int minX = Integer.MAX_VALUE;
            int maxX = Integer.MIN_VALUE;
            int minY = Integer.MAX_VALUE;
            int maxY = Integer.MIN_VALUE;
            for (Star s : stars) {
                if (s.getX(t) > maxX) {
                    maxX = s.getX(t);
                } else if (s.getX(t) < minX) {
                    minX = s.getX(t);
                }
                if (s.getY(t) > maxY) {
                    maxY = s.getY(t);
                } else if (s.getY(t) < minY) {
                    minY = s.getY(t);
                }
            }

            if (maxY - minY < 30) { //assumption made about the message
                System.out.println(t + ":");
                for (int y = 0; y < maxY - minY + 1; y++) {
                    tag1:
                    for (int x = 0; x < maxX - minX + 1; x++) {
                        for (Star s : stars) {
                            if ((s.getX(t) - minX == x) && (s.getY(t) - minY == y)) {
                                System.out.print("#");
                                continue tag1;
                            }
                        }
                        System.out.print(".");
                    }
                    System.out.println();
                }
                System.out.println("--------------------------------------");
                return true;
            }
            return false;
        }

    }

    static class Star {
        private int x;
        private int y;
        private int xSpeed;
        private int ySpeed;

        Star(int x, int y, int xSpeed, int ySpeed) {
            this.x = x;
            this.y = y;
            this.xSpeed = xSpeed;
            this.ySpeed = ySpeed;
        }

        int getX(int t) {
            return x + t * xSpeed;
        }

        int getY(int t) {
            return y + t * ySpeed;
        }
    }
}