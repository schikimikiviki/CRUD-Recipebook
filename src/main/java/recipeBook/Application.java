package recipeBook;

import recipeBook.data.Recipe;
import recipeBook.database.Database;
import recipeBook.database.DatabaseActions;
import recipeBook.initialize.TableInitializer;
import recipeBook.initialize.TableStatements;
import recipeBook.logic.RecipeRepository;
import recipeBook.ui.PrintStatements;


import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Database database = new Database(
                "jdbc:postgresql://localhost:5432/recipe_book",
                "postgres",
                "postgres");
        Map<String, String> tables = Map.of(
                "recipes", TableStatements.RECIPES
        );
        TableInitializer tableInitializer = new TableInitializer(database, tables);
        tableInitializer.initialize();

        PrintStatements printStatements = new PrintStatements();
        int chosenAction = printStatements.printInitialMessage();
        RecipeRepository repository = new RecipeRepository();
        DatabaseActions databaseActions = new DatabaseActions(database);
        Scanner scanner = new Scanner(System.in);

        boolean inputIdentifier = false;

        while (true) {
            while (!inputIdentifier) { // Inner loop to keep asking for input
                switch (chosenAction) {
                    case 1:
                        Recipe recipe = repository.addRecipe();
                        printStatements.printRecipe(recipe);
                        databaseActions.save(recipe);
                        inputIdentifier = true;
                        break;
                    case 2:
                        String searchTerm = repository.searchRecipe();

                        try {
                            List<Recipe> matchingRecipes = databaseActions.findRecipe(searchTerm);
                            printStatements.printRecipesList(matchingRecipes);
                            inputIdentifier = true;
                        } catch (DatabaseActions.NoRecipeFoundException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 3:
                        String recipeNameToDelete = repository.deleteRecipe();
                        databaseActions.deleteRecipe(recipeNameToDelete);
                        inputIdentifier = true;
                        break;
                    case 4:
                        repository.updateRecipe();
                        inputIdentifier = true;
                        break;

                    case 5:
                        repository.viewAllRecipes();
                        inputIdentifier = true;
                        break;

                    default:
                        System.out.println("Please enter a valid number.");
                        chosenAction = scanner.nextInt();
                        break;
                }
            }


        }


    }
}
