package recipeBook.database;

import recipeBook.data.Recipe;

import java.sql.Connection;
import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;


public class DatabaseActions {
    private final Database database;

    public DatabaseActions(Database database) {
        this.database = database;
    }

    public void save(Recipe recipe) {

        String template = "INSERT INTO recipes (name, ingredients, number_of_ingredients , duration, servings, tags, instructions ) VALUES (?, ?, ?, ? , ?, ?, ?)";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(template)) {

            statement.setString(1, recipe.name());
            statement.setArray(2, connection.createArrayOf("VARCHAR", recipe.ingredients()));
            statement.setInt(3, recipe.numberOfIngredients());
            statement.setInt(4, recipe.duration());
            statement.setInt(5, recipe.numberOfServings());
            statement.setArray(6, connection.createArrayOf("VARCHAR", recipe.tags()));
            statement.setString(7, recipe.instructions());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Recipe> findRecipe(String searchTerm) {
        String template = "SELECT * FROM recipes WHERE name LIKE ? OR ? = ANY(tags) OR ? = ANY(ingredients)";

        List<Recipe> matchingRecipesList = new ArrayList<>();

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(template)) {

            String wildcardSearchTerm = "%" + searchTerm + "%";

            statement.setString(1, wildcardSearchTerm);
            statement.setString(2, searchTerm);
            statement.setString(3, searchTerm);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String recipeName = resultSet.getString("name");
                    String[] ingredients = resultSet.getString("ingredients").split(",");
                    int numberOfIngredients = resultSet.getInt("number_of_ingredients");
                    int duration = resultSet.getInt("duration");
                    int numberOfServings = resultSet.getInt("servings");
                    String[] tags = resultSet.getString("tags").split(",");
                    String instructions = resultSet.getString("instructions");

                    Recipe recipe = new Recipe(recipeName, ingredients, numberOfIngredients, duration, numberOfServings, tags, instructions);
                    matchingRecipesList.add(recipe);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return matchingRecipesList;
    }



}
