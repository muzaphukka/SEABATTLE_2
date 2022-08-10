import java.util.Arrays;
import java.util.Stack;
import java.util.Scanner;

public class Field {
    private int SIZE = 10;
    private Cell field[][] = new Cell[SIZE][SIZE];
    private String NAME;

    public Scanner in = new Scanner(System.in);

    public Field() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                field[i][j] = Cell.SEA;
            }
        }
        ;

    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getNAME() {
        return NAME;
    }

    private void setShip(int x, int y, int decks, int direction) {
        if (noShips(x, y, decks, direction)) {
            if (direction == 1) {
                if (x >= 0 & x < field.length) {
                    for (int i = 0; i < decks; i++) {
                        field[x][y] = Cell.DECK;
                        x++;
                    }
                } else {
                    System.out.println("координаты не соответсвуют возможностям поля");
                }
            } else {
                if (y >= 0 & y < field.length) {
                    for (int i = 0; i < decks; i++) {
                        field[x][y] = Cell.DECK;
                        y++;
                    }
                } else {
                    System.out.println("координаты не соответсвуют возможностям поля");
                }
            }
        } else {
            System.out.println("Невозможно разместить, мешают другие корабли.");
        }
    }

    public void setShips() {
        Stack<Integer> fleet = new Stack<>();
        //  fleet.push(1);
        //  fleet.push(1);
        //  fleet.push(1);
        //  fleet.push(1);
        //  fleet.push(2);
        //  fleet.push(2);
        //  fleet.push(2);
        fleet.push(3);
        //    fleet.push(3);
        // fleet.push(4);
        for (int i = 0; i < fleet.capacity(); i++) {
            if (fleet.isEmpty()) {
                break;
            }
            setShip(in.nextInt(), in.nextInt(), fleet.pop(), in.nextInt());
            print();
        }

    }


    private boolean noShips(int x, int y, int decks, int direction) {
        if (direction == 1) {
            return noShipsV(x, y, decks);
        } else {
            return noShipsG(x, y, decks);
        }
    }

    private boolean noShipsG(int x, int y, int decks) {
        if (y <= 0 || (x == 0 || field[x][y - 1] != Cell.SEA) || field[x - 1][y - 1] != Cell.SEA || field[x + 1][y - 1] != Cell.SEA) {
            return false;
        }
        if (y >= field.length - 2 || field[x][y + decks] != Cell.SEA || field[x - 1][y + decks] != Cell.SEA || field[x + 1][y + decks] != Cell.SEA) {
            return false;
        }
        for (int i = 0; i < decks; i++) {
            if (field[x + 1][y] != Cell.SEA || field[x - 1][y] != Cell.SEA) {
                return false;
            }
            y++;
        }
        return true;
    }

    private boolean noShipsV(int x, int y, int decks) {
        if (x <= 0 || field[x - 1][y] == Cell.DECK || field[x - 1][y - 1] == Cell.DECK || field[x - 1][y + 1] == Cell.DECK) {
            return false;
        }
        if (x >= field.length - 2 || field[x + decks][y] == Cell.DECK || field[x + decks][y - 1] == Cell.DECK || field[x + decks][y + 1] == Cell.DECK) {
            return false;
        }
        for (int i = 0; i < decks; i++) {
            if (field[x][y + 1] == Cell.DECK || field[x][y - 1] == Cell.DECK) {
                return false;
            }
            x++;
        }
        return true;
    }

    public void print() {
        System.out.print(" ");
        for (int i = 0; i < SIZE; i++) {
            System.out.print("  " + i);
        }
        System.out.println();
        for (int w = 0; w < field.length; w++) {
            System.out.print(w + " ");
            for (int h = 0; h < field.length; h++) {
                switch (this.field[w][h]) {
                    case SEA:
                        System.out.print("|~~");
                        break;
                    case DECK:
                        System.out.print("|[]");
                        break;
                    case HIT:
                        System.out.print("|))");
                        break;
                    case MISS:
                        System.out.print("|><");
                        break;
                    case DEAD:
                        System.out.print("|@@");
                        break;
                }

            }
            System.out.print("|");
            System.out.println();
        }

    }

    public void printBlind() {
        System.out.print(" ");
        for (int i = 0; i < SIZE; i++) {
            System.out.print("  " + i);
        }
        System.out.println();
        for (int w = 0; w < field.length; w++) {
            System.out.print(w + " ");
            for (int h = 0; h < field.length; h++) {
                switch (this.field[w][h]) {
                    case SEA:
                        System.out.print("|~~");
                        break;
                    case DECK:
                        System.out.print("|~~");
                        break;
                    case HIT:
                        System.out.print("|))");
                        break;
                    case MISS:
                        System.out.print("|><");
                        break;
                    case DEAD:
                        System.out.print("|@@");
                        break;
                }

            }
            System.out.print("|");
            System.out.println();
        }

    }

    public void shot(int y, int x) {
        switch (this.field[x][y]) {
            case SEA:
                field[x][y] = Cell.MISS;
                break;
            case DECK:
                field[x][y] = Cell.HIT;
                System.out.println("Поле игрока " + getNAME());
                printBlind();
                if (isDead(x, y)) {
                    deathV(x, y, 1);
                    deathV(x, y, -1);
                    deathG(x, y, 1);
                    deathG(x, y, -1);
                    if (loseCheck()) {
                        System.out.println("Поздравляю, " + NAME + " победил!");
                        print();
                    }
                    shot(in.nextInt(), in.nextInt());
                    System.out.println("Поле игрока " + getNAME());
                    printBlind();

                }
                shot(in.nextInt(), in.nextInt());
                System.out.println("Поле игрока " + getNAME());
                printBlind();
            case MISS:
                System.out.println("Упс, сюда уже стреляли, попробуйте снова!");
                //  shot(x,y);
                break;
            case DEAD:
                System.out.println("Этот корабль уже на дне....Успокойся, маньяк!");
                System.out.println("Жахни куда нибудь ещё");
                //   shot(x,y);
                break;
            case HIT:
                System.out.println("Ты сюда уже стрелял, причем успешно.");
                System.out.print("Попробуй куда нибудь рядом жахнуть, вдруг получится...");
                //    shot(x,y);
                break;

        }
    }

    public boolean isDead(int x, int y) {
        return (isDeadG(x, y, 1) & isDeadG(x, y, -1)) & (isDeadV(x, y, 1) & isDeadV(x, y, -1));
    }

    private boolean isDeadV(int x, int y, int direction) {
        if (y < 0 || y >= field.length || (field[x][y].equals(Cell.SEA) || field[x][y].equals(Cell.MISS))) {
            return true;
        }
        if (field[x][y].equals(Cell.HIT)) {
            return isDeadV(x, y + direction, direction);
        }
        return false;
    }


    private boolean isDeadG(int x, int y, int direction) {
        if (x < 0 || x >= field.length || (field[x][y].equals(Cell.SEA) || field[x][y].equals(Cell.MISS))) {
            return true;
        }
        if (field[x][y].equals(Cell.HIT)) {
            return isDeadG(x + direction, y, direction);
        }
        return false;
    }

    private void deathV(int x, int y, int direction) {
        if (field[x][y].equals(Cell.HIT) || field[x][y].equals(Cell.DEAD)) {
            field[x][y] = Cell.DEAD;
            if (x > 0 & x <= field.length - 1) {
                deathV(x + direction, y, direction);
            }
        }
    }

    private void deathG(int x, int y, int direction) {
        if (field[x][y].equals(Cell.HIT) || field[x][y].equals(Cell.DEAD)) {
            field[x][y] = Cell.DEAD;
            if (y > 0 & y <= field.length - 1) {
                deathG(x, y + direction, direction);
            }
        }
    }

    private boolean loseCheck() {
        if (Arrays.stream(field).anyMatch(Cell.DECK)) {
            return false;
        }
        return true;
    }


}
