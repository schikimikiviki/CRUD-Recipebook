package recipeBook;

import recipeBook.database.Database;
import recipeBook.initialize.TableInitializer;
import recipeBook.initialize.TableStatements;
import recipeBook.logic.RecipeRepository;
import recipeBook.ui.PrintStatements;


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

        switch (chosenAction) {
            case 1:
                repository.addRecipe();
                break;
            case 2:
                repository.searchRecipe();
            case 3:
                repository.deleteRecipe();
            case 4:
                repository.updateRecipe();
        }

    }
}
