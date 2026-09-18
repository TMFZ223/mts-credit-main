package asserts;


import asserts.conditions.*;
import com.example.creditservice.model.request.TariffDTO;

public class Conditions {
    public static TokenCondition hasNotEmptyToken() {
        return new TokenCondition();
    }

    public static ErrorCondition hasError(String expectedCode, String expectedError) {
        return new ErrorCondition(expectedCode, expectedError);
    }

    public static TariffInformationCondition hasCorrectTariffList() {
        return new TariffInformationCondition();
    }

    public static CreatedTariffCondition hasCreatedTariffInList(TariffDTO tariffDTO) {
        return new CreatedTariffCondition(tariffDTO);
    }

    public static StatusCodeCondition hasStatusCode(Integer expectedStatus) {
        return new StatusCodeCondition(expectedStatus);
    }
}
