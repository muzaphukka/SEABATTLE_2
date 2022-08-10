


public class Field {
    private int SIZE = 10;
    private Cell field[][] = new Cell[SIZE][SIZE];


    public Field() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                field[i][j] = Cell.SEA;
            }
        }
        ;

    }

    public void setShip(int x, int y, int decks, int direction) {
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

    private boolean noShips(int x, int y, int decks, int direction) {
        if (direction == 1) {
            if ((x <= 0 || x >= field.length - 2) & noShipsV(x, y, decks)) {
                return true;
            }
            if (!noShipsV(x, y, decks)) {
                return false;
            }
        } else {
            if ((y <= 0 || y >= field.length - 2) & noShipsG(x, y, decks)) {
                return true;
            }
            if (!noShipsG(x, y, decks)) {
                return false;
            }
        }
        return true;
    }

    private boolean noShipsG(int x, int y, int decks) {
        if (y <= 0 || field[x][y - 1] == Cell.DECK || field[x - 1][y - 1] == Cell.DECK || field[x + 1][y - 1] == Cell.DECK) {
            return false;
        }
        if (y >= field.length - 2 || field[x][y + decks] == Cell.DECK || field[x - 1][y + decks] == Cell.DECK || field[x + 1][y + decks] == Cell.DECK) {
            return false;
        }
        for (int i = 0; i < decks; i++) {
            if (field[x + 1][y] == Cell.DECK || field[x - 1][y] == Cell.DECK) {
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

    public void shot(int x, int y) {
        switch (this.field[x][y]) {
            case SEA:
                field[x][y] = Cell.MISS;
                break;
            case DECK:
                field[x][y] = Cell.HIT;
                if (isDead(x, y)) {
                    System.out.println("DEATH");
                }
                break;
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
        return (isDeadG(x, y, 1) & isDeadG(x, y, -1)) || (isDeadV(x, y, 1) & isDeadV(x, y, -1));
    }

    private boolean isDeadV(int x, int y, int direction) {
        if (y < 0 || y >= field.length || (field[x][y] == Cell.SEA || field[x][y] == Cell.MISS)) {
            return true;
        }
        if (field[x][y] == Cell.HIT) {
            return isDeadV(x, y + direction, direction);
        }
        return false;
    }


    private boolean isDeadG(int x, int y, int direction) {
        if (x < 0 || x >= field.length || (field[x][y] == Cell.SEA || field[x][y] == Cell.MISS)) {
            return true;
        }
        if (field[x][y] == Cell.HIT) {
            return isDeadG(x + direction, y, direction);
        }
        return false;
    }

    private void death(int x, int y) {
        while (field[x][y] == Cell.HIT) {
            field[x][y] = Cell.DEAD;
            x++;
        }
        while (field[x][y] == Cell.HIT || field[x][y] == Cell.DEAD) {
            field[x][y] = Cell.DEAD;
            x--;
        }
        while (field[x][y] == Cell.HIT) {
            field[x][y] = Cell.DEAD;
            y++;
        }
        while (field[x][y] == Cell.HIT || field[x][y] == Cell.DEAD) {
            field[x][y] = Cell.DEAD;
            y--;
        }


    }


}
