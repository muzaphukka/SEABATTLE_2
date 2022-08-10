import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        Field field1 = new Field();
        Field field2 = new Field();
        System.out.println("Привет! Добро пожаловать в консольный морской бой!");
        System.out.println("Первому игроку приготовиться, введите имя:");
        field1.setNAME(in.next());
        System.out.println(field1.getNAME() + "разместите ваши корабли");









        field1.setShip(0,3,2,1);
        field1.shot(0,3);
        field1.shot(1,3);
        field1.print();






    }
}