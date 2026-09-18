package factories;

import com.example.creditservice.model.request.AuthenticationRequest;
import com.example.creditservice.model.request.RegisterRequest;
import net.datafaker.Faker;
import utils.PropertyReader;

public class UserFactory {
    private static final Faker faker = new Faker();
    private static final String CYRILLIC = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";

    private static char randomCyrillic() {
        return CYRILLIC.charAt(faker.random().nextInt(CYRILLIC.length()));
    }

    private static String generateValidEmail() {
        return faker.internet().emailAddress();
    }

    private static String generateEmailWithoutAt() {
        return faker.internet().emailAddress()
                .replace("@", "");
    }

    private static String generateEmailWithoutLocalPart() {
        return "@" + faker.internet().domainName();
    }

    private static String generateEmailWithoutDomain() {
        return faker.name().malefirstName() + "@";
    }

    private static String generateEmailWithoutAtAndDot() {
        return faker.internet().emailAddress()
                .replace("@", "")
                .replace(".", "");
    }

    private static String generateEmailWithSpace() {
        return faker.internet().emailAddress()
                .replace("@", " @");
    }

    private static String generateFirstName() {
        return faker.name().firstName();
    }

    private static String generateLastName() {
        return faker.name().lastName();
    }

    private static String generatePasswordWithinAllowedLength() {
        return faker.internet().password(25, 25);
    }

    private static String generate7CharactersPassword() {
        return faker.internet().password(7, 7);
    }

    private static String generate8CharactersPassword() {
        return faker.internet().password(8, 8);
    }

    private static String generate9CharactersPassword() {
        return faker.internet().password(9, 9);
    }

    private static String generate24CharactersPassword() {
        return faker.internet().password(24, 24);
    }

    private static String generate25CharactersPassword() {
        return faker.internet().password(25, 25);
    }

    private static String generate26CharactersPassword() {
        return faker.internet().password(26, 26);
    }

    private static String generatePasswordWithSpaceAtTheBeginning() {
        return " " + faker.internet().password(7, 24);
    }

    private static String generatePasswordWithSpaceAtTheEnd() {
        return faker.internet().password(7, 24) + " ";
    }

    private static String generatePasswordWithSpaceAtTheMiddle() {
        String password = faker.internet().password(8, 25);
        int length = password.length();
        int position = (length - 1) / 2;
        return password.substring(0, position) + " " + password.substring(position + 1);
    }

    private static String generatePasswordWithCyrillicAtTheBeginning() {
        return randomCyrillic() + faker.internet().password(7, 24);
    }

    private static String generatePasswordWithCyrillicAtTheEnd() {
        return faker.internet().password(7, 24) + randomCyrillic();
    }

    private static String generatePasswordWithCyrillicAtTheMiddle() {
        String password = faker.internet().password(8, 25);
        int length = password.length();
        int position = (length - 1) / 2;
        char cyrillic = randomCyrillic();
        return password.substring(0, position) + cyrillic + password.substring(position + 1);
    }

    public static AuthenticationRequest authAdmin() {
        return AuthenticationRequest.builder()
                .email(PropertyReader.getProperty("admin.email"))
                .password(PropertyReader.getProperty("admin.password"))
                .build();
    }

    public static AuthenticationRequest authUser() {
        return AuthenticationRequest.builder()
                .email(PropertyReader.getProperty("user.email"))
                .password(PropertyReader.getProperty("user.password"))
                .build();
    }

    public static AuthenticationRequest authWithRegisterData(RegisterRequest registerRequestData) {
        return AuthenticationRequest.builder()
                .email(registerRequestData.getEmail())
                .password(registerRequestData.getPassword())
                .build();
    }

    public static AuthenticationRequest authWithEmptyEmail() {
        return AuthenticationRequest.builder()
                .email("")
                .password(PropertyReader.getProperty("admin.password"))
                .build();
    }

    public static AuthenticationRequest authWithNullEmail() {
        return AuthenticationRequest.builder()
                .email(null)
                .password(PropertyReader.getProperty("admin.password"))
                .build();
    }

