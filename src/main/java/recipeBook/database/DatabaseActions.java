package recipeBook.database;

import recipeBook.data.Recipe;

import java.sql.Connection;
import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class DatabaseActions {
    private final Database database;

    Scanner scanner = new Scanner(System.in);

    public DatabaseActions(Database database) {
        this.database = database;
    }

    public void save(Recipe recipe) {
        String checkNameTemplate = "SELECT COUNT(*) FROM recipes WHERE name = ?";
        String insertTemplate = "INSERT INTO recipes (name, ingredients, number_of_ingredients, duration, servings, tags, instructions) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = database.getConnection();
             PreparedStatement checkNameStatement = connection.prepareStatement(checkNameTemplate);
             PreparedStatement insertStatement = connection.prepareStatement(insertTemplate)) {

            String newName = recipe.name();
            boolean nameIsUnique = false;

            while (!nameIsUnique) {
                checkNameStatement.setString(1, newName);
                try (ResultSet resultSet = checkNameStatement.executeQuery()) {
                    if (resultSet.next() && resultSet.getInt(1) > 0) {
                        System.out.println("A recipe with the same name already exists. Please choose a different name:");
                        newName = scanner.nextLine();
                    } else {
                        nameIsUnique = true;
                    }
                }
            }

            // Insert the new recipe with the unique name
            insertStatement.setString(1, newName);
            insertStatement.setArray(2, connection.createArrayOf("VARCHAR", recipe.ingredients()));
            insertStatement.setInt(3, recipe.numberOfIngredients());
            insertStatement.setInt(4, recipe.duration());
            insertStatement.setInt(5, recipe.numberOfServings());
            insertStatement.setArray(6, connection.createArrayOf("VARCHAR", recipe.tags()));
            insertStatement.setString(7, recipe.instructions());
            insertStatement.executeUpdate();

            System.out.println("Recipe was saved with the new unique name: " + newName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public boolean isRecipeNameUnique(String recipeName) {
        String checkNameTemplate = "SELECT COUNT(*) FROM recipes WHERE name = ?";

        try (Connection connection = database.getConnection();
             PreparedStatement checkNameStatement = connection.prepareStatement(checkNameTemplate)) {

            checkNameStatement.setString(1, recipeName);
            try (ResultSet resultSet = checkNameStatement.executeQuery()) {
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    return false; // Name is not unique
                }
            }

            return true; // Name is unique
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Recipe> findRecipe(String searchTerm) throws NoRecipeFoundException {
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

        if (matchingRecipesList.size() == 0){
            throw new NoRecipeFoundException("No recipe found. Sorry :(");
        }

        return matchingRecipesList;
    }

    public class NoRecipeFoundException extends Exception {
        public NoRecipeFoundException(String message) {
            super(message);
        }
    }

    public void deleteRecipe(String recipeName) {
        String deleteTemplate = "DELETE FROM recipes WHERE name = ? AND id = ?";

        try (Connection connection = database.getConnection();
             PreparedStatement deleteStatement = connection.prepareStatement(deleteTemplate)) {


            List<Integer> recipeIds = findRecipeIdsByName(recipeName);

            if (recipeIds.isEmpty()) {
                System.out.println("Recipe not found");
            } else if (recipeIds.size() == 1) {
                // If there is only one recipe with the given name, delete it directly
                int recipeId = recipeIds.get(0);
                deleteStatement.setString(1, recipeName);
                deleteStatement.setInt(2, recipeId);
                deleteStatement.executeUpdate();
                System.out.println("Recipe was deleted");
                return;
            } else {

                System.out.println("Multiple recipes with the same name found. Delete all? Type [y] or [n]");
                String answer = scanner.nextLine();
               //todo: what would make sense here?
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private List<Integer> findRecipeIdsByName(String recipeName) {
        List<Integer> recipeIds = new ArrayList<>();
        String selectTemplate = "SELECT id FROM recipes WHERE name = ?";

        try (Connection connection = database.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(selectTemplate)) {

            selectStatement.setString(1, recipeName);

            try (ResultSet resultSet = selectStatement.executeQuery()) {
                while (resultSet.next()) {
                    int recipeId = resultSet.getInt("id");
                    recipeIds.add(recipeId);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return recipeIds;
    }





}
