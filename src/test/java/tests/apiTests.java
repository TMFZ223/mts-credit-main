package tests;

import asserts.Conditions;
import com.example.creditservice.model.request.AuthenticationRequest;
import com.example.creditservice.model.request.RegisterRequest;
import factories.UserFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import steps.UserSteps;

import java.util.stream.Stream;

public class apiTests {
    private final UserSteps userSteps = new UserSteps();

    private static Stream<Arguments> negativeRegisterProvider() {
        return Stream.of(
                Arguments.of(UserFactory.registerWithEmptyFirstName(), "VALIDATION_ERROR", "first name is required"),
                Arguments.of(UserFactory.registerWithNullFirstName(), "VALIDATION_ERROR", "first name is required"),
                Arguments.of(UserFactory.registerWithEmptyLastName(), "VALIDATION_ERROR", "last name is required"),
                Arguments.of(UserFactory.registerWithNullLastName(), "VALIDATION_ERROR", "last name is required"),
                Arguments.of(UserFactory.registerWithEmptyEmail(), "VALIDATION_ERROR", "email is required"),
                Arguments.of(UserFactory.registerWithNullEmail(), "VALIDATION_ERROR", "email is required"),
                Arguments.of(UserFactory.registerWithoutAtInEmail(), "VALIDATION_ERROR", "Invalid email address"),
                Arguments.of(UserFactory.registerWithoutDomainInEmail(), "VALIDATION_ERROR", "Invalid email address"),
                Arguments.of(UserFactory.registerWithoutLocalPartInEmail(), "VALIDATION_ERROR", "Invalid email address"),
                Arguments.of(UserFactory.registerWithoutAtAndDotInEmail(), "VALIDATION_ERROR", "Invalid email address"),
                Arguments.of(UserFactory.registerWithSpaceInEmail(), "VALIDATION_ERROR", "Invalid email address"),
                Arguments.of(UserFactory.registerWithExistingEmailInDB(), "err", "Email already in used"),
                Arguments.of(UserFactory.registerWithExistingEmailInDBUperCase(), "err", "Email already in used"),
                Arguments.of(UserFactory.registerWithEmptyPassword(), "VALIDATION_ERROR", "password is required"),
                Arguments.of(UserFactory.registerWithNullPassword(), "VALIDATION_ERROR", "password is required"),
                Arguments.of(UserFactory.registerWith7CharactersInPassword(), "VALIDATION_ERROR", "Password must be between 8 and 25 characters"),
                Arguments.of(UserFactory.registerWith26CharactersInPassword(), "VALIDATION_ERROR", "Password must be between 8 and 25 characters"),
                Arguments.of(UserFactory.registerWithSpaceInPasswordAtTheBeginning(), "VALIDATION_ERROR", "Invalid format of password"),
                Arguments.of(UserFactory.registerWithSpaceInPasswordAtTheEnd(), "VALIDATION_ERROR", "Invalid format of password"),
                Arguments.of(UserFactory.registerWithSpaceInPasswordAtTheMiddle(), "VALIDATION_ERROR", "Invalid format of password"),
                Arguments.of(UserFactory.registerWithCyrillicInPasswordAtTheBeginning(), "VALIDATION_ERROR", "Invalid format of password"),
                Arguments.of(UserFactory.registerWithCyrillicInPasswordAtTheEnd(), "VALIDATION_ERROR", "Invalid format of password"),
                Arguments.of(UserFactory.registerWithCyrillicInPasswordAtTheMiddle(), "VALIDATION_ERROR", "Invalid format of password")
        );
    }

    private static Stream<Arguments> negativeAuthenticateProvider() {
        return Stream.of(
                Arguments.of(UserFactory.authWithEmptyEmail(), "VALIDATION_ERROR", "email is required"),
                Arguments.of(UserFactory.authWithNullEmail(), "VALIDATION_ERROR", "email is required"),
                Arguments.of(UserFactory.authWithEmptyPassword(), "VALIDATION_ERROR", "password is required"),
                Arguments.of(UserFactory.authWithNullPassword(), "VALIDATION_ERROR", "password is required"),
                Arguments.of(UserFactory.authenticateWithUnknownEmail(), "INVALID_CREDENTIALS", "Неверный email или пароль")
        );
    }

    @Test
    @DisplayName("Позитивный тест регистрации пользователя")
    public void positiveRegisterTest() {
        RegisterRequest registerBody = UserFactory.correctRegister();
        userSteps.register(registerBody)
                .should(Conditions.hasStatusCode(200));
        userSteps.authenticateUser(UserFactory.authWithRegisterData(registerBody))
                .should(Conditions.hasStatusCode(200))
                .should(Conditions.hasNotEmptyToken());
    }

    @MethodSource("negativeRegisterProvider")
    @ParameterizedTest
    @DisplayName("Негативный тест регистрации пользователя")
    public void negativeRegisterTest(RegisterRequest registerRequest, String expectedErrorCode, String expectedErrorMessage) {
        userSteps.register(registerRequest)
                .should(Conditions.hasStatusCode(400))
                .should(Conditions.hasError(expectedErrorCode, expectedErrorMessage));
    }

    @Test
    @DisplayName("Тест аутентификации администратора")
    public void adminAuthenticateTest() {
        userSteps.authenticateUser(UserFactory.authAdmin())
                .should(Conditions.hasStatusCode(200))
                .should(Conditions.hasNotEmptyToken());
    }

    @MethodSource("negativeAuthenticateProvider")
    @ParameterizedTest
    @DisplayName("Негативный тест аутентификации пользователя")
    public void negativeAuthenticateTest(AuthenticationRequest authenticationRequestBody, String expectedErrorCode, String expectedErrorMessage) {
        userSteps.authenticateUser(authenticationRequestBody)
                .should(Conditions.hasStatusCode(400))
                .should(Conditions.hasError(expectedErrorCode, expectedErrorMessage));
    }
}
