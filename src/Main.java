import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        Field field1 = new Field();
        Field field2 = new Field();
        System.out.println("Привет! Добро пожаловать в консольный морской бой!");
        System.out.println("Первому игроку приготовиться, введите имя:");
        field1.setNAME(in.next());
        System.out.println(field1.getNAME() + " разместите ваши корабли");
        field1.print();
        field1.setShips();
        System.out.println("а теперь второй игрок:");
        field2.setNAME(in.next());
        System.out.println(field2.getNAME() + " разместите ваши корабли");
        field2.print();
        field2.setShips();
        System.out.println("Корабли расставлены, пора играть. К бою!");

        while (true) {
            System.out.println(field1.getNAME() + " , в атаку!");
            System.out.println("Поле игрока " + field2.getNAME());
            field2.printBlind();
            System.out.println("Поле игрока " + field1.getNAME());
            field1.print();
            field2.shot(in.nextInt(), in.nextInt());

            System.out.println(field2.getNAME() + " , в атаку!");
            System.out.println("Поле игрока " + field1.getNAME());
            field1.printBlind();
            System.out.println("Поле игрока " + field2.getNAME());
            field2.print();
            field1.shot(in.nextInt(), in.nextInt());
        }





    }
}