    public static AuthenticationRequest authWithEmptyPassword() {
        return AuthenticationRequest.builder()
                .email(PropertyReader.getProperty("admin.email"))
                .password("")
                .build();
    }

    public static AuthenticationRequest authWithNullPassword() {
        return AuthenticationRequest.builder()
                .email(PropertyReader.getProperty("admin.email"))
                .password(null)
                .build();
    }

    public static AuthenticationRequest authenticateWithUnknownEmail() {
        return AuthenticationRequest.builder()
                .email(generateValidEmail())
                .password(PropertyReader.getProperty("admin.password"))
                .build();
    }

    public static RegisterRequest correctRegister() {
        return RegisterRequest.builder()
                .firstname(generateFirstName())
                .lastname(generateLastName())
                .email(generateValidEmail())
                .password(generatePasswordWithinAllowedLength())
                .build();
    }

    public static RegisterRequest registerWithEmptyFirstName() {
        return RegisterRequest.builder()
                .firstname("")
                .lastname(generateLastName())
                .email(generateValidEmail())
                .password(generatePasswordWithinAllowedLength())
                .build();
    }

    public static RegisterRequest registerWithNullFirstName() {
        return RegisterRequest.builder()
                .firstname(null)
                .lastname(generateLastName())
                .email(generateValidEmail())
                .password(generatePasswordWithinAllowedLength())
                .build();
    }

    public static RegisterRequest registerWithEmptyLastName() {
        return RegisterRequest.builder()
                .firstname(generateFirstName())
                .lastname("")
                .email(generateValidEmail())
                .password(generatePasswordWithinAllowedLength())
                .build();
    }

    public static RegisterRequest registerWithNullLastName() {
        return RegisterRequest.builder()
                .firstname(generateFirstName())
                .lastname(null)
                .email(generateValidEmail())
                .password(generatePasswordWithinAllowedLength())
                .build();
    }

    public static RegisterRequest registerWithEmptyEmail() {
        return RegisterRequest.builder()
                .firstname(generateFirstName())
                .lastname(generateLastName())
                .email("")
                .password(generatePasswordWithinAllowedLength())
                .build();
    }

    public static RegisterRequest registerWithNullEmail() {
        return RegisterRequest.builder()
                .firstname(generateFirstName())
                .lastname(generateLastName())
                .email(null)
                .password(generatePasswordWithinAllowedLength())
                .build();
    }

    public static RegisterRequest registerWithoutAtInEmail() {
        return RegisterRequest.builder()
                .firstname(generateFirstName())
                .lastname(generateLastName())
                .email(generateEmailWithoutAt())
                .password(generatePasswordWithinAllowedLength())
                .build();
    }

    public static RegisterRequest registerWithoutLocalPartInEmail() {
        return RegisterRequest.builder()
                .firstname(generateFirstName())
                .lastname(generateLastName())
                .email(generateEmailWithoutLocalPart())
                .password(generatePasswordWithinAllowedLength())
                .build();
    }

    public static RegisterRequest registerWithoutDomainInEmail() {
        return RegisterRequest.builder()
                .firstname(generateFirstName())
                .lastname(generateLastName())
                .email(generateEmailWithoutDomain())
                .password(generatePasswordWithinAllowedLength())
                .build();
    }

    public static RegisterRequest registerWithoutAtAndDotInEmail() {
        return RegisterRequest.builder()
                .firstname(generateFirstName())
                .lastname(generateLastName())
                .email(generateEmailWithoutAtAndDot())
                .password(generatePasswordWithinAllowedLength())
                .build();
    }

    public static RegisterRequest registerWithSpaceInEmail() {
        return RegisterRequest.builder()
                .firstname(generateFirstName())
                .lastname(generateLastName())
                .email(generateEmailWithSpace())
                .password(generatePasswordWithinAllowedLength())
                .build();
    }

    public static RegisterRequest registerWithExistingEmailInDB() {
        return RegisterRequest.builder()
                .firstname(generateFirstName())
                .lastname(generateLastName())
                .email(PropertyReader.getProperty("admin.email"))
                .password(generatePasswordWithinAllowedLength())
                .build();
    }

