package recipeBook.database;

import recipeBook.data.Recipe;
import java.sql.Connection;
import java.sql.*;
import java.sql.PreparedStatement;


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
            statement.setArray(5, connection.createArrayOf("VARCHAR", recipe.tags()));
            statement.setString(5, recipe.instructions());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
