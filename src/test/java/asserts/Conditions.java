package asserts;


import asserts.conditions.ErrorCondition;
import asserts.conditions.TokenCondition;
import asserts.conditions.StatusCodeCondition;

public class Conditions {
    public static TokenCondition hasNotEmptyToken() {
        return new TokenCondition();
    }

    public static ErrorCondition hasError(String expectedCode, String expectedError) {
        return new ErrorCondition(expectedCode, expectedError);
    }

    public static StatusCodeCondition hasStatusCode(Integer expectedStatus) {
        return new StatusCodeCondition(expectedStatus);
    }
}
