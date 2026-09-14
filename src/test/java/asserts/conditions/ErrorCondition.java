package asserts.conditions;

import asserts.Condition;
import com.example.creditservice.model.error.CustomError;
import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;

@RequiredArgsConstructor
public class ErrorCondition implements Condition {
    private final String expectedErrorCode;
    private final String expectedMessage;

    @Override
    public void check(ValidatableResponse response) {
        CustomError customErrorResponseBody = response.extract().jsonPath().getObject("error", CustomError.class);
        Assertions.assertAll(
                () -> Assertions.assertEquals(expectedErrorCode, customErrorResponseBody.getCode()),
                () -> Assertions.assertEquals(expectedMessage, customErrorResponseBody.getMessage())
        );
    }
}