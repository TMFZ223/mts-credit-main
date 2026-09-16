package tests;

import asserts.Conditions;
import com.example.creditservice.model.request.AuthenticationRequest;
import com.example.creditservice.model.request.RegisterRequest;
import com.example.creditservice.model.request.TariffDTO;
import factories.TariffFactory;
import factories.UserFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import steps.TariffSteps;
import steps.UserSteps;

import java.util.stream.Stream;

public class apiTests {
    private final UserSteps userSteps = new UserSteps();
    private final TariffSteps tariffSteps = new TariffSteps();

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

    private static Stream<Object> positivePasswordBoundaryValuesProvider() {
        return Stream.of(UserFactory.registerWith8CharactersInPassword(), UserFactory.registerWith9CharactersInPassword(), UserFactory.registerWith24CharactersInPassword(), UserFactory.registerWith25CharactersInPassword());
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

    private static Stream<Arguments> skipTariffKeyProvider() {
        return Stream.of(
                Arguments.of(TariffFactory.addTariffWithEmptyType(), "VALIDATION_ERROR", "type is required"),
                Arguments.of(TariffFactory.addTariffWithNullType(), "VALIDATION_ERROR", "type is required"),
                Arguments.of(TariffFactory.addTariffWithEmptyInterestRate(), "VALIDATION_ERROR", "interest rate is required"),
                Arguments.of(TariffFactory.addTariffWithNullInterestRate(), "VALIDATION_ERROR", "interest rate is required")
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

    @MethodSource("positivePasswordBoundaryValuesProvider")
    @ParameterizedTest
    @DisplayName("регистрация с позитивными граничными значениями пароля")
    public void registerWithPositivePasswordBoundaryValuesTest(RegisterRequest registerRequest) {
        userSteps.register(registerRequest)
                .should(Conditions.hasStatusCode(200));
        userSteps.authenticateUser(UserFactory.authWithRegisterData(registerRequest))
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

    @Test
    @DisplayName("Позитивный тест аутентификации обычного пользователя")
    public void userAuthenticateTest() {
        userSteps.authenticateUser(UserFactory.authUser())
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

    @Test
    @DisplayName("Получение списка тарифов")
    public void getTariffsTest() {
        tariffSteps.getTariffs()
                .should(Conditions.hasStatusCode(200))
                .should(Conditions.hasCorrectTariffList());
    }

    @Test
    @DisplayName("Добавление тарифа администратором")
    public void addTariffAdminTest() {
        String token = userSteps.authenticateUser(UserFactory.authAdmin()).asJwt();
        TariffDTO tariffBody = TariffFactory.addTariff();
        tariffSteps.addTariff(tariffBody, token)
                .should(Conditions.hasStatusCode(200));
        tariffSteps.getTariffs()
                .should(Conditions.hasCreatedTariffInList(tariffBody));
    }

    @Test
    @DisplayName("Добавление нескольких одинаковых тарифов администратором")
    public void addMultipleIdenticalTariffAdminTest() {
        String token = userSteps.authenticateUser(UserFactory.authAdmin()).asJwt();
        TariffDTO tariff = TariffFactory.addTariff();
        tariffSteps.addTariff(tariff, token)
                .should(Conditions.hasStatusCode(200));
        tariffSteps.addTariff(tariff, token)
                .should(Conditions.hasStatusCode(400))
                .should(Conditions.hasError("err", "type already in used"));
    }

    @MethodSource("skipTariffKeyProvider")
    @ParameterizedTest
    @DisplayName("Добавление тарифа без обязательного поля и с null значением")
    public void addTariffWithoutAnyEmptyNullKeyTest(TariffDTO tariff, String expectedErrorCode, String expectedErrorMessage) {
        String token = userSteps.authenticateUser(UserFactory.authAdmin()).asJwt();
        tariffSteps.addTariff(tariff, token)
                .should(Conditions.hasStatusCode(400))
                .should(Conditions.hasError(expectedErrorCode, expectedErrorMessage));
    }

    @Test
    @DisplayName("Добавление тарифа обычным пользователем")
    public void addTariffUserTest() {
        String token = userSteps.authenticateUser(UserFactory.authUser()).asJwt();
        tariffSteps.addTariff(TariffFactory.addTariff(), token)
                .should(Conditions.hasStatusCode(403))
                .should(Conditions.hasError("err", "Forbidden for this user"));
    }

    @Test
    @DisplayName("Добавление тарифа без авторизации")
    public void addTariffWithoutAuthenticateTest() {
        tariffSteps.addTariff(TariffFactory.addTariff(), "")
                .should(Conditions.hasStatusCode(401))
                .should(Conditions.hasError("err", "token is required"));
    }

    @Test
    @DisplayName("Удаление существующего тарифа администратором")
    public void deleteExistsTariffAdminTest() {
        String token = userSteps.authenticateUser(UserFactory.authAdmin()).asJwt();
        Long randTId = tariffSteps.getTariffs().chooseRandomTariffId();
        tariffSteps.deleteTariff(randTId, token)
                .should(Conditions.hasStatusCode(200));
        tariffSteps.deleteTariff(randTId, token)
                .should(Conditions.hasStatusCode(400))
                .should(Conditions.hasError("TARIFF_NOT_FOUND", "Тариф не найден"));
    }

    @Test
    @DisplayName("Удаление несуществующего тарифа")
    public void deleteNotExistsTariffTest() {
        String token = userSteps.authenticateUser(UserFactory.authAdmin()).asJwt();
        tariffSteps.deleteTariff((long) -1, token)
                .should(Conditions.hasStatusCode(400))
                .should(Conditions.hasError("TARIFF_NOT_FOUND", "Тариф не найден"));
    }

    @Test
    @DisplayName("Удаление тарифа без авторизации")
    public void deleteTariffWithoutAuthenticate() {
        Long randTId = tariffSteps.getTariffs().chooseRandomTariffId();
        tariffSteps.deleteTariff(randTId, "")
                .should(Conditions.hasStatusCode(401))
                .should(Conditions.hasError("err", "token is required"));
    }

    @Test
    @DisplayName("Удаление тарифа обычным пользователем")
    public void deleteTariffUserTest() {
        String token = userSteps.authenticateUser(UserFactory.authUser()).asJwt();
        Long randTId = tariffSteps.getTariffs().chooseRandomTariffId();
        tariffSteps.deleteTariff(randTId, token)
                .should(Conditions.hasStatusCode(403))
                .should(Conditions.hasError("err", "Forbidden for this user"));
    }
}
