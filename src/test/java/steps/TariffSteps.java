package steps;

import asserts.AssertableResponse;
import com.example.creditservice.model.request.TariffDTO;

public class TariffSteps extends BaseSteps {

    public AssertableResponse getTariffs() {
        return new AssertableResponse(givenBase()
                .when()
                .get("loan-service/getTariffs")
                .then()
                .log().all());
    }

    public AssertableResponse addTariff(TariffDTO tariff, String token) {
        return new AssertableResponse(givenBase()
                .body(tariff)
                .header("Authorization", "Bearer " + token)
                .when()
                .post("loan-service/addTariff")
                .then()
                .log().all());
    }

    public AssertableResponse deleteTariff(Long tariffId, String token) {
        return new AssertableResponse(givenBase()
                .queryParam("id", tariffId)
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("loan-service/deleteTariff")
                .then()
                .log().all());
    }
}
