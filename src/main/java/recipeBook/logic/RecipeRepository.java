package recipeBook.logic;

import recipeBook.data.Recipe;
import recipeBook.ui.PrintStatements;

import java.time.Duration;
import java.util.Arrays;
import java.util.Scanner;

public class RecipeRepository {

    Scanner scanner = new Scanner(System.in);
    PrintStatements printStatements = new PrintStatements();

    public void addRecipe() {
        System.out.println("Give the recipe a name");
        String recipeName = scanner.nextLine();

        System.out.println("What ingredients are used? Type them in separated by commas.");
        String ingredientsList = scanner.nextLine();
        String[] ingredientsListArray = ingredientsList.split(",", 0);
        int numberOfIngredients = ingredientsListArray.length;

        System.out.println("How long does this take to make? Type in the number of minutes");
        int duration = scanner.nextInt();
        scanner.nextLine();

        System.out.println("How many servings is the recipe?");
        int numberOfServings = scanner.nextInt();
        scanner.nextLine();

        System.out.println("What tags can be used to describe this recipe? Type them in separated by commas.");
        String tagList = scanner.nextLine();
        String[] tagListArray = tagList.split(",", 0);

        System.out.println("What are the instructions?");
        String instructions = scanner.nextLine();

        Recipe recipe = new Recipe(recipeName, ingredientsListArray, numberOfIngredients, duration, numberOfServings, tagListArray, instructions);

        printStatements.printRecipe(recipe);
    }



    public void searchRecipe() {

    }

    public void deleteRecipe() {

    }

    public void updateRecipe() {

    }
}
