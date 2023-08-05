package recipeBook.data;

import java.time.Duration;

public record Recipe(String name, String[] ingredients, int numberOfIngredients, int duration, int numberOfServings, String[] tags, String instructions) {
}
