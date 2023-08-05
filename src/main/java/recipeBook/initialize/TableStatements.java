package recipeBook.initialize;

public interface TableStatements {
    String RECIPES = "CREATE TABLE IF NOT EXISTS recipes (" +
            "id SERIAL PRIMARY KEY," +
            "name VARCHAR(100)," +
            "ingredients TEXT[]," +
            "number_of_ingredients INT," +
            "duration int," +
            "servings INT," +
            "tags TEXT[]," +
            "instructions TEXT" +
            ");";

}