    public static RegisterRequest registerWithExistingEmailInDBUperCase() {
        return RegisterRequest.builder()
                .firstname(generateFirstName())
                .lastname(generateLastName())
                .email(PropertyReader.getProperty("admin.email").toUpperCase())
                .password(generatePasswordWithinAllowedLength())
                .build();
    }

    public static RegisterRequest registerWithEmptyPassword() {
        return RegisterRequest.builder()
                .firstname(generateFirstName())
                .lastname(generateLastName())
                .email(generateValidEmail())
                .password("")
                .build();
    }

    public static RegisterRequest registerWithNullPassword() {
        return RegisterRequest.builder()
                .firstname(generateFirstName())
                .lastname(generateLastName())
                .email(generateValidEmail())
                .password(null)
                .build();
    }

    public static RegisterRequest registerWith7CharactersInPassword() {
        return RegisterRequest.builder()
                .firstname(generateFirstName())
                .lastname(generateLastName())
                .email(generateValidEmail())
                .password(generate7CharactersPassword())
                .build();
    }

    public static RegisterRequest registerWith8CharactersInPassword() {
        return RegisterRequest.builder()
                .firstname(generateFirstName())
                .lastname(generateLastName())
                .email(generateValidEmail())
                .password(generate8CharactersPassword())
                .build();
    }

    public static RegisterRequest registerWith9CharactersInPassword() {
        return RegisterRequest.builder()
                .firstname(generateFirstName())
                .lastname(generateLastName())
                .email(generateValidEmail())
                .password(generate9CharactersPassword())
                .build();
    }

    public static RegisterRequest registerWith24CharactersInPassword() {
        return RegisterRequest.builder()
                .firstname(generateFirstName())
                .lastname(generateLastName())
                .email(generateValidEmail())
                .password(generate24CharactersPassword())
                .build();
    }

    public static RegisterRequest registerWith25CharactersInPassword() {
        return RegisterRequest.builder()
                .firstname(generateFirstName())
                .lastname(generateLastName())
                .email(generateValidEmail())
                .password(generate25CharactersPassword())
                .build();
    }

    public static RegisterRequest registerWith26CharactersInPassword() {
        return RegisterRequest.builder()
                .firstname(generateFirstName())
                .lastname(generateLastName())
                .email(generateValidEmail())
                .password(generate26CharactersPassword())
                .build();
    }

    public static RegisterRequest registerWithSpaceInPasswordAtTheBeginning() {
        return RegisterRequest.builder()
                .firstname(generateFirstName())
                .lastname(generateLastName())
                .email(generateValidEmail())
                .password(generatePasswordWithSpaceAtTheBeginning())
                .build();
    }

    public static RegisterRequest registerWithSpaceInPasswordAtTheEnd() {
        return RegisterRequest.builder()
                .firstname(generateFirstName())
                .lastname(generateLastName())
                .email(generateValidEmail())
                .password(generatePasswordWithSpaceAtTheEnd())
                .build();
    }

    public static RegisterRequest registerWithSpaceInPasswordAtTheMiddle() {
        return RegisterRequest.builder()
                .firstname(generateFirstName())
                .lastname(generateLastName())
                .email(generateValidEmail())
                .password(generatePasswordWithSpaceAtTheMiddle())
                .build();
    }

    public static RegisterRequest registerWithCyrillicInPasswordAtTheBeginning() {
        return RegisterRequest.builder()
                .firstname(generateFirstName())
                .lastname(generateLastName())
                .email(generateValidEmail())
                .password(generatePasswordWithCyrillicAtTheBeginning())
                .build();
    }

    public static RegisterRequest registerWithCyrillicInPasswordAtTheEnd() {
        return RegisterRequest.builder()
                .firstname(generateFirstName())
                .lastname(generateLastName())
                .email(generateValidEmail())
                .password(generatePasswordWithCyrillicAtTheEnd())
                .build();
    }

    public static RegisterRequest registerWithCyrillicInPasswordAtTheMiddle() {
        return RegisterRequest.builder()
                .firstname(generateFirstName())
                .lastname(generateLastName())
                .email(generateValidEmail())
                .password(generatePasswordWithCyrillicAtTheMiddle())
                .build();
    }
}