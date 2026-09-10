package steps;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import utils.PropertyReader;

abstract class BaseSteps {
    private static final String BASE_URI = PropertyReader.getProperty("api.base.url");

    public RequestSpecification givenBase() {
        return RestAssured.given().spec(getRequestSpecification());
    }

    private RequestSpecification getRequestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }
}
