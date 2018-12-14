package adventofcode2k18;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day13 {

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        ArrayList<String> in = GetInput.get("13.txt");

        int length = 0;
        for (String line : in) {
            if (line.length() > length) {
                length = line.length();
            }
        }
        Mine mine = new Mine(length, in.size());
        for (int i = 0; i < in.size(); i++) {
            if (i >= 107 && i <= 111) {
                System.out.println(in.get(i));
            }
            for (int j = 0; j < in.get(i).length(); j++) {
                mine.setTrack(j, i, in.get(i).charAt(j));
            }
        }
        while (!mine.moveCarts()) ;

    }

    static class Mine {
        int length;
        int height;
        Track[][] tracks;
        List<Cart> carts;
        int time;

        private enum Track {
            VERTICAL,
            HORIZONTAL,
            CROSS,
            LEFT_UP_RIGHT_DOWN,
            LEFT_DOWN_RIGHT_UP,
            NOTHING
        }

        Mine(int x, int y) {
            length = x;
            height = y;

            tracks = new Track[x][y];
            for (int i = 0; i < tracks.length; i++) {
                for (int j = 0; j < tracks[i].length; j++) {
                    tracks[i][j] = Track.NOTHING;
                }
            }
            carts = new ArrayList<>();

            time = 0;
        }

        void setTrack(int x, int y, char c) {
            switch (c) {
                case '>':
                case '<':
                    carts.add(new Cart(carts.size(), x, y, c));
                case '-':
                    tracks[x][y] = Track.HORIZONTAL;
                    break;
                case '^':
                case 'v':
                    carts.add(new Cart(carts.size(), x, y, c));
                case '|':
                    tracks[x][y] = Track.VERTICAL;
                    break;
                case '\\':
                    tracks[x][y] = Track.LEFT_DOWN_RIGHT_UP;
                    break;
                case '/':
                    tracks[x][y] = Track.LEFT_UP_RIGHT_DOWN;
                    break;
                case '+':
                    tracks[x][y] = Track.CROSS;
                    break;
            }
        }

        boolean moveCarts() {
            System.out.println(time + ": moving carts...");
            for (Cart c : carts) {
                switch (c.direction) {
                    case UP:
                        c.moveUp();
                        break;
                    case DOWN:
                        c.moveDown();
                        break;
                    case LEFT:
                        c.moveLeft();
                        break;
                    case RIGHT:
                        c.moveRight();
                        break;
                }

                Track currentTrack = tracks[c.x][c.y];
                switch (currentTrack) {
                    case CROSS:
                        c.changeIntersectionDirection();
                        break;
                    case LEFT_UP_RIGHT_DOWN:
                        switch (c.direction) {
                            case UP:
                                c.changeDirection(Cart.Direction.RIGHT);
                                break;
                            case DOWN:
                                c.changeDirection(Cart.Direction.LEFT);
                                break;
                            case LEFT:
                                c.changeDirection(Cart.Direction.DOWN);
                                break;
                            case RIGHT:
                                c.changeDirection(Cart.Direction.UP);
                                break;
                        }
                        break;
                    case LEFT_DOWN_RIGHT_UP:
                        switch (c.direction) {
                            case UP:
                                c.changeDirection(Cart.Direction.LEFT);
                                break;
                            case DOWN:
                                c.changeDirection(Cart.Direction.RIGHT);
                                break;
                            case LEFT:
                                c.changeDirection(Cart.Direction.UP);
                                break;
                            case RIGHT:
                                c.changeDirection(Cart.Direction.DOWN);
                                break;
                        }
                        break;
                    case NOTHING:
                        System.out.println("Error at time " + time);
                        break;
                }
                if (checkCollisions()) return true;
            }
            time++;
            return false;
        }

        boolean checkCollisions() {
            for (Cart c1 : carts) {
                for (Cart c2 : carts) {
                    if (c1 != c2 && c1.x == c2.x && c1.y == c2.y) {
                        System.out.println("answer: " + c1.x + "," + c1.y + " at time " + time);
                        return true;
                    }
                }
            }
            return false;
        }
    }

    static class Cart {
        int id;

        int x;
        int y;

        Direction direction;

        IntersectionDirection nextDir;

        private enum Direction {
            UP,
            DOWN,
            LEFT,
            RIGHT
        }

        private enum IntersectionDirection {
            LEFT,
            STRAIGHT,
            RIGHT
        }

        Cart(int id, int x, int y, char c) {
            this.id = id;
            this.x = x;
            this.y = y;

            switch (c) {
                case '>':
                    direction = Direction.RIGHT;
                    break;
                case '<':
                    direction = Direction.LEFT;
                    break;
                case '^':
                    direction = Direction.UP;
                    break;
                case 'v':
                    direction = Direction.DOWN;
                    break;
            }

            nextDir = IntersectionDirection.LEFT;
        }

        void changeIntersectionDirection() {
            switch (nextDir) {
                case LEFT:
                    rotateLeft();
                    nextDir = IntersectionDirection.STRAIGHT;
                    break;
                case STRAIGHT:
                    nextDir = IntersectionDirection.RIGHT;
                    break;
                case RIGHT:
                    rotateRight();
                    nextDir = IntersectionDirection.LEFT;
                    break;
            }
        }

        void changeDirection(Direction dir) {
            direction = dir;
        }

        void rotateLeft() {
            switch (direction) {
                case UP:
                    changeDirection(Direction.LEFT);
                    break;
                case DOWN:
                    changeDirection(Direction.RIGHT);
                    break;
                case LEFT:
                    changeDirection(Direction.DOWN);
                    break;
                case RIGHT:
                    changeDirection(Direction.UP);
                    break;
            }
        }

        void rotateRight() {
            switch (direction) {
                case UP:
                    changeDirection(Direction.RIGHT);
                    break;
                case DOWN:
                    changeDirection(Direction.LEFT);
                    break;
                case LEFT:
                    changeDirection(Direction.UP);
                    break;
                case RIGHT:
                    changeDirection(Direction.DOWN);
                    break;
            }
        }

        void moveUp() {
            y--;
        }

        void moveDown() {
            y++;
        }

        void moveLeft() {
            x--;
        }

        void moveRight() {
            x++;
        }
    }
}