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

        switch (chosenAction) {
            case 1:
                Recipe recipe = repository.addRecipe();
                printStatements.printRecipe(recipe);
                databaseActions.save(recipe);
                break;
            case 2:
                String searchTerm = repository.searchRecipe();
                List<Recipe> matchingRecipes =  databaseActions.findRecipe(searchTerm);
                printStatements.printRecipesList(matchingRecipes);

            case 3:
                repository.deleteRecipe();
            case 4:
                repository.updateRecipe();
        }

    }
}
