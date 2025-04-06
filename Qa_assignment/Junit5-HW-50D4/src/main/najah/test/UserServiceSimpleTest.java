package main.najah.test;

import main.najah.code.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceSimpleTest{

    private UserService service;

    @BeforeAll
    void setupAll() {
        System.out.println("Starting UserServiceTest...");
    }

    @BeforeEach
    void setup() {
        service = new UserService();
        System.out.println("New UserService instance created.");
    }

    @AfterEach
    void cleanup() {
        System.out.println("Finished a test in UserService.");
    }

    @AfterAll
    void teardownAll() {
        System.out.println("UserServiceTest completed.");
    }

    @Test
    @DisplayName("Test valid email format")
    void testValidEmail() {
        assertAll("Valid emails",
            () -> assertTrue(service.isValidEmail("user@example.com")),
            () -> assertTrue(service.isValidEmail("a.b@c.d"))
        );
    }

    @Test
    @DisplayName("Test invalid email format")
    void testInvalidEmail() {
        assertAll("Invalid emails",
            () -> assertFalse(service.isValidEmail("invalidemail")),
            () -> assertFalse(service.isValidEmail("missing@dot")),
            () -> assertFalse(service.isValidEmail(null))
        );
    }

    @ParameterizedTest
    @CsvSource({
        "admin,1234,true",
        "user,1234,false",
        "admin,wrong,false",
        "guest,pass,false"
    })
    @DisplayName("Test authentication with different credentials")
    void testAuthentication(String username, String password, boolean expected) {
        assertEquals(expected, service.authenticate(username, password));
    }

    @Test
    @DisplayName("Timeout test for isValidEmail")
    @Timeout(1)
    void testEmailValidationTimeout() {
        assertTrue(service.isValidEmail("timeout@test.com"));
    }

    @Test
    @Disabled("Authentication logic to be updated to use hashed passwords")
    @DisplayName("Disabled test for future authentication system")
    void testDisabledAuthentication() {
        assertTrue(service.authenticate("admin", "hashed_password")); // Intentionally incorrect
    }
}