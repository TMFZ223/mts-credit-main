package asserts.conditions;

import asserts.Condition;
import com.example.creditservice.model.response.DataResponseTariff;
import com.example.creditservice.model.tariff.Tariff;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class TariffInformationCondition implements Condition {

    @Override
    public void check(ValidatableResponse response) {
        DataResponseTariff dataResponseTariff = response.extract().jsonPath().getObject("data", DataResponseTariff.class);
        List<Tariff> tariffs = dataResponseTariff.getTariffs();
        int actualTariffListSize = tariffs.size();
        Assertions.assertTrue(actualTariffListSize > 0, "actual size of list: " + actualTariffListSize);
        for (Tariff tariff : tariffs) {
            Assertions.assertAll(
                    () -> Assertions.assertNotEquals("", tariff.getType()),
                    () -> Assertions.assertNotEquals("", tariff.getInterestRate())
            );
        }
    }
}
