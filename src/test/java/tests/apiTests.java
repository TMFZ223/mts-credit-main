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
                Arguments.of(UserFactory.registerWithEmptyFirstName(), "first name is required"),
                Arguments.of(UserFactory.registerWithNullFirstName(), "first name is required"),
                Arguments.of(UserFactory.registerWithEmptyLastName(), "last name is required"),
                Arguments.of(UserFactory.registerWithNullLastName(), "last name is required"),
                Arguments.of(UserFactory.registerWithEmptyEmail(), "email is required"),
                Arguments.of(UserFactory.registerWithNullEmail(), "email is required"),
                Arguments.of(UserFactory.registerWithoutAtInEmail(), "Invalid email address"),
                Arguments.of(UserFactory.registerWithoutDomainInEmail(), "Invalid email address"),
                Arguments.of(UserFactory.registerWithoutLocalPartInEmail(), "Invalid email address"),
                Arguments.of(UserFactory.registerWithoutAtAndDotInEmail(), "Invalid email address"),
                Arguments.of(UserFactory.registerWithSpaceInEmail(), "Invalid email address"),
                Arguments.of(UserFactory.registerWithExistingEmailInDB(), "Email already in used"),
                Arguments.of(UserFactory.registerWithExistingEmailInDBUperCase(), "Email already in used"),
                Arguments.of(UserFactory.registerWithEmptyPassword(), "password is required"),
                Arguments.of(UserFactory.registerWithNullPassword(), "password is required"),
                Arguments.of(UserFactory.registerWith7CharactersInPassword(), "Password must be between 8 and 25 characters"),
                Arguments.of(UserFactory.registerWith26CharactersInPassword(), "Password must be between 8 and 25 characters"),
                Arguments.of(UserFactory.registerWithSpaceInPasswordAtTheBeginning(), "Invalid format of password"),
                Arguments.of(UserFactory.registerWithSpaceInPasswordAtTheEnd(), "Invalid format of password"),
                Arguments.of(UserFactory.registerWithSpaceInPasswordAtTheMiddle(), "Invalid format of password"),
                Arguments.of(UserFactory.registerWithCyrillicInPasswordAtTheBeginning(), "Invalid format of password"),
                Arguments.of(UserFactory.registerWithCyrillicInPasswordAtTheEnd(), "Invalid format of password"),
                Arguments.of(UserFactory.registerWithCyrillicInPasswordAtTheMiddle(), "Invalid format of password")
        );
    }

    private static Stream<Arguments> negativeAuthenticateProvider() {
        return Stream.of(
                Arguments.of(UserFactory.authWithEmptyEmail(), "email is required"),
                Arguments.of(UserFactory.authWithNullEmail(), "email is required"),
                Arguments.of(UserFactory.authWithEmptyPassword(), "password is required"),
                Arguments.of(UserFactory.authWithNullPassword(), "password is required"),
                Arguments.of(UserFactory.authenticateWithUnknownEmail(), "user not found")
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
    public void negativeRegisterTest(RegisterRequest registerRequest, String expectedErrorMessage) {
        userSteps.register(registerRequest)
                .should(Conditions.hasStatusCode(400))
                .should(Conditions.hasError(expectedErrorMessage));
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
    public void negativeAuthenticateTest(AuthenticationRequest authenticationRequestBody, String expectedErrorMessage) {
        userSteps.authenticateUser(authenticationRequestBody)
                .should(Conditions.hasStatusCode(400))
                .should(Conditions.hasError(expectedErrorMessage));
    }
}
