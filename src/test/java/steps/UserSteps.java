package steps;

import com.example.creditservice.model.request.AuthenticationRequest;
import asserts.AssertableResponse;
import com.example.creditservice.model.request.RegisterRequest;

public class UserSteps extends BaseSteps {

    public AssertableResponse register(RegisterRequest registerRequestBody) {
        return new AssertableResponse(givenBase()
                .body(registerRequestBody)
                .when()
                .post("user/register")
                .then()
                .log().all());
    }

    public AssertableResponse authenticateUser(AuthenticationRequest authenticationRequestBody) {
        return new AssertableResponse(givenBase()
                .body(authenticationRequestBody)
                .when()
                .post("user/authenticate")
                .then()
                .log().all());
    }
}
