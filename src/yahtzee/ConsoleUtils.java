package yahtzee;

import java.util.Scanner;

class ConsoleUtils {
    static int menu() {
        int selection;
        Scanner input = new Scanner(System.in);
        System.out.println("-------------------------");
        System.out.println("1 - Keeper Selection");
        System.out.println("2 - Re-roll");
        System.out.println("3 - Score Selection");
        System.out.println("-------------------------");
        selection = input.nextInt();
       // input.close();
        return selection;
    }

    static int keeperSelect() {
        int selection;
        Scanner input = new Scanner(System.in);
        System.out.println("Input the dice number to add it to keep.");
        System.out.println("Input 0 to exit this menu");

        selection = input.nextInt();
        // input.close();
        return selection;
    }

    static int scoreSelect() {
        int selection;
        Scanner input = new Scanner(System.in);

        System.out.println("Input the score number to add it to keep.");
        System.out.println("Input 0 to exit this menu");

        selection = input.nextInt();
        // input.close();
        return selection;
    }


}
