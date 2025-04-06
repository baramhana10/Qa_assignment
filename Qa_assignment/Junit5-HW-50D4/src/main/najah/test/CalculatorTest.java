package main.najah.test;

import main.najah.code.Calculator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)
@Execution(ExecutionMode.CONCURRENT)
public class CalculatorTest {

    private Calculator calculator;

    @BeforeAll
    static void initAll() {
        System.out.println("Starting CalculatorTest...");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("CalculatorTest completed.");
    }

    @BeforeEach
    void init() {
        calculator = new Calculator();
        System.out.println("New Calculator instance created.");
    }

    @AfterEach
    void cleanup() {
        System.out.println("Test complete.");
    }

    @Test
    @DisplayName("Test addition with valid input")
    @Order(1)
    void testAddValid() {
        int result = calculator.add(1, 2, 3);
        assertAll("Sum Check",
            () -> assertEquals(6, result),
            () -> assertTrue(result > 0)
        );
    }

    @Test
    @DisplayName("Test division by zero")
    @Order(2)
    void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> calculator.divide(10, 0));
    }

    @Test
    @DisplayName("Test factorial of negative number (invalid)")
    @Order(3)
    void testFactorialNegative() {
        assertThrows(IllegalArgumentException.class, () -> calculator.factorial(-5));
    }

    @ParameterizedTest
    @CsvSource({
        "0, 1",
        "1, 1",
        "3, 6",
        "5, 120"
    })
    @DisplayName("Test factorial with valid inputs")
    @Order(4)
    void testFactorialParameterized(int input, int expected) {
        assertEquals(expected, calculator.factorial(input));
    }

    @Test
    @DisplayName("Timeout test for add")
    @Order(5)
    @Timeout(1)
    void testAddTimeout() {
        assertEquals(15, calculator.add(5, 5, 5));
    }

   
}
