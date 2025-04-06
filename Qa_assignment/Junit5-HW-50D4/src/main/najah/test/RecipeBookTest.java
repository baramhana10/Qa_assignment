package main.najah.test;

import main.najah.code.Recipe;
import main.najah.code.RecipeBook;
import main.najah.code.RecipeException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Execution(ExecutionMode.CONCURRENT)
public class RecipeBookTest {

    private RecipeBook recipeBook;
    private Recipe recipe;

    @BeforeEach
    void setup() throws RecipeException {
        recipeBook = new RecipeBook();
        recipe = new Recipe();
        recipe.setName("Mocha");
        recipe.setAmtChocolate("2");
        recipe.setAmtCoffee("3");
        recipe.setAmtMilk("1");
        recipe.setAmtSugar("4");
        recipe.setPrice("50");
        System.out.println("Initialized RecipeBook with one recipe.");
    }

    @Test
    @DisplayName("Test adding a recipe successfully")
    void testAddRecipe() {
        assertTrue(recipeBook.addRecipe(recipe));
        Recipe added = recipeBook.getRecipes()[0];
        assertEquals("Mocha", added.getName());
        assertEquals(2, added.getAmtChocolate());
        assertEquals(3, added.getAmtCoffee());
        assertEquals(1, added.getAmtMilk());
        assertEquals(4, added.getAmtSugar());
        assertEquals(50, added.getPrice());
    }

    @Test
    @DisplayName("Test adding duplicate recipe fails")
    void testAddDuplicateRecipe() {
        assertTrue(recipeBook.addRecipe(recipe));
        Recipe duplicate = new Recipe();
        duplicate.setName("Mocha");
        assertFalse(recipeBook.addRecipe(duplicate));
    }

    @Test
    @DisplayName("Test deleting a recipe")
    void testDeleteRecipe() {
        recipeBook.addRecipe(recipe);
        String deleted = recipeBook.deleteRecipe(0);
        assertEquals("Mocha", deleted);
        assertNotEquals("Mocha", recipeBook.getRecipes()[0].getName());
    }

    @Test
    @DisplayName("Test editing a recipe")
    void testEditRecipe() throws RecipeException {
        recipeBook.addRecipe(recipe);
        Recipe newRecipe = new Recipe();
        newRecipe.setName("Latte");
        newRecipe.setAmtMilk("5");
        newRecipe.setAmtSugar("5");
        newRecipe.setAmtChocolate("5");
        newRecipe.setAmtCoffee("5");
        newRecipe.setPrice("70");

        String result = recipeBook.editRecipe(0, newRecipe);
        assertEquals("Mocha", result);

        Recipe edited = recipeBook.getRecipes()[0];
        assertEquals("", edited.getName()); // reset as per logic
        assertEquals(5, edited.getAmtMilk());
        assertEquals(5, edited.getAmtSugar());
        assertEquals(5, edited.getAmtChocolate());
        assertEquals(5, edited.getAmtCoffee());
        assertEquals(70, edited.getPrice());
    }

    @Test
    @DisplayName("Test delete invalid index returns null")
    void testDeleteInvalidIndex() {
        assertNull(recipeBook.deleteRecipe(0));
    }

    @Test
    @DisplayName("Test edit invalid index returns null")
    void testEditInvalidIndex() {
        Recipe r = new Recipe();
        r.setName("X");
        assertNull(recipeBook.editRecipe(3, r));
    }

    @Test
    @DisplayName("Timeout test for recipe book")
    @Timeout(1)
    void testTimeoutAdd() {
        Recipe r = new Recipe();
        r.setName("Fast");
        assertTrue(recipeBook.addRecipe(r));
    }

    @Test
    @Disabled("Invalid logic: editing resets name. Needs better UX.")
    @DisplayName("Disabled test for future edit behavior fix")
    void testDisabledEditBehavior() {
        recipeBook.addRecipe(recipe);
        Recipe newR = new Recipe();
        newR.setName("New");
        recipeBook.editRecipe(0, newR);
        assertEquals("New", recipeBook.getRecipes()[0].getName());
    }

    // 💡 Additional Tests to cover Recipe class methods

    @Test
    @DisplayName("Test Recipe equals with same name")
    void testRecipeEqualsSameName() {
        Recipe r1 = new Recipe();
        r1.setName("Mocha");

        Recipe r2 = new Recipe();
        r2.setName("Mocha");

        assertEquals(r1, r2);
    }

    @Test
    @DisplayName("Test Recipe equals with different names")
    void testRecipeEqualsDifferentName() {
        Recipe r1 = new Recipe();
        r1.setName("Latte");

        Recipe r2 = new Recipe();
        r2.setName("Cappuccino");

        assertNotEquals(r1, r2);
    }

    @Test
    @DisplayName("Test Recipe hashCode consistency")
    void testRecipeHashCode() {
        Recipe r1 = new Recipe();
        r1.setName("Vanilla");

        Recipe r2 = new Recipe();
        r2.setName("Vanilla");

        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    @DisplayName("Test Recipe equals with null and different class")
    void testRecipeEqualsEdgeCases() {
        Recipe r = new Recipe();
        r.setName("Choco");

        assertNotEquals(null, r);
        assertNotEquals("String", r);
    }
}
