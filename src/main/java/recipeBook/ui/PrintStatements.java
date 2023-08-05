package recipeBook.ui;

import java.util.Scanner;

public class PrintStatements {

    Scanner scanner = new Scanner(System.in);

    public int printInitialMessage() {

        System.out.println("What do you want to do?");
        System.out.println("*--------------------*");
        System.out.println("[1] - Add recipe");
        System.out.println("[2] - Search for recipe");
        System.out.println("[3] - Delete recipe");
        System.out.println("[4] - Update recipe");
        int chosenPossibility = scanner.nextInt();
        return chosenPossibility;
    }
}
