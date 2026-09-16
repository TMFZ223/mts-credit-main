package factories;

import com.example.creditservice.model.request.TariffDTO;
import com.example.creditservice.model.tariff.Tariff;
import com.example.creditservice.repository.TariffRepository;
import com.example.creditservice.repository.impl.TariffRepositoryImpl;
import net.datafaker.Faker;

import java.util.List;

public class TariffFactory {
    private static final Faker faker= new Faker();
    private static final String[] types = {"kredit", "ipoteka", "avtokredit", "educationKredit"};

    private static String generateTariffType() {
        return faker.options().option(types);
    }

    private static String generateInterestRate() {
        return faker.number().numberBetween(5, 30) + "%";
    }
    public static TariffDTO addTariff() {
        return new TariffDTO(generateTariffType(), generateInterestRate());
    }

    public static TariffDTO addTariffWithEmptyType() {
        return new TariffDTO("", generateInterestRate());
    }

    public static TariffDTO addTariffWithNullType() {
        return new TariffDTO(null, generateInterestRate());
    }

    public static TariffDTO addTariffWithEmptyInterestRate() {
        return new TariffDTO(generateTariffType(), "");
    }

    public static TariffDTO addTariffWithNullInterestRate() {
        return new TariffDTO(generateTariffType(), null);
    }
}