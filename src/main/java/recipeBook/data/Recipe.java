package recipeBook.data;

public record Recipe( String name, String[] ingredients, int numberOfIngredients, int duration, int numberOfServings, String[] tags, String instructions) {
}
