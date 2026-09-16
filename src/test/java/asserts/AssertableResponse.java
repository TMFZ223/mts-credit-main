package asserts;

import com.example.creditservice.model.response.DataResponseTariff;
import com.example.creditservice.model.tariff.Tariff;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class AssertableResponse {
    private final ValidatableResponse response;

    public AssertableResponse should(Condition condition) {
        condition.check(response);
        return this;
    }

    public String asJwt() {
        return response.extract().jsonPath().getString("token");
    }

    public Long chooseRandomTariffId() {
        List<Tariff> tariffs = response.extract().jsonPath().getList("data.tariffs", Tariff.class);
        Random random = new Random();
        long rIndex = random.nextLong(tariffs.size());
        return tariffs.get((int) rIndex).getId();
    }
}
