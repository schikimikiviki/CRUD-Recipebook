package recipeBook;

import recipeBook.database.Database;
import recipeBook.initialize.TableInitializer;
import recipeBook.initialize.TableStatements;


import java.util.Map;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Database database = new Database(
                "jdbc:postgresql://localhost:5432/recipe-book",
                "postgres",
                "postgres");
        Map<String, String> tables = Map.of(
                "recipes", TableStatements.RECIPES
        );
        TableInitializer tableInitializer = new TableInitializer(database, tables);
        tableInitializer.initialize();

        Scanner scanner = new Scanner(System.in);

    }
}
