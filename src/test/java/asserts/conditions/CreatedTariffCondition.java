package asserts.conditions;

import asserts.Condition;
import com.example.creditservice.model.request.TariffDTO;
import com.example.creditservice.model.response.DataResponseTariff;
import com.example.creditservice.model.tariff.Tariff;
import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;

import java.util.List;

@RequiredArgsConstructor
public class CreatedTariffCondition implements Condition {
    private final TariffDTO tariffDTO;

    @Override
    public void check(ValidatableResponse response) {
        DataResponseTariff dataResponseTariff = response.extract().jsonPath().getObject("data", DataResponseTariff.class);
        List<Tariff> tariffs = dataResponseTariff.getTariffs();
        Assertions.assertTrue(tariffs.stream()
                .anyMatch(tariff ->
                        tariffDTO.getType().equals(tariff.getType()) &&
                                tariffDTO.getInterestRate().equals(tariff.getInterestRate())
                )
        );
    }
}