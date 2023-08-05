package recipeBook.initialize;

public interface TableStatements {
    String RECIPES = "CREATE TABLE IF NOT EXISTS recipes (" +
            "id SERIAL PRIMARY KEY," +
            "ingredients TEXT[]," +
            "number_of_ingredients INT," +
            "duration INTERVAL," +
            "servings INT," +
            "tags TEXT[]," +
            "instructions TEXT" +
            ");";

}
