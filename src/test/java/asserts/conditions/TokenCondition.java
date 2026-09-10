package asserts.conditions;

import asserts.Condition;
import com.example.creditservice.model.response.AuthenticationResponse;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;

public class TokenCondition implements Condition {

    @Override
    public void check(ValidatableResponse response) {
        AuthenticationResponse authenticationResponseBody = response.extract().jsonPath().getObject("", AuthenticationResponse.class);
        Assertions.assertNotEquals("", authenticationResponseBody.getToken());
    }
}
