package main.najah.test;


import main.najah.code.Product;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductTest {

    @BeforeAll
    void setupAll() {
        System.out.println("Running ProductTest suite...");
    }

    @BeforeEach
    void setup() {
        System.out.println("Creating a new Product...");
    }

    @AfterEach
    void cleanup() {
        System.out.println("Finished Product test.");
    }

    @AfterAll
    void teardownAll() {
        System.out.println("Completed ProductTest suite.");
    }

    @Test
    @DisplayName("Test product creation with valid input")
    void testValidProductCreation() {
        Product product = new Product("Notebook", 20.0);
        assertAll("Product Properties",
            () -> assertEquals("Notebook", product.getName()),
            () -> assertEquals(20.0, product.getPrice()),
            () -> assertEquals(0.0, product.getDiscount())
        );
    }

    @Test
    @DisplayName("Test product creation with negative price (invalid)")
    void testNegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> new Product("Invalid", -10.0));
    }

    @Test
    @DisplayName("Test applying valid discount")
    void testValidDiscount() {
        Product product = new Product("Pen", 10.0);
        product.applyDiscount(20.0);
        assertEquals(8.0, product.getFinalPrice(), 0.001);
    }

    @Test
    @DisplayName("Test applying invalid discount")
    void testInvalidDiscount() {
        Product product = new Product("Mouse", 100.0);
        assertAll("Invalid Discounts",
            () -> assertThrows(IllegalArgumentException.class, () -> product.applyDiscount(-10)),
            () -> assertThrows(IllegalArgumentException.class, () -> product.applyDiscount(70))
        );
    }

    @ParameterizedTest
    @CsvSource({
        "100, 0, 100.0",
        "100, 10, 90.0",
        "100, 50, 50.0"
    })
    @DisplayName("Parameterized test for final price calculation")
    void testFinalPrice(double price, double discount, double expectedFinal) {
        Product product = new Product("Test", price);
        product.applyDiscount(discount);
        assertEquals(expectedFinal, product.getFinalPrice(), 0.001);
    }

    @Test
    @DisplayName("Timeout test for discount logic")
    @Timeout(1)
    void testApplyDiscountTimeout() {
        Product product = new Product("Speedy", 100.0);
        product.applyDiscount(25);
        assertEquals(75.0, product.getFinalPrice(), 0.001);
    }

    @Test
    @Disabled("Bug: change expected result after fixing discount logic rounding")
    @DisplayName("Disabled test example for wrong expected output")
    void testDisabledFinalPrice() {
        Product product = new Product("Book", 100.0);
        product.applyDiscount(15);
        assertEquals(90.0, product.getFinalPrice()); // Intentionally incorrect
    }
}
