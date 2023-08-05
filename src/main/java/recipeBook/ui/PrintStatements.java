package recipeBook.ui;

import recipeBook.data.Recipe;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PrintStatements {

    Scanner scanner = new Scanner(System.in);
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_ORANGE = "\u001B[38;5;202m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_INDIGO = "\u001B[38;5;69m";
    public static final String ANSI_VIOLET = "\u001B[35m";


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

    public void printRecipe(Recipe recipe) {
        System.out.println("----------------------------");
        System.out.println(ANSI_RED + "Recipe: " + ANSI_RESET + recipe.name());
        System.out.println(ANSI_ORANGE + "Ingredients: " + ANSI_RESET + Arrays.toString(recipe.ingredients()));
        System.out.println(ANSI_YELLOW + "Number of Ingredients: " + ANSI_RESET + recipe.numberOfIngredients());
        System.out.println(ANSI_GREEN + "Duration: " + ANSI_RESET + (recipe.duration()));
        System.out.println(ANSI_BLUE + "Number of Servings: " + ANSI_RESET + recipe.numberOfServings());
        System.out.println(ANSI_INDIGO + "Tags: " + ANSI_RESET + Arrays.toString(recipe.tags()));
        System.out.println(ANSI_VIOLET + "Instructions: " + ANSI_RESET + recipe.instructions());
        System.out.println("----------------------------");
    }

    public void printRecipesList (List<Recipe> matchingRecipes){

        for (Recipe recipe : matchingRecipes){
            printRecipe(recipe);
        }
    }

}
