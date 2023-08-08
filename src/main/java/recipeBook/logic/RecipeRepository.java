package recipeBook.logic;

import recipeBook.data.Recipe;

import recipeBook.ui.PrintStatements;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RecipeRepository {

    Scanner scanner = new Scanner(System.in);
    PrintStatements printStatements = new PrintStatements();


    public Recipe addRecipe() {
        System.out.println("Give the recipe a name");
        String recipeName = scanner.nextLine();

        System.out.println("What ingredients are used? Type them in separated by commas.");
        String ingredientsList = scanner.nextLine();
        String[] ingredientsListArray = ingredientsList.split(",", 0);

        for (int i = 0; i < ingredientsListArray.length; i++) {
            ingredientsListArray[i] = " " + ingredientsListArray[i].trim() + " ";
        }

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

        for (int i = 0; i < tagListArray.length; i++) {
            tagListArray[i] = " " + tagListArray[i].trim() + " ";
        }

        System.out.println("What are the instructions?");
        String instructions = scanner.nextLine();

        Recipe recipe = new Recipe(recipeName, ingredientsListArray, numberOfIngredients, duration, numberOfServings, tagListArray, instructions);

        return recipe;
    }



    public String searchRecipe() {
        System.out.println("What recipe are you searching for? You can search for tags, recipe names, ingredients, ... ");
        String searchTerm = scanner.nextLine();
        return searchTerm;
    }

    public String deleteRecipe() {
        System.out.println("Which recipe do you want to delete ? Please enter a valid recipe name");
        String recipeToDelete = scanner.nextLine();
        return recipeToDelete;
    }

    //todo: all input should be case-insensitive

    public String updateRecipe() {
        System.out.println("Which recipe do you want to update ? Please provide the recipe name");
        String recipeToUpdate = scanner.nextLine();
        return recipeToUpdate;

    }


}